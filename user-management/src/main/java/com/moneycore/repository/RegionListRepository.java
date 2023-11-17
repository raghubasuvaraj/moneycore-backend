package com.moneycore.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.moneycore.entity.RegionList;

@Repository
public interface RegionListRepository extends JpaRepository<RegionList, String> {

	@Query(value = "SELECT * FROM region_list where institution_code = :institutionCode AND region_code = :regionCode  order by date_create desc", nativeQuery = true)
	Optional<RegionList> find(@Param("institutionCode") String institutionCode, @Param("regionCode") String regionCode);

	@Query(value = "SELECT * FROM region_list where institution_code = :institutionCode  order by date_create desc", nativeQuery = true)
	List<RegionList> fetchList(@Param("institutionCode") String institutionCode);

	@Query(value = "SELECT * FROM region_list where region_code = :regionCode  order by date_create desc", nativeQuery = true)
	Optional<RegionList> find(@Param("regionCode") String regionCode);

	@Query(value = "SELECT * FROM region_list where institution_code = :institutionCode AND region_name = :regionName ", nativeQuery = true)
	List<RegionList> FindByRegionName(@Param("regionName")String regionName,@Param("institutionCode") String institutionCode);

	@Query(value = "SELECT * FROM region_list where institution_code = :institutionCode AND region_name = :regionName  and region_code <> :regionCode ", nativeQuery = true)
	List<RegionList> FindByRegionName(@Param("regionName") String regionName,@Param("institutionCode") String institutionCode,@Param("regionCode") String regionCode);

}
