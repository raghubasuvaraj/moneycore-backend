package com.moneycore.serviceimpl;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.moneycore.entity.BulkTnxImportLines;
import com.moneycore.entity.StatusList;
import com.moneycore.entity.Wallet;
import com.moneycore.entity.WalletBalance;
import com.moneycore.model.ResponseModel;
import com.moneycore.repository.BulkTnxImportLinesRepository;
import com.moneycore.repository.WalletRepository;
import com.moneycore.service.TransactionListService;
import com.moneycore.service.WalletBalanceService;
import com.moneycore.service.WalletTransaction121Service;
import com.moneycore.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.List;


@Service
public class WalletTransaction121ServiceImpl implements WalletTransaction121Service {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    BulkTnxImportLinesRepository tnxImportLinesRepository;
    @Autowired
    WalletRepository walletRepository;
    @Autowired
    WalletBalanceService walletBalanceService;
    @Autowired
    TransactionListService transactionListService;
    @PersistenceContext
    private EntityManager em;


    @Override
    public Wallet findWallet(String walletId) {
        Wallet wallet = null;
        wallet = (Wallet) em.createQuery("FROM Wallet where wallet_id='" + walletId + "' and is_deleted=false").getSingleResult();
        return wallet;
    }

    @Override
    public Wallet findWalletByPhone(String phoneNum, String institutionCode) {
        Wallet wallet = null;
        wallet = (Wallet) em.createQuery("FROM Wallet where pr_phone_1='" + phoneNum + "' and institution_code='"+institutionCode+"'").getSingleResult();
        return wallet;
    }

    private StatusList getStatus(String statusCode) {
        ResponseModel responseModel = null;
        StatusList statusList = null;
        String url = CommonUtil.getApplicationBaseUrl() + "/api/usermanagement/internal/user/status/" + statusCode;
        ResponseEntity<ResponseModel> responseEntity = CommonUtil.getDataFromOtherService(restTemplate, url, null);
        if (responseEntity.getStatusCode().equals(HttpStatus.ACCEPTED)) {
            responseModel = responseEntity.getBody();
            statusList = CommonUtil.convertToOriginalObject(responseModel.getResult(), StatusList.class);
        }
        return statusList;
    }


    public void updateBalanceImportLine(BulkTnxImportLines tnxImportLinesInst, Double accBalance){
        tnxImportLinesInst.setAccBalance(accBalance);
        tnxImportLinesRepository.saveAndFlush(tnxImportLinesInst);
    }
    public void updateValidationError(BulkTnxImportLines tnxImportLinesInst, String errorMsg){
        tnxImportLinesInst.setErrorMessage(errorMsg);
        tnxImportLinesRepository.saveAndFlush(tnxImportLinesInst);
    }

    @Override
    public String doTransaction(Long importLineId) throws JsonProcessingException {

        BulkTnxImportLines tnxImportLines =  tnxImportLinesRepository.findByPrimaryId(importLineId);
        if (tnxImportLines == null){
            return null;
        }

        // validation-1
        Wallet walletInst = findWalletByPhone(tnxImportLines.getPhoneNumber(), tnxImportLines.getInstitutionCode());
        if(walletInst == null){
            updateValidationError(tnxImportLines, "Wallet account not found by phone number");
            return null;
        }

        // validation-2
        String walletId = walletInst.getWalletId();
        WalletBalance walBal = walletBalanceService.findWalletByWalletId(walletId);
        if(walBal == null){
            updateValidationError(tnxImportLines, "No wallet balance associate with this wallet id");
            return null;
        }

        // set balance in import lines
        updateBalanceImportLine(tnxImportLines, walBal.getBalance());


        // validation-3
        String tnxCurrency = tnxImportLines.getCurrency();
        String accCurrency =  walBal.getWalletId().getControlProfile().getCurrencyCode();
        if (!accCurrency.contains(tnxCurrency)) {
            updateValidationError(tnxImportLines, "Your currency is not valid with associate domain control, domain currency is: "+accCurrency);
            return null;
        }

        // validation-4
        String balCurrencyCode = walBal.getCurrencyCode().getCurrencyCode();
        if (!balCurrencyCode.equalsIgnoreCase(tnxCurrency)) {
            updateValidationError(tnxImportLines, "Your transaction currency is not valid with associate balance currency, balance currency is: "+balCurrencyCode);
            return null;
        }

        // validation-5
        String tnxTransactionCode = tnxImportLines.getTransactionCode();
        List<String> accTnxCodeList = Arrays.asList(walBal.getWalletId().getControlProfile().getTransactionCode().split(","));
        if (!accTnxCodeList.contains(tnxTransactionCode)) {
            updateValidationError(tnxImportLines, "Your TransactionCode is not valid with associate domain control, domain TransactionCode is: "+accTnxCodeList.toString());
            return null;
        }

        // Activate account
        if(!walBal.getClientCode().getIsMerchant() && walBal.getClientCode().getStatusCode().getStatusCode().equals("I")){
            walletBalanceService.activateClient(walBal.getClientCode().getClientCode());
            Wallet wallet = findWallet(walBal.getWalletId().getWalletId());
            walBal.getWalletId().setStatusCode(getStatus("A"));
            walletRepository.save(wallet);
        }



        double oldBalance = 0.0;
        oldBalance = walBal.getBalance();

        char transactionType = 'D';
        String typeOfTransfer = null;
        /*
        "transactionCode": "100",
        "transactionName": "Withdrawal",

        "transactionCode": "101",
        "transactionName": "Cash In",

        "transactionCode": "103",
        "transactionName": "Purchase",

         "transactionCode": "104",
        "transactionName": "Transfer",
         */
        if(tnxTransactionCode.equals("101")){
            typeOfTransfer = "C2W";
            transactionType = 'C';
            walBal.setBalance(walBal.getBalance() + tnxImportLines.getAmount());
        }

        if(tnxTransactionCode.equals("100")){
            if (walBal.getBalance() > 0 && walBal.getBalance() <= tnxImportLines.getAmount()) {
                updateValidationError(tnxImportLines, "Invalid operation. Wallet balance should be greater than or equal to the withdraw amount, current balance: " + walBal.getBalance() + ", withdraw amount: " + tnxImportLines.getAmount());
                return null;
            }
            typeOfTransfer = "D2W";
            transactionType = 'D';
            walBal.setBalance(walBal.getBalance() - tnxImportLines.getAmount());
        }

        walletBalanceService.saveWalletBalance(walBal);
        transactionListService.saveTransaction(
                walBal,
                tnxImportLines.getTransactionCode(),
                tnxImportLines.getReason(),
                tnxImportLines.getAmount(),
                walBal.getCurrencyCode().getCurrencyCode(),
                typeOfTransfer,
                null,
                transactionType,
                (tnxImportLines.getAmount() + oldBalance)
        );

        return "success";

    }



}
