package com.moneycore.bean;

public class WalletBalanceInfo {

	private double balance;
	private double facilities;
	private double blockedAmount;
	private double currentAuth;
	private double availableBalance;

	public WalletBalanceInfo() {
	}

	public WalletBalanceInfo(double balance, double facilities, double blockedAmount, double currentAuth,
			double availableBalance) {
		this.balance = balance;
		this.facilities = facilities;
		this.blockedAmount = blockedAmount;
		this.currentAuth = currentAuth;
		this.availableBalance = availableBalance;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public double getFacilities() {
		return facilities;
	}

	public void setFacilities(double facilities) {
		this.facilities = facilities;
	}

	public double getBlockedAmount() {
		return blockedAmount;
	}

	public void setBlockedAmount(double blockedAmount) {
		this.blockedAmount = blockedAmount;
	}

	public double getCurrentAuth() {
		return currentAuth;
	}

	public void setCurrentAuth(double currentAuth) {
		this.currentAuth = currentAuth;
	}

	public double getAvailableBalance() {
		return availableBalance;
	}

	public void setAvailableBalance(double availableBalance) {
		this.availableBalance = availableBalance;
	}
}
