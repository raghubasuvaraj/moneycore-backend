package com.moneycore.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.moneycore.entity.AccountTypeList;

@Repository("accountTypeListRepository")
public interface AccountTypeListRepository extends JpaRepository<AccountTypeList, String> {

	@Query(value = "SELECT * FROM account_type_list where institution_code = :institutionCode AND account_type_code = :accountTypeCode  order by date_create desc", nativeQuery = true)
	Optional<AccountTypeList> find(@Param("institutionCode") String institutionCode,
			@Param("accountTypeCode") String accountTypeCode);

	@Query(value = "SELECT * FROM account_type_list where institution_code = :institutionCode  order by date_create desc", nativeQuery = true)
	List<AccountTypeList> findList(String institutionCode);

	@Query(value = "SELECT * FROM account_type_list where account_type_code = :accountTypeCode  order by date_create desc", nativeQuery = true)
	AccountTypeList find(@Param("accountTypeCode") String accountTypeCode);
	boolean existsByAccountTypeNameAndInstitutionCode(String Name,String institutionCode);

	@Query(value = "SELECT * FROM account_type_list where institution_code = :institutionCode AND account_type_name =:accountTypeName AND account_type_code <> :accountTypeCode  order by date_create desc", nativeQuery = true)
	List<AccountTypeList> findByAccountType(@Param("institutionCode") String institutionCode,@Param("accountTypeName") String accountTypeName,
								   @Param("accountTypeCode") String accountTypeCode);

}
