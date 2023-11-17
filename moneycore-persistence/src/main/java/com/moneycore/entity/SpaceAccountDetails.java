package com.moneycore.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "space_account_details")
public class SpaceAccountDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "space_account_details_pk")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int SpaceAccountDetailsPk;
	@OneToOne
	@JoinColumn(name = "space_account_fk")
	private SpaceAccounts spaceAccountFk;
	@OneToOne
	@JoinColumn(name = "space_account_fields_fk")
	private SpaceAccountFields spaceAccountFieldsFk;
	@Column(name = "space_account_field_value")
	private String spaceAccountFieldValue;

	public int getSpaceAccountDetailsPk() {
		return SpaceAccountDetailsPk;
	}

	public void setSpaceAccountDetailsPk(int spaceAccountDetailsPk) {
		SpaceAccountDetailsPk = spaceAccountDetailsPk;
	}

	public SpaceAccounts getSpaceAccountFk() {
		return spaceAccountFk;
	}

	public void setSpaceAccountFk(SpaceAccounts spaceAccountFk) {
		this.spaceAccountFk = spaceAccountFk;
	}

	public SpaceAccountFields getSpaceAccountFieldsFk() {
		return spaceAccountFieldsFk;
	}

	public void setSpaceAccountFieldsFk(SpaceAccountFields spaceAccountFieldsFk) {
		this.spaceAccountFieldsFk = spaceAccountFieldsFk;
	}

	public String getSpaceAccountFieldValue() {
		return spaceAccountFieldValue;
	}

	public void setSpaceAccountFieldValue(String spaceAccountFieldValue) {
		this.spaceAccountFieldValue = spaceAccountFieldValue;
	}

}
