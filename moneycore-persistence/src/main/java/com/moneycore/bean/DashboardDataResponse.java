package com.moneycore.bean;

import com.moneycore.entity.SpaceAccounts;

import java.util.ArrayList;
import java.util.List;

public class DashboardDataResponse {

	private long totalInstitutions;
	private long totalBranches;
	private long totalClients;
	private long totalMerchants;
	private long activeUsers;
	private long cancelledUsers;
	private long suspendedUsers;
	private long BlockedUsers;
	private long totalNumberOfSpaceAccounts;
	private long totalMoneyInSpaces;
	private long totalTopup;
	private long totalPurchase;
	private long totalWallet2Wallet;
	private int registrationData;

	public long getTotalInstitutions() {
		return totalInstitutions;
	}

	public void setTotalInstitutions(long totalInstitutions) {
		this.totalInstitutions = totalInstitutions;
	}

	public long getTotalBranches() {
		return totalBranches;
	}

	public void setTotalBranches(long totalBranches) {
		this.totalBranches = totalBranches;
	}

	public long getTotalClients() {
		return totalClients;
	}

	public void setTotalClients(long totalClients) {
		this.totalClients = totalClients;
	}

	public long getTotalMerchants() {
		return totalMerchants;
	}

	public void setTotalMerchants(long totalMerchants) {
		this.totalMerchants = totalMerchants;
	}

	public long getActiveUsers() {
		return activeUsers;
	}

	public void setActiveUsers(long activeUsers) {
		this.activeUsers = activeUsers;
	}

	public long getCancelledUsers() {
		return cancelledUsers;
	}

	public void setCancelledUsers(long cancelledUsers) {
		this.cancelledUsers = cancelledUsers;
	}

	public long getSuspendedUsers() {
		return suspendedUsers;
	}

	public void setSuspendedUsers(long suspendedUsers) {
		this.suspendedUsers = suspendedUsers;
	}

	public long getBlockedUsers() {
		return BlockedUsers;
	}

	public void setBlockedUsers(long blockedUsers) {
		BlockedUsers = blockedUsers;
	}

	public long getTotalNumberOfSpaceAccounts() {
		return totalNumberOfSpaceAccounts;
	}

	public void setTotalNumberOfSpaceAccounts(long totalNumberOfSpaceAccounts) {
		this.totalNumberOfSpaceAccounts = totalNumberOfSpaceAccounts;
	}

	public long getTotalMoneyInSpaces() {
		return totalMoneyInSpaces;
	}

	public void setTotalMoneyInSpaces(long totalMoneyInSpaces) {
		this.totalMoneyInSpaces = totalMoneyInSpaces;
	}

	public long getTotalTopup() {
		return totalTopup;
	}

	public void setTotalTopup(long totalTopup) {
		this.totalTopup = totalTopup;
	}

	public long getTotalPurchase() {
		return totalPurchase;
	}

	public void setTotalPurchase(long totalPurchase) {
		this.totalPurchase = totalPurchase;
	}

	public long getTotalWallet2Wallet() {
		return totalWallet2Wallet;
	}

	public void setTotalWallet2Wallet(long totalWallet2Wallet) {
		this.totalWallet2Wallet = totalWallet2Wallet;
	}

	public int getRegistrationData() {
		return registrationData;
	}

	public void setRegistrationData(int registrationData) {
		this.registrationData = registrationData;
	}
}
