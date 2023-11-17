package com.moneycore.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "wallet_balance")
public class WalletBalance implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@OneToOne
	@JoinColumn(name = "account_number", referencedColumnName = "account_number")
	private WalletAccountLink accountNumber;
	@OneToOne
	@JoinColumn(name = "institution_code")
	private InstitutionList institutionCode;
	@OneToOne
	@JoinColumn(name = "wallet_id")
	private Wallet walletId;
	@ManyToOne
	@JoinColumn(name = "branch_code")
	private Branch branchCode;
	@OneToOne
	@JoinColumn(name = "currency_code")
	private CurrencyList currencyCode;
	@Column(name = "balance")
	private Double balance;
	@Column(name = "facilities")
	private Double facilities;
	@Column(name = "blocked_amount")
	private Double blockedAmount;
	@Column(name = "current_auth")
	private Double currentAuth;
	@Column(name = "account_status")
	private char accountStatus;
	@OneToOne
	@JoinColumn(name = "client_code")
	private Client clientCode;
	@Column(name = "private_tlv_data")
	private String privateTlvData;
	@Column(name = "sensitive_operation_record")
	private char sensitiveOperationRecord;
	@Column(name = "user_create")
	private String userCreate;
	@Column(name = "date_create")
	private Date dateCreate;
	@Column(name = "user_modif")
	private String userModif;
	@Column(name = "date_modif")
	private Date dateModif;

	public WalletAccountLink getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(WalletAccountLink accountNumber) {
		this.accountNumber = accountNumber;
	}

	public InstitutionList getInstitutionCode() {
		return institutionCode;
	}

	public void setInstitutionCode(InstitutionList institutionCode) {
		this.institutionCode = institutionCode;
	}

	public Wallet getWalletId() {
		return walletId;
	}

	public void setWalletId(Wallet walletId) {
		this.walletId = walletId;
	}

	public Branch getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(Branch branchCode) {
		this.branchCode = branchCode;
	}

	public CurrencyList getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(CurrencyList currencyCode) {
		this.currencyCode = currencyCode;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public Double getFacilities() {
		return facilities;
	}

	public void setFacilities(Double facilities) {
		this.facilities = facilities;
	}

	public Double getBlockedAmount() {
		return blockedAmount;
	}

	public void setBlockedAmount(Double blockedAmount) {
		this.blockedAmount = blockedAmount;
	}

	public Double getCurrentAuth() {
		return currentAuth;
	}

	public void setCurrentAuth(Double currentAuth) {
		this.currentAuth = currentAuth;
	}

	public char getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(char accountStatus) {
		this.accountStatus = accountStatus;
	}

	public Client getClientCode() {
		return clientCode;
	}

	public void setClientCode(Client clientCode) {
		this.clientCode = clientCode;
	}

	public String getPrivateTlvData() {
		return privateTlvData;
	}

	public void setPrivateTlvData(String privateTlvData) {
		this.privateTlvData = privateTlvData;
	}

	public char getSensitiveOperationRecord() {
		return sensitiveOperationRecord;
	}

	public void setSensitiveOperationRecord(char sensitiveOperationRecord) {
		this.sensitiveOperationRecord = sensitiveOperationRecord;
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
