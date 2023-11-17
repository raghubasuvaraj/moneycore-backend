package com.moneycore.repository;

import com.moneycore.entity.Beneficiary;
import com.moneycore.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("beneficiaryRepository")
public interface BeneficiaryRepository extends JpaRepository<Beneficiary, Integer> {

	@Query(value = "SELECT * FROM beneficiary WHERE wallet_id =:walletId and is_deleted=false", nativeQuery = true)
	Optional<Beneficiary> findByWallet(@Param("walletId") String walletId);


}
