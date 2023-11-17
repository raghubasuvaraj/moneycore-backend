package com.moneycore.bean;

public class WalletTransactionModel {

	private String referenceCode;
	private String senderWalletId;
	private String receiverWalletId;
	private String receiverName;
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

	public String getReceiverWalletId() {
		return receiverWalletId;
	}

	public void setReceiverWalletId(String receiverWalletId) {
		this.receiverWalletId = receiverWalletId;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
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

	public static WalletTransactionModel castWalletMerchantPayInfo(WalletMerchantPayInfo walletMerchantPayInfo) {
		WalletTransactionModel walletTransactionModel = new WalletTransactionModel();
		walletTransactionModel.setReferenceCode(walletMerchantPayInfo.getReferenceCode());
		walletTransactionModel.setSenderWalletId(walletMerchantPayInfo.getSenderWalletId());
		walletTransactionModel.setReceiverWalletId(walletMerchantPayInfo.getMerchantWalletId());
		walletTransactionModel.setSenderName(walletMerchantPayInfo.getSenderName());
		walletTransactionModel.setReceiverName(walletMerchantPayInfo.getMerchantName());
		walletTransactionModel.setTransactionMoney(walletMerchantPayInfo.getTransactionMoney());
		walletTransactionModel.setTransactionCode(walletMerchantPayInfo.getTransactionCode());
		walletTransactionModel.setReason(walletMerchantPayInfo.getReason());
		walletTransactionModel.setCurrency(walletMerchantPayInfo.getCurrency());
		walletTransactionModel.setFee(walletMerchantPayInfo.getFee());
		return walletTransactionModel;
	}

}
