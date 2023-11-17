package com.moneycore.bean;

public class SearchTransaction {

	private String walletId;
	private String fromDate;
	private String toDate;
	private int pageNo;
	private int pageSize;
	private String transactionId;
	private String transactionType;
	private String serviceType;
	private String transactionCode;
	private String sortBy;

	public SearchTransaction() {

	}

	public SearchTransaction(String walletId, String fromDate, String toDate, int pageNo, int pageSize, String transactionId, String transactionType, String serviceType, String transactionCode, String sortBy) {
		this.walletId = walletId;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.transactionId = transactionId;
		this.transactionType = transactionType;
		this.serviceType = serviceType;
		this.transactionCode = transactionCode;
		this.sortBy = sortBy;
	}

	public String getWalletId() {
		return walletId;
	}

	public void setWalletId(String walletId) {
		this.walletId = walletId;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getTransactionCode() {
		return transactionCode;
	}

	public void setTransactionCode(String transactionCode) {
		this.transactionCode = transactionCode;
	}

	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}
}
