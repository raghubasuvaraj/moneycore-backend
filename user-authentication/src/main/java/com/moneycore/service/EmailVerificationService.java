package com.moneycore.service;

import java.util.Optional;

import com.moneycore.entity.EmailVerification;

public interface EmailVerificationService {

	EmailVerification addVerificationCode(int clientCode, String newEmail);

	Optional<EmailVerification> find(int clientCode, String newEmail, String verificationCode);

	void deleteExisting(int clientCode);

}
