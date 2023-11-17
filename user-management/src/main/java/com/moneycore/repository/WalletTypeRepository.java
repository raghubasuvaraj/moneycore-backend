package com.moneycore.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.moneycore.entity.WalletType;

@Repository("walletTypeRepository")
public interface WalletTypeRepository extends JpaRepository<WalletType, String> {

	@Query(value = "SELECT * FROM wallet_type where institution_code = :institutionCode AND wallet_type_id = :walletTypeId  order by date_create desc", nativeQuery = true)
	Optional<WalletType> find(@Param("institutionCode") String institutionCode,
			@Param("walletTypeId") String walletTypeId);

	@Query(value = "SELECT * FROM wallet_type where institution_code = :institutionCode  order by date_create desc", nativeQuery = true)
	List<WalletType> findList(@Param("institutionCode") String institutionCode);

	@Query(value = "SELECT * FROM wallet_type WHERE institution_code = :institutionCode AND is_wallet_type_default = true  order by date_create desc", nativeQuery = true)
	WalletType findDefaultIsAlreadyPresent(@Param("institutionCode") String institutionCode);

	@Query(value = "SELECT * FROM wallet_type where wallet_type_id = :walletTypeId  order by date_create desc", nativeQuery = true)
	Optional<WalletType> find(@Param("walletTypeId") String walletTypeId);
	boolean existsByWalletTypeNameAndInstitutionCode(String walletTypeName,String institutionCode);

	@Query(value = "SELECT * FROM wallet_type where institution_code = :institutionCode AND wallet_type_name =:walletTypeName AND wallet_type_id <> :walletTypeId  order by date_create desc", nativeQuery = true)
	List<WalletType> findByWalletType(@Param("institutionCode") String institutionCode,@Param("walletTypeName") String walletTypeName,
							  @Param("walletTypeId") String walletTypeId);
}
