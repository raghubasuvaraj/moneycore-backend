package com.moneycore.bean;

import java.util.List;

public interface DashboardInfo {
	
	Long getTotalInstitutions();
	Long getTotalBranches();
	Long getTotalClients();
	Long getTotalMerchants();
	String getTotalTopup();
	String getTotalTransfer();
	String getTotalPurchase();
	Long getTotalWallet2Wallet();
	Long getTotalNumberOfSpaceAccounts();
	String getTotalMoneyInSpaces();
	Long getCancelledUsers();
	Long getBlockedUsers();
	Long getActiveUsers();
	Long getInActivedUsers();
	Long getMonitoredUsers();
	Long getSuspendedUsers();

}
