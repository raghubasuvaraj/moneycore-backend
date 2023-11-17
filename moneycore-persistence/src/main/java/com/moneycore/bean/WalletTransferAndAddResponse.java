package com.moneycore.bean;

import com.moneycore.entity.WalletBalance;

public class WalletTransferAndAddResponse {

	private double totalBalance;
	private double walletBalance;
	private double totalSendAmount;
	private double totalReceivedAmount;
	private String currency;
	private double fee;
	private String transactionId;

	public double getTotalBalance() {
		return totalBalance;
	}

	public void setTotalBalance(double totalBalance) {
		this.totalBalance = totalBalance;
	}

	public double getWalletBalance() {
		return walletBalance;
	}

	public void setWalletBalance(double walletBalance) {
		this.walletBalance = walletBalance;
	}

	public double getTotalSendAmount() {
		return totalSendAmount;
	}

	public void setTotalSendAmount(double totalSendAmount) {
		this.totalSendAmount = totalSendAmount;
	}

	public double getTotalReceivedAmount() {
		return totalReceivedAmount;
	}

	public void setTotalReceivedAmount(double totalReceivedAmount) {
		this.totalReceivedAmount = totalReceivedAmount;
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

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public static WalletTransferAndAddResponse createResponse(WalletBalance sender, WalletTransactionModel model) {
		WalletTransferAndAddResponse response = new WalletTransferAndAddResponse();
		response.setWalletBalance(sender.getBalance());
		response.setTotalSendAmount(model.getTransactionMoney());
		response.setCurrency(model.getCurrency());
		response.setFee(model.getFee());
		return response;
	}

}
