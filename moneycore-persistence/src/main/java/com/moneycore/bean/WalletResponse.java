package com.moneycore.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.moneycore.entity.CountryList;
import com.moneycore.entity.NationalityList;
import com.moneycore.entity.Wallet;

public class WalletResponse {

	private String walletId;
	private String prPhone1Code;
	private String prPhone1;
	private boolean activationFlag;
	private int clientCode;
	private String familyName;
	private String email;
	private String firstName;
	private String middleName;
	private String lastName;
	private String gender;
	private String prEmail;
	private String currencyCode;
	private Date statusDate;
	private CountryList country;
	private NationalityList nationality;
	private String controlIndex;
	private String pricingIndex;
	private String branchCode;
	private String branchName;
	private String titleCode;
	private String titleName;
	private String languageCode;
	private String languageName;
	private String statusReasonCode;
	private String statusReasonName;
	private String statusCode;
	private String statusName;
	private String institutionCode;
	private String institutionName;
	private String walletTypeId;
	private String walletTypeName;
	private String accountTypeCode;
	private String accountTypeName;
	private Date dateCreate;
	private boolean isMerchant;
	private String statusFromDate;
	private String statusToDate;

	public String getWalletId() {
		return walletId;
	}

	public void setWalletId(String walletId) {
		this.walletId = walletId;
	}

	public String getPrPhone1Code() {
		return prPhone1Code;
	}

	public void setPrPhone1Code(String prPhone1Code) {
		this.prPhone1Code = prPhone1Code;
	}

	public String getPrPhone1() {
		return prPhone1;
	}

	public void setPrPhone1(String prPhone1) {
		this.prPhone1 = prPhone1;
	}

	public boolean isActivationFlag() {
		return activationFlag;
	}

	public void setActivationFlag(boolean activationFlag) {
		this.activationFlag = activationFlag;
	}

	public int getClientCode() {
		return clientCode;
	}

	public void setClientCode(int clientCode) {
		this.clientCode = clientCode;
	}

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPrEmail() {
		return prEmail;
	}

	public void setPrEmail(String prEmail) {
		this.prEmail = prEmail;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public Date getStatusDate() {
		return statusDate;
	}

	public void setStatusDate(Date statusDate) {
		this.statusDate = statusDate;
	}

	public CountryList getCountry() {
		return country;
	}

	public void setCountry(CountryList country) {
		this.country = country;
	}

	public NationalityList getNationality() {
		return nationality;
	}

	public void setNationality(NationalityList nationality) {
		this.nationality = nationality;
	}

	public String getControlIndex() {
		return controlIndex;
	}

	public void setControlIndex(String controlIndex) {
		this.controlIndex = controlIndex;
	}

	public String getPricingIndex() {
		return pricingIndex;
	}

	public void setPricingIndex(String pricingIndex) {
		this.pricingIndex = pricingIndex;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getTitleCode() {
		return titleCode;
	}

	public void setTitleCode(String titleCode) {
		this.titleCode = titleCode;
	}

	public String getTitleName() {
		return titleName;
	}

	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}

	public String getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	public String getLanguageName() {
		return languageName;
	}

	public void setLanguageName(String languageName) {
		this.languageName = languageName;
	}

	public String getStatusReasonCode() {
		return statusReasonCode;
	}

	public void setStatusReasonCode(String statusReasonCode) {
		this.statusReasonCode = statusReasonCode;
	}

	public String getStatusReasonName() {
		return statusReasonName;
	}

	public void setStatusReasonName(String statusReasonName) {
		this.statusReasonName = statusReasonName;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getInstitutionCode() {
		return institutionCode;
	}

	public void setInstitutionCode(String institutionCode) {
		this.institutionCode = institutionCode;
	}

	public String getInstitutionName() {
		return institutionName;
	}

	public void setInstitutionName(String institutionName) {
		this.institutionName = institutionName;
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

	public String getAccountTypeCode() {
		return accountTypeCode;
	}

	public void setAccountTypeCode(String accountTypeCode) {
		this.accountTypeCode = accountTypeCode;
	}

	public String getAccountTypeName() {
		return accountTypeName;
	}

	public void setAccountTypeName(String accountTypeName) {
		this.accountTypeName = accountTypeName;
	}

	public Date getDateCreate() {
		return dateCreate;
	}

	public void setDateCreate(Date dateCreate) {
		this.dateCreate = dateCreate;
	}

	public boolean isMerchant() {
		return isMerchant;
	}

	public void setMerchant(boolean merchant) {
		isMerchant = merchant;
	}

	public String getStatusFromDate() {
		return statusFromDate;
	}

	public void setStatusFromDate(String statusFromDate) {
		this.statusFromDate = statusFromDate;
	}

	public String getStatusToDate() {
		return statusToDate;
	}

	public void setStatusToDate(String statusToDate) {
		this.statusToDate = statusToDate;
	}

	public static List<WalletResponse> createResponse(List<Wallet> wallets) {
		List<WalletResponse> walletResponses = new ArrayList<WalletResponse>();
		for (Wallet wallet : wallets) {
			WalletResponse walletResponse = new WalletResponse();
			walletResponse.setWalletId(wallet.getWalletId());
			walletResponse.setPrPhone1Code(wallet.getClientCode().getPrPhone1Code());
			walletResponse.setPrPhone1(wallet.getClientCode().getPrPhone1());
			walletResponse.setActivationFlag(wallet.getActivationFlag());
			walletResponse.setClientCode(wallet.getClientCode().getClientCode());
			walletResponse.setFamilyName(wallet.getClientCode().getFamilyName());
			walletResponse.setEmail(wallet.getClientCode().getPrEmail());
			walletResponse.setFirstName(wallet.getClientCode().getFirstName());
			walletResponse.setMiddleName(wallet.getClientCode().getMiddleName());
			walletResponse.setLastName(wallet.getClientCode().getFamilyName());
			walletResponse.setGender(wallet.getClientCode().getGender());
			walletResponse.setPrEmail(wallet.getClientCode().getPrEmail());
			walletResponse.setCurrencyCode(wallet.getClientCode().getCurrencyCode().getCurrencyCode());
			walletResponse.setStatusDate(wallet.getClientCode().getStatusDate());
			if (wallet.getClientCode().getNationalityCode() != null) {
				walletResponse.setNationality(wallet.getClientCode().getNationalityCode());
			}
			if (wallet.getClientCode().getCountryCode() != null) {
				walletResponse.setCountry(wallet.getClientCode().getCountryCode());
			}
			if (wallet.getControlProfile() != null)
				walletResponse.setControlIndex(wallet.getControlProfile().getControlIndex());
			if (wallet.getPricingProfile() != null)
				walletResponse.setPricingIndex(wallet.getPricingProfile().getPricingIndex());
			if (wallet.getClientCode().getBranchList() != null) {
				walletResponse.setBranchCode(wallet.getClientCode().getBranchList().getBranchCode());
				walletResponse.setBranchName(wallet.getClientCode().getBranchList().getBranchName());
			}
			if (wallet.getClientCode().getTitleCode() != null) {
				walletResponse.setTitleCode(wallet.getClientCode().getTitleCode().getTitleCode());
				walletResponse.setTitleName(wallet.getClientCode().getTitleCode().getTitleName());
			}
			if (wallet.getClientCode().getLanguageCode() != null) {
				walletResponse.setLanguageCode(wallet.getClientCode().getLanguageCode().getLanguageCode());
				walletResponse.setLanguageName(wallet.getClientCode().getLanguageCode().getLanguageName());
			}
			if (wallet.getStatusReason() != null) {
				walletResponse.setStatusReasonCode(wallet.getStatusReason().getStatusReasonCode());
				walletResponse.setStatusReasonName(wallet.getStatusReason().getStatusReasonName());
			}
			if (wallet.getStatusCode() != null) {
				walletResponse.setStatusCode(wallet.getStatusCode().getStatusCode());
				walletResponse.setStatusName(wallet.getStatusCode().getStatusName());
			}
			walletResponse.setInstitutionCode(wallet.getClientCode().getInstitutionList().getInstitutionCode());
			walletResponse.setInstitutionName(wallet.getClientCode().getInstitutionList().getInstitutionName());
			if (wallet.getWalletType() != null) {
				walletResponse.setWalletTypeId(wallet.getWalletType().getWalletTypeId());
				walletResponse.setWalletTypeName(wallet.getWalletType().getWalletTypeName());
			}
			walletResponse.setAccountTypeCode(wallet.getAccountTypeCode());
			walletResponse.setAccountTypeName(wallet.getAccountTypeName());
			walletResponse.setDateCreate(wallet.getDateCreate());
			walletResponse.setMerchant(wallet.getClientCode().getIsMerchant());
			walletResponse.setStatusFromDate(wallet.getClientCode().getStatusFromDate());
			walletResponse.setStatusToDate(wallet.getClientCode().getStatusToDate());
			walletResponses.add(walletResponse);
		}
		return walletResponses;
	}

}
