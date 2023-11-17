package com.moneycore.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.moneycore.entity.ProfilePicture;

@Repository("ProfilePictureRepository")
public interface ProfilePictureRepository extends JpaRepository<ProfilePicture, Integer> {

	@Query(value = "SELECT * FROM profile_picture where client_code = :clientCode", nativeQuery = true)
	public Optional<ProfilePicture> findByClientCode(@Param("clientCode") int clientCode);

}
