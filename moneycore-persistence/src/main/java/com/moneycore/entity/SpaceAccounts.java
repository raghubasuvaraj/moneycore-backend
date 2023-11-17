package com.moneycore.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "space_accounts")
public class SpaceAccounts implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "space_account_pk")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int SpaceAccountPk;
	@Column(name = "wallet_id")
	private String walletId;
	@Column(name = "iban")
	private String IBAN;
	@Column(name = "space_name")
	private String spaceName;

	public int getSpaceAccountPk() {
		return SpaceAccountPk;
	}

	public void setSpaceAccountPk(int spaceAccountPk) {
		SpaceAccountPk = spaceAccountPk;
	}

	public String getWalletId() {
		return walletId;
	}

	public void setWalletId(String walletId) {
		this.walletId = walletId;
	}

	public String getIBAN() {
		return IBAN;
	}

	public void setIBAN(String iBAN) {
		IBAN = iBAN;
	}

	public String getSpaceName() {
		return spaceName;
	}

	public void setSpaceName(String spaceName) {
		this.spaceName = spaceName;
	}

}
