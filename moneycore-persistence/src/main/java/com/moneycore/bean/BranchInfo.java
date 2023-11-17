package com.moneycore.bean;

import java.util.Date;

import com.moneycore.entity.Branch;

public class BranchInfo {

	private String branchCode;
	private String branchName;
	private String abrvWording;
	private String wording;
	private String website;
	private int noOfEmployees;
	private String bussinessAddress;
	private String contactPerson;
	private String personDesignation;
	private Long phoneNumber;
	private Long alternateNumber;
	private String tradeLicenseNumber;
	private String userCreate;
	private Date dateCreate;
	private String userModif;
	private Date dateModif;
	private InstitutionListInfo institutionList;
	private String country;

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

	public String getAbrvWording() {
		return abrvWording;
	}

	public void setAbrvWording(String abrvWording) {
		this.abrvWording = abrvWording;
	}

	public String getWording() {
		return wording;
	}

	public void setWording(String wording) {
		this.wording = wording;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public int getNoOfEmployees() {
		return noOfEmployees;
	}

	public void setNoOfEmployees(int noOfEmployees) {
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

	public InstitutionListInfo getInstitutionList() {
		return institutionList;
	}

	public void setInstitutionList(InstitutionListInfo institutionList) {
		this.institutionList = institutionList;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public static BranchInfo createResponse(Branch branch) {
		BranchInfo branchInfo = new BranchInfo();
		branchInfo.setBranchCode(branch.getBranchCode());
		branchInfo.setBranchName(branch.getBranchName());
		branchInfo.setAbrvWording(branch.getAbrvWording());
		branchInfo.setWording(branch.getWording());
		branchInfo.setWebsite(branch.getWebsite());
		branchInfo.setNoOfEmployees(branch.getNoOfEmployees());
		branchInfo.setBussinessAddress(branch.getBussinessAddress());
		branchInfo.setContactPerson(branch.getContactPerson());
		branchInfo.setPersonDesignation(branch.getpersonDesignation());
		branchInfo.setPhoneNumber(branch.getPhoneNumber());
		branchInfo.setAlternateNumber(branch.getAlternateNumber());
		branchInfo.setTradeLicenseNumber(branch.getTradeLicenseNumber());
		branchInfo.setUserCreate(branch.getUserCreate());
		branchInfo.setDateCreate(branch.getDateCreate());
		branchInfo.setDateModif(branch.getDateModif());
		branchInfo.setUserModif(branch.getUserModif());
		branchInfo.setCountry(branch.getCountry());
		if(branch.getInstitutionList() != null) {
			InstitutionListInfo institutionListInfo = InstitutionListInfo.createSingleResponse(branch.getInstitutionList());
			branchInfo.setInstitutionList(institutionListInfo);
		}
		return branchInfo;
	}

}
