package com.moneycore.service;

import java.util.List;

import com.moneycore.bean.PricingProfileInfo;
import com.moneycore.entity.Client;
import com.moneycore.entity.PricingProfile;

public interface PricingProfileService {

	PricingProfile save(PricingProfileInfo pricingProfileInfo);

	List<PricingProfile> fetchPricingProfile(String institutionCode, String pricingIndex);


	public void priceFetch(String fee);
	PricingProfile fetch(String institutionCode, String pricingIndex);

	PricingProfile findpricingIndex(String pricingIndex);
	PricingProfile update(PricingProfile pricingProfile, PricingProfileInfo pricingProfileInfo);
	public void delete(String pricingIndex);
}
