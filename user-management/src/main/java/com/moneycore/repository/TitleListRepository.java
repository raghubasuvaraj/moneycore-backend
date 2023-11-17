package com.moneycore.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.moneycore.entity.TitleList;

@Repository
public interface TitleListRepository extends JpaRepository<TitleList, String> {

	@Query(value = "SELECT * FROM title_list where institution_code = :institutionCode AND title_code = :titleCode  order by date_create desc", nativeQuery = true)
	Optional<TitleList> find(@Param("institutionCode") String institutionCode, @Param("titleCode") String titleCode);

	@Query(value = "SELECT * FROM title_list where institution_code = :institutionCode  order by date_create desc", nativeQuery = true)
	List<TitleList> fetchList(@Param("institutionCode") String institutionCode);

	@Query(value = "SELECT * FROM title_list where title_code = :titleCode  order by date_create desc", nativeQuery = true)
	Optional<TitleList> find(@Param("titleCode") String titleCode);
	boolean existsByTitleNameAndInstitutionCode(String Name,String institutionCode);

	@Query(value = "SELECT * FROM title_list where institution_code = :institutionCode AND title_name =:titleName AND title_code <> :titleCode  order by date_create desc", nativeQuery = true)
	List<TitleList> findByTitleName(@Param("institutionCode") String institutionCode, @Param("titleName") String titleName, @Param("titleCode") String titleCode);

}
