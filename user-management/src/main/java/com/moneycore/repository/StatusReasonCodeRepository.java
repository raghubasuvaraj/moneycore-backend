package com.moneycore.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.moneycore.entity.StatusReasonCode;

@Repository("statusReasonCodeRepository")
public interface StatusReasonCodeRepository extends JpaRepository<StatusReasonCode, String> {

	@Query(value = "SELECT * FROM status_reason_code where institution_code = :institutionCode AND status_code = :statusCode AND status_reason_code = :statusReasonCode  order by date_create desc", nativeQuery = true)
	Optional<StatusReasonCode> find(@Param("institutionCode") String institutionCode,
			@Param("statusCode") String statusCode, @Param("statusReasonCode") String statusReasonCode);

	@Query(value = "SELECT * FROM status_reason_code where institution_code = :institutionCode AND status_code = :statusCode order by date_create desc", nativeQuery = true)
	List<StatusReasonCode> findList(@Param("institutionCode") String institutionCode,
			@Param("statusCode") String statusCode);

	@Query(value = "SELECT * FROM status_reason_code where institution_code = :institutionCode order by date_create desc", nativeQuery = true)
	List<StatusReasonCode> findListIC(@Param("institutionCode") String institutionCode);

	@Query(value = "SELECT * FROM status_reason_code where status_reason_code = :statusReasonCode order by date_create desc", nativeQuery = true)
	Optional<StatusReasonCode> find(@Param("statusReasonCode") String statusReasonCode);

	@Query(value = "SELECT * FROM status_reason_code where  institution_code = :institutionCode AND status_code = :statusCode  order by date_create desc", nativeQuery = true)
	Optional<StatusReasonCode> findStatusReasonCodeByStatusCode(@Param("institutionCode") String institutionCode, @Param("statusCode") String statusCode);

	boolean existsByStatusReasonNameAndInstitutionCode(String Name,String institutionCode);

	@Query(value = "SELECT * FROM status_reason_code where institution_code = :institutionCode AND status_reason_name = :statusReasonName AND status_reason_code <> :statusReasonCode   order by date_create desc", nativeQuery = true)
	List <StatusReasonCode> findByStatusReasonName(@Param("institutionCode") String institutionCode,@Param("statusReasonName") String statusReasonName, @Param("statusReasonCode") String statusReasonCode);

}
