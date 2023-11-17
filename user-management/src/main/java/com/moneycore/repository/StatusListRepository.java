package com.moneycore.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.moneycore.entity.StatusList;

@Repository("statusListRepository")
public interface StatusListRepository extends JpaRepository<StatusList, String> {

	@Query(value = "SELECT * FROM status_list where institution_code = :institutionCode AND status_code = :statusCode  order by date_create desc", nativeQuery = true)
	Optional<StatusList> find(@Param("institutionCode") String institutionCode, @Param("statusCode") String statusCode);

	@Query(value = "SELECT * FROM status_list where institution_code = :institutionCode  order by date_create desc", nativeQuery = true)
	List<StatusList> findList(@Param("institutionCode") String institutionCode);

	@Query(value = "SELECT * FROM status_list where status_code = :statusCode  order by date_create desc", nativeQuery = true)
	Optional<StatusList> find(@Param("statusCode") String statusCode);
	boolean existsByStatusNameAndInstitutionCode(String Name,String institutionCode);

	@Query(value = "SELECT * FROM status_list where institution_code = :institutionCode AND status_name =:StatusName  AND status_code <> :statusCode  order by date_create desc", nativeQuery = true)
	List<StatusList> findByStatusName(@Param("institutionCode") String institutionCode,@Param("StatusName") String StatusName, @Param("statusCode") String statusCode);

}
