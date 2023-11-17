package com.moneycore.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.moneycore.entity.CountryList;

@Repository("countryListRespository")
public interface CountryListRespository extends JpaRepository<CountryList, String> {

	@Query(value = "SELECT * FROM country_list where institution_code = :institutionCode and country_code = :countryCode  order by date_create desc", nativeQuery = true)
	Optional<CountryList> find(@Param("institutionCode") String institutionCode, @Param("countryCode") String countryCode);

	@Query(value = "SELECT * FROM country_list where country_code = :countryCode ", nativeQuery = true)
	Optional<CountryList> find(@Param("countryCode") String countryCode);

	@Query(value = "SELECT * FROM country_list where institution_code = :institutionCode and region_code = :regionCode  order by date_create desc", nativeQuery = true)
	List<CountryList> findList(@Param("institutionCode") String institutionCode, @Param("regionCode") String regionCode);

	@Query(value = "SELECT * FROM country_list where institution_code = :institutionCode  order by date_create desc", nativeQuery = true)
	List<CountryList> findList(@Param("institutionCode") String institutionCode);

	@Query(value = "SELECT * FROM country_list where  region_code = :regionCode  order by date_create desc", nativeQuery = true)
	List<CountryList> findRegionList( @Param("regionCode") String regionCode);

	boolean existsByCountryNameAndInstitutionCode(String Name,String institutionCode);

	@Query(value = "SELECT * FROM country_list where institution_code = :institutionCode and country_name = :countryName and country_code <> :countryCode order by date_create desc", nativeQuery = true)
	List<CountryList> findByCountryName(@Param("institutionCode") String institutionCode, @Param("countryName") String countryName,@Param("countryCode") String countryCode);


}