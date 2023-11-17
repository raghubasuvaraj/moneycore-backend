package com.moneycore.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.moneycore.entity.SpaceAccounts;

@Repository("spaceAccountsRepository")
public interface SpaceAccountsRepository extends JpaRepository<SpaceAccounts, Integer> {

	@Query(value = "SELECT * FROM space_accounts where wallet_id = :walletId AND iban = :iban", nativeQuery = true)
	Optional<SpaceAccounts> find(@Param("walletId") String walletId, @Param("iban") String iban);

	@Query(value = "SELECT * FROM space_accounts where wallet_id = :walletId", nativeQuery = true)
	List<SpaceAccounts> findList(@Param("walletId") String walletId);

}
