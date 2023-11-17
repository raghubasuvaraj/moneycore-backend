package com.moneycore.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.moneycore.bean.SearchTransaction;
import com.moneycore.bean.SearchWallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.itextpdf.text.DocumentException;
import com.moneycore.bean.StatementInfo;
import com.moneycore.bean.TransactionFee;
import com.moneycore.entity.Address;
import com.moneycore.entity.Branch;
import com.moneycore.entity.TransactionList;
import com.moneycore.entity.Wallet;
import com.moneycore.entity.WalletType;
import com.moneycore.model.ResponseModel;
import com.moneycore.model.TransactionHistoryModel;
import com.moneycore.service.PdfGeneratorService;
import com.moneycore.service.TransactionListService;
import com.moneycore.service.WalletService;
import com.moneycore.util.CommonUtil;

@RestController
@RequestMapping("/api/walletmanagement")
public class TransactionManagementController {

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	TransactionListService transactionListService;

	@Autowired
	WalletService walletService;

	@Autowired
	PdfGeneratorService pdfGeneratorService;

	@RequestMapping(value = {
			"/wallet/transactions/{walletid}" }, method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> transactionHistory(@PathVariable("walletid") String walletId,
			@RequestParam String fromdate, @RequestParam String todate,@RequestParam(defaultValue = "1") int pageNo,
			@RequestParam(required = false) String transationId, @RequestParam(required = false) String transactionCode,
			@RequestParam(defaultValue = "A") String transationType, @RequestParam(required = false) String serviceType,
			@RequestParam(defaultValue = "10") int pageSize,
			@RequestParam(defaultValue = "transaction_date") String sortBy
			) {
		SearchTransaction searchTransaction = new SearchTransaction(walletId,  fromdate,  todate,  pageNo,  pageSize,  transationId,  transationType,  serviceType,  transactionCode, sortBy);
		return ResponseEntity.status(HttpStatus.OK).body(transactionListService.getWalletTransactionHisotry(searchTransaction));
	}

	@RequestMapping(value = {
			"/wallet/currenttransactions/{walletid}" }, method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> lastTenTransaction(@PathVariable("walletid") String walletId) {
		List<TransactionHistoryModel> historyModels = new ArrayList<TransactionHistoryModel>();
		List<TransactionList> transactionLists = transactionListService.findLast10ByWalletId(walletId);
		if (transactionLists.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK).body("No transaction available");
		}
		for (TransactionList transactionList : transactionLists) {
			TransactionHistoryModel transactionHistoryModel = new TransactionHistoryModel();
//			Client client = transactionListService.getNameOfClient(transactionList.getClientCode());
//			transactionHistoryModel
//					.setName(client.getFirstName() + " " + client.getMiddleName() + " " + client.getLastName());
			String[] wording = transactionList.getTransactionWording().split("transaction", 2);
			String name = wording[1];
			transactionHistoryModel.setName(name);
			transactionHistoryModel.setStatus(transactionList.getReversalFlag());
			transactionHistoryModel.setTransactionAmount(transactionList.getTransactionAmount());
			transactionHistoryModel.setTransactionDate(transactionList.getTransactionDate());
			transactionHistoryModel.setTransactionType(transactionList.getTransactionSign());
			transactionHistoryModel.setDescription(transactionList.getTransactionDescription().replace(" transaction", ""));
			transactionHistoryModel.setTransactionId(transactionList.getMicrofilmRefNumber());
			transactionHistoryModel.setTransactionCode(transactionList.getTransactionCode());
			historyModels.add(transactionHistoryModel);
		}
		return ResponseEntity.status(HttpStatus.OK).body(historyModels);
	}

	@RequestMapping(value = {
			"/wallet/statements/{walletid}" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> statementHistory(@PathVariable("walletid") String walletId, @RequestParam String startDate,
			@RequestParam String endDate, @RequestParam(defaultValue = "0") int pageNo,
			@RequestParam(defaultValue = "10") int pageSize) {
		ResponseModel responseModel = new ResponseModel(true, "Wallet Statement",
				transactionListService.getWalletStatementHistory(walletId, startDate, endDate, pageNo, pageSize));
		return ResponseEntity.accepted().body(responseModel);
	}

//	@RequestMapping(value = {"/wallet/statement/download/{walletid}" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_PDF_VALUE)
	@RequestMapping(value = {"/wallet/statement/download/{walletid}" }, method = RequestMethod.GET)
	public ResponseEntity<?> statementDownload(@PathVariable("walletid") String walletId,
			@RequestParam String startDate, @RequestParam String endDate) throws IOException, DocumentException {
		Wallet wallet = walletService.findWallet(walletId);
		Address address = getAddress(wallet.getClientCode().getClientCode());
		String branchName = "";// getBranchName(wallet.getClientCode().getBranchList());
		String walletTypeName = "";
		if (wallet.getWalletType() != null)
			walletTypeName = "";//getWalletTypeName(wallet.getWalletType());
		StatementInfo statementInfo = transactionListService.getWalletStatementToDownload(walletId, startDate, endDate);
		if(statementInfo.getStatement() == null){
			ResponseModel responseModel = new ResponseModel(false, "No transaction data found between " + startDate + " and " + endDate, null);
			return ResponseEntity.status(200).body(responseModel);
		}
		ByteArrayInputStream bis = pdfGeneratorService.generatePDF(wallet, address, branchName, statementInfo, walletTypeName);
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Disposition", "inline; filename=statementreport.pdf");
		return ResponseEntity.ok().headers(responseHeaders).contentType(MediaType.APPLICATION_PDF)
				.body(new InputStreamResource(bis));
	}

	@RequestMapping(value = {
			"/wallet/transaction/fee/{institutioncode}/{walletid}/{transactiontype}" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> getAccountType(HttpServletRequest request,
			@PathVariable("institutioncode") String institutionCode, @PathVariable("walletid") String walletId,
			@PathVariable("transactiontype") String transactionType) {
		Wallet wallet = walletService.findWalletId(walletId);
		if (wallet == null) {
			ResponseModel responseModel = new ResponseModel(false, "Data not present for this Wallet Id", null);
			return ResponseEntity.status(200).body(responseModel);
		}
		TransactionFee transactionFee = null;
		if (transactionType.equalsIgnoreCase("cashin")) {
			transactionFee = TransactionFee.createResponse(wallet.getPricingProfile(), transactionType);
		}
		if (transactionType.equalsIgnoreCase("transfer")) {
			transactionFee = TransactionFee.createResponse(wallet.getPricingProfile(), transactionType);
		}
		if (transactionType.equalsIgnoreCase("purchase")) {
			transactionFee = TransactionFee.createResponse(wallet.getPricingProfile(), transactionType);
		}
		if (transactionFee != null) {
			ResponseModel responseModel = new ResponseModel(true, "Fee Details", transactionFee);
			return ResponseEntity.status(200).body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(false, "Something went wrong", null);
		return ResponseEntity.status(500).body(responseModel);
	}

	private Address getAddress(int clientCode) {
		ResponseModel responseModel = null;
		Address address = null;
		String url = CommonUtil.getApplicationBaseUrl() + "/api/clientmanagement/internal/client/address/" + clientCode;
		ResponseEntity<ResponseModel> responseEntity = CommonUtil.getDataFromOtherService(restTemplate, url, null);
		if (responseEntity.getStatusCode().equals(HttpStatus.ACCEPTED)) {
			responseModel = responseEntity.getBody();
			address = CommonUtil.convertToOriginalObject(responseModel.getResult(), Address.class);
		}
		return address;
	}

	private String getBranchName(String branchCode) {
		ResponseModel responseModel = null;
		Branch branch = null;
		String url = CommonUtil.getApplicationBaseUrl() + "/api/institutionmanagement/internal/branch/" + branchCode;
		ResponseEntity<ResponseModel> responseEntity = CommonUtil.getDataFromOtherService(restTemplate, url, null);
		if (responseEntity.getStatusCode().equals(HttpStatus.ACCEPTED)) {
			responseModel = responseEntity.getBody();
			branch = CommonUtil.convertToOriginalObject(responseModel.getResult(), Branch.class);
		}
		return branch.getBranchName();
	}

	private String getWalletTypeName(String walletTypeId) {
		ResponseModel responseModel = null;
		WalletType walletType = null;
		String url = CommonUtil.getApplicationBaseUrl() + "/api/usermanagement/internal/user/wallet/type/"
				+ walletTypeId;
		ResponseEntity<ResponseModel> responseEntity = CommonUtil.getDataFromOtherService(restTemplate, url, null);
		if (responseEntity.getStatusCode().equals(HttpStatus.ACCEPTED)) {
			responseModel = responseEntity.getBody();
			walletType = CommonUtil.convertToOriginalObject(responseModel.getResult(), WalletType.class);
		}
		return walletType.getWalletTypeName();
	}

}
