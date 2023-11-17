package com.moneycore.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.moneycore.entity.MccList;

@Repository("mccListRepository")
public interface MccListRepository extends JpaRepository<MccList, String> {

	@Query(value = "SELECT * FROM mcc_list WHERE institution_code = :institutionCode AND mcc_code = :mccCode  order by date_create desc", nativeQuery = true)
	Optional<MccList> find(@Param("institutionCode") String institutionCode, @Param("mccCode") String mccCode);

	@Query(value = "SELECT * FROM mcc_list WHERE mcc_code = :mccCode  order by date_create desc", nativeQuery = true)
	Optional<MccList> find(@Param("mccCode") String mccCode);

	@Query(value = "SELECT * FROM mcc_list WHERE institution_code = :institutionCode  order by date_create desc", nativeQuery = true)
	List<MccList> findList(@Param("institutionCode") String institutionCode);

	boolean existsByMccNameAndInstitutionCode(String mccName,String institutionCode);

	@Query(value = "SELECT * FROM mcc_list WHERE institution_code = :institutionCode AND mcc_name=:mccName  AND mcc_code <> :mccCode   order by date_create desc", nativeQuery = true)
	List<MccList> findByMccName(@Param("institutionCode") String institutionCode,@Param("mccName") String mccName, @Param("mccCode") String mccCode);

}
