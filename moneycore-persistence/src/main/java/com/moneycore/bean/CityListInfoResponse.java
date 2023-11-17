package com.moneycore.bean;

import java.util.ArrayList;
import java.util.List;

import com.moneycore.entity.CityList;

public class CityListInfoResponse {

	private String institutionCode;
	private RegionListInfo regionCode;
	private CountryListInfoResponse countryCode;
	private String cityCode;
	private String cityName;
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

	public RegionListInfo getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(RegionListInfo regionCode) {
		this.regionCode = regionCode;
	}

	public CountryListInfoResponse getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(CountryListInfoResponse countryCode) {
		this.countryCode = countryCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
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

	public static List<CityListInfoResponse> createResponse(List<CityList> cityList) {
		List<CityListInfoResponse> cityListInfos = new ArrayList<CityListInfoResponse>();
		for (CityList list : cityList) {
			CityListInfoResponse cityListInfo = new CityListInfoResponse();
			cityListInfo.setInstitutionCode(list.getInstitutionCode());
			cityListInfo.setCityCode(list.getCityCode());
			cityListInfo.setCityName(list.getCityName());
			RegionListInfo regionListInfo = RegionListInfo.createSingleResponse(list.getRegionCode());
			cityListInfo.setRegionCode(regionListInfo);
			if(list.getCountryCode() != null) {
				CountryListInfoResponse countryListInfoResponse = CountryListInfoResponse.createSingleResponse(list.getCountryCode());
				cityListInfo.setCountryCode(countryListInfoResponse);
			}
			cityListInfo.setAbbrevation(list.getAbrvWording());
			cityListInfo.setWording(list.getWording());
			cityListInfo.setUserCreate(list.getUserCreate());
			cityListInfo.setUserModif(list.getUserModif());
			cityListInfos.add(cityListInfo);
		}
		return cityListInfos;
	}

}
