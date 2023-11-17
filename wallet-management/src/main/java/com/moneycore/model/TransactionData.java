package com.moneycore.model;

import java.util.List;

public class TransactionData {

	private int totalPages;

	private List<TransactionHistoryModel> transactionHistoryModels;

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public List<TransactionHistoryModel> getTransactionHistoryModels() {
		return transactionHistoryModels;
	}

	public void setTransactionHistoryModels(List<TransactionHistoryModel> transactionHistoryModels) {
		this.transactionHistoryModels = transactionHistoryModels;
	}

}
