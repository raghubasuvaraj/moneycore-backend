package com.moneycore.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.moneycore.entity.InstitutionRegister;

public class InstitutionRegisterInfo {

	private String institutionCode;
	private String institutionName;
	private String description;
	private String abbreviation;
	private String bussinessType;
	private String businessAddress;
	private Long noOfEmployees;
	private String tradeName;
	private String tradeAs;
	private String tradeLicenseNumber;
	private String website;
	private Long phoneNumber;
	private String phoneCountryCode;
	private Long alternatePhoneNumber;
	private String alternatephoneCountryCode;
	private String contactPersonName;
	private String designation;
	private String emailAddress;
	private String reason;
	private String status;
	private Date dateCreate;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public String getBussinessType() {
		return bussinessType;
	}

	public void setBussinessType(String bussinessType) {
		this.bussinessType = bussinessType;
	}

	public String getBusinessAddress() {
		return businessAddress;
	}

	public void setBusinessAddress(String businessAddress) {
		this.businessAddress = businessAddress;
	}

	public Long getNoOfEmployees() {
		return noOfEmployees;
	}

	public void setNoOfEmployees(Long noOfEmployees) {
		this.noOfEmployees = noOfEmployees;
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

	public String getTradeLicenseNumber() {
		return tradeLicenseNumber;
	}

	public void setTradeLicenseNumber(String tradeLicenseNumber) {
		this.tradeLicenseNumber = tradeLicenseNumber;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public Long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(Long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPhoneCountryCode() {
		return phoneCountryCode;
	}

	public void setPhoneCountryCode(String phoneCountryCode) {
		this.phoneCountryCode = phoneCountryCode;
	}

	public Long getAlternatePhoneNumber() {
		return alternatePhoneNumber;
	}

	public void setAlternatePhoneNumber(Long alternatePhoneNumber) {
		this.alternatePhoneNumber = alternatePhoneNumber;
	}

	public String getAlternatephoneCountryCode() {
		return alternatephoneCountryCode;
	}

	public void setAlternatephoneCountryCode(String alternatephoneCountryCode) {
		this.alternatephoneCountryCode = alternatephoneCountryCode;
	}

	public String getContactPersonName() {
		return contactPersonName;
	}

	public void setContactPersonName(String contactPersonName) {
		this.contactPersonName = contactPersonName;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getDateCreate() {
		return dateCreate;
	}

	public void setDateCreate(Date dateCreate) {
		this.dateCreate = dateCreate;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public static List<InstitutionRegisterInfo> createResponse(List<InstitutionRegister> instituteRegisters) {
		List<InstitutionRegisterInfo> infos = new ArrayList<InstitutionRegisterInfo>();
		for (InstitutionRegister institutionRegister : instituteRegisters) {
			InstitutionRegisterInfo institutionRegisterInfo = new InstitutionRegisterInfo();
			institutionRegisterInfo.setInstitutionCode(institutionRegister.getInstitutionCode());
			institutionRegisterInfo.setInstitutionName(institutionRegister.getInstitutionName());
			institutionRegisterInfo.setDescription(institutionRegister.getWording());
			institutionRegisterInfo.setAbbreviation(institutionRegister.getAbrvWording());
			institutionRegisterInfo.setBussinessType(institutionRegister.getBussinessType());
			institutionRegisterInfo.setBusinessAddress(institutionRegister.getBussinessAddress());
			institutionRegisterInfo.setNoOfEmployees(institutionRegister.getNoOfEmployees());
			institutionRegisterInfo.setTradeAs(institutionRegister.getTradeAs());
			institutionRegisterInfo.setTradeName(institutionRegister.getTradeName());
			institutionRegisterInfo.setTradeLicenseNumber(institutionRegister.getTradeLicenseNumber());
			institutionRegisterInfo.setWebsite(institutionRegister.getWebsite());
			institutionRegisterInfo.setPhoneNumber(institutionRegister.getPhoneNumber());
			institutionRegisterInfo.setPhoneCountryCode(institutionRegister.getPhoneCountryCode());
			institutionRegisterInfo.setAlternatePhoneNumber(institutionRegister.getAlternateNumber());
			institutionRegisterInfo.setAlternatephoneCountryCode(institutionRegister.getAlternatePhoneCountryCode());
			institutionRegisterInfo.setContactPersonName(institutionRegister.getContactPerson());
			institutionRegisterInfo.setDesignation(institutionRegister.getPersonDesignation());
			institutionRegisterInfo.setEmailAddress(institutionRegister.getEmail());
			institutionRegisterInfo.setReason(institutionRegister.getReason());
			String status = null;
			if (institutionRegister.getIsApproved() == 'P') {
				status = "pending";
			}
			if (institutionRegister.getIsApproved() == 'D') {
				status = "decline";
			}
			institutionRegisterInfo.setStatus(status);
			institutionRegisterInfo.setDateCreate(institutionRegister.getDateCreate());
			institutionRegisterInfo.setCountry(institutionRegister.getCountry());
			infos.add(institutionRegisterInfo);
		}
		return infos;
	}

	public static InstitutionRegisterInfo createSingleResponse(InstitutionRegister institutionRegister) {
		InstitutionRegisterInfo institutionRegisterInfo = new InstitutionRegisterInfo();
		institutionRegisterInfo.setInstitutionCode(institutionRegister.getInstitutionCode());
		institutionRegisterInfo.setInstitutionName(institutionRegister.getInstitutionName());
		institutionRegisterInfo.setDescription(institutionRegister.getWording());
		institutionRegisterInfo.setAbbreviation(institutionRegister.getAbrvWording());
		institutionRegisterInfo.setBussinessType(institutionRegister.getBussinessType());
		institutionRegisterInfo.setBusinessAddress(institutionRegister.getBussinessAddress());
		institutionRegisterInfo.setNoOfEmployees(institutionRegister.getNoOfEmployees());
		institutionRegisterInfo.setTradeAs(institutionRegister.getTradeAs());
		institutionRegisterInfo.setTradeName(institutionRegister.getTradeName());
		institutionRegisterInfo.setTradeLicenseNumber(institutionRegister.getTradeLicenseNumber());
		institutionRegisterInfo.setWebsite(institutionRegister.getWebsite());
		institutionRegisterInfo.setPhoneNumber(institutionRegister.getPhoneNumber());
		institutionRegisterInfo.setPhoneCountryCode(institutionRegister.getPhoneCountryCode());
		institutionRegisterInfo.setAlternatePhoneNumber(institutionRegister.getAlternateNumber());
		institutionRegisterInfo.setAlternatephoneCountryCode(institutionRegister.getAlternatePhoneCountryCode());
		institutionRegisterInfo.setContactPersonName(institutionRegister.getContactPerson());
		institutionRegisterInfo.setDesignation(institutionRegister.getPersonDesignation());
		institutionRegisterInfo.setEmailAddress(institutionRegister.getEmail());
		institutionRegisterInfo.setReason(institutionRegister.getReason());
		String status = null;
		if (institutionRegister.getIsApproved() == 'P') {
			status = "pending";
		}
		if (institutionRegister.getIsApproved() == 'D') {
			status = "decline";
		}
		institutionRegisterInfo.setStatus(status);
		institutionRegisterInfo.setDateCreate(institutionRegister.getDateCreate());
		institutionRegisterInfo.setCountry(institutionRegister.getCountry());
		return institutionRegisterInfo;
	}

}
