package com.moneycore.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.moneycore.entity.PricingProfile;

public class PricingProfileInfo {

	private String institutionCode;
	private String pricingIndex;
	private String abbreviation;
	private String description;
	private String currencyCodeList;
	private double subscriptionAmount;
	private double feesAmount;
	private double topupFees;
	private double reloadFees;
	private double operationFees;
	private double serviceFees;
	private Date promotionStartingDate;
	private Date promotionEndingDate;
	private double promotionFeesAmount;
	private String otherFees;
	private String otherFeesIndicator;
	private String userCreate;
	private String userModif;
	private boolean isDefault;
	private String walletTypeId;
	private String walletTypeName;

	public String getInstitutionCode() {
		return institutionCode;
	}

	public void setInstitutionCode(String institutionCode) {
		this.institutionCode = institutionCode;
	}

	public String getPricingIndex() {
		return pricingIndex;
	}

	public void setPricingIndex(String pricingIndex) {
		this.pricingIndex = pricingIndex;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCurrencyCodeList() {
		return currencyCodeList;
	}

	public void setCurrencyCodeList(String currencyCodeList) {
		this.currencyCodeList = currencyCodeList;
	}

	public double getSubscriptionAmount() {
		return subscriptionAmount;
	}

	public void setSubscriptionAmount(double subscriptionAmount) {
		this.subscriptionAmount = subscriptionAmount;
	}

	public double getFeesAmount() {
		return feesAmount;
	}

	public void setFeesAmount(double feesAmount) {
		this.feesAmount = feesAmount;
	}

	public double getTopupFees() {
		return topupFees;
	}

	public void setTopupFees(double topupFees) {
		this.topupFees = topupFees;
	}

	public double getReloadFees() {
		return reloadFees;
	}

	public void setReloadFees(double reloadFees) {
		this.reloadFees = reloadFees;
	}

	public double getOperationFees() {
		return operationFees;
	}

	public void setOperationFees(double operationFees) {
		this.operationFees = operationFees;
	}

	public double getServiceFees() {
		return serviceFees;
	}

	public void setServiceFees(double serviceFees) {
		this.serviceFees = serviceFees;
	}

	public Date getPromotionStartingDate() {
		return promotionStartingDate;
	}

	public void setPromotionStartingDate(Date promotionStartingDate) {
		this.promotionStartingDate = promotionStartingDate;
	}

	public Date getPromotionEndingDate() {
		return promotionEndingDate;
	}

	public void setPromotionEndingDate(Date promotionEndingDate) {
		this.promotionEndingDate = promotionEndingDate;
	}

	public double getPromotionFeesAmount() {
		return promotionFeesAmount;
	}

	public void setPromotionFeesAmount(double promotionFeesAmount) {
		this.promotionFeesAmount = promotionFeesAmount;
	}

	public String getOtherFees() {
		return otherFees;
	}

	public void setOtherFees(String otherFees) {
		this.otherFees = otherFees;
	}

	public String getOtherFeesIndicator() {
		return otherFeesIndicator;
	}

	public void setOtherFeesIndicator(String otherFeesIndicator) {
		this.otherFeesIndicator = otherFeesIndicator;
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

	public boolean getIsDefault() {
		return isDefault;
	}

	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
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

}
