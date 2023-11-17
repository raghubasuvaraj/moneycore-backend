package com.moneycore.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.moneycore.entity.EmailVerification;

@Repository("EmailVerificationRepository")
public interface EmailVerificationRepository extends JpaRepository<EmailVerification, Integer> {

	@Query(value = "DELETE FROM email_verification where client_code = :clientCode", nativeQuery = true)
	public void deleteByClientCode(@Param("clientCode") int clientCode);

	@Query(value = "SELECT * FROM email_verification where client_code = :clientCode AND email= :newEmail", nativeQuery = true)
	public Optional<EmailVerification> findExisting(@Param("clientCode") int clientCode, @Param("newEmail") String newEmail);

	@Query(value = "SELECT * FROM email_verification where client_code = :clientCode AND email= :newEmail AND verification_code= :verificationCode", nativeQuery = true)
	public Optional<EmailVerification> findExisting(@Param("clientCode") int clientCode, @Param("newEmail") String newEmail,
			@Param("verificationCode") String verificationCode);

}
