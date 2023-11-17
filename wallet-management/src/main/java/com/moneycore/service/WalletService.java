package com.moneycore.service;

import java.util.List;

import javax.validation.Valid;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;

import com.moneycore.bean.SearchWallet;
import com.moneycore.bean.TopUpWalletModel;
import com.moneycore.bean.WalletTransactionModel;
import com.moneycore.bean.WalletUpdateInfo;
import com.moneycore.entity.Wallet;
import com.moneycore.entity.WalletBalance;
import com.moneycore.model.ResponseModel;
import com.moneycore.model.SearchWalletResponse;

public interface WalletService {

    List<Wallet> findByWalletId(String walletId);

    Wallet addWallet(@Valid Wallet wallet);

    List<Wallet> findById(int clientCode, String institutionCode);

    Wallet findWallet(String walletId);

    SearchWalletResponse getWalletDetails(SearchWallet searchWallet);

    public String getWalletIdByClientCode(int clientCode);

    public Wallet updateWallet(Wallet wallet, WalletUpdateInfo walletUpdateInfo);

    Wallet findWalletId(String walletId);

    ResponseEntity<ResponseModel> checkTransferAndPay(WalletTransactionModel model, WalletBalance sender,
                                                      WalletBalance receiver, boolean isPay) throws JsonProcessingException;

    ResponseEntity<ResponseModel> checkAdjustBalance(TopUpWalletModel topUpWalletModel, String operation,
                                                     String type, WalletBalance walBal) throws JsonProcessingException;

    Wallet updateWalletStatus(Wallet wallet, String statusCode);

    Wallet updateWallet(Wallet wallet);

    public void profileBalance();

    public void walletType();

    public void delete(String walletId);

}
