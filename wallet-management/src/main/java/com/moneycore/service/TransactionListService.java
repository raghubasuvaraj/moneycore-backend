package com.moneycore.service;

import java.util.List;

import com.moneycore.bean.SearchTransaction;
import com.moneycore.bean.StatementInfo;
import com.moneycore.entity.Client;
import com.moneycore.entity.TransactionList;
import com.moneycore.entity.WalletBalance;
import com.moneycore.model.TransactionData;

public interface TransactionListService {

	TransactionList saveTransaction(WalletBalance walBal, String transactionCode, String reason, double amount, String currency,
			String typeOfTransfer, String name, char transactionType, double transactionAvailableBalance);

	TransactionList saveTransactionSplit(WalletBalance walBal, String transactionCode, String reason, double amount, String currency,
									String typeOfTransfer, String name, char transactionType, double transactionAvailableBalance,String referenceCode);


	double findTotalReceivedAmount(String walletId);

	double findTotalSendAmount(String walletId);

	TransactionData getWalletTransactionHisotry(SearchTransaction searchTransaction);

	Client getNameOfClient(String walletId);

	List<TransactionList> findLast10ByWalletId(String walletId);

	StatementInfo getWalletStatementHistory(String walletId, String startDate, String endDate, int pageNo, int pageSize);

	StatementInfo getWalletStatementToDownload(String walletId, String startDate, String endDate);

	List<TransactionList> findDailyInternalTransactionByWalletId(String walletId);

	double findTotalInternalTransactionAmountLimitByWalletId(String walletId);

	double findtotalNoOfInternalTransactions(String walletId);

	double findNoOfDailyInternalTransactions(String walletId);

	double findDailyTransactionAmountLimit(String walletId);

	double findtotalTransactionAmountLimit(String senderWalletId);

	double findtotalNoOfTransactions(String senderWalletId);
}
