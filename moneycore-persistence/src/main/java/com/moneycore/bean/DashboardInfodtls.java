package com.moneycore.bean;

import java.util.List;

public class DashboardInfodtls {
	
	private Long totalInstitutions;
	private Long totalBranches;
	private Long totalClients;
	private Long totalMerchants;
	private String totalTopup;
	private String totalTransfer;
	private String totalPurchase;
	private Long totalWallet2Wallet;
	private Long totalNumberOfSpaceAccounts;
	private String totalMoneyInSpaces;
	private Long cancelledUsers;
	private Long blockedUsers;
	private Long activeUsers;
	private Long inActiveUsers;
	private Long monitorUsers;
	private Long suspendedUsers;

	List<RegDataInfodtls>  registrationData;

	public Long getTotalInstitutions() {
		return totalInstitutions;
	}

	public void setTotalInstitutions(Long totalInstitutions) {
		this.totalInstitutions = totalInstitutions;
	}

	public Long getTotalBranches() {
		return totalBranches;
	}

	public void setTotalBranches(Long totalBranches) {
		this.totalBranches = totalBranches;
	}

	public Long getTotalClients() {
		return totalClients;
	}

	public void setTotalClients(Long totalClients) {
		this.totalClients = totalClients;
	}

	public Long getTotalMerchants() {
		return totalMerchants;
	}

	public void setTotalMerchants(Long totalMerchants) {
		this.totalMerchants = totalMerchants;
	}

	public String getTotalTopup() {
		return totalTopup;
	}

	public void setTotalTopup(String totalTopup) {
		this.totalTopup = totalTopup;
	}

	public String getTotalTransfer() {
		return totalTransfer;
	}

	public void setTotalTransfer(String totalTransfer) {
		this.totalTransfer = totalTransfer;
	}

	public String getTotalPurchase() {
		return totalPurchase;
	}

	public void setTotalPurchase(String totalPurchase) {
		this.totalPurchase = totalPurchase;
	}

	public Long getTotalWallet2Wallet() {
		return totalWallet2Wallet;
	}

	public void setTotalWallet2Wallet(Long totalWallet2Wallet) {
		this.totalWallet2Wallet = totalWallet2Wallet;
	}

	public Long getTotalNumberOfSpaceAccounts() {
		return totalNumberOfSpaceAccounts;
	}

	public void setTotalNumberOfSpaceAccounts(Long totalNumberOfSpaceAccounts) {
		this.totalNumberOfSpaceAccounts = totalNumberOfSpaceAccounts;
	}

	public String getTotalMoneyInSpaces() {
		return totalMoneyInSpaces;
	}

	public void setTotalMoneyInSpaces(String totalMoneyInSpaces) {
		this.totalMoneyInSpaces = totalMoneyInSpaces;
	}

	public Long getCancelledUsers() {
		return cancelledUsers;
	}

	public void setCancelledUsers(Long cancelledUsers) {
		this.cancelledUsers = cancelledUsers;
	}

	public Long getBlockedUsers() {
		return blockedUsers;
	}

	public void setBlockedUsers(Long blockedUsers) {
		this.blockedUsers = blockedUsers;
	}

	public Long getActiveUsers() {
		return activeUsers;
	}

	public void setActiveUsers(Long activeUsers) {
		this.activeUsers = activeUsers;
	}

	public Long getInActiveUsers() {
		return inActiveUsers;
	}

	public void setInActiveUsers(Long inActiveUsers) {
		this.inActiveUsers = inActiveUsers;
	}

	public Long getMonitorUsers() {
		return monitorUsers;
	}

	public void setMonitorUsers(Long monitorUsers) {
		this.monitorUsers = monitorUsers;
	}

	public Long getSuspendedUsers() {
		return suspendedUsers;
	}

	public void setSuspendedUsers(Long suspendedUsers) {
		this.suspendedUsers = suspendedUsers;
	}

	public List<RegDataInfodtls> getRegistrationData() {
		return registrationData;
	}

	public void setRegistrationData(List<RegDataInfodtls> registrationData) {
		this.registrationData = registrationData;
	}

	public DashboardInfodtls(Long totalInstitutions, Long totalBranches, Long totalClients, Long totalMerchants, String totalTopup, String totalTransfer, String totalPurchase, Long totalWallet2Wallet, Long totalNumberOfSpaceAccounts, String totalMoneyInSpaces, Long cancelledUsers, Long blockedUsers, Long activeUsers, Long inActiveUsers, Long monitorUsers, Long suspendedUsers, List<RegDataInfodtls> registrationData) {
		this.totalInstitutions = totalInstitutions;
		this.totalBranches = totalBranches;
		this.totalClients = totalClients;
		this.totalMerchants = totalMerchants;
		this.totalTopup = totalTopup;
		this.totalTransfer = totalTransfer;
		this.totalPurchase = totalPurchase;
		this.totalWallet2Wallet = totalWallet2Wallet;
		this.totalNumberOfSpaceAccounts = totalNumberOfSpaceAccounts;
		this.totalMoneyInSpaces = totalMoneyInSpaces;
		this.cancelledUsers = cancelledUsers;
		this.blockedUsers = blockedUsers;
		this.activeUsers = activeUsers;
		this.inActiveUsers = inActiveUsers;
		this.monitorUsers = monitorUsers;
		this.suspendedUsers = suspendedUsers;
		this.registrationData = registrationData;
	}

	public DashboardInfodtls() {
	}
}
