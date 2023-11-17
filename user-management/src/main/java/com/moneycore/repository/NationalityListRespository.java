package com.moneycore.repository;

import com.moneycore.entity.NationalityList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("nationalityListRespository")
public interface NationalityListRespository extends JpaRepository<NationalityList, Integer> {
	@Query(value = "SELECT * FROM nationality_list where nationality_id = :nationalityId", nativeQuery = true)
	Optional<NationalityList> findByNationalityId(@Param("nationalityId") int nationalityId);

	@Query(value = "SELECT * FROM nationality_list where nationality_code = :nationalityCode", nativeQuery = true)
	Optional<NationalityList> findByNationalityCode(@Param("nationalityCode") String nationalityCode);

	@Query(value = "SELECT * FROM nationality_list", nativeQuery = true)
	List<NationalityList> fetchNationality();
}