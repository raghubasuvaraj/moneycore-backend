package com.moneycore.entity;

import com.moneycore.entityListener.InstitutionListListener;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 * The persistent class for the institution_list database table.
 * 
 */
@Entity
@Table(name = "institution_list")
@EntityListeners(InstitutionListListener.class)
public class InstitutionList implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "institution_code")
	private String institutionCode;

	@Column(name = "institution_name")
	private String institutionName;

	@Column(name = "abrv_wording")
	private String abrvWording;

	@Column(name = "currency_code")
	private String currencyCode;

	@Column(name = "wording")
	private String wording;

	@Column(name = "trade_name")
	private String tradeName;

	@Column(name = "trade_as")
	private String tradeAs;

	@Column(name = "bussiness_type")
	private String bussinessType;

	@Column(name = "website")
	private String website;

	@Column(name = "no_of_employees")
	private Long noOfEmployees;

	@Column(name = "bussiness_address")
	private String bussinessAddress;

	@Column(name = "contact_person")
	private String contactPerson;

	@Column(name = "contact_person_designation")
	private String personDesignation;

	@Column(name = "phone_number")
	private Long phoneNumber;

	@Column(name = "alternate_number")
	private Long alternateNumber;

	@Column(name = "trade_license_number")
	private String tradeLicenseNumber;

	@Column(name = "default_branch")
	private String defaultBranch;

	@Temporal(TemporalType.DATE)
	@Column(name = "date_create")
	private Date dateCreate;

	@Temporal(TemporalType.DATE)
	@Column(name = "date_modif")
	private Date dateModif;

	@Column(name = "user_create")
	private String userCreate;

	@Column(name = "user_modif")
	private String userModif;

	@Column(name = "email")
	private String email;

	@Column(name = "phone_country_code")
	private String phoneCountryCode;

	@Column(name = "alternate_phone_country_code")
	private String alternatePhoneCountryCode;

	@Column(name = "country")
	private String country;


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

	public String getWording() {
		return wording;
	}

	public void setWording(String wording) {
		this.wording = wording;
	}

	public String getInstitutionName() {
		return institutionName;
	}

	public void setInstitutionName(String institutionName) {
		this.institutionName = institutionName;
	}

	public String getInstitutionCode() {
		return this.institutionCode;
	}

	public void setInstitutionCode(String institutionCode) {
		this.institutionCode = institutionCode;
	}

	public String getAbrvWording() {
		return this.abrvWording;
	}

	public void setAbrvWording(String abrvWording) {
		this.abrvWording = abrvWording;
	}

	public String getCurrencyCode() {
		return this.currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public Date getDateCreate() {
		return this.dateCreate;
	}

	public void setDateCreate(Date dateCreate) {
		this.dateCreate = dateCreate;
	}

	public Date getDateModif() {
		return this.dateModif;
	}

	public void setDateModif(Date dateModif) {
		this.dateModif = dateModif;
	}

	public String getUserCreate() {
		return this.userCreate;
	}

	public void setUserCreate(String userCreate) {
		this.userCreate = userCreate;
	}

	public String getUserModif() {
		return this.userModif;
	}

	public void setUserModif(String userModif) {
		this.userModif = userModif;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneCountryCode() {
		return phoneCountryCode;
	}

	public void setPhoneCountryCode(String phoneCountryCode) {
		this.phoneCountryCode = phoneCountryCode;
	}

	public String getAlternatePhoneCountryCode() {
		return alternatePhoneCountryCode;
	}

	public void setAlternatePhoneCountryCode(String alternatePhoneCountryCode) {
		this.alternatePhoneCountryCode = alternatePhoneCountryCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

}