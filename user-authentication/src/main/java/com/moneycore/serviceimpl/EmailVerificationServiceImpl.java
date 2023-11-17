package com.moneycore.serviceimpl;

import java.util.Date;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moneycore.entity.EmailVerification;
import com.moneycore.repository.EmailVerificationRepository;
import com.moneycore.service.EmailVerificationService;

@Service("emailVerificationService")
public class EmailVerificationServiceImpl implements EmailVerificationService {

	@Autowired
	EmailVerificationRepository emailVerificationRepository;

	@Override
	public EmailVerification addVerificationCode(int clientCode, String newEmail) {
		String verificationCode = generateVerificationCode();
		EmailVerification emailVerification = new EmailVerification();
		emailVerification.setVerificationCode(verificationCode);
		emailVerification.setClientCode(clientCode);
		emailVerification.setEmail(newEmail);
		emailVerification.setVerificationDate(new Date());
		return emailVerificationRepository.save(emailVerification);
	}

	private String generateVerificationCode() {
		Random random = new Random();
		int number = random.nextInt(999999);
		return String.format("%06d", number);
	}

	@Override
	public Optional<EmailVerification> find(int clientCode, String newEmail, String verificationCode) {
		Optional<EmailVerification> optional = null;
		if (verificationCode == null) {
			optional = emailVerificationRepository.findExisting(clientCode, newEmail);
		} else {
			optional = emailVerificationRepository.findExisting(clientCode, newEmail, verificationCode);
		}
		return optional;
	}

	@Override
	public void deleteExisting(int clientCode) {
		emailVerificationRepository.deleteByClientCode(clientCode);
	}
}
