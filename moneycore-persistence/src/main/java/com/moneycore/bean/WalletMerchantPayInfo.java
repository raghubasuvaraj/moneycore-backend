package com.moneycore.bean;

public class WalletMerchantPayInfo {

	private String referenceCode;
	private String senderWalletId;
	private String merchantWalletId;
	private String merchantName;
	private String senderName;
	private Double transactionMoney;
	private String transactionCode;
	private String reason;
	private String currency;
	private double fee;

	public String getReferenceCode() {
		return referenceCode;
	}

	public void setReferenceCode(String referenceCode) {
		this.referenceCode = referenceCode;
	}

	public String getSenderWalletId() {
		return senderWalletId;
	}

	public void setSenderWalletId(String senderWalletId) {
		this.senderWalletId = senderWalletId;
	}

	public String getMerchantWalletId() {
		return merchantWalletId;
	}

	public void setMerchantWalletId(String merchantWalletId) {
		this.merchantWalletId = merchantWalletId;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public Double getTransactionMoney() {
		return transactionMoney;
	}

	public void setTransactionMoney(Double transactionMoney) {
		this.transactionMoney = transactionMoney;
	}

	public String getTransactionCode() {
		return transactionCode;
	}

	public void setTransactionCode(String transactionCode) {
		this.transactionCode = transactionCode;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public double getFee() {
		return fee;
	}

	public void setFee(double fee) {
		this.fee = fee;
	}

}
