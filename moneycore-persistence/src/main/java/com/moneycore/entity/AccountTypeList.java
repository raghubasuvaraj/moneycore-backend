package com.moneycore.entity;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "account_type_list")

//@IdClass(AccountTypeListKey.class)
public class AccountTypeList implements Serializable {

	private static final long serialVersionUID = 1L;

//	@EmbeddedId
//	private AccountTypeListKey accountTypeListKey;
//	@Id
	@Column(name = "institution_code")
	private String institutionCode;
	@Id
	@Column(name = "account_type_code")
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String accountTypeCode;
	@Column(name = "account_type_name")
	private String accountTypeName;
	@Column(name = "abrv_wording")
	private String abrvWording;
	@Column(name = "wording")
	private String wording;
	@Column(name = "user_create")
	private String userCreate;
	@Column(name = "date_create")
	private Date dateCreate;
	@Column(name = "user_modif")
	private String userModif;
	@Column(name = "date_modif")
	private Date dateModif;




	public AccountTypeList() {

	}

	public String getInstitutionCode() {
		return institutionCode;
	}

	public void setInstitutionCode(String institutionCode) {
		this.institutionCode = institutionCode;
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

}
