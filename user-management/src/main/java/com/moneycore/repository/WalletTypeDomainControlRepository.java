package com.moneycore.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.moneycore.entity.WalletTypeDomainControl;

public interface WalletTypeDomainControlRepository extends JpaRepository<WalletTypeDomainControl, String> {

	@Query(value = "SELECT * FROM wallet_type_domain_control where domain_control_id = :controlIndex LIMIT 1", nativeQuery = true)
	Optional<WalletTypeDomainControl> find(@Param("controlIndex") String controlIndex);

	@Query(value = "SELECT * FROM wallet_type_domain_control where wallet_type_id = :walletTypeId LIMIT 1", nativeQuery = true)
	Optional<WalletTypeDomainControl> findByWalletType(@Param("walletTypeId") String walletTypeId);

}
