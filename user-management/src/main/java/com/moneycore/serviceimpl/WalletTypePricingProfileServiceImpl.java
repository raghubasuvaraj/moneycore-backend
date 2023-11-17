package com.moneycore.serviceimpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moneycore.entity.WalletTypePricingProfile;
import com.moneycore.repository.WalletTypePricingProfileRepository;
import com.moneycore.service.WalletTypePricingProfileService;

@Service
@Transactional
public class WalletTypePricingProfileServiceImpl implements WalletTypePricingProfileService {

	@Autowired
	WalletTypePricingProfileRepository walletTypePricingProfileRepository;

	@Override
	public WalletTypePricingProfile findPricingIndex(String pricingIndex) {
		WalletTypePricingProfile walletTypePricingProfile = null;
		Optional<WalletTypePricingProfile> optional = walletTypePricingProfileRepository.find(pricingIndex);
		if (optional.isPresent()) {
			walletTypePricingProfile = optional.get();
		}
		return walletTypePricingProfile;
	}

	@Override
	public WalletTypePricingProfile findPricingIndexByWalletType(String walletTypeId) {
		WalletTypePricingProfile walletTypePricingProfile = null;
		Optional<WalletTypePricingProfile> optional = walletTypePricingProfileRepository.findByWalletType(walletTypeId);
		if (optional.isPresent()) {
			walletTypePricingProfile = optional.get();
		}
		return walletTypePricingProfile;
	}

}
