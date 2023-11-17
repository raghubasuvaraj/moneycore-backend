package com.moneycore.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.moneycore.entity.WalletSpace;

@Repository("walletSpaceRepository")
public interface WalletSpaceRepository extends JpaRepository<WalletSpace, String> {

	@Query(value = "SELECT * FROM wallet_space ORDER BY target_date DESC LIMIT 1", nativeQuery = true)
	Optional<WalletSpace> fetchAccountNumberLength();

	@Query(value = "SELECT * FROM wallet_space WHERE institution_code = :institutionCode AND wallet_id = :walletId", nativeQuery = true)
	List<WalletSpace> fetchList(@Param("institutionCode") String institutionCode, @Param("walletId") String walletId);

	@Query(value = "SELECT * FROM wallet_space WHERE institution_code = :institutionCode AND wallet_id = :walletId AND space_id =:spaceId", nativeQuery = true)
	Optional<WalletSpace> fetch(@Param("institutionCode") String institutionCode, @Param("walletId") String walletId,
			@Param("spaceId") String spaceId);

	@Query(value = "SELECT * FROM wallet_space WHERE institution_code = :institutionCode AND wallet_id = :walletId AND account_number =:accountNumber", nativeQuery = true)
	Optional<WalletSpace> fetchByAccountNumber(@Param("institutionCode") String institutionCode, @Param("walletId") String walletId,
			@Param("accountNumber") String accountNumber);

	@Query(value = "SELECT sum(amount) FROM wallet_space WHERE wallet_id = :walletId", nativeQuery = true)
	double fetchTotalBalance(@Param("walletId") String walletId);

	@Query(value = "SELECT * FROM wallet_space WHERE wallet_id = :walletId LIMIT 1", nativeQuery = true)
	Optional<WalletSpace> fetchByWalletId(@Param("walletId") String walletId);

}
