package com.moneycore.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.moneycore.entity.CityList;

@Repository("cityListRespository")
public interface CityListRepository extends JpaRepository<CityList, String> {

	@Query(value = "SELECT * FROM city_list where institution_code = :institutionCode AND city_code = :cityCode   order by date_create desc", nativeQuery = true)
	Optional<CityList> find(@Param("institutionCode") String institutionCode, @Param("cityCode") String cityCode);

	@Query(value = "SELECT * FROM city_list where city_code = :cityCode  order by date_create desc", nativeQuery = true)
	Optional<CityList> find(@Param("cityCode") String cityCode);

	@Query(value = "SELECT * FROM city_list where institution_code = :institutionCode  order by date_create desc", nativeQuery = true)
	List<CityList> findList(@Param("institutionCode") String institutionCode);

	@Query(value = "SELECT * FROM city_list where institution_code = :institutionCode AND region_code = :regionCode AND country_code = :countryCode  order by date_create desc", nativeQuery = true)
	List<CityList> findListByCountryCode(@Param("institutionCode") String institutionCode,
			@Param("regionCode") String regionCode, @Param("countryCode") String countryCode);

	boolean existsByCityNameAndInstitutionCode(String cityName,String institutionCode);

	@Query(value = "SELECT * FROM city_list where institution_code = :institutionCode AND city_name = :cityName AND city_code <> :cityCode order by date_create desc", nativeQuery = true)
	List<CityList> findcity(@Param("institutionCode") String institutionCode, @Param("cityName") String cityName,@Param("cityCode") String cityCode);
}
