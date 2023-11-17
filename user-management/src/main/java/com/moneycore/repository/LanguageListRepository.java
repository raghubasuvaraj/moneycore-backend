package com.moneycore.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.moneycore.entity.LanguageList;

@Repository("languageListRepository")
public interface LanguageListRepository extends JpaRepository<LanguageList, String> {

	@Query(value = "SELECT * FROM language_list where institution_code = :institutionCode AND language_code = :languageCode  order by date_create desc", nativeQuery = true)
	Optional<LanguageList> find(@Param("institutionCode") String institutionCode,
			@Param("languageCode") String languageCode);

	@Query(value = "SELECT * FROM language_list where institution_code = :institutionCode  order by date_create desc", nativeQuery = true)
	List<LanguageList> findList(@Param("institutionCode") String institutionCode);

	@Query(value = "SELECT * FROM language_list where language_code = :languageCode  order by date_create desc", nativeQuery = true)
	Optional<LanguageList> find(@Param("languageCode") String languageCode);

	boolean existsByLanguageNameAndInstitutionCode(String languageName,String institutionCode);

	@Query(value = "SELECT * FROM language_list where institution_code = :institutionCode AND language_name=:languageName AND language_code <> :languageCode  order by date_create desc", nativeQuery = true)
	List<LanguageList> findByLang(@Param("institutionCode") String institutionCode,@Param("languageName") String languageName,@Param("languageCode") String languageCode);

}
