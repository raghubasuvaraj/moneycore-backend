package com.moneycore.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.moneycore.entity.Wallet;

@Repository("walletRepository")
public interface WalletRepository extends JpaRepository<Wallet, String> {

	@Query(value = "SELECT * FROM wallet WHERE wallet_id =:walletId and is_deleted=false", nativeQuery = true)
	Optional<Wallet> find(@Param("walletId") String walletId);

	@Query(value = "SELECT * FROM wallet WHERE client_code =:clientCode and is_deleted=false", nativeQuery = true)
	Optional<Wallet> findByClientCode(@Param("clientCode") int clientCode);

}
