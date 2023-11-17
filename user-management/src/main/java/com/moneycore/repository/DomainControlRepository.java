package com.moneycore.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.moneycore.entity.DomainControl;

@Repository
public interface DomainControlRepository extends JpaRepository<DomainControl, String> {

	@Query(value = "SELECT * FROM domain_control where is_deleted=false order by date_create desc", nativeQuery = true)
	List<DomainControl> fetch();

	@Query(value = "SELECT * FROM domain_control where institution_code = :institutionCode AND control_index = :controlIndex and is_deleted=false order by date_create desc", nativeQuery = true)
	DomainControl fetch(@Param("institutionCode") String institutionCode,
			@Param("controlIndex") String controlIndex);

	@Query(value = "SELECT * FROM domain_control where institution_code = :institutionCode and is_deleted=false order by date_create desc", nativeQuery = true)
	List<DomainControl> fetch(@Param("institutionCode") String institutionCode);

	@Query(value = "SELECT * FROM domain_control where institution_code = :institutionCode AND control_index = :controlIndex  and is_deleted=false order by date_create desc", nativeQuery = true)
	Optional<DomainControl> find(@Param("institutionCode") String institutionCode,
			@Param("controlIndex") String controlIndex);

	@Query(value = "SELECT * FROM domain_control where institution_code = :institutionCode AND is_default = true and is_deleted=false order by date_create desc", nativeQuery = true)
	DomainControl checkDefault(@Param("institutionCode") String institutionCode);

	@Query(value = "SELECT * FROM domain_control where institution_code = :institutionCode AND merchant_id LIKE CONCAT('%',:clientCode,'%') and is_deleted=false  order by date_create desc", nativeQuery = true)
	Optional<DomainControl> checkIfClientHasDomainControl(@Param("institutionCode") String institutionCode,
			@Param("clientCode") String clientCode);

	@Query(value = "SELECT * FROM domain_control where control_index = :controlIndex and is_deleted=false order by date_create desc", nativeQuery = true)
	Optional<DomainControl> find(@Param("controlIndex") String controlIndex);

}
