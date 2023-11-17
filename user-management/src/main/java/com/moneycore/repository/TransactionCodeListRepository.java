package com.moneycore.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.moneycore.entity.TransactionCodeList;

@Repository("transactionCodeListRepository")
public interface TransactionCodeListRepository extends JpaRepository<TransactionCodeList, String> {

	@Query(value = "SELECT * FROM transaction_code_list WHERE institution_code = :institutionCode AND transaction_code = :transactionCode   order by date_create desc", nativeQuery = true)
	Optional<TransactionCodeList> find(@Param("institutionCode") String institutionCode,
			@Param("transactionCode") String transactionCode);

	@Query(value = "SELECT * FROM transaction_code_list WHERE transaction_code = :transactionCode  order by date_create desc", nativeQuery = true)
	Optional<TransactionCodeList> find(@Param("transactionCode") String transactionCode);

	@Query(value = "SELECT * FROM transaction_code_list WHERE institution_code = :institutionCode  order by date_create desc", nativeQuery = true)
	List<TransactionCodeList> findList(@Param("institutionCode") String institutionCode);

	@Query(value = "SELECT * FROM transaction_code_list WHERE institution_code = :institutionCode AND service_code = :service_code  order by date_create desc", nativeQuery = true)
	List<TransactionCodeList> findList(@Param("institutionCode") String institutionCode,
									   @Param("service_code") int service_code);

	boolean existsByTransactionNameAndInstitutionCode(String transactionName,String institutionCode);

	@Query(value = "SELECT * FROM transaction_code_list WHERE institution_code = :institutionCode AND transaction_name =:transactionName AND transaction_code <> :transactionCode  order by date_create desc", nativeQuery = true)
	List<TransactionCodeList> findByTransName(@Param("institutionCode") String institutionCode,@Param("transactionName") String transactionName,
									   @Param("transactionCode") String transactionCode);



}
