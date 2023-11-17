package com.moneycore.bean;

import java.util.ArrayList;
import java.util.List;

import com.moneycore.entity.CountryList;

public class CountryListInfoResponse {

	private String institutionCode;
	private String countryCode;
	private String countryName;
	private RegionListInfo regionCode;
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

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public RegionListInfo getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(RegionListInfo regionCode) {
		this.regionCode = regionCode;
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

	public static List<CountryListInfoResponse> createResponse(List<CountryList> countryList) {
		List<CountryListInfoResponse> countryListInfos = new ArrayList<CountryListInfoResponse>();
		for (CountryList list : countryList) {
			CountryListInfoResponse countryListInfo = new CountryListInfoResponse();
			countryListInfo.setInstitutionCode(list.getInstitutionCode());
			countryListInfo.setCountryCode(list.getCountryCode());
			countryListInfo.setCountryName(list.getCountryName());
			RegionListInfo regionListInfo = RegionListInfo.createSingleResponse(list.getRegionCode());
			countryListInfo.setRegionCode(regionListInfo);
			countryListInfo.setAbbrevation(list.getAbrvWording());
			countryListInfo.setWording(list.getWording());
			countryListInfo.setUserCreate(list.getUserCreate());
			countryListInfo.setUserModif(list.getUserModif());
			countryListInfos.add(countryListInfo);
		}
		return countryListInfos;
	}

	public static CountryListInfoResponse createSingleResponse(CountryList list) {
		CountryListInfoResponse countryListInfo = new CountryListInfoResponse();
		countryListInfo.setInstitutionCode(list.getInstitutionCode());
		countryListInfo.setCountryCode(list.getCountryCode());
		countryListInfo.setCountryName(list.getCountryName());
		if(list.getRegionCode() != null ) {
			RegionListInfo regionListInfo = RegionListInfo.createSingleResponse(list.getRegionCode());
			countryListInfo.setRegionCode(regionListInfo);
		}
		countryListInfo.setAbbrevation(list.getAbrvWording());
		countryListInfo.setWording(list.getWording());
		countryListInfo.setUserCreate(list.getUserCreate());
		countryListInfo.setUserModif(list.getUserModif());
		return countryListInfo;
	}
}
