package com.moneycore.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.moneycore.entity.PricingProfile;

@Repository("pricingProfileRepository")
public interface PricingProfileRepository extends JpaRepository<PricingProfile, String> {

	@Query(value = "SELECT * FROM pricing_profile", nativeQuery = true)
	List<PricingProfile> fetch();

	@Query(value = "SELECT * FROM pricing_profile where institution_code = :institutionCode AND pricing_index = :pricingIndex and is_deleted=false order by date_create desc", nativeQuery = true)
	PricingProfile fetch(@Param("institutionCode") String institutionCode,
			@Param("pricingIndex") String pricingIndex);

	@Query(value = "SELECT * FROM pricing_profile where institution_code = :institutionCode and is_deleted=false ORDER BY date_create DESC", nativeQuery = true)
	List<PricingProfile> fetch(@Param("institutionCode") String institutionCode);

	@Query(value = "SELECT * FROM pricing_profile where institution_code = :institutionCode AND pricing_index = :pricingIndex and is_deleted=false order by date_create desc", nativeQuery = true)
	Optional<PricingProfile> find(@Param("institutionCode") String institutionCode,
			@Param("pricingIndex") String pricingIndex);

	@Query(value = "SELECT * FROM pricing_profile where pricing_index = :pricingIndex and is_deleted=false order by date_create desc", nativeQuery = true)
	Optional<PricingProfile> find(@Param("pricingIndex") String pricingIndex);

}
