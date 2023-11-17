package com.moneycore.service;

import com.moneycore.entity.WalletTypePricingProfile;

public interface WalletTypePricingProfileService {

	WalletTypePricingProfile findPricingIndex(String pricingIndex);

	WalletTypePricingProfile findPricingIndexByWalletType(String walletTypeId);

}
