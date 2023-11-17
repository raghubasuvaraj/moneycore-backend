package com.moneycore.model;

import java.util.List;

import com.moneycore.bean.WalletResponse;
import com.moneycore.entity.Wallet;

public class SearchWalletResponse {

	private int totalPages;

//	private List<Wallet> wallets;
	private List<WalletResponse> walletResponses;

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

//	public List<Wallet> getWallets() {
//		return wallets;
//	}
//
//	public void setWallets(List<Wallet> wallets) {
//		this.wallets = wallets;
//	}

	public List<WalletResponse> getWalletResponses() {
		return walletResponses;
	}

	public void setWalletResponses(List<WalletResponse> walletResponses) {
		this.walletResponses = walletResponses;
	}
}
