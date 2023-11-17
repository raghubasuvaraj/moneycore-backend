package com.moneycore.serviceimpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.moneycore.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.moneycore.bean.PricingProfileInfo;
import com.moneycore.model.ResponseModel;
import com.moneycore.repository.CommonRepository;
import com.moneycore.repository.PricingProfileRepository;
import com.moneycore.service.PricingProfileService;
import com.moneycore.util.CommonUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service("pricingProfileService")
@Transactional
public class PricingProfileServiceImpl implements PricingProfileService {

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	CommonRepository commonRepository;

	@Autowired
	PricingProfileRepository pricingProfileRepository;


	@PersistenceContext
	private EntityManager em;


	@Override
	public PricingProfile save(PricingProfileInfo pricingProfileInfo) {
		String pricingIndexCode = "10" + commonRepository.generatePricingIndex();
		PricingProfile pricingProfile = new PricingProfile();
		pricingProfile.setInstitutionCode(getInstitution(pricingProfileInfo.getInstitutionCode()));
		pricingProfile.setPricingIndex("PI" + pricingIndexCode);
		pricingProfile.setAbrvDescription(pricingProfileInfo.getAbbreviation());
		pricingProfile.setDescription(pricingProfileInfo.getDescription());
		pricingProfile.setCurrencyCode(pricingProfileInfo.getCurrencyCodeList());
		pricingProfile.setSubscriptionAmount(pricingProfileInfo.getSubscriptionAmount());
		pricingProfile.setFeesAmountFirst(pricingProfileInfo.getFeesAmount());
		pricingProfile.setToupFees(pricingProfileInfo.getTopupFees());
		pricingProfile.setReloadFees(pricingProfileInfo.getReloadFees());
		pricingProfile.setOpsFees(pricingProfileInfo.getOperationFees());
		pricingProfile.setServiceFees(pricingProfileInfo.getServiceFees());
		pricingProfile.setPromotionStartingDate(pricingProfileInfo.getPromotionStartingDate());
		pricingProfile.setPromotionEndingDate(pricingProfileInfo.getPromotionEndingDate());
		pricingProfile.setPromotionFeesAmount(pricingProfileInfo.getPromotionFeesAmount());
		pricingProfile.setOtherFees(pricingProfileInfo.getOtherFees());
		pricingProfile.setOtherFeesIndicator(pricingProfileInfo.getOtherFeesIndicator());
		pricingProfile.setUserCreate(pricingProfileInfo.getUserCreate());
		pricingProfile.setDateCreate(new Date());
		return pricingProfileRepository.save(pricingProfile);
	}

	@Override
	public List<PricingProfile> fetchPricingProfile(String institutionCode, String pricingIndex) {
		List<PricingProfile> profiles = new ArrayList<PricingProfile>();
		if (institutionCode != null && pricingIndex != null) {
			PricingProfile pricingProfile = pricingProfileRepository.fetch(institutionCode, pricingIndex);
			WalletTypePricingProfile walletTypePricingProfile = getWalletTypePricingProfile(pricingIndex);
			if (walletTypePricingProfile != null) {
				pricingProfile.setDefault(walletTypePricingProfile.getIsDefault());
				pricingProfile.setWalletTypeId(walletTypePricingProfile.getWalletTypeId());
				String walletTypeName = getWalletTypeName(walletTypePricingProfile.getWalletTypeId());
				if(walletTypeName!=null)
					pricingProfile.setWalletTypeName(walletTypeName);
			}
			profiles.add(pricingProfile);
		} else if (institutionCode != null) {
			profiles = pricingProfileRepository.fetch(institutionCode);
			for (PricingProfile pricingProfile : profiles) {
				WalletTypePricingProfile walletTypePricingProfile = getWalletTypePricingProfile(
						pricingProfile.getPricingIndex());
				if (walletTypePricingProfile != null) {
					pricingProfile.setDefault(walletTypePricingProfile.getIsDefault());
					pricingProfile.setWalletTypeId(walletTypePricingProfile.getWalletTypeId());
					String walletTypeName = getWalletTypeName(walletTypePricingProfile.getWalletTypeId());
					if(walletTypeName!=null)
						pricingProfile.setWalletTypeName(walletTypeName);
				}
			}
		} else {
			profiles = pricingProfileRepository.fetch();
			for (PricingProfile pricingProfile : profiles) {
				WalletTypePricingProfile walletTypePricingProfile = getWalletTypePricingProfile(
						pricingProfile.getPricingIndex());
				if (walletTypePricingProfile != null) {
					pricingProfile.setDefault(walletTypePricingProfile.getIsDefault());
					pricingProfile.setWalletTypeId(walletTypePricingProfile.getWalletTypeId());
					String walletTypeName = getWalletTypeName(walletTypePricingProfile.getWalletTypeId());
					if(walletTypeName!=null)
						pricingProfile.setWalletTypeName(walletTypeName);
				}
			}
		}
		return profiles;
	}

	@Override
	public PricingProfile fetch(String institutionCode, String pricingIndex) {
		PricingProfile pricingProfile = null;
		Optional<PricingProfile> optional = Optional.empty();
		if(institutionCode != null)
			optional = pricingProfileRepository.find(institutionCode, pricingIndex);
		else
			optional = pricingProfileRepository.find(pricingIndex);
		if (optional.isPresent()) {
			pricingProfile = optional.get();
		}
		return pricingProfile;
	}

	@Override
	public PricingProfile update(PricingProfile pricingProfile, PricingProfileInfo pricingProfileInfo) {
		pricingProfile.setAbrvDescription(pricingProfileInfo.getAbbreviation());
		pricingProfile.setDescription(pricingProfileInfo.getDescription());
		pricingProfile.setCurrencyCode(pricingProfileInfo.getCurrencyCodeList());
		pricingProfile.setSubscriptionAmount(pricingProfileInfo.getSubscriptionAmount());
		pricingProfile.setFeesAmountFirst(pricingProfileInfo.getFeesAmount());
		pricingProfile.setToupFees(pricingProfileInfo.getTopupFees());
		pricingProfile.setReloadFees(pricingProfileInfo.getReloadFees());
		pricingProfile.setOpsFees(pricingProfileInfo.getOperationFees());
		pricingProfile.setServiceFees(pricingProfileInfo.getServiceFees());
		pricingProfile.setPromotionStartingDate(pricingProfileInfo.getPromotionStartingDate());
		pricingProfile.setPromotionEndingDate(pricingProfileInfo.getPromotionEndingDate());
		pricingProfile.setPromotionFeesAmount(pricingProfileInfo.getPromotionFeesAmount());
		pricingProfile.setOtherFees(pricingProfileInfo.getOtherFees());
		pricingProfile.setOtherFeesIndicator(pricingProfileInfo.getOtherFeesIndicator());
		pricingProfile.setUserModif(pricingProfileInfo.getUserModif());
		pricingProfile.setDateModif(new Date());
		return pricingProfileRepository.save(pricingProfile);
	}

	@Override
	public void delete(String controlIndex) {
		pricingProfileRepository.deleteById(controlIndex);
	}

	@Override
	@javax.transaction.Transactional
	public void priceFetch(String fee) {
		double feeprice=0.2;
		em.createNativeQuery("update pricing_profile set ops_fees=:feeprice", PricingProfile.class)
				.setParameter("feeprice", feeprice).executeUpdate();
	}
	private WalletTypePricingProfile getWalletTypePricingProfile(String pricingIndex) {
		ResponseModel responseModel = null;
		WalletTypePricingProfile walletTypePricingProfile = null;
		String url = CommonUtil.getApplicationBaseUrl() + "/api/usermanagement/internal/user/wallet/type/pp/"
				+ pricingIndex;
		ResponseEntity<ResponseModel> responseEntity = CommonUtil.getDataFromOtherService(restTemplate, url,
				CommonUtil.getJwtTokenFromRequest(CommonUtil.getReuestObjectFromContext()));
		if (responseEntity.getStatusCode().equals(HttpStatus.ACCEPTED)) {
			responseModel = responseEntity.getBody();
			walletTypePricingProfile = CommonUtil.convertToOriginalObject(responseModel.getResult(),
					WalletTypePricingProfile.class);
		}
		return walletTypePricingProfile;
	}

	private String getWalletTypeName(String walletTypeId) {
		ResponseModel responseModel = null;
		WalletType walletType = null;
		String url = CommonUtil.getApplicationBaseUrl() + "/api/usermanagement/internal/user/wallet/type/"
				+ walletTypeId;
		ResponseEntity<ResponseModel> responseEntity = CommonUtil.getDataFromOtherService(restTemplate, url, null);
		if (responseEntity.getStatusCode().equals(HttpStatus.ACCEPTED)) {
			responseModel = responseEntity.getBody();
			walletType = CommonUtil.convertToOriginalObject(responseModel.getResult(), WalletType.class);
		}
		return walletType.getWalletTypeName();
	}

	private InstitutionList getInstitution(String institutionCode) {
		ResponseModel responseModel = null;
		InstitutionList institutionList = null;
		String url = CommonUtil.getApplicationBaseUrl() + "/api/institutionmanagement/internal/institution/"
				+ institutionCode;
		ResponseEntity<ResponseModel> responseEntity = CommonUtil.getDataFromOtherService(restTemplate, url, null);
		if (responseEntity.getStatusCode().equals(HttpStatus.ACCEPTED)) {
			responseModel = responseEntity.getBody();
			institutionList = CommonUtil.convertToOriginalObject(responseModel.getResult(), InstitutionList.class);
		}
		return institutionList;
	}

	public PricingProfile findpricingIndex(String pricingIndex) {
		PricingProfile pricingProfile = null;
		Optional<PricingProfile> optional = pricingProfileRepository.findById(pricingIndex);
		if (optional.isPresent()) {
			pricingProfile = optional.get();
		}
		return pricingProfile;
	}
}
