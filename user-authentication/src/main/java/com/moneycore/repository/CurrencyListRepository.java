package com.moneycore.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.moneycore.entity.CurrencyList;

@Repository("currencyListRepository")
public interface CurrencyListRepository extends JpaRepository<CurrencyList, String> {

	@Query(value = "SELECT * FROM currency_list where institution_code = :institutionCode AND currency_code = :currencyCode ", nativeQuery = true)
	Optional<CurrencyList> find(@Param("institutionCode") String institutionCode, @Param("currencyCode") String currencyCode);

	@Query(value = "SELECT * FROM currency_list where currency_code = :currencyCode ", nativeQuery = true)
	Optional<CurrencyList> find(@Param("currencyCode") String currencyCode);

	@Query(value = "SELECT * FROM currency_list where institution_code = :institutionCode ", nativeQuery = true)
	List<CurrencyList> findList(String institutionCode);

}