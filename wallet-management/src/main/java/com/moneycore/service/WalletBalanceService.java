package com.moneycore.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.moneycore.entity.Client;
import com.moneycore.entity.ServiceList;
import com.moneycore.entity.WalletBalance;

import java.util.List;

public interface WalletBalanceService {

	WalletBalance saveWalletBalance(WalletBalance walletBalance);

	List<WalletBalance> findByWalletId(String walletId);

	WalletBalance findWalletByWalletId(String walletId);

	WalletBalance find(String walletId);

	public void adjustBalance();

	void activateClient(int clientCode) throws JsonProcessingException;

	List<ServiceList> getServiceListByInstitutionCode(String institutionCode);

	public void walletBalance();
}
