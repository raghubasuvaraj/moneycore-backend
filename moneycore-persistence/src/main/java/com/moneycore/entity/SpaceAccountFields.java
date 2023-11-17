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
@Table(name = "space_account_fields")
public class SpaceAccountFields implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "space_account_fields_pk")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int SpaceAccountFieldsPk;
	@OneToOne
	@JoinColumn(name = "institution_list_fk")
	private InstitutionList institutionListFk;
	@Column(name = "space_account_field_name")
	private String spaceAccountFieldName;
	@Column(name = "space_account_field_type")
	private String spaceAccountFieldType;
	@Column(name = "display_in_mobile")
	private boolean displayInMobile;

	public int getSpaceAccountFieldsPk() {
		return SpaceAccountFieldsPk;
	}

	public void setSpaceAccountFieldsPk(int spaceAccountFieldsPk) {
		SpaceAccountFieldsPk = spaceAccountFieldsPk;
	}

	public InstitutionList getInstitutionListFk() {
		return institutionListFk;
	}

	public void setInstitutionListFk(InstitutionList institutionListFk) {
		this.institutionListFk = institutionListFk;
	}

	public String getSpaceAccountFieldName() {
		return spaceAccountFieldName;
	}

	public void setSpaceAccountFieldName(String spaceAccountFieldName) {
		this.spaceAccountFieldName = spaceAccountFieldName;
	}

	public String getSpaceAccountFieldType() {
		return spaceAccountFieldType;
	}

	public void setSpaceAccountFieldType(String spaceAccountFieldType) {
		this.spaceAccountFieldType = spaceAccountFieldType;
	}

	public boolean getDisplayInMobile() {
		return displayInMobile;
	}

	public void setDisplayInMobile(boolean displayInMobile) {
		this.displayInMobile = displayInMobile;
	}

}
