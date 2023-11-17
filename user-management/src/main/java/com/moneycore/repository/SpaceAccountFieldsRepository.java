package com.moneycore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.moneycore.entity.SpaceAccountFields;

@Repository("spaceAccountFieldsRepository")
public interface SpaceAccountFieldsRepository extends JpaRepository<SpaceAccountFields, Integer> {

	@Query(value = "SELECT * FROM space_account_fields where institution_list_fk = :institutionCode AND display_in_mobile=true order by date_create desc", nativeQuery = true)
	List<SpaceAccountFields> findList(@Param("institutionCode") String institutionCode);

}
