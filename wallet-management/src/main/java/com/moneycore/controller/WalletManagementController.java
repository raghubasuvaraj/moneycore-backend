package com.moneycore.controller;

import java.text.ParseException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.moneycore.bean.*;
import com.moneycore.component.Translator;
import com.moneycore.entity.*;
import com.moneycore.model.*;
import com.moneycore.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


import com.moneycore.repository.CommonRepository;
import com.moneycore.util.CommonUtil;

@RestController
@RequestMapping("/api/walletmanagement")
@Slf4j
public class WalletManagementController {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	WalletService walletService;

	@Autowired
	CommonRepository commoneRepository;

	@Autowired
	WalletAccountLinkService walletAccountLinkService;

	@Autowired
	CurrencyListService currencyListService;

	@Autowired
	WalletBalanceService walletBalanceService;

	@Autowired
	TransactionListService transactionListService;

	@Autowired
	WalletSpaceService walletSpaceService;

	@Autowired
	BeneficiaryService beneficiaryService;

	@Autowired
	ReferenceCodeService referenceCodeService;

	@Autowired
	private FirebaseMessagingService firebaseService;

	@Autowired
	private NotificationServiceDefWall notificationServiceDef;


	@Autowired
	BillService billService;
	@RequestMapping(value = { "/internal/client/wallet/{walletid}" }, method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<ResponseModel> getStatusCode(HttpServletRequest request,
																	 @PathVariable("walletid") String walletId) {
		Wallet wallet = walletService.findWalletId(walletId);
		ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("wallet.view", null), wallet);
		return ResponseEntity.accepted().body(responseModel);
	}

	@RequestMapping(value = {
			"/client/walletid" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getClientWalletId() {
		Client client = getClientByEmail(CommonUtil.getCurrentUserName());
		if (client != null) {
			String walletId = walletService.getWalletIdByClientCode(client.getClientCode());
			client.setWalletId(walletId);
			WalletBalance walletBalance = walletBalanceService.findWalletByWalletId(walletId);
			ClientLoginResponse clientLoginResponse = ClientLoginResponse.createResponse(walletBalance);
			List<ServiceList> serviceLists = walletBalanceService.getServiceListByInstitutionCode(clientLoginResponse.getInstitutionCode());
			clientLoginResponse.setJwtToken(CommonUtil.getJwtTokenFromRequest(CommonUtil.getReuestObjectFromContext()).replaceAll("Bearer","").trim());
			clientLoginResponse.setServiceList(serviceLists);
			ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("wallet.clientdetials", null), clientLoginResponse);
			return ResponseEntity.accepted().body(responseModel);
		} else {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("wallet.emailid", null), null);
			return ResponseEntity.status(200).body(responseModel);
		}
	}

	@RequestMapping(value = {
			"/wallet/{walletid}" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> walletList(@PathVariable("walletid") String walletId) {
		List<WalletBalance> walletBalList = walletBalanceService.findByWalletId(walletId);
		if (walletBalList.isEmpty()) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("wallet.nowalletid", null), null);
			return ResponseEntity.accepted().body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("wallet.walletbal", null), walletBalList);
		return ResponseEntity.accepted().body(responseModel);
	}
	@RequestMapping(value = { "/wallet/balance" }, method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<ResponseModel> swaggerData(HttpServletRequest request) {
		walletBalanceService.walletBalance();
		ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("wallet.walletbal", null), null);
		return ResponseEntity.accepted().body(responseModel);
	}
	@RequestMapping(value = {
			"/wallet/register" }, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> registerWallet(@Valid @RequestBody Wallet wallet) {
		List<Wallet> myWallet = walletService.findById(wallet.getClientCode().getClientCode(),
				wallet.getInstitutionCode().getInstitutionCode());
		if (!myWallet.isEmpty()) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("wallet.walletidregexist", null), null);
			return ResponseEntity.status(200).body(responseModel);
		}
		Client client = getClientByClientId(wallet.getClientCode().getClientCode());
		int code = commoneRepository.generateCode();
		int accountCode = code;
		String wCode = "10" + code;
		wallet.setWalletId("WL" + wCode);
		wallet.setActivationFlag(true);
		wallet.setActivationDate(new Date());
		if (client != null) {
			wallet.setPrEmail(client.getPrEmail());
			wallet.setTitleCode(client.getTitleCode());
			wallet.setFirstName(client.getFirstName());
			wallet.setMiddelName(client.getMiddleName());
			wallet.setFamilyName(client.getFamilyName());
			wallet.setGender(client.getGender());
			wallet.setBirthDate(wallet.getBirthDate());
			wallet.setPrPhone1(client.getPrPhone1());
			wallet.setPrPhone2(client.getPrPhone2());
			wallet.setPrPhone3(client.getPrPhone3());
			wallet.setPrPhone4(client.getPrPhone4());
			wallet.setInstitutionCode(client.getInstitutionList());
			wallet.setBranchCode(client.getBranchList());
			wallet.setNationalCode(client.getNationalityCode());
			wallet.setPrCountryCode(client.getCountryCode());
			wallet.setLanguageCode(client.getLanguageCode());
			wallet.setActivityCode(client.getActivityCode());
			wallet.setCustomerSegmentIndex(client.getCustomerSegment());
			// mobile-end
			if (wallet.getStatusCodeT() == null) {
				wallet.setStatusCode(getStatus("I"));
				wallet.setStatusReason(getStatusReasonCode(client.getInstitutionList().getInstitutionCode(), "I"));
			}
			// backoffice-end
			else {
				wallet.setStatusCode(getStatus(wallet.getStatusCodeT()));
				wallet.setStatusReason(getStatusReasonCode(client.getInstitutionList().getInstitutionCode(), wallet.getStatusCodeT()));
			}
			wallet.setStatusDate(client.getStatusDate());
		}
		wallet.setDateCreate(new Date());
		if (wallet.getControlProfileT() == null) {
			WalletTypeDomainControl walletTypeDomainControl = getDomainControlByWalletType(wallet.getWalletTypeT());
			if (walletTypeDomainControl != null) {
				wallet.setControlProfile(getDomainControl(walletTypeDomainControl.getDomainControlId()));
			}
		}
		if (wallet.getPricingProfileT() == null) {
			WalletTypePricingProfile walletTypePricingProfile = getPricingProfileByWalletType(wallet.getWalletTypeT());
			if (walletTypePricingProfile != null) {
				wallet.setPricingProfile(getPricingProfile(walletTypePricingProfile.getPricingProfileId()));
			}
		}

		Wallet addWallet = walletService.addWallet(wallet);

		AccountTypeList accountTypeList = null;
		if(wallet.getAccountTypeCode() != null) {
			accountTypeList = getAccountTypeByCode(wallet.getAccountTypeCode());
		}
//		accountTypeList.setAccountTypeCode(Integer.toString(accountCode));
//		accountTypeList.setInstitutionCode(addWallet.getInstitutionCode().getInstitutionCode());
//		accountTypeList.setUserCreate(addWallet.getUserCreate());
//		accountTypeList.setDateCreate(new Date());
//		AccountTypeList saveAccountTypeList = accountTypeListService.saveAccountTypeList(accountTypeList);

		WalletAccountLink accountLink = new WalletAccountLink();
		accountLink.setEntityCode("WL");
		accountLink.setEntityId(addWallet);
		accountLink.setAccountInstitutionCode(addWallet.getInstitutionCode());
		accountLink.setAccountBranchCode(addWallet.getBranchCode());
		accountLink.setAccountType(accountTypeList);
		accountLink.setDefaultAccount('Y');
		String original = walletAccountLinkService.getLastAccountNumber();
		if (original == null) {
			original = "0000000000000";
		}
		String incremented = String.format("%0" + original.length() + "d", accountCode);
		accountLink.setAccountNumber(
				addWallet.getInstitutionCode().getInstitutionCode() + addWallet.getBranchCode() + incremented);
		accountLink.setUserCreate(addWallet.getUserCreate());
		accountLink.setDateCreate(new Date());
		WalletAccountLink addWalletAccountLink = walletAccountLinkService.saveWalletAccount(accountLink);

		CurrencyList currencyList = null;
		if (wallet.getCurrencyCode() != null) {
			currencyList = getCurrencyByCode(wallet.getCurrencyCode());
		} else {
			currencyList = getCurrencyByCode(addWalletAccountLink.getEntityId().getClientCode().getCurrencyCode().getCurrencyCode());
		}

		WalletBalance walletBalance = new WalletBalance();
		walletBalance.setAccountNumber(addWalletAccountLink);
		walletBalance.setInstitutionCode(addWalletAccountLink.getAccountInstitutionCode());
		walletBalance.setBranchCode(addWalletAccountLink.getAccountBranchCode());
		walletBalance.setCurrencyCode(currencyList);
		walletBalance.setBalance(0 - wallet.getPricingProfile().getSubscriptionAmount());
		walletBalance.setClientCode(addWallet.getClientCode());
		walletBalance.setWalletId(addWallet);
		walletBalance.setUserCreate(addWallet.getUserCreate());
		walletBalance.setDateCreate(new Date());
		WalletBalance addWalletBalance = walletBalanceService.saveWalletBalance(walletBalance);
		if(addWalletBalance!=null) {
			transactionListService.saveTransaction(addWalletBalance, null, "", wallet.getPricingProfile().getSubscriptionAmount(),
					walletBalance.getCurrencyCode().getCurrencyCode(), "REG2W",
					"Registration Fee", 'D',addWalletBalance.getBalance());


			Note note = new Note();
			note.setSubject("Your wallet account has been created, start using your wallet, cheers!");
			note.setContent("Your wallet account has been created, start using your wallet, cheers!");
			Map<String,String> data = new HashMap();
			data.put("Info","Your wallet account has been created, start using your wallet, cheers!");
			note.setData(data);

			String token=client.getDeviceToken();
			Date sysdate = new Date();
			String insCodevalue=client.getInstitutionList().getInstitutionCode();
			String branchCodeValue="";
			String clientCodeValue=String.valueOf(client.getClientCode());
			String userCreate="";

			if(token!=null){
				try {
					firebaseService.sendNotification(note, token);

					NotoficationService saveService = new NotoficationService("Your wallet account has been created, start using your wallet, cheers!", "Your wallet account has been created, start using your wallet, cheers!",insCodevalue,branchCodeValue,clientCodeValue,userCreate,sysdate,"Success","Yes","");
					notificationServiceDef.saveNotification(saveService);
				} catch (FirebaseMessagingException e) {
					e.printStackTrace();
					NotoficationService saveService = new NotoficationService("Your wallet account has been created, start using your wallet, cheers!", "Your wallet account has been created, start using your wallet, cheers!",insCodevalue,branchCodeValue,clientCodeValue,userCreate,sysdate,"Failed","No",e.getMessage());
					notificationServiceDef.saveNotification(saveService);
				}

			}else{
				NotoficationService saveService = new NotoficationService("Your wallet account has been created, start using your wallet, cheers!", "Your wallet account has been created, start using your wallet, cheers!",insCodevalue,branchCodeValue,clientCodeValue,userCreate,sysdate,"Failed","No","Token IS null");
				notificationServiceDef.saveNotification(saveService);
			}

			ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("wallet.walletreg", null), null);
			return ResponseEntity.accepted().body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("wallet.erroroccured", null), null);
		return ResponseEntity.accepted().body(responseModel);
	}

	/**
	 *
	 * Transfer Type T2W - Top up To Wallet D2W - Debit To Wallet (Only User-end) A2W - Adjust To Wallet
	 */
	@RequestMapping(value = {
			"/wallet/adjustbalance" }, method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> adjustWalletBalance(@Valid @RequestBody TopUpWalletModel topUpWalletModel,
															   @RequestParam(required = false) String operation, @RequestParam(required = false) String type) throws JsonProcessingException {
		WalletBalance walBal = walletBalanceService.findWalletByWalletId(topUpWalletModel.getWalletId());

		Note note = new Note();
		note.setSubject("You have added "+topUpWalletModel.getCurrency()+" "+topUpWalletModel.getAmount()+" in your wallet.");
		note.setContent("You have added "+topUpWalletModel.getCurrency()+" "+topUpWalletModel.getAmount()+" in your wallet.");
		Map<String,String> data = new HashMap();
		data.put("Info","You have added "+topUpWalletModel.getCurrency()+" "+topUpWalletModel.getAmount()+" in your wallet.");
		note.setData(data);

		String token=walBal.getClientCode().getDeviceToken();
		Date sysdate = new Date();
		String insCodevalue=walBal.getClientCode().getInstitutionList().getInstitutionCode();
		String branchCodeValue="";
		String clientCodeValue=String.valueOf(walBal.getClientCode().getClientCode());
		String userCreate="";

		if(token!=null){
			try {
				firebaseService.sendNotification(note, token);

				NotoficationService saveService = new NotoficationService("You have added "+topUpWalletModel.getCurrency()+" "+topUpWalletModel.getAmount()+" in your wallet.", "You have added "+topUpWalletModel.getCurrency()+" "+topUpWalletModel.getAmount()+" in your wallet.",insCodevalue,branchCodeValue,clientCodeValue,userCreate,sysdate,"Success","Yes","");
				notificationServiceDef.saveNotification(saveService);
			} catch (FirebaseMessagingException e) {
				e.printStackTrace();
				NotoficationService saveService = new NotoficationService("You have added "+topUpWalletModel.getCurrency()+" "+topUpWalletModel.getAmount()+" in your wallet.", "You have added "+topUpWalletModel.getCurrency()+" "+topUpWalletModel.getAmount()+" in your wallet.",insCodevalue,branchCodeValue,clientCodeValue,userCreate,sysdate,"Failed","No",e.getMessage());
				notificationServiceDef.saveNotification(saveService);
			}

		}else{
			NotoficationService saveService = new NotoficationService("You have added "+topUpWalletModel.getCurrency()+" "+topUpWalletModel.getAmount()+" in your wallet.", "You have added "+topUpWalletModel.getCurrency()+" "+topUpWalletModel.getAmount()+" in your wallet.",insCodevalue,branchCodeValue,clientCodeValue,userCreate,sysdate,"Failed","No","Token IS null");
			notificationServiceDef.saveNotification(saveService);
		}
		ResponseEntity<ResponseModel> responseModel = walletService.checkAdjustBalance(topUpWalletModel, operation, type, walBal);
		return responseModel;
	}

	/**
	 *
	 * Transfer Type : W2W - Wallet To Wallet R2W - Received To Wallet
	 */
	@RequestMapping(value = {
			"/wallet/walletmoneytranfer" }, method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> walletTransfer(@Valid @RequestBody WalletTransactionModel model) throws JsonProcessingException {
		if (model.getSenderWalletId().equalsIgnoreCase(model.getReceiverWalletId())) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("wallet.tansgfer", null), null);
			return ResponseEntity.accepted().body(responseModel);
		}
		WalletBalance sender = walletBalanceService.find(model.getSenderWalletId());
		WalletBalance receiver = walletBalanceService.find(model.getReceiverWalletId());
		ResponseEntity<ResponseModel> responseModel = walletService.checkTransferAndPay(model, sender, receiver, false );
		Note note = new Note();
		note.setSubject("Your transfer of  "+model.getCurrency() +" "+model.getTransactionMoney()+" to  "+model.getReceiverName()+" was sucessful");
		note.setContent("Your transfer of  "+model.getCurrency() +" "+model.getTransactionMoney()+" to  "+model.getReceiverName()+" was sucessful");
		Map<String,String> data = new HashMap();
		data.put("Info","Your transfer of  "+model.getCurrency() +" "+model.getTransactionMoney()+" to  "+model.getReceiverName()+" was sucessful");
		note.setData(data);

		String token=sender.getClientCode().getDeviceToken();
		Date sysdate = new Date();
		String insCodevalue=sender.getClientCode().getInstitutionList().getInstitutionCode();
		String branchCodeValue="";
		String clientCodeValue=String.valueOf(sender.getClientCode().getClientCode());
		String userCreate="";

		if(token!=null){
			try {
				firebaseService.sendNotification(note, token);

				NotoficationService saveService = new NotoficationService("Your transfer of  "+model.getCurrency() +" "+model.getTransactionMoney()+" to  "+model.getReceiverName()+" was sucessful", "Your transfer of  "+model.getCurrency() +" "+model.getTransactionMoney()+" to  "+model.getReceiverName()+" was sucessful",insCodevalue,branchCodeValue,clientCodeValue,userCreate,sysdate,"Success","Yes","");
				notificationServiceDef.saveNotification(saveService);
			} catch (FirebaseMessagingException e) {
				e.printStackTrace();
				NotoficationService saveService = new NotoficationService("Your transfer of  "+model.getCurrency() +" "+model.getTransactionMoney()+" to  "+model.getReceiverName()+" was sucessful", "Your transfer of  "+model.getCurrency() +" "+model.getTransactionMoney()+" to  "+model.getReceiverName()+" was sucessful",insCodevalue,branchCodeValue,clientCodeValue,userCreate,sysdate,"Failed","No",e.getMessage());
				notificationServiceDef.saveNotification(saveService);
			}

		}else{
			NotoficationService saveService = new NotoficationService("Your transfer of  "+model.getCurrency() +" "+model.getTransactionMoney()+" to  "+model.getReceiverName()+" was sucessful", "Your transfer of  "+model.getCurrency() +" "+model.getTransactionMoney()+" to  "+model.getReceiverName()+" was sucessful",insCodevalue,branchCodeValue,clientCodeValue,userCreate,sysdate,"Failed","No","Token IS null");
			notificationServiceDef.saveNotification(saveService);
		}
		return responseModel;
	}

	/**
	 *
	 * Transfer Type : W2W - Wallet To Wallet R2W - Received To Wallet
	 */
	@RequestMapping(value = {
			"/wallet/merchantpay" }, method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> walletTransferToMerchant(@Valid @RequestBody WalletMerchantPayInfo model) throws JsonProcessingException {
		WalletTransactionModel walletTransactionModel = WalletTransactionModel.castWalletMerchantPayInfo(model);
		if (walletTransactionModel.getSenderWalletId().equalsIgnoreCase(walletTransactionModel.getReceiverWalletId())) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("wallet.wallettransfer", null), null);
			return ResponseEntity.accepted().body(responseModel);
		}
		if(model.getReferenceCode() !="" && model.getReferenceCode() !=null) {
			ReferenceCode referenceCode = referenceCodeService.findByReferencecodeId(model.getReferenceCode());
			if (referenceCode == null) {
				ResponseModel responseModel = new ResponseModel(false, "No data present with this ReferenceCode", null);
				return ResponseEntity.accepted().body(responseModel);
			}
		}
		WalletBalance sender = walletBalanceService.find(walletTransactionModel.getSenderWalletId());
		WalletBalance receiver = walletBalanceService.find(walletTransactionModel.getReceiverWalletId());
		if(!receiver.getWalletId().getClientCode().getIsMerchant()) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("wallet.noexistmerchant", null), null);
			return ResponseEntity.accepted().body(responseModel);
		}
		ResponseEntity<ResponseModel> responseModel = walletService.checkTransferAndPay(walletTransactionModel, sender,
				receiver, true);

		if(responseModel.getBody().getMessage().equalsIgnoreCase("Payment Successful") || responseModel.getBody().getMessage().equalsIgnoreCase("Transferred Successful") ){

			if(model.getReferenceCode() !="" && model.getReferenceCode() !=null) {
				ReferenceCode referenceCode = referenceCodeService.findByReferencecodeId(model.getReferenceCode());
				double balanceAmount = referenceCode.getAmountService() - model.getTransactionMoney();
				double pendingAmount = referenceCode.getAmountService() - balanceAmount;
				List<Bill> Billvalue = billService.findByReferencecodeId(model.getReferenceCode());
				Integer Billsequence = (Billvalue.size() + 1);

				if (!Billvalue.isEmpty()) {
					double BillpaySum = Billvalue.stream().mapToDouble(b -> b.getPayAmount()).sum();
					balanceAmount = (referenceCode.getAmountService() - (BillpaySum + model.getTransactionMoney()));
				}
				int code = commoneRepository.generateBillId();
				int accountCode = code;
				String bCode = "10" + code;
				Bill bill = new Bill();
				bill.setBillId("BILL" + bCode);
				bill.setPayAmount(model.getTransactionMoney());
				bill.setPendingAmount(balanceAmount);
				bill.setTotal(referenceCode.getAmountService());
				bill.setReferencecodeId(referenceCode);
				bill.setClientCode(referenceCode.getClientCode());
				bill.setDateCreate(new Date());
				bill.setDateUpdate(new Date());
				bill.setEntityName(referenceCode.getEntityName());
				bill.setService(referenceCode.getService());
				bill.setTransactionCode(referenceCode.getTransactionCode());
				bill.setTransactionFee(referenceCode.getTransactionFee());
				bill.setBillSequence(Billsequence);
				billService.save(bill);

				Note note = new Note();
				note.setSubject("Your payment of  "+model.getCurrency()+" "+model.getTransactionMoney()+" to  "+model.getMerchantName()+" was sucessful");
				note.setContent("Your payment of  "+model.getCurrency()+" "+model.getTransactionMoney()+" to  "+model.getMerchantName()+" was sucessful");
				Map<String,String> data = new HashMap();
				data.put("Info","Your payment of  "+model.getCurrency()+" "+model.getTransactionMoney()+" to  "+model.getMerchantName()+" was sucessful");
				note.setData(data);

				String token=referenceCode.getClientCode().getDeviceToken();
				Date sysdate = new Date();
				String insCodevalue=referenceCode.getClientCode().getInstitutionList().getInstitutionCode();
				String branchCodeValue="";
				String clientCodeValue=String.valueOf(referenceCode.getClientCode().getClientCode());
				String userCreate="";

				if(token!=null){
					try {
						firebaseService.sendNotification(note, token);

						NotoficationService saveService = new NotoficationService("Your payment of  "+model.getCurrency()+" "+model.getTransactionMoney()+" to  "+model.getMerchantName()+" was sucessful", "Your payment of  "+model.getCurrency()+" "+model.getTransactionMoney()+" to  "+model.getMerchantName()+" was sucessful",insCodevalue,branchCodeValue,clientCodeValue,userCreate,sysdate,"Success","Yes","");
						notificationServiceDef.saveNotification(saveService);
					} catch (FirebaseMessagingException e) {
						e.printStackTrace();
						NotoficationService saveService = new NotoficationService("Your payment of  "+model.getCurrency()+" "+model.getTransactionMoney()+" to  "+model.getMerchantName()+" was sucessful", "Your payment of  "+model.getCurrency()+" "+model.getTransactionMoney()+" to  "+model.getMerchantName()+" was sucessful",insCodevalue,branchCodeValue,clientCodeValue,userCreate,sysdate,"Failed","No",e.getMessage());
						notificationServiceDef.saveNotification(saveService);
					}

				}else{
					NotoficationService saveService = new NotoficationService("Your payment of  "+model.getCurrency()+" "+model.getTransactionMoney()+" to  "+model.getMerchantName()+" was sucessful", "Your payment of  "+model.getCurrency()+" "+model.getTransactionMoney()+" to  "+model.getMerchantName()+" was sucessful",insCodevalue,branchCodeValue,clientCodeValue,userCreate,sysdate,"Failed","No","Token IS null");
					notificationServiceDef.saveNotification(saveService);
				}
			}
		}



		return responseModel;
	}
	@RequestMapping(value = {
			"/wallet/balance/adjustbalance" }, method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> adjustWalletBalance(){
        walletBalanceService.adjustBalance();
		ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("wallet.walletstatus", null), null);
		return ResponseEntity.accepted().body(responseModel);
	}
	@RequestMapping(value = {
			"/wallet/balance/{walletid}" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> getReceivedBalance(@PathVariable("walletid") String walletId) {
		WalletTransferAndAddResponse response = new WalletTransferAndAddResponse();
		try {
			WalletBalance walletBalance = walletBalanceService.findWalletByWalletId(walletId);
			if (walletBalance == null) {
				ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("wallet.nowalletid", null), null);
				return ResponseEntity.accepted().body(responseModel);
			}

			WalletSpace walletSpace = walletSpaceService.findSpaceByWalletId(walletBalance.getWalletId().getWalletId());
			if (walletSpace != null) {
				double totalBalance = walletSpaceService.findTotalBalanceOfWalletSpaces(walletBalance.getWalletId().getWalletId());
				response.setTotalBalance(walletBalance.getBalance() + totalBalance);
			} else {
				response.setTotalBalance(walletBalance.getBalance());
			}
			response.setWalletBalance(walletBalance.getBalance());
			response.setTotalReceivedAmount(transactionListService.findTotalReceivedAmount(walletId));
			response.setTotalSendAmount(transactionListService.findTotalSendAmount(walletId));
			response.setCurrency((walletBalance.getCurrencyCode() != null) ? walletBalance.getCurrencyCode().getCurrencyCode() : null);

		}catch(Exception e){
			ResponseModel responseModel = new ResponseModel(false, e.getLocalizedMessage(), null);
			return ResponseEntity.accepted().body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("wallet.walletstatus", null), response);
		return ResponseEntity.accepted().body(responseModel);
	}

	@RequestMapping(value = {
			"/wallet/blockwallet/{walletid}" }, method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> blockWallet(@PathVariable("walletid") String walletId) {
		Wallet wallet = walletService.findWallet(walletId);
		if (wallet == null) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("wallet.nowalletidexist", null), null);
			return ResponseEntity.accepted().body(responseModel);
		}
		if (wallet.getStatusCode().getStatusCode().equalsIgnoreCase("S")) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("wallet.walletexistblocked", null), null);
			return ResponseEntity.accepted().body(responseModel);
		}
		wallet.setStatusCode(getStatus("S"));
		walletService.addWallet(wallet);
		ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("wallet.walletblock", null), null);
		return ResponseEntity.accepted().body(responseModel);
	}

	@RequestMapping(value = {
			"/wallet/unblockwallet/{walletid}" }, method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> unblockWallet(@PathVariable("walletid") String walletId) {
		Wallet wallet = walletService.findWallet(walletId);
		if (wallet == null) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("wallet.nowalletidexist", null), null);
			return ResponseEntity.accepted().body(responseModel);
		}
		if (wallet.getStatusCode().getStatusCode().equalsIgnoreCase("A")) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("wallet.walletexistunblock", null), null);
			return ResponseEntity.accepted().body(responseModel);
		}
		wallet.setStatusCode(getStatus("A"));
		walletService.addWallet(wallet);
		ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("wallet.walletunblocked", null), null);
		return ResponseEntity.accepted().body(responseModel);
	}
	@RequestMapping(value = {
			"/wallet/balance/profilebalance" }, method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> adjustProfileBalance(){
		walletService.profileBalance();
		ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("wallet.walletstatus", null), null);
		return ResponseEntity.accepted().body(responseModel);
	}
	@RequestMapping(value = {
			"/wallet/activatewallet/{walletid}" }, method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> activateWallet(@PathVariable("walletid") String walletId) {
		Wallet wallet = walletService.findWallet(walletId);
		if (wallet == null) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("wallet.nowalletidexist", null), null);
			return ResponseEntity.accepted().body(responseModel);
		}
		if (wallet.getActivationFlag() == null || !wallet.getActivationFlag()) {
			wallet.setActivationFlag(true);
			walletService.addWallet(wallet);
			ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("wallet.walletactived", null), null);
			return ResponseEntity.accepted().body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("wallet.walleteixst", null), null);
		return ResponseEntity.accepted().body(responseModel);
	}

	@RequestMapping(value = {
			"/wallet/search" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> searchWallet(@RequestParam(required = false) String institutionCode,
										  @RequestParam(required = false) String walletId, @RequestParam(defaultValue = "0") int clientCode,
										  @RequestParam(required = false) String firstName, @RequestParam(required = false) String familyName,
										  @RequestParam(required = false) String legalId, @RequestParam(required = false) String statusCode,
										  @RequestParam(required = false) String prPhone1, @RequestParam(defaultValue = "1") int pageNo,
										  @RequestParam(required = false) String statusFromDate, @RequestParam(required = false) String statusToDate,
										  @RequestParam(defaultValue = "10") int pageSize, @RequestParam(required = false) String isMerchant) {
		SearchWallet searchWallet = new SearchWallet(institutionCode, walletId, clientCode, firstName, familyName,
				legalId, statusCode, prPhone1, pageNo, pageSize, isMerchant, statusFromDate, statusToDate);
		return ResponseEntity.status(HttpStatus.OK).body(walletService.getWalletDetails(searchWallet));
	}

	@RequestMapping(value = {
			"/wallet/{walletid}" }, method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> updateWallet(@PathVariable("walletid") String walletId,
														@RequestBody WalletUpdateInfo walletUpdateInfo) {
		Wallet wallet = walletService.findWallet(walletId);
		if (wallet == null) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("wallet.nowalletidexist", null), null);
			return ResponseEntity.accepted().body(responseModel);
		}
		Wallet walletResponse = walletService.updateWallet(wallet, walletUpdateInfo);
		if (walletResponse != null) {
			WalletUpdateInfo infoResponse = WalletUpdateInfo.createResponse(walletResponse);
			ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("wallet.walletupdate", null), infoResponse);
			return ResponseEntity.accepted().body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(false, "Something went wrong...", null);
		return ResponseEntity.accepted().body(responseModel);
	}

	@RequestMapping(value = {
			"/wallet/space" }, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> addPricingProfile(HttpServletRequest request,
															 @RequestBody WalletSpaceInfo walletSpaceInfo) {
		WalletBalance walletBalance = walletBalanceService.findWalletByWalletId(walletSpaceInfo.getWalletId());
		if (walletBalance == null) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("wallet.walletinvalidid", null), null);
			return ResponseEntity.status(200).body(responseModel);
		}
		if (walletBalance.getBalance() < walletSpaceInfo.getAmount()) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("wallet.walletbalancespace", null),
					null);
			return ResponseEntity.status(200).body(responseModel);
		}
		WalletSpace walletSpace = walletSpaceService.save(walletSpaceInfo);
		if (walletSpace != null) {
			walletBalance.setBalance(walletBalance.getBalance() - walletSpaceInfo.getAmount());
			walletBalance = walletBalanceService.saveWalletBalance(walletBalance);
			transactionListService.saveTransaction(walletBalance, walletSpaceInfo.getTransactionCode(), "Transfer to Space", walletSpace.getAmount(),
					walletBalance.getCurrencyCode().getCurrencyCode(), "W2S", walletSpace.getSpaceName(), '\u0000',walletBalance.getBalance());

			Note note = new Note();
			note.setSubject("You have sucessfully created the Space  "+walletSpace.getSpaceName()+"");
			note.setContent("You have sucessfully created the Space  "+walletSpace.getSpaceName()+"");
			Map<String,String> data = new HashMap();
			data.put("Info","You have sucessfully created the Space  "+walletSpace.getSpaceName()+"");
			note.setData(data);

			String token=walletSpace.getWalletId().getClientCode().getDeviceToken();
			Date sysdate = new Date();
			String insCodevalue=walletSpace.getWalletId().getInstitutionCode().getInstitutionCode();
			String branchCodeValue="";
			String clientCodeValue=String.valueOf(walletSpace.getWalletId().getClientCode().getClientCode());
			String userCreate="";

			if(token!=null){
				try {
					firebaseService.sendNotification(note, token);

					NotoficationService saveService = new NotoficationService("You have sucessfully created the Space  "+walletSpace.getSpaceName()+"", "You have sucessfully created the Space  "+walletSpace.getSpaceName()+"",insCodevalue,branchCodeValue,clientCodeValue,userCreate,sysdate,"Success","Yes","");
					notificationServiceDef.saveNotification(saveService);
				} catch (FirebaseMessagingException e) {
					e.printStackTrace();
					NotoficationService saveService = new NotoficationService("You have sucessfully created the Space  "+walletSpace.getSpaceName()+"", "You have sucessfully created the Space  "+walletSpace.getSpaceName()+"",insCodevalue,branchCodeValue,clientCodeValue,userCreate,sysdate,"Failed","No",e.getMessage());
					notificationServiceDef.saveNotification(saveService);
				}

			}else{
				NotoficationService saveService = new NotoficationService("You have sucessfully created the Space  "+walletSpace.getSpaceName()+"", "You have sucessfully created the Space  "+walletSpace.getSpaceName()+"",insCodevalue,branchCodeValue,clientCodeValue,userCreate,sysdate,"Failed","No","Token IS null");
				notificationServiceDef.saveNotification(saveService);
			}

			ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("wallet.walletspacesuccess", null), null);
			return ResponseEntity.status(201).body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(false, "Something went wrong...", null);
		return ResponseEntity.status(500).body(responseModel);
	}

	@RequestMapping(value = {
			"/wallet/space/{institutioncode}/{walletid}" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> getMccList(HttpServletRequest request,
													  @PathVariable("institutioncode") String institutionCode, @PathVariable("walletid") String walletId) {
		List<WalletSpace> walletSpaces = walletSpaceService.fetchSpaces(institutionCode, walletId);
		if (!walletSpaces.isEmpty() && walletSpaces != null) {
			List<WalletSpaceInfoResponse> mccListInfos = WalletSpaceInfoResponse.createResponse(walletSpaces);
			ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("wallet.walletspacelist", null), mccListInfos);
			return ResponseEntity.status(200).body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("wallet.nodatainstitut", null), null);
		return ResponseEntity.status(200).body(responseModel);
	}
	@RequestMapping(value = {
			"/wallet/balance/typebalance/{walletId}" }, method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> walletType(@PathVariable("walletId") String walletId){
		try {
			walletService.walletType();
		}catch(Exception e){
        log.debug("wallet",e.getMessage());
		}
		ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("wallet.walletstatus", null), null);
		return ResponseEntity.accepted().body(responseModel);
	}
	@RequestMapping(value = {
			"/wallet/space/{institutioncode}/{walletid}/{spaceid}" }, method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> deleteAddress(@PathVariable("institutioncode") String institutionCode,
														 @PathVariable("walletid") String walletId, @PathVariable("spaceid") String spaceId) {
		WalletSpace walletSpace = walletSpaceService.findSpaceIfExist(institutionCode, walletId, spaceId);
		if (walletSpace == null) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("wallet.walletspaceno", null), null);
			return ResponseEntity.status(200).body(responseModel);
		}
		walletSpaceService.delete(spaceId);
		ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("wallet.walletspace", null), null);
		return ResponseEntity.accepted().body(responseModel);
	}

	@RequestMapping(value = {
			"/wallet/space/transfer/{institutioncode}/{walletid}" }, method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> addAmountToSpace(@PathVariable("institutioncode") String institutionCode,
															@PathVariable("walletid") String walletId, @RequestBody WalletSpaceAddAmountInfo walletSpaceAddAmountInfo) {
		if (walletSpaceAddAmountInfo.getAccountNumber() == "" || walletSpaceAddAmountInfo.getAccountNumber() == null) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("wallet.accountno", null), null);
			return ResponseEntity.status(200).body(responseModel);
		}
		WalletBalance walletBalance = walletBalanceService.findWalletByWalletId(walletId);
		if (walletBalance == null) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("wallet.walletidnoexist", null), null);
			return ResponseEntity.status(200).body(responseModel);
		}
		WalletSpace walletSpace = walletSpaceService.findSpace(institutionCode, walletId,
				walletSpaceAddAmountInfo.getAccountNumber());
		if (walletSpace == null) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("wallet.walletspaceno", null), null);
			return ResponseEntity.status(200).body(responseModel);
		}
		if (walletBalance.getBalance() < walletSpaceAddAmountInfo.getAmount()) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("wallet.walletnobalance", null),
					null);
			return ResponseEntity.status(200).body(responseModel);
		}
		walletBalance.setBalance(walletBalance.getBalance() - walletSpaceAddAmountInfo.getAmount());
		walletBalance = walletBalanceService.saveWalletBalance(walletBalance);
		walletSpace.setAmount(walletSpace.getAmount() + walletSpaceAddAmountInfo.getAmount());
		walletSpace = walletSpaceService.updateWalletSpace(walletSpace);
		if (walletBalance != null && walletSpace != null) {
			transactionListService.saveTransaction(walletBalance, walletSpaceAddAmountInfo.getTransactionCode(), "Transfer to Space",
					walletSpaceAddAmountInfo.getAmount(), walletBalance.getCurrencyCode().getCurrencyCode(), "W2S",
					walletSpace.getSpaceName(), '\u0000', walletBalance.getBalance());
			ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("wallet.amtspace", null), null);

			Note note = new Note();
			note.setSubject("Your transfer of  "+ walletBalance.getCurrencyCode().getCurrencyCode() +walletSpaceAddAmountInfo.getAmount()+" to Space "+walletSpace.getSpaceName()+" was sucessful");
			note.setContent("Your transfer of  "+ walletBalance.getCurrencyCode().getCurrencyCode() +walletSpaceAddAmountInfo.getAmount()+" to Space "+walletSpace.getSpaceName()+" was sucessful");
			Map<String,String> data = new HashMap();
			data.put("Info","Your transfer of  "+ walletBalance.getCurrencyCode().getCurrencyCode() +walletSpaceAddAmountInfo.getAmount()+" to Space "+walletSpace.getSpaceName()+" was sucessful");
			note.setData(data);

			String token=walletSpace.getWalletId().getClientCode().getDeviceToken();
			Date sysdate = new Date();
			String insCodevalue=walletSpace.getWalletId().getInstitutionCode().getInstitutionCode();
			String branchCodeValue="";
			String clientCodeValue=String.valueOf(walletSpace.getWalletId().getClientCode().getClientCode());
			String userCreate="";

			if(token!=null){
				try {
					firebaseService.sendNotification(note, token);

					NotoficationService saveService = new NotoficationService("Your transfer of  "+ walletBalance.getCurrencyCode().getCurrencyCode() +walletSpaceAddAmountInfo.getAmount()+" to Space "+walletSpace.getSpaceName()+" was sucessful", "Your transfer of  "+ walletBalance.getCurrencyCode().getCurrencyCode() +walletSpaceAddAmountInfo.getAmount()+" to Space "+walletSpace.getSpaceName()+" was sucessful",insCodevalue,branchCodeValue,clientCodeValue,userCreate,sysdate,"Success","Yes","");
					notificationServiceDef.saveNotification(saveService);
				} catch (FirebaseMessagingException e) {
					e.printStackTrace();
					NotoficationService saveService = new NotoficationService("Your transfer of  "+ walletBalance.getCurrencyCode().getCurrencyCode() +walletSpaceAddAmountInfo.getAmount()+" to Space "+walletSpace.getSpaceName()+" was sucessful", "Your transfer of  "+ walletBalance.getCurrencyCode().getCurrencyCode() +walletSpaceAddAmountInfo.getAmount()+" to Space "+walletSpace.getSpaceName()+" was sucessful",insCodevalue,branchCodeValue,clientCodeValue,userCreate,sysdate,"Failed","No",e.getMessage());
					notificationServiceDef.saveNotification(saveService);
				}

			}else{
				NotoficationService saveService = new NotoficationService("Your transfer of  "+ walletBalance.getCurrencyCode().getCurrencyCode() +walletSpaceAddAmountInfo.getAmount()+" to Space "+walletSpace.getSpaceName()+" was sucessful", "Your transfer of  "+ walletBalance.getCurrencyCode().getCurrencyCode() +walletSpaceAddAmountInfo.getAmount()+" to Space "+walletSpace.getSpaceName()+" was sucessful",insCodevalue,branchCodeValue,clientCodeValue,userCreate,sysdate,"Failed","No","Token IS null");
				notificationServiceDef.saveNotification(saveService);
			}
			return ResponseEntity.status(200).body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("wallet.wentwrong", null), null);
		return ResponseEntity.status(200).body(responseModel);
	}
	@RequestMapping(value = {
			"/space/wallet/transfer" }, method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> spaceToWalletTransfer(@RequestBody WalletSpaceAddAmountInfo walletSpaceAddAmountInfo) {
		if (walletSpaceAddAmountInfo.getAccountNumber() == "" || walletSpaceAddAmountInfo.getAccountNumber() == null) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("wallet.accountno", null), null);
			return ResponseEntity.status(200).body(responseModel);
		}
		WalletBalance walletBalance = walletBalanceService.findWalletByWalletId(walletSpaceAddAmountInfo.getWalletId());
		if (walletBalance == null) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("wallet.walletidnoexist", null), null);
			return ResponseEntity.status(200).body(responseModel);
		}
		WalletSpace walletSpace = walletSpaceService.findSpace(walletSpaceAddAmountInfo.getInstitutionCode(), walletSpaceAddAmountInfo.getWalletId(),
				walletSpaceAddAmountInfo.getAccountNumber());
		if (walletSpace == null) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("wallet.walletspaceno", null), null);
			return ResponseEntity.status(200).body(responseModel);
		}
		if (walletSpace.getAmount() < walletSpaceAddAmountInfo.getAmount()) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("wallet.spaceamtlow", null),
					null);
			return ResponseEntity.status(200).body(responseModel);
		}
		walletSpace.setAmount(walletSpace.getAmount() - walletSpaceAddAmountInfo.getAmount());
		walletSpace = walletSpaceService.updateWalletSpace(walletSpace);
		walletBalance.setBalance(walletBalance.getBalance() + walletSpaceAddAmountInfo.getAmount());
		walletBalance = walletBalanceService.saveWalletBalance(walletBalance);
		if (walletBalance != null && walletSpace != null) {
			transactionListService.saveTransaction(walletBalance, walletSpaceAddAmountInfo.getTransactionCode(), "Transfer to wallet from Space",
					walletSpaceAddAmountInfo.getAmount(), walletBalance.getCurrencyCode().getCurrencyCode(), "S2W",
					walletSpace.getSpaceName(), '\u0000', walletBalance.getBalance());
			ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("wallet.amttransfer", null), null);
			Note note = new Note();
			note.setSubject("Your transfer of  "+ walletBalance.getCurrencyCode().getCurrencyCode() +walletSpaceAddAmountInfo.getAmount()+" to Space "+walletSpace.getSpaceName()+" to wallet was sucessful");
			note.setContent("Your transfer of  "+ walletBalance.getCurrencyCode().getCurrencyCode() +walletSpaceAddAmountInfo.getAmount()+" to Space "+walletSpace.getSpaceName()+" to wallet was sucessful");
			Map<String,String> data = new HashMap();
			data.put("Info","Your transfer of  "+ walletBalance.getCurrencyCode().getCurrencyCode() +walletSpaceAddAmountInfo.getAmount()+" to Space "+walletSpace.getSpaceName()+"  to wallet was sucessful");
			note.setData(data);

			String token=walletSpace.getWalletId().getClientCode().getDeviceToken();
			Date sysdate = new Date();
			String insCodevalue=walletSpace.getWalletId().getInstitutionCode().getInstitutionCode();
			String branchCodeValue="";
			String clientCodeValue=String.valueOf(walletSpace.getWalletId().getClientCode().getClientCode());
			String userCreate="";

			if(token!=null){
				try {
					firebaseService.sendNotification(note, token);

					NotoficationService saveService = new NotoficationService("Your transfer of  "+ walletBalance.getCurrencyCode().getCurrencyCode() +walletSpaceAddAmountInfo.getAmount()+" to Space "+walletSpace.getSpaceName()+"  to wallet was sucessful", "Your transfer of  "+ walletBalance.getCurrencyCode().getCurrencyCode() +walletSpaceAddAmountInfo.getAmount()+" to Space "+walletSpace.getSpaceName()+" to wallet was sucessful",insCodevalue,branchCodeValue,clientCodeValue,userCreate,sysdate,"Success","Yes","");
					notificationServiceDef.saveNotification(saveService);
				} catch (FirebaseMessagingException e) {
					e.printStackTrace();
					NotoficationService saveService = new NotoficationService("Your transfer of  "+ walletBalance.getCurrencyCode().getCurrencyCode() +walletSpaceAddAmountInfo.getAmount()+" to Space "+walletSpace.getSpaceName()+"  to wallet was sucessful", "Your transfer of  "+ walletBalance.getCurrencyCode().getCurrencyCode() +walletSpaceAddAmountInfo.getAmount()+" to Space "+walletSpace.getSpaceName()+"  to wallet was sucessful",insCodevalue,branchCodeValue,clientCodeValue,userCreate,sysdate,"Failed","No",e.getMessage());
					notificationServiceDef.saveNotification(saveService);
				}

			}else{
				NotoficationService saveService = new NotoficationService("Your transfer of  "+ walletBalance.getCurrencyCode().getCurrencyCode() +walletSpaceAddAmountInfo.getAmount()+" to Space "+walletSpace.getSpaceName()+"  to wallet was sucessful", "Your transfer of  "+ walletBalance.getCurrencyCode().getCurrencyCode() +walletSpaceAddAmountInfo.getAmount()+" to Space "+walletSpace.getSpaceName()+"  to wallet was sucessful",insCodevalue,branchCodeValue,clientCodeValue,userCreate,sysdate,"Failed","No","Token IS null");
				notificationServiceDef.saveNotification(saveService);
			}
			return ResponseEntity.status(200).body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("wallet.wentwrong", null), null);
		return ResponseEntity.status(200).body(responseModel);
	}

	@RequestMapping(value = {
			"/wallet/check/{phonenumber}" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> getWalletIdByPhoneNumber(HttpServletRequest request,
																	@PathVariable("phonenumber") String phoneNumber) {
		Client client = getClientByPhoneNumber(phoneNumber);
		if (client == null) {
			ResponseModel responseModel = new ResponseModel(false,
					Translator.toLocale("wallet.nowalletreg", null), null);
			return ResponseEntity.status(200).body(responseModel);
		}
		String walletId = walletService.getWalletIdByClientCode(client.getClientCode());
		if (walletId != null) {
			RegisteredWalletReponse registeredWalletReponse = RegisteredWalletReponse.createResponse(client, walletId);
			ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("wallet.phonnoreg", null), registeredWalletReponse);
			return ResponseEntity.status(200).body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("wallet.nowalletreg", null), null);
		return ResponseEntity.status(200).body(responseModel);
	}

	private Client getClientByClientId(int clientId) {
		ResponseModel responseModel = null;
		Client client = null;
		String url = CommonUtil.getApplicationBaseUrl() + "/api/clientmanagement/internal/client/id/" + clientId;
		ResponseEntity<ResponseModel> responseEntity = CommonUtil.getDataFromOtherService(restTemplate, url,
				CommonUtil.getJwtTokenFromRequest(CommonUtil.getReuestObjectFromContext()));
		if (responseEntity.getStatusCode().equals(HttpStatus.ACCEPTED)) {
			responseModel = responseEntity.getBody();
			client = CommonUtil.convertToOriginalObject(responseModel.getResult(), Client.class);
		}
		return client;
	}

	private Client getClientByEmail(String email) {
		ResponseModel responseModel = null;
		Client client = null;
		String url = CommonUtil.getApplicationBaseUrl() + "/api/clientmanagement/internal/client/" + email;
		ResponseEntity<ResponseModel> responseEntity = CommonUtil.getDataFromOtherService(restTemplate, url, null);
		if (responseEntity.getStatusCode().equals(HttpStatus.ACCEPTED)) {
			responseModel = responseEntity.getBody();
			client = CommonUtil.convertToOriginalObject(responseModel.getResult(), Client.class);
		}
		return client;
	}

	private CurrencyList getCurrencyByCode(String currencyCode) {
		ResponseModel responseModel = null;
		CurrencyList currencyList = null;
		String url = CommonUtil.getApplicationBaseUrl() + "/api/usermanagement/internal/user/currency/code/" + currencyCode;
		ResponseEntity<ResponseModel> responseEntity = CommonUtil.getDataFromOtherService(restTemplate, url, null);
		if (responseEntity.getStatusCode().equals(HttpStatus.ACCEPTED)) {
			responseModel = responseEntity.getBody();
			currencyList = CommonUtil.convertToOriginalObject(responseModel.getResult(), CurrencyList.class);
		}
		return currencyList;
	}

	private AccountTypeList getAccountTypeByCode(String accountTypeCode) {
		ResponseModel responseModel = null;
		AccountTypeList accountTypeList = null;
		String url = CommonUtil.getApplicationBaseUrl() + "/api/usermanagement/internal/user/account/type/" + accountTypeCode;
		ResponseEntity<ResponseModel> responseEntity = CommonUtil.getDataFromOtherService(restTemplate, url, null);
		if (responseEntity.getStatusCode().equals(HttpStatus.ACCEPTED)) {
			responseModel = responseEntity.getBody();
			accountTypeList = CommonUtil.convertToOriginalObject(responseModel.getResult(), AccountTypeList.class);
		}
		return accountTypeList;
	}

	private WalletTypeDomainControl getDomainControlByWalletType(String walletTypeId) {
		ResponseModel responseModel = null;
		WalletTypeDomainControl walletTypeDomainControl = null;
		String url = CommonUtil.getApplicationBaseUrl() + "/api/usermanagement/internal/user/wallet/type/dc/id/"
				+ walletTypeId;
		ResponseEntity<ResponseModel> responseEntity = CommonUtil.getDataFromOtherService(restTemplate, url, null);
		if (responseEntity.getStatusCode().equals(HttpStatus.ACCEPTED)) {
			responseModel = responseEntity.getBody();
			walletTypeDomainControl = CommonUtil.convertToOriginalObject(responseModel.getResult(),
					WalletTypeDomainControl.class);
		}
		return walletTypeDomainControl;
	}

	private WalletTypePricingProfile getPricingProfileByWalletType(String walletTypeId) {
		ResponseModel responseModel = null;
		WalletTypePricingProfile walletTypePricingProfile = null;
		String url = CommonUtil.getApplicationBaseUrl() + "/api/usermanagement/internal/user/wallet/type/pp/id/"
				+ walletTypeId;
		ResponseEntity<ResponseModel> responseEntity = CommonUtil.getDataFromOtherService(restTemplate, url, null);
		if (responseEntity.getStatusCode().equals(HttpStatus.ACCEPTED)) {
			responseModel = responseEntity.getBody();
			walletTypePricingProfile = CommonUtil.convertToOriginalObject(responseModel.getResult(),
					WalletTypePricingProfile.class);
		}
		return walletTypePricingProfile;
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

	private DomainControl getDomainControl(String id) {
		ResponseModel responseModel = null;
		DomainControl domainControl = null;
		String url = CommonUtil.getApplicationBaseUrl() + "/api/usermanagement/internal/user/domaincontrol/" + id;
		ResponseEntity<ResponseModel> responseEntity = CommonUtil.getDataFromOtherService(restTemplate, url, null);
		if (responseEntity.getStatusCode().equals(HttpStatus.ACCEPTED)) {
			responseModel = responseEntity.getBody();
			domainControl = CommonUtil.convertToOriginalObject(responseModel.getResult(), DomainControl.class);
		}
		return domainControl;
	}

	private PricingProfile getPricingProfile(String id) {
		ResponseModel responseModel = null;
		PricingProfile pricingProfile = null;
		String url = CommonUtil.getApplicationBaseUrl() + "/api/usermanagement/internal/user/pricingprofile/" + id;
		ResponseEntity<ResponseModel> responseEntity = CommonUtil.getDataFromOtherService(restTemplate, url, null);
		if (responseEntity.getStatusCode().equals(HttpStatus.ACCEPTED)) {
			responseModel = responseEntity.getBody();
			pricingProfile = CommonUtil.convertToOriginalObject(responseModel.getResult(), PricingProfile.class);
		}
		return pricingProfile;
	}

	private Client getClientByPhoneNumber(String phoneNumber) {
		ResponseModel responseModel = null;
		Client client = null;
		String url = CommonUtil.getApplicationBaseUrl() + "/api/clientmanagement/internal/client/phone/" + phoneNumber;
		ResponseEntity<ResponseModel> responseEntity = CommonUtil.getDataFromOtherService(restTemplate, url, null);
		if (responseEntity.getStatusCode().equals(HttpStatus.ACCEPTED)) {
			responseModel = responseEntity.getBody();
			client = CommonUtil.convertToOriginalObject(responseModel.getResult(), Client.class);
		}
		return client;
	}

	private StatusReasonCode getStatusReasonCode(String institutionCode, String statusCode) {
		ResponseModel responseModel = null;
		StatusReasonCode reasonCode = null;
		String url = CommonUtil.getApplicationBaseUrl() + "/api/usermanagement/internal/user/statusreason/status/" + institutionCode+"/"+ statusCode;
		ResponseEntity<ResponseModel> responseEntity = CommonUtil.getDataFromOtherService(restTemplate, url, null);
		if (responseEntity.getStatusCode().equals(HttpStatus.ACCEPTED)) {
			responseModel = responseEntity.getBody();
			reasonCode = CommonUtil.convertToOriginalObject(responseModel.getResult(), StatusReasonCode.class);
		}
		return reasonCode;
	}


	@RequestMapping(value = {
			"/client/walletid/{username}" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getClientWalletId(@PathVariable("username") String username) {
		Client client = getClientByEmail(username);
		if (client != null) {
			String walletId = walletService.getWalletIdByClientCode(client.getClientCode());
			client.setWalletId(walletId);
			WalletBalance walletBalance = walletBalanceService.findWalletByWalletId(walletId);
			ClientLoginResponse clientLoginResponse = ClientLoginResponse.createResponse(walletBalance);
			List<ServiceList> serviceLists = walletBalanceService.getServiceListByInstitutionCode(clientLoginResponse.getInstitutionCode());
			clientLoginResponse.setServiceList(serviceLists);
			ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("wallet.clientdetials", null), clientLoginResponse);
			return ResponseEntity.accepted().body(responseModel);
		} else {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("wallet.emailid", null), null);
			return ResponseEntity.status(200).body(responseModel);
		}
	}

	@RequestMapping(value = {
			"/wallet/getAllWalletSpace" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAllWalletSpace() {
		List<WalletSpace> walletSpaceList = walletSpaceService.findAll();
		ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("wallet.walletspacedetials", null), walletSpaceList);
		return ResponseEntity.accepted().body(responseModel);
	}

	@RequestMapping(value = {
			"/internal/wallet/unblock/{clientcode}" }, method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> changeClientWalletStatus(HttpServletRequest request, @PathVariable("clientcode") int clientCode) throws ParseException, JsonProcessingException {
		Client client = getClient(clientCode);
		if (client == null) {
			ResponseModel responseModel = new ResponseModel(false, "Invalid Client Code", null);
			return ResponseEntity.status(200).body(responseModel);
		}
		String walletId = walletService.getWalletIdByClientCode(clientCode);
		Wallet wallet = walletService.findWallet(walletId);
		if (wallet == null) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("wallet.walletno", null), null);
			return ResponseEntity.status(200).body(responseModel);
		}else{
			walletBalanceService.activateClient(client.getClientCode());
			walletService.updateWalletStatus(wallet, "A");
			ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("wallet.walletactivet", null), null);
			return ResponseEntity.accepted().body(responseModel);
		}
	}

	private Client getClient(int clientCode) {
		ResponseModel responseModel = null;
		Client cient = null;
		String url = CommonUtil.getApplicationBaseUrl() + "/api/clientmanagement/internal/client/id/" + clientCode;
		ResponseEntity<ResponseModel> responseEntity = CommonUtil.getDataFromOtherService(restTemplate, url, null);
		if (responseEntity.getStatusCode().equals(HttpStatus.ACCEPTED)) {
			responseModel = responseEntity.getBody();
			cient = CommonUtil.convertToOriginalObject(responseModel.getResult(), Client.class);
		}
		return cient;
	}

	@RequestMapping(value = {
			"/wallet/clientCode/{clientCode}" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getWalletByClientCode(@PathVariable("clientCode") int clientCode) {
		String walletId = walletService.getWalletIdByClientCode(clientCode);
		if (walletId != null) {
			ResponseModel responseModel = new ResponseModel(true, "walletId", walletId);
			return ResponseEntity.accepted().body(responseModel);
		} else {
			ResponseModel responseModel = new ResponseModel(false, "Wallete not mapped with Client code", null);
			return ResponseEntity.status(200).body(responseModel);
		}
	}

	@RequestMapping(value = {
			"/wallet/{walletid}/{phoneNo}" }, method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> updateWalletPhoneNo(@PathVariable("walletid") String walletId,
															   @PathVariable String phoneNo) {
		Wallet wallet = walletService.findWallet(walletId);
		if (wallet == null) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("wallet.nowalletidexist", null), null);
			return ResponseEntity.accepted().body(responseModel);
		}
		if(phoneNo !=null){
			wallet.setPrPhone1(phoneNo);
			wallet = walletService.updateWallet(wallet);
		}
		if (wallet != null) {
			ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("wallet.walletupdate", null), wallet);
			return ResponseEntity.accepted().body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(false, "Something went wrong...", null);
		return ResponseEntity.accepted().body(responseModel);
	}




	@RequestMapping(value = {
			"/beneficiary/{walletid}" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> beneficiaryList(@PathVariable("walletid") String walletId) {
		List<BeneficiaryInfo> beneficiaryList = beneficiaryService.findByWalletId(walletId);
		if (beneficiaryList.isEmpty()) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("wallet.nowalletid", null), null);
			return ResponseEntity.accepted().body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(true, "Beneficaiary List", beneficiaryList);
		return ResponseEntity.accepted().body(responseModel);
	}

	@RequestMapping(value = {
			"/beneficiary/check" }, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> beneficiaryList(@Valid @RequestBody Beneficiary beneficiary) {
		Long checkCount = beneficiaryService.checkBeneficiary(beneficiary.getWalletId(),beneficiary.getBeneficiaryWalletId());
		if (checkCount == 0) {
			ResponseModel responseModel = new ResponseModel(false, "Not Addded", null);
			return ResponseEntity.accepted().body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(true, "Already Added", null);
		return ResponseEntity.accepted().body(responseModel);
	}

	@RequestMapping(value = {
			"/beneficiary/register" }, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> registerBeneficiary(@Valid @RequestBody Beneficiary beneficiary) {
		Wallet wallet = walletService.findWallet(beneficiary.getBeneficiaryWalletId());
		if (wallet.getWalletId().isEmpty()) {
			ResponseModel responseModel = new ResponseModel(false, "No data present with this beneficiary wallet id", null);
			return ResponseEntity.accepted().body(responseModel);
		}
		Wallet walletDetails = walletService.findWallet(beneficiary.getWalletId());
		if (walletDetails==null) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("wallet.nowalletid", null), null);
			return ResponseEntity.accepted().body(responseModel);
		}
		beneficiary.setDateCreate(new Date());
		beneficiary.setDateModified(new Date());
		beneficiary.setUserCreate(wallet.getUserCreate());
		beneficiary.setUserModified(wallet.getUserCreate());
		beneficiary.setClientCode(wallet.getClientCode());
		beneficiary.setBranchCode(wallet.getBranchCode());
		beneficiary.setInstitutionCode(wallet.getInstitutionCode());
		beneficiary.setBeneficiaryName(wallet.getFirstName());
		beneficiary.setPhoneNumber(wallet.getPrPhone1());
		beneficiary.setWallet(walletDetails);
		Beneficiary addBeneficiary = beneficiaryService.addBeneficiary(beneficiary);
		ResponseModel responseModel = new ResponseModel(true, "Beneficiary registered successfuly", null);
		return ResponseEntity.accepted().body(responseModel);
	}

	@RequestMapping(value = {
			"/beneficiarydetails/{beneficiaryId}" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> beneficiaryFind(@PathVariable("beneficiaryId") int walletId) {
		Optional<Beneficiary> beneficiary = beneficiaryService.findById(walletId);
		if (!beneficiary.isPresent()) {
			ResponseModel responseModel = new ResponseModel(false, "No data present with this Beneficiary id", null);
			return ResponseEntity.accepted().body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(true, "Beneficiary Details", beneficiary);
		return ResponseEntity.accepted().body(responseModel);
	}

	@RequestMapping(value = {
			"/beneficiary/delete/{beneficiaryId}" }, method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> deleteMenu(@PathVariable("beneficiaryId") int beneficiaryId) {
		Optional<Beneficiary> beneficiary = beneficiaryService.findById(beneficiaryId);
		if (!beneficiary.isPresent()) {
			ResponseModel responseModel = new ResponseModel(false, "No data present with this Beneficiary id", null);
			return ResponseEntity.accepted().body(responseModel);
		}
		beneficiaryService.delete(beneficiaryId);
		ResponseModel responseModel = new ResponseModel(true, "Beneficiary deleted successfully", null);
		return ResponseEntity.accepted().body(responseModel);
	}


	@RequestMapping(value = {"/reference/qrcode" }, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> postQrcode(@RequestBody @Valid RefrenceCodeModel RefrenceCode) {

		Wallet wallet = walletService.findWallet(RefrenceCode.getMerchantWalletID());
		if (wallet==null) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("wallet.nowalletid", null), null);
			return ResponseEntity.accepted().body(responseModel);
		}
		ReferenceCode ReferenceCode=new ReferenceCode();
		int code = commoneRepository.generateReferenceCode();
		int referenceCode = code;
		String rCode = "10" + code;
		ReferenceCode.setReferencecodeId("REF" + rCode);
		ReferenceCode.setAmountService(RefrenceCode.getAmount());
		ReferenceCode.setMerchantWalletId(RefrenceCode.getMerchantWalletID());
		ReferenceCode.setEntityName(wallet.getClientCode().getEntityName());
		ReferenceCode.setService(RefrenceCode.getService());
		ReferenceCode.setTransactionFee(wallet.getPricingProfile().getServiceFees());
		ReferenceCode.setTransactionCode("103");
		ReferenceCode.setDateCreate(new Date());
		ReferenceCode.setDateUpdate(new Date());
		ReferenceCode.setClientCode(wallet.getClientCode());
		ReferenceCode reference=  referenceCodeService.save(ReferenceCode);

		List<Bill> Billvalue=billService.findByReferencecodeId(reference.getReferencecodeId());
		if(Billvalue.size()==0) {
			ReferenceCode referencecodevalue = referenceCodeService.findByReferencecodeId(reference.getReferencecodeId());
			Integer Billsequence = (Billvalue.size() + 1);
			int codebill = commoneRepository.generateBillId();
			String bCode = "10" + codebill;
			Bill bill = new Bill();
			bill.setBillId("BILL" + bCode);
			bill.setPayAmount(0);
			bill.setPendingAmount(RefrenceCode.getAmount());
			bill.setTotal(RefrenceCode.getAmount());
			bill.setReferencecodeId(referencecodevalue);
			bill.setClientCode(referencecodevalue.getClientCode());
			bill.setDateCreate(new Date());
			bill.setDateUpdate(new Date());
			bill.setEntityName(referencecodevalue.getEntityName());
			bill.setService(referencecodevalue.getService());
			bill.setTransactionCode(referencecodevalue.getTransactionCode());
			bill.setTransactionFee(referencecodevalue.getTransactionFee());
			bill.setBillSequence(Billsequence);
			billService.save(bill);
		}



		ReferenceCodeResponse referenceCodeResponse=new ReferenceCodeResponse();
		referenceCodeResponse.setReferenceCode(reference.getReferencecodeId());

		Note note = new Note();
		note.setSubject("Your service QR code has been generated sucessfully.");
		note.setContent("Your service QR code has been generated sucessfully.");
		Map<String,String> data = new HashMap();
		data.put("Info","Your service QR code has been generated sucessfully.");
		note.setData(data);

		String token=wallet.getClientCode().getDeviceToken();
		Date sysdate = new Date();
		String insCodevalue=wallet.getInstitutionCode().getInstitutionCode();
		String branchCodeValue="";
		String clientCodeValue=String.valueOf(wallet.getClientCode().getClientCode());
		String userCreate="";

		if(token!=null){
			try {
				firebaseService.sendNotification(note, token);

				NotoficationService saveService = new NotoficationService("Your service QR code has been generated sucessfully.", "Your service QR code has been generated sucessfully.",insCodevalue,branchCodeValue,clientCodeValue,userCreate,sysdate,"Success","Yes","");
				notificationServiceDef.saveNotification(saveService);
			} catch (FirebaseMessagingException e) {
				e.printStackTrace();
				NotoficationService saveService = new NotoficationService("Your service QR code has been generated sucessfully.", "Your service QR code has been generated sucessfully.",insCodevalue,branchCodeValue,clientCodeValue,userCreate,sysdate,"Failed","No",e.getMessage());
				notificationServiceDef.saveNotification(saveService);
			}

		}else{
			NotoficationService saveService = new NotoficationService("Your service QR code has been generated sucessfully.", "Your service QR code has been generated sucessfully.",insCodevalue,branchCodeValue,clientCodeValue,userCreate,sysdate,"Failed","No","Token IS null");
			notificationServiceDef.saveNotification(saveService);
		}
		ResponseModel responseModel = new ResponseModel(true, "QR Code generated", referenceCodeResponse);

		return ResponseEntity.accepted().body(responseModel);
	}


	@RequestMapping(value = {"/reference/payment" }, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> postPayment(@RequestBody @Valid BillPayment billPayment) {
		ReferenceCode referenceCode=referenceCodeService.findByReferencecodeId(billPayment.getRefrenceCode());
		if (referenceCode==null) {
			ResponseModel responseModel = new ResponseModel(false, "No data present with this ReferenceCode", null);
			return ResponseEntity.accepted().body(responseModel);
		}
		double balanceAmount=referenceCode.getAmountService()-billPayment.getPaid();
		double pendingAmount=referenceCode.getAmountService()-balanceAmount;
		List<Bill> Billvalue=billService.findByReferencecodeId(billPayment.getRefrenceCode());
		Integer Billsequence=(Billvalue.size() + 1);

		if(!Billvalue.isEmpty()){
			double BillpaySum=Billvalue.stream().mapToDouble(b->b.getPayAmount()).sum();
			balanceAmount=(referenceCode.getAmountService()-(BillpaySum+billPayment.getPaid()));
			if(BillpaySum==referenceCode.getAmountService()) {
				ResponseModel responseModel = new ResponseModel(false, "Bill Payment have been already payed successfully!", null);
				return ResponseEntity.accepted().body(responseModel);
			}
		}
		if(balanceAmount < 0.0){
			ResponseModel responseModel = new ResponseModel(false, "Bill Payment greater than actually Amount!", null);
			return ResponseEntity.accepted().body(responseModel);
		}
		int code = commoneRepository.generateBillId();
		int accountCode = code;
		String bCode = "10" + code;
		Bill bill=new Bill();
		bill.setBillId("BILL"+bCode);
		bill.setPayAmount(billPayment.getPaid());
		bill.setPendingAmount(balanceAmount);
		bill.setTotal(referenceCode.getAmountService());
		bill.setReferencecodeId(referenceCode);
		bill.setClientCode(referenceCode.getClientCode());
		bill.setDateCreate(new Date());
		bill.setDateUpdate(new Date());
		bill.setEntityName(referenceCode.getEntityName());
		bill.setService(referenceCode.getService());
		bill.setTransactionCode(referenceCode.getTransactionCode());
		bill.setTransactionFee(referenceCode.getTransactionFee());
		bill.setBillSequence(Billsequence);
		billService.save(bill);

		BillPaymentResponse billPaymentResponse=new BillPaymentResponse();
		billPaymentResponse.setBalanceAmount(balanceAmount);
		billPaymentResponse.setTotalAmount(referenceCode.getAmountService());
		billPaymentResponse.setPaidAmount(pendingAmount);
		billPaymentResponse.setRefrenceCode(referenceCode.getReferencecodeId());
		ResponseModel responseModel = new ResponseModel(true, "Sucessfully Paid and Payment Details", billPaymentResponse);
		return ResponseEntity.accepted().body(responseModel);
	}

	@RequestMapping(value = {"/reference/paymentlist" }, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getPaymentList(@RequestBody Map<String, String> billPayment) {

		String billReferenceCode = billPayment.get("refrenceCode");
		ReferenceCode referenceCode=referenceCodeService.findByReferencecodeId(billReferenceCode);
		if (referenceCode==null) {
			ResponseModel responseModel = new ResponseModel(false, "No data present with this ReferenceCode", null);
			return ResponseEntity.accepted().body(responseModel);
		}
		List<BillPaymentResponse> Billvalue=billService.PaymentBillListResponse(billReferenceCode);

		ResponseModel responseModel = new ResponseModel(true, "Payment Details", Billvalue);

		return ResponseEntity.accepted().body(responseModel);
	}

	@RequestMapping(value = {"/reference/details" }, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getreferenceDetails(@RequestBody Map<String, String> referencesCodeId) {

		String referenceCodeId = referencesCodeId.get("referenceCode");
		List<Bill> Billvalue=billService.findByReferencecodeId(referenceCodeId);

		if (Billvalue.isEmpty()) {
			ResponseModel responseModel = new ResponseModel(false, "Details not found for this ReferenceCode", null);
			return ResponseEntity.accepted().body(responseModel);
		}
		Bill bill = Billvalue.stream().max(Comparator.comparingInt(Bill::getBillSequence)).get();

		if(bill.getPendingAmount()==0){
			ResponseModel responseModel = new ResponseModel(true, "QR Code Completed, Full Payment done", null);
			return ResponseEntity.accepted().body(responseModel);
		}

		ReferenceCodeDetials referencesResponse=new ReferenceCodeDetials();
		referencesResponse.setService(bill.getService());
		referencesResponse.setAmount(bill.getPendingAmount());
		referencesResponse.setTransactionCode(bill.getTransactionCode());
		referencesResponse.setMerchantWalletID(bill.getReferencecodeId().getMerchantWalletId());
		referencesResponse.setTransactionFee(bill.getTransactionFee());
		referencesResponse.setEntityName(bill.getEntityName());
		referencesResponse.setTransactionCode(bill.getTransactionCode());
		ResponseModel responseModel = new ResponseModel(true, "Details found", referencesResponse);

		return ResponseEntity.accepted().body(responseModel);
	}


	private List<Transcationlistcode> gettransactionCode(String institutionCode) {
		ResponseModel responseModel = null;
		List<Transcationlistcode> transactionCodeList = null;
		String url = CommonUtil.getApplicationBaseUrl() + "/api/usermanagement/user/transactioncode/"+institutionCode;

		ResponseEntity<ResponseModel> responseEntity = CommonUtil.getDataFromOtherService(restTemplate, url,
				CommonUtil.getJwtTokenFromRequest(CommonUtil.getReuestObjectFromContext()));
		if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
			responseModel = responseEntity.getBody();
			Transcationlistcode[] transactionCodearray = CommonUtil.convertToOriginalObject(responseModel.getResult(),
					Transcationlistcode[].class);
			if (transactionCodearray != null) {
				transactionCodeList = Arrays.asList(transactionCodearray);
			}
		}


		return transactionCodeList;
	}

	@RequestMapping(value = {"/wallet/delete/{walletCode}"}, method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> deletewalletCode(@PathVariable("walletCode") String walletCode) {
		Wallet walletlist = walletService.findWalletId(walletCode);
		if ( walletlist == null) {
			ResponseModel responseModel = new ResponseModel(false, "wallet is not Found", null);
			return ResponseEntity.status(200).body(responseModel);
		}
		String message=deleteClientCode(walletlist.getClientCode().getClientCode());
		if(message.equalsIgnoreCase("Success")){
			walletService.delete(walletCode);
		}else{
			ResponseModel responseModel = new ResponseModel(false, "wallet not deleted successfully", null);
			return ResponseEntity.accepted().body(responseModel);
		}

		ResponseModel responseModel = new ResponseModel(true, "wallet deleted successfully", null);
		return ResponseEntity.accepted().body(responseModel);
	}

	private String deleteClientCode(int clientCode) {
		ResponseModel responseModel = null;
		String url = CommonUtil.getApplicationBaseUrl() + "/api/clientmanagement/client/delete/"+clientCode;
        String message="failure";
		ResponseEntity<ResponseModel> responseEntity = CommonUtil.deleteDataFromOtherService(restTemplate, url,
				CommonUtil.getJwtTokenFromRequest(CommonUtil.getReuestObjectFromContext()));
		if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
			message="Success";
		}
      return message;
	}

}
