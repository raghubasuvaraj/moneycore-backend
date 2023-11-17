package com.moneycore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.moneycore.entity.Profile;

@Repository("profileRepository")
public interface ProfileRepository extends JpaRepository<Profile, Integer>{
	public Profile findByName(String name);

	@Query(value = "SELECT * FROM profile WHERE institution_fk =:institutionCode and name != 'SUPER ADMIN' order by profile_id DESC", nativeQuery = true)
	public List<Profile> findByIC(@Param("institutionCode") String institutionCode);


	@Query(value = "select a.* from profile a  where a.name=:profileName and  a.institution_fk=:institutionCode ",nativeQuery = true)
	List<Profile> findByNameandInstitutionName(@Param("profileName") String profileName, @Param("institutionCode") String institutionCode);


	@Query(value = "select a.* from profile a  where a.name=:profileName and  a.institution_fk=:institutionCode and profile_id <> :profileId ",nativeQuery = true)
	List<Profile> findByProfileName(@Param("profileName") String profileName, @Param("institutionCode") String institutionCode,@Param("profileId")  Integer profileId);

	@Query(value = "select u.* from profile u where u.name != 'SUPER ADMIN' order by u.profile_id DESC",nativeQuery = true)
	List<Profile> findAllProfile();
}
