package com.moneycore.serviceimpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moneycore.bean.WalletTypeInfo;
import com.moneycore.entity.WalletType;
import com.moneycore.entity.WalletTypeDomainControl;
import com.moneycore.entity.WalletTypePricingProfile;
import com.moneycore.repository.CommonRepository;
import com.moneycore.repository.WalletTypeRepository;
import com.moneycore.service.WalletTypeService;

@Service("walletTypeService")
@Transactional
public class WalletTypeServiceImpl implements WalletTypeService {

	@Autowired
	WalletTypeRepository walletTypeRepository;

	@Autowired
	CommonRepository commonRepository;
	public boolean findWalletTypeDup(String walletName,String institutionCode ){

		return  walletTypeRepository.existsByWalletTypeNameAndInstitutionCode(walletName,institutionCode);

	}
	public List<WalletType> findWalletTypeDup(String institutionCode,String walletName,String walletTypeId ){
		return  walletTypeRepository.findByWalletType(institutionCode,walletName,walletTypeId);

	}


	@Override
	public WalletType findWalletType(String institutionCode, String walletTypeId) {
		WalletType walletType = null;
		Optional<WalletType> optional = walletTypeRepository.find(institutionCode, walletTypeId);
		if (optional.isPresent()) {
			walletType = optional.get();
		}
		return walletType;
	}

	@Override
	public WalletType save(WalletTypeInfo walletTypeInfo) {
		WalletType walletType = new WalletType();
		walletType.setInstitutionCode(walletTypeInfo.getInstitutionCode());
		int id = commonRepository.generateWalletTypeId();
		walletType.setWalletTypeId("WT" + id);
		walletType.setWalletTypeName(walletTypeInfo.getWalletTypeName());
		walletType.setAbrvWording(walletTypeInfo.getAbbreviation());
		walletType.setWording(walletTypeInfo.getWording());
		WalletTypePricingProfile walletTypePricingProfile = new WalletTypePricingProfile();
		walletTypePricingProfile.setWalletTypeId(walletType.getWalletTypeId());
		walletTypePricingProfile.setPricingProfileId(walletTypeInfo.getPricingProfileId());
		walletTypePricingProfile.setDefault(true);
		walletType.setWalletTypePricingProfile(walletTypePricingProfile);
		WalletTypeDomainControl walletTypeDomainControl = new WalletTypeDomainControl();
		walletTypeDomainControl.setWalletTypeId(walletType.getWalletTypeId());
		walletTypeDomainControl.setDomainControlId(walletTypeInfo.getDomainControlId());
		walletTypeDomainControl.setDefault(true);
		walletType.setWalletTypeDomainControl(walletTypeDomainControl);
		walletType.setUserCreate(walletTypeInfo.getUserCreate());
		walletType.setDateCreate(new Date());
		return walletTypeRepository.save(walletType);
	}

	@Override
	public WalletType update(WalletType walletType, WalletTypeInfo walletTypeInfo) {
		walletType.setAbrvWording(walletTypeInfo.getAbbreviation());
		walletType.setWalletTypeName(walletTypeInfo.getWalletTypeName());
		walletType.setWording(walletTypeInfo.getWording());
		WalletTypePricingProfile walletTypePricingProfile = walletType.getWalletTypePricingProfile();
		walletTypePricingProfile.setWalletTypeId(walletType.getWalletTypeId());
		if (!walletType.getWalletTypePricingProfile().getPricingProfileId()
				.equalsIgnoreCase(walletTypeInfo.getPricingProfileId())) {
			walletTypePricingProfile.setPricingProfileId(walletTypeInfo.getPricingProfileId());
			walletTypePricingProfile.setDefault(true);
		}
		walletType.setWalletTypePricingProfile(walletTypePricingProfile);
		WalletTypeDomainControl walletTypeDomainControl = walletType.getWalletTypeDomainControl();
		walletTypeDomainControl.setWalletTypeId(walletType.getWalletTypeId());
		if (!walletType.getWalletTypeDomainControl().getDomainControlId()
				.equalsIgnoreCase(walletTypeInfo.getDomainControlId())) {
			walletTypeDomainControl.setDomainControlId(walletTypeInfo.getDomainControlId());
			walletTypeDomainControl.setDefault(true);
		}
		walletType.setWalletTypeDomainControl(walletTypeDomainControl);
		walletType.setUserModif(walletTypeInfo.getUserModif());
		walletType.setDateModif(new Date());
		return walletTypeRepository.save(walletType);
	}

	@Override
	public List<WalletType> fetchWalletType(String institutionCode) {
		return walletTypeRepository.findList(institutionCode);
	}

	@Override
	public WalletType findDefaultCheck(String institutionCode) {
		return walletTypeRepository.findDefaultIsAlreadyPresent(institutionCode);
	}

	@Override
	public WalletType findWalletType(String walletTypeId) {
		WalletType walletType = null;
		Optional<WalletType> optional = walletTypeRepository.find(walletTypeId);
		if (optional.isPresent()) {
			walletType = optional.get();
		}
		return walletType;
	}
	public void delete(String walletTypeId) {
		walletTypeRepository.deleteById(walletTypeId);
	}
}
