package com.moneycore.bean;

import java.util.ArrayList;
import java.util.List;

import com.moneycore.entity.CurrencyList;

public class CurrencyListInfo {

	private String institutionCode;
	private String currencyCode;
	private String abrvWording;
	private String wording;
	private int precisionValue;
	private String userCreate;
	private String userModif;

	public String getInstitutionCode() {
		return institutionCode;
	}

	public void setInstitutionCode(String institutionCode) {
		this.institutionCode = institutionCode;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getAbrvWording() {
		return abrvWording;
	}

	public void setAbrvWording(String abrvWording) {
		this.abrvWording = abrvWording;
	}

	public String getWording() {
		return wording;
	}

	public void setWording(String wording) {
		this.wording = wording;
	}

	public int getPrecisionValue() {
		return precisionValue;
	}

	public void setPrecisionValue(int precisionValue) {
		this.precisionValue = precisionValue;
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

	public static List<CurrencyListInfo> createResponse(List<CurrencyList> currencyList) {
		List<CurrencyListInfo> currencyListInfos = new ArrayList<CurrencyListInfo>();
		for (CurrencyList list : currencyList) {
			CurrencyListInfo currencyListInfo = new CurrencyListInfo();
			currencyListInfo.setInstitutionCode(list.getInstitutionCode());
			currencyListInfo.setCurrencyCode(list.getCurrencyCode());
			currencyListInfo.setAbrvWording(list.getAbrvWording());
			currencyListInfo.setWording(list.getWording());
			currencyListInfo.setPrecisionValue(list.getPrecisionValue());
			currencyListInfo.setUserCreate(list.getUserCreate());
			currencyListInfo.setUserModif(list.getUserModif());
			currencyListInfos.add(currencyListInfo);
		}
		return currencyListInfos;
	}

	public static CurrencyListInfo createSingleResponse(CurrencyList list) {
		CurrencyListInfo currencyListInfo = new CurrencyListInfo();
		currencyListInfo.setInstitutionCode(list.getInstitutionCode());
		currencyListInfo.setCurrencyCode(list.getCurrencyCode());
		currencyListInfo.setAbrvWording(list.getAbrvWording());
		currencyListInfo.setWording(list.getWording());
		currencyListInfo.setPrecisionValue(list.getPrecisionValue());
		currencyListInfo.setUserCreate(list.getUserCreate());
		currencyListInfo.setUserModif(list.getUserModif());
		return currencyListInfo;
	}

}