package com.moneycore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.moneycore.entity.Wallet;
import com.moneycore.entity.WalletBalance;

@Repository("walletBalanceRepository")
public interface WalletBalanceRepository extends JpaRepository<WalletBalance, Integer>{

	public WalletBalance findByWalletId(Wallet wallet);
}
