package com.moneycore.bean;

import java.util.ArrayList;
import java.util.List;

import com.moneycore.entity.TitleList;

public class TitleListInfo {

	private String institutionCode;
	private String titleCode;
	private String titleName;
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

	public String getTitleCode() {
		return titleCode;
	}

	public void setTitleCode(String titleCode) {
		this.titleCode = titleCode;
	}

	public String getTitleName() {
		return titleName;
	}

	public void setTitleName(String titleName) {
		this.titleName = titleName;
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

	public static List<TitleListInfo> createResponse(List<TitleList> titleList) {
		List<TitleListInfo> titleListInfos = new ArrayList<TitleListInfo>();
		for (TitleList list : titleList) {
			TitleListInfo titleListInfo = new TitleListInfo();
			titleListInfo.setInstitutionCode(list.getInstitutionCode());
			titleListInfo.setTitleCode(list.getTitleCode());
			titleListInfo.setTitleName(list.getTitleName());
			titleListInfo.setAbbreviation(list.getAbrvWording());
			titleListInfo.setWording(list.getWording());
			titleListInfo.setUserCreate(list.getUserCreate());
			titleListInfo.setUserModif(list.getUserModif());
			titleListInfos.add(titleListInfo);
		}
		return titleListInfos;
	}

	public static TitleListInfo createSingleResponse(TitleList list) {
		TitleListInfo titleListInfo = new TitleListInfo();
		titleListInfo.setInstitutionCode(list.getInstitutionCode());
		titleListInfo.setTitleCode(list.getTitleCode());
		titleListInfo.setTitleName(list.getTitleName());
		titleListInfo.setAbbreviation(list.getAbrvWording());
		titleListInfo.setWording(list.getWording());
		titleListInfo.setUserCreate(list.getUserCreate());
		titleListInfo.setUserModif(list.getUserModif());
		return titleListInfo;
	}

}
