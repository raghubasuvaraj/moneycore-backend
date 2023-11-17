package com.moneycore.bean;

import java.util.ArrayList;
import java.util.List;

import com.moneycore.entity.MccList;

public class MccListInfo {

	private String institutionCode;
	private String mccCode;
	private String mccName;
	private String abbreviation;
	private String wording;
	private String userCreate;
	private String userModif;

	public String getInstitutionCode() {
		return institutionCode;
	}

	public void setInstitutionCode(String institutionCode) {
		this.institutionCode = institutionCode;
	}

	public String getMccCode() {
		return mccCode;
	}

	public void setMccCode(String mccCode) {
		this.mccCode = mccCode;
	}

	public String getMccName() {
		return mccName;
	}

	public void setMccName(String mccName) {
		this.mccName = mccName;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
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

	public String getUserModif() {
		return userModif;
	}

	public void setUserModif(String userModif) {
		this.userModif = userModif;
	}

	public static List<MccListInfo> createResponse(List<MccList> mLists) {
		List<MccListInfo> mccListInfos = new ArrayList<MccListInfo>();
		for (MccList list : mLists) {
			MccListInfo mccListInfo = new MccListInfo();
			mccListInfo.setInstitutionCode(list.getInstitutionCode());
			mccListInfo.setMccCode(list.getMccCode());
			mccListInfo.setMccName(list.getMccName());
			mccListInfo.setAbbreviation(list.getAbrvWording());
			mccListInfo.setWording(list.getWording());
			mccListInfo.setUserCreate(list.getUserCreate());
			mccListInfo.setUserModif(list.getUserModif());
			mccListInfos.add(mccListInfo);
		}
		return mccListInfos;
	}

}
