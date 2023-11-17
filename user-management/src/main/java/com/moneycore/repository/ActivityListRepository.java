package com.moneycore.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.moneycore.entity.ActivityList;

@Repository
public interface ActivityListRepository extends JpaRepository<ActivityList, String> {

	@Query(value = "SELECT * FROM activity_list where institution_code = :institutionCode AND activity_code = :activityCode  order by date_create desc", nativeQuery = true)
	Optional<ActivityList> find(@Param("institutionCode") String institutionCode,
			@Param("activityCode") String activityCode);

	@Query(value = "SELECT * FROM activity_list where institution_code = :institutionCode  order by date_create desc", nativeQuery = true)
	List<ActivityList> findList(@Param("institutionCode") String institutionCode);

	@Query(value = "SELECT * FROM activity_list where activity_code = :activityCode  order by date_create desc", nativeQuery = true)
	Optional<ActivityList> find(@Param("activityCode") String activityCode);

	boolean existsByActivityNameAndInstitutionCode(String activityName,String institutionCode);

	@Query(value = "SELECT * FROM activity_list where institution_code = :institutionCode AND activity_name= :activityName AND activity_code <> :activityCode order by date_create desc", nativeQuery = true)
	List<ActivityList> findByActivityName(@Param("institutionCode") String institutionCode,@Param("activityName") String activityName,
								@Param("activityCode") String activityCode);
}
