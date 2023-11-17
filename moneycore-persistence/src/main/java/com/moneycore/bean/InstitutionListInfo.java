package com.moneycore.bean;

import java.util.Date;

import com.moneycore.entity.InstitutionList;

public class InstitutionListInfo {

	private String institutionCode;
	private String institutionName;
	private String abrvWording;
	private String currencyCode;
	private String wording;
	private String tradeName;
	private String tradeAs;
	private String bussinessType;
	private String website;
	private Long noOfEmployees;
	private String bussinessAddress;
	private String contactPerson;
	private String personDesignation;
	private Long phoneNumber;
	private Long alternateNumber;
	private String tradeLicenseNumber;
	private String defaultBranch;
	private Date dateCreate;
	private Date dateModif;
	private String userCreate;
	private String userModif;
	private String country;

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

	
	public String getAbrvWording() {
		return abrvWording;
	}

	public void setAbrvWording(String abrvWording) {
		this.abrvWording = abrvWording;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getWording() {
		return wording;
	}

	public void setWording(String wording) {
		this.wording = wording;
	}

	public String getTradeName() {
		return tradeName;
	}

	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}

	public String getTradeAs() {
		return tradeAs;
	}

	public void setTradeAs(String tradeAs) {
		this.tradeAs = tradeAs;
	}

	public String getBussinessType() {
		return bussinessType;
	}

	public void setBussinessType(String bussinessType) {
		this.bussinessType = bussinessType;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public Long getNoOfEmployees() {
		return noOfEmployees;
	}

	public void setNoOfEmployees(Long noOfEmployees) {
		this.noOfEmployees = noOfEmployees;
	}

	public String getBussinessAddress() {
		return bussinessAddress;
	}

	public void setBussinessAddress(String bussinessAddress) {
		this.bussinessAddress = bussinessAddress;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getPersonDesignation() {
		return personDesignation;
	}

	public void setPersonDesignation(String personDesignation) {
		this.personDesignation = personDesignation;
	}

	public Long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(Long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Long getAlternateNumber() {
		return alternateNumber;
	}

	public void setAlternateNumber(Long alternateNumber) {
		this.alternateNumber = alternateNumber;
	}

	public String getTradeLicenseNumber() {
		return tradeLicenseNumber;
	}

	public void setTradeLicenseNumber(String tradeLicenseNumber) {
		this.tradeLicenseNumber = tradeLicenseNumber;
	}

	public String getDefaultBranch() {
		return defaultBranch;
	}

	public void setDefaultBranch(String defaultBranch) {
		this.defaultBranch = defaultBranch;
	}

	public Date getDateCreate() {
		return dateCreate;
	}

	public void setDateCreate(Date dateCreate) {
		this.dateCreate = dateCreate;
	}

	public Date getDateModif() {
		return dateModif;
	}

	public void setDateModif(Date dateModif) {
		this.dateModif = dateModif;
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

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public static InstitutionListInfo createSingleResponse(InstitutionList institutionList) {
		InstitutionListInfo institutionListInfo = new InstitutionListInfo();
		institutionListInfo.setInstitutionCode(institutionList.getInstitutionCode());
		institutionListInfo.setInstitutionName(institutionList.getInstitutionName());
		institutionListInfo.setAbrvWording(institutionList.getAbrvWording());
		institutionListInfo.setWording(institutionList.getWording());
		institutionListInfo.setWebsite(institutionList.getWebsite());
		institutionListInfo.setTradeName(institutionList.getTradeName());
		institutionListInfo.setTradeAs(institutionList.getTradeAs());
		institutionListInfo.setBussinessType(institutionList.getBussinessType());
		institutionListInfo.setNoOfEmployees(institutionList.getNoOfEmployees());
		institutionListInfo.setBussinessAddress(institutionList.getBussinessAddress());
		institutionListInfo.setContactPerson(institutionList.getContactPerson());
		institutionListInfo.setPersonDesignation(institutionList.getPersonDesignation());
		institutionListInfo.setPhoneNumber(institutionList.getPhoneNumber());
		institutionListInfo.setAlternateNumber(institutionList.getAlternateNumber());
		institutionListInfo.setTradeLicenseNumber(institutionList.getTradeLicenseNumber());
		institutionListInfo.setDefaultBranch(institutionList.getDefaultBranch());
		institutionListInfo.setUserCreate(institutionList.getUserCreate());
		institutionListInfo.setDateCreate(institutionList.getDateCreate());
		institutionListInfo.setUserModif(institutionList.getUserModif());
		institutionListInfo.setDateModif(institutionList.getDateModif());
		institutionListInfo.setCountry(institutionList.getCountry());
		return institutionListInfo;
	}

}
