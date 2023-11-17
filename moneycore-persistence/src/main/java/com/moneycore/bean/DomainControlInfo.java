package com.moneycore.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.moneycore.entity.DomainControl;

public class DomainControlInfo {

	private String institutionCode;
	private String controlIndex;
	private String abbreviation;
	private String description;
	private String currencyCodeList;
	private String mccCodeList;
	private String countryCodeList;
	private String merchantsList;
	private String transactionsList;
	private double dailyInternalTransactionLimit;
	private double noOfDailyInternalTransactions;
	private double dailyNationalTransactionLimit;
	private double noOfDailyNationalTransactions;
	private double dailyInterntionalTransactionLimit;
	private double noOfDailyInternationalTransactions;
	private double dailyTotalTransactionLimit;
	private double totalNoOfDailyTransactions;
	private double internalTransactionAmountLimit;
	private double noOfInternalTransactions;
	private double nationalTransactionAmountLimit;
	private double noOfNationalTransactions;
	private double internationalTransactionAmountLimit;
	private double noOfInternationalTransactions;
	private double totalTransactionAmountLimit;
	private double totalNoOfTransactions;
	private String userCreate;
	private Date dateCreate;
	private String userModif;
	private Date dateModif;
	private boolean isDefault;
	private String wallettypeId;
	private String walletTypeName;

	public String getInstitutionCode() {
		return institutionCode;
	}

	public void setInstitutionCode(String institutionCode) {
		this.institutionCode = institutionCode;
	}

	public String getControlIndex() {
		return controlIndex;
	}

	public void setControlIndex(String controlIndex) {
		this.controlIndex = controlIndex;
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

	public String getMccCodeList() {
		return mccCodeList;
	}

	public void setMccCodeList(String mccCodeList) {
		this.mccCodeList = mccCodeList;
	}

	public String getCountryCodeList() {
		return countryCodeList;
	}

	public void setCountryCodeList(String countryCodeList) {
		this.countryCodeList = countryCodeList;
	}

	public String getMerchantsList() {
		return merchantsList;
	}

	public void setMerchantsList(String merchantsList) {
		this.merchantsList = merchantsList;
	}

	public String getTransactionsList() {
		return transactionsList;
	}

	public void setTransactionsList(String transactionsList) {
		this.transactionsList = transactionsList;
	}

	public double getDailyInternalTransactionLimit() {
		return dailyInternalTransactionLimit;
	}

	public void setDailyInternalTransactionLimit(double dailyInternalTransactionLimit) {
		this.dailyInternalTransactionLimit = dailyInternalTransactionLimit;
	}

	public double getNoOfDailyInternalTransactions() {
		return noOfDailyInternalTransactions;
	}

	public void setNoOfDailyInternalTransactions(double noOfDailyInternalTransactions) {
		this.noOfDailyInternalTransactions = noOfDailyInternalTransactions;
	}

	public double getDailyNationalTransactionLimit() {
		return dailyNationalTransactionLimit;
	}

	public void setDailyNationalTransactionLimit(double dailyNationalTransactionLimit) {
		this.dailyNationalTransactionLimit = dailyNationalTransactionLimit;
	}

	public double getNoOfDailyNationalTransactions() {
		return noOfDailyNationalTransactions;
	}

	public void setNoOfDailyNationalTransactions(double noOfDailyNationalTransactions) {
		this.noOfDailyNationalTransactions = noOfDailyNationalTransactions;
	}

	public double getDailyInterntionalTransactionLimit() {
		return dailyInterntionalTransactionLimit;
	}

	public void setDailyInterntionalTransactionLimit(double dailyInterntionalTransactionLimit) {
		this.dailyInterntionalTransactionLimit = dailyInterntionalTransactionLimit;
	}

	public double getNoOfDailyInternationalTransactions() {
		return noOfDailyInternationalTransactions;
	}

	public void setNoOfDailyInternationalTransactions(double noOfDailyInternationalTransactions) {
		this.noOfDailyInternationalTransactions = noOfDailyInternationalTransactions;
	}

	public double getDailyTotalTransactionLimit() {
		return dailyTotalTransactionLimit;
	}

	public void setDailyTotalTransactionLimit(double dailyTotalTransactionLimit) {
		this.dailyTotalTransactionLimit = dailyTotalTransactionLimit;
	}

	public double getTotalNoOfDailyTransactions() {
		return totalNoOfDailyTransactions;
	}

	public void setTotalNoOfDailyTransactions(double totalNoOfDailyTransactions) {
		this.totalNoOfDailyTransactions = totalNoOfDailyTransactions;
	}

	public double getInternalTransactionAmountLimit() {
		return internalTransactionAmountLimit;
	}

	public void setInternalTransactionAmountLimit(double internalTransactionAmountLimit) {
		this.internalTransactionAmountLimit = internalTransactionAmountLimit;
	}

	public double getNoOfInternalTransactions() {
		return noOfInternalTransactions;
	}

	public void setNoOfInternalTransactions(double noOfInternalTransactions) {
		this.noOfInternalTransactions = noOfInternalTransactions;
	}

	public double getNationalTransactionAmountLimit() {
		return nationalTransactionAmountLimit;
	}

	public void setNationalTransactionAmountLimit(double nationalTransactionAmountLimit) {
		this.nationalTransactionAmountLimit = nationalTransactionAmountLimit;
	}

	public double getNoOfNationalTransactions() {
		return noOfNationalTransactions;
	}

	public void setNoOfNationalTransactions(double noOfNationalTransactions) {
		this.noOfNationalTransactions = noOfNationalTransactions;
	}

	public double getInternationalTransactionAmountLimit() {
		return internationalTransactionAmountLimit;
	}

	public void setInternationalTransactionAmountLimit(double internationalTransactionAmountLimit) {
		this.internationalTransactionAmountLimit = internationalTransactionAmountLimit;
	}

	public double getNoOfInternationalTransactions() {
		return noOfInternationalTransactions;
	}

	public void setNoOfInternationalTransactions(double noOfInternationalTransactions) {
		this.noOfInternationalTransactions = noOfInternationalTransactions;
	}

	public double getTotalTransactionAmountLimit() {
		return totalTransactionAmountLimit;
	}

	public void setTotalTransactionAmountLimit(double totalTransactionAmountLimit) {
		this.totalTransactionAmountLimit = totalTransactionAmountLimit;
	}

	public double getTotalNoOfTransactions() {
		return totalNoOfTransactions;
	}

	public void setTotalNoOfTransactions(double totalNoOfTransactions) {
		this.totalNoOfTransactions = totalNoOfTransactions;
	}

	public String getUserCreate() {
		return userCreate;
	}

	public void setUserCreate(String userCreate) {
		this.userCreate = userCreate;
	}

	public Date getDateCreate() {
		return dateCreate;
	}

	public void setDateCreate(Date dateCreate) {
		this.dateCreate = dateCreate;
	}

	public String getUserModif() {
		return userModif;
	}

	public void setUserModif(String userModif) {
		this.userModif = userModif;
	}

	public Date getDateModif() {
		return dateModif;
	}

	public void setDateModif(Date dateModif) {
		this.dateModif = dateModif;
	}

	public boolean isDefault() {
		return isDefault;
	}

	public void setDefault(boolean aDefault) {
		isDefault = aDefault;
	}

	public String getWallettypeId() {
		return wallettypeId;
	}

	public void setWallettypeId(String wallettypeId) {
		this.wallettypeId = wallettypeId;
	}

	public String getWalletTypeName() {
		return walletTypeName;
	}

	public void setWalletTypeName(String walletTypeName) {
		this.walletTypeName = walletTypeName;
	}
}
