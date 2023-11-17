package com.moneycore.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.moneycore.entity.WalletTypePricingProfile;

public interface WalletTypePricingProfileRepository extends JpaRepository<WalletTypePricingProfile, String> {

	@Query(value = "SELECT * FROM wallet_type_pricing_profile where pricing_profile_id = :pricingIndex LIMIT 1", nativeQuery = true)
	Optional<WalletTypePricingProfile> find(@Param("pricingIndex") String pricingIndex);

	@Query(value = "SELECT * FROM wallet_type_pricing_profile where wallet_type_id = :walletTypeId LIMIT 1", nativeQuery = true)
	Optional<WalletTypePricingProfile> findByWalletType(@Param("walletTypeId") String walletTypeId);

}