package com.moneycore.service;

import java.util.List;

import com.moneycore.entity.WalletAccountLink;

public interface WalletAccountLinkService {

	String getLastAccountNumber();

	WalletAccountLink saveWalletAccount(WalletAccountLink accountLink);

	List<WalletAccountLink> findByWalletId(String walletId);

}
