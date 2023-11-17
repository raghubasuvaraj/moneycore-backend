package com.moneycore.entity;

import com.moneycore.entityListener.BranchListener;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name = "branch_list")
@EntityListeners(BranchListener.class)
public class Branch implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "branch_code")
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String branchCode;
	@Column(name = "branch_name")
	private String branchName;
	@Column(name = "abrv_wording")
	private String abrvWording;
	@Column(name = "wording")
	private String wording;
	@Column(name = "website")
	private String website;

	@Column(name = "no_of_employees")
	private int noOfEmployees;

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

	@Column(name = "user_create")
	private String userCreate;
	@Column(name = "date_create")
	@Temporal(TemporalType.DATE)
	private Date dateCreate;
	@Column(name = "user_modif")
	private String userModif;
	@Column(name = "date_modif")
	@Temporal(TemporalType.DATE)
	private Date dateModif;
	@ManyToOne
	@JoinColumn(name = "institution_code")
	private InstitutionList institutionList;

	@Column(name = "country")
	private String country;



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

	public String getpersonDesignation() {
		return personDesignation;
	}

	public void setpersonDesignation(String personDesignation) {
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

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
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

	public InstitutionList getInstitutionList() {
		return institutionList;
	}

	public void setInstitutionList(InstitutionList institutionList) {
		this.institutionList = institutionList;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

}
