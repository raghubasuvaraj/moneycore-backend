package com.moneycore.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.moneycore.entity.InstitutionList;

@Repository("institutionRepository")
public interface InstitutionRepository extends JpaRepository<InstitutionList, String> {

	@Query(value = "SELECT * FROM institution_list WHERE email = :emailAddress and is_deleted=false", nativeQuery = true)
	Optional<InstitutionList> findByEmailId(@Param("emailAddress") String emailAddress);

	boolean existsByInstitutionName(String institutionName);

	@Query(value = "SELECT * FROM institution_list WHERE institution_name = :institutionName and institution_code <>:institutionCode ", nativeQuery = true)
	List<InstitutionList> FindByName(@Param("institutionName")String institutionName,@Param("institutionCode") String institutionCode);

	@Query(value = "select u.* from institution_list u where u.user_create != '0'", nativeQuery = true)
	List<InstitutionList> findAllInstitut();
}