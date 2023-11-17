package com.moneycore.bean;

import java.util.ArrayList;
import java.util.List;

import com.moneycore.entity.WalletType;

public class WalletTypeInfo {

	private String institutionCode;
	private String walletTypeId;
	private String walletTypeName;
	private String abbreviation;
	private String wording;
	private String pricingProfileId;
	private String domainControlId;
	private String userCreate;
	private String userModif;

	public String getInstitutionCode() {
		return institutionCode;
	}

	public void setInstitutionCode(String institutionCode) {
		this.institutionCode = institutionCode;
	}

	public String getWalletTypeId() {
		return walletTypeId;
	}

	public void setWalletTypeId(String walletTypeId) {
		this.walletTypeId = walletTypeId;
	}

	public String getWalletTypeName() {
		return walletTypeName;
	}

	public void setWalletTypeName(String walletTypeName) {
		this.walletTypeName = walletTypeName;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public String getWording() {
		return wording;
	}

	public void setWording(String wording) {
		this.wording = wording;
	}

	public String getPricingProfileId() {
		return pricingProfileId;
	}

	public void setPricingProfileId(String pricingProfileId) {
		this.pricingProfileId = pricingProfileId;
	}

	public String getDomainControlId() {
		return domainControlId;
	}

	public void setDomainControlId(String domainControlId) {
		this.domainControlId = domainControlId;
	}

	public String getUserCreate() {
		return userCreate;
	}

	public void setUserCreate(String userCreate) {
		this.userCreate = userCreate;
	}

	public String getUserModif() {
		return userModif;
	}

	public void setUserModif(String userModif) {
		this.userModif = userModif;
	}

	public static List<WalletTypeInfo> createResponse(List<WalletType> walletType) {
		List<WalletTypeInfo> walletTypeInfos = new ArrayList<WalletTypeInfo>();
		for (WalletType list : walletType) {
			WalletTypeInfo walletTypeInfo = new WalletTypeInfo();
			walletTypeInfo.setInstitutionCode(list.getInstitutionCode());
			walletTypeInfo.setWalletTypeId(list.getWalletTypeId());
			walletTypeInfo.setWalletTypeName(list.getWalletTypeName());
			walletTypeInfo.setAbbreviation(list.getAbrvWording());
			walletTypeInfo.setWording(list.getWording());
			walletTypeInfo.setPricingProfileId(list.getWalletTypePricingProfile().getPricingProfileId());
			walletTypeInfo.setDomainControlId(list.getWalletTypeDomainControl().getDomainControlId());
			walletTypeInfo.setUserCreate(list.getUserCreate());
			walletTypeInfo.setUserModif(list.getUserModif());
			walletTypeInfos.add(walletTypeInfo);
		}
		return walletTypeInfos;
	}

}
