package com.moneycore.bean;

import java.util.ArrayList;
import java.util.List;

import com.moneycore.entity.SpaceAccountFields;

public class SpaceAccountFieldsInfo {

	private int SpaceAccountFieldsPk;
	private String institutionListFk;
	private String spaceAccountFieldName;
	private String spaceAccountFieldType;
	private boolean displayInMobile;

	public int getSpaceAccountFieldsPk() {
		return SpaceAccountFieldsPk;
	}

	public void setSpaceAccountFieldsPk(int spaceAccountFieldsPk) {
		SpaceAccountFieldsPk = spaceAccountFieldsPk;
	}

	public String getInstitutionListFk() {
		return institutionListFk;
	}

	public void setInstitutionListFk(String institutionListFk) {
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

	public static List<SpaceAccountFieldsInfo> createResponse(List<SpaceAccountFields> spaceAccountFields) {
		List<SpaceAccountFieldsInfo> infos = new ArrayList<SpaceAccountFieldsInfo>();
		for (SpaceAccountFields list : spaceAccountFields) {
			SpaceAccountFieldsInfo spaceAccountFieldsInfo = new SpaceAccountFieldsInfo();
			spaceAccountFieldsInfo.setInstitutionListFk(list.getInstitutionListFk().getInstitutionCode());
			spaceAccountFieldsInfo.setSpaceAccountFieldsPk(list.getSpaceAccountFieldsPk());
			spaceAccountFieldsInfo.setSpaceAccountFieldName(list.getSpaceAccountFieldName());
			spaceAccountFieldsInfo.setSpaceAccountFieldType(list.getSpaceAccountFieldType());
			spaceAccountFieldsInfo.setDisplayInMobile(list.getDisplayInMobile());
			infos.add(spaceAccountFieldsInfo);
		}
		return infos;
	}

}
