package com.moneycore.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "wallet_account_link")
public class WalletAccountLink implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "entity_code")
	private String entityCode;
	@OneToOne
	@JoinColumn(name = "entity_id", referencedColumnName = "wallet_id")
	private Wallet entityId;
	@Column(name = "account_sequence")
	private Long accountSequence;
	@OneToOne
	@JoinColumn(name = "account_institution_code")
	private InstitutionList accountInstitutionCode;
	@ManyToOne
	@JoinColumn(name = "account_branch_code", referencedColumnName = "branch_code")
	private Branch accountBranchCode;
	@Column(name = "account_number")
	private String accountNumber;
	@OneToOne
	@JoinColumn(name = "account_type_code")
	private AccountTypeList accountType;
	@ManyToOne
	@JoinColumn(name = "account_currency_code", referencedColumnName = "currency_code")
	private CurrencyList accountCurrencyCode;
	@Column(name = "account_status")
	private char accountStatus;
	@Column(name = "default_flag")
	private char defaultFlag;
	@Column(name = "iban")
	private String iBan;
	@Column(name = "private_data_1")
	private String privateData1;
	@Column(name = "private_data_2")
	private String privateData2;
	@Column(name = "private_data_3")
	private Long privateData3;
	@Column(name = "private_data_4")
	private Long privateData4;
	@Column(name = "private_data_5")
	private Date privateData5;
	@Column(name = "private_data_6")
	private Date privateData6;
	@Column(name = "default_account")
	private char defaultAccount;
	@Column(name = "user_create")
	private String userCreate;
	@Column(name = "date_create")
	private Date dateCreate;
	@Column(name = "user_modif")
	private String userModif;
	@Column(name = "date_modif")
	private Date dateModif;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEntityCode() {
		return entityCode;
	}

	public void setEntityCode(String entityCode) {
		this.entityCode = entityCode;
	}

	public Wallet getEntityId() {
		return entityId;
	}

	public void setEntityId(Wallet entityId) {
		this.entityId = entityId;
	}

	public Long getAccountSequence() {
		return accountSequence;
	}

	public void setAccountSequence(Long accountSequence) {
		this.accountSequence = accountSequence;
	}

	public InstitutionList getAccountInstitutionCode() {
		return accountInstitutionCode;
	}

	public void setAccountInstitutionCode(InstitutionList accountInstitutionCode) {
		this.accountInstitutionCode = accountInstitutionCode;
	}

	public Branch getAccountBranchCode() {
		return accountBranchCode;
	}

	public void setAccountBranchCode(Branch accountBranchCode) {
		this.accountBranchCode = accountBranchCode;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public AccountTypeList getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountTypeList accountType) {
		this.accountType = accountType;
	}

	public CurrencyList getAccountCurrencyCode() {
		return accountCurrencyCode;
	}

	public void setAccountCurrencyCode(CurrencyList accountCurrencyCode) {
		this.accountCurrencyCode = accountCurrencyCode;
	}

	public char getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(char accountStatus) {
		this.accountStatus = accountStatus;
	}

	public char getDefaultFlag() {
		return defaultFlag;
	}

	public void setDefaultFlag(char defaultFlag) {
		this.defaultFlag = defaultFlag;
	}

	public String getiBan() {
		return iBan;
	}

	public void setiBan(String iBan) {
		this.iBan = iBan;
	}

	public String getPrivateData1() {
		return privateData1;
	}

	public void setPrivateData1(String privateData1) {
		this.privateData1 = privateData1;
	}

	public String getPrivateData2() {
		return privateData2;
	}

	public void setPrivateData2(String privateData2) {
		this.privateData2 = privateData2;
	}

	public Long getPrivateData3() {
		return privateData3;
	}

	public void setPrivateData3(Long privateData3) {
		this.privateData3 = privateData3;
	}

	public Long getPrivateData4() {
		return privateData4;
	}

	public void setPrivateData4(Long privateData4) {
		this.privateData4 = privateData4;
	}

	public Date getPrivateData5() {
		return privateData5;
	}

	public void setPrivateData5(Date privateData5) {
		this.privateData5 = privateData5;
	}

	public Date getPrivateData6() {
		return privateData6;
	}

	public void setPrivateData6(Date privateData6) {
		this.privateData6 = privateData6;
	}

	public char getDefaultAccount() {
		return defaultAccount;
	}

	public void setDefaultAccount(char defaultAccount) {
		this.defaultAccount = defaultAccount;
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
