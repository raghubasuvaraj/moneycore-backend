package com.moneycore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.moneycore.entity.SpaceAccountDetails;

@Repository("spaceAccountDetailsRepository")
public interface SpaceAccountDetailsRepository extends JpaRepository<SpaceAccountDetails, Integer> {

	@Query(value = "SELECT * FROM space_account_details where space_account_fk in"
			+ "(SELECT space_account_fk FROM space_accounts where wallet_id= :walletId and space_account_fields_fk in"
			+ "(SELECT space_account_fields_pk FROM space_account_fields))", nativeQuery = true)
	List<SpaceAccountDetails> findList(@Param("walletId") String walletId);

	@Query(value = "DELETE FROM space_account_details where space_account_fk = :spaceAccountFk", nativeQuery = true)
	SpaceAccountDetails deleteBySpaceAccountFk(@Param("spaceAccountFk") int spaceAccountFk);

	@Query(value = "SELECT * FROM space_account_details where space_account_fk = :spaceId", nativeQuery = true)
	List<SpaceAccountDetails> findListByFk(@Param("spaceId") String spaceId);

}
