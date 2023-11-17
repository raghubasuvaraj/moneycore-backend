package com.moneycore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.moneycore.entity.WalletAccountLink;

@Repository("walletAccountLinkRepository")
public interface WalletAccountLinkRepository extends JpaRepository<WalletAccountLink, Integer>{

}
