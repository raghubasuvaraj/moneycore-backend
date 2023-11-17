package com.moneycore.bean;

import java.util.ArrayList;
import java.util.List;

import com.moneycore.entity.LanguageList;

public class LanguageListInfo {

	private String institutionCode;
	private String languageCode;
	private String languageName;
	private String abbrevation;
	private String wording;
	private String userCreate;
	private String userModif;

	public String getInstitutionCode() {
		return institutionCode;
	}

	public void setInstitutionCode(String institutionCode) {
		this.institutionCode = institutionCode;
	}

	public String getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	public String getLanguageName() {
		return languageName;
	}

	public void setLanguageName(String languageName) {
		this.languageName = languageName;
	}

	public String getAbbrevation() {
		return abbrevation;
	}

	public void setAbbrevation(String abbrevation) {
		this.abbrevation = abbrevation;
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

	public static List<LanguageListInfo> createResponse(List<LanguageList> languageList) {
		List<LanguageListInfo> languageListInfos = new ArrayList<LanguageListInfo>();
		for (LanguageList list : languageList) {
			LanguageListInfo languageListInfo = new LanguageListInfo();
			languageListInfo.setInstitutionCode(list.getInstitutionCode());
			languageListInfo.setLanguageCode(list.getLanguageCode());
			languageListInfo.setLanguageName(list.getLanguageName());
			languageListInfo.setAbbrevation(list.getAbrvWording());
			languageListInfo.setWording(list.getWording());
			languageListInfo.setUserCreate(list.getUserCreate());
			languageListInfo.setUserModif(list.getUserModif());
			languageListInfos.add(languageListInfo);
		}
		return languageListInfos;
	}

	public static LanguageListInfo createSingleResponse(LanguageList list) {
		LanguageListInfo languageListInfo = new LanguageListInfo();
		languageListInfo.setInstitutionCode(list.getInstitutionCode());
		languageListInfo.setLanguageCode(list.getLanguageCode());
		languageListInfo.setLanguageName(list.getLanguageName());
		languageListInfo.setAbbrevation(list.getAbrvWording());
		languageListInfo.setWording(list.getWording());
		languageListInfo.setUserCreate(list.getUserCreate());
		languageListInfo.setUserModif(list.getUserModif());
		return languageListInfo;
	}

}
