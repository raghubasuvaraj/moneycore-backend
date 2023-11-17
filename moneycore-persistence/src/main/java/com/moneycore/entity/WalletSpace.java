package com.moneycore.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "wallet_space")
@NamedQuery(query = "select u from WalletSpace u", name = "WalletSpace.query_find_all_walletspace")
public class WalletSpace implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "institution_code")
	private InstitutionList institutionCode;
	@Id
	@Column(name = "space_id")
	private String spaceId;
	@Column(name = "space_name", length = 35)
	private String spaceName;
	@Column(name = "amount")
	private double amount;
	@ManyToOne
	@JoinColumn(name = "wallet_id")
	private Wallet walletId;
	@Column(name = "target_date")
	@Temporal(TemporalType.DATE)
	private Date targetDate;
	@Column(name = "account_number")
	private String accountNumber;
	@Column(name = "description", length = 50)
	private String description;

	public InstitutionList getInstitutionCode() {
		return institutionCode;
	}

	public void setInstitutionCode(InstitutionList institutionCode) {
		this.institutionCode = institutionCode;
	}

	public String getSpaceId() {
		return spaceId;
	}

	public void setSpaceId(String spaceId) {
		this.spaceId = spaceId;
	}

	public String getSpaceName() {
		return spaceName;
	}

	public void setSpaceName(String spaceName) {
		this.spaceName = spaceName;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Wallet getWalletId() {
		return walletId;
	}

	public void setWalletId(Wallet walletId) {
		this.walletId = walletId;
	}

	public Date getTargetDate() {
		return targetDate;
	}

	public void setTargetDate(Date targetDate) {
		this.targetDate = targetDate;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
