package com.moneycore.bean;

import java.util.ArrayList;
import java.util.List;

import com.moneycore.entity.RegionList;

public class RegionListInfo {

	private String institutionCode;
	private String regionCode;
	private String regionName;
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

	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
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

	public static List<RegionListInfo> createResponse(List<RegionList> regionList) {
		List<RegionListInfo> infos = new ArrayList<RegionListInfo>();
		for (RegionList list : regionList) {
			RegionListInfo regionListInfo = new RegionListInfo();
			regionListInfo.setInstitutionCode(list.getInstitutionCode());
			regionListInfo.setRegionCode(list.getRegionCode());
			regionListInfo.setRegionName(list.getRegionName());
			regionListInfo.setAbbreviation(list.getAbrvWording());
			regionListInfo.setWording(list.getWording());
			regionListInfo.setUserCreate(list.getUserCreate());
			regionListInfo.setUserModif(list.getUserModif());
			infos.add(regionListInfo);
		}
		return infos;
	}

	public static RegionListInfo createSingleResponse(RegionList list) {
		RegionListInfo regionListInfo = new RegionListInfo();
		regionListInfo.setInstitutionCode(list.getInstitutionCode());
		regionListInfo.setRegionCode(list.getRegionCode());
		regionListInfo.setRegionName(list.getRegionName());
		regionListInfo.setAbbreviation(list.getAbrvWording());
		regionListInfo.setWording(list.getWording());
		regionListInfo.setUserCreate(list.getUserCreate());
		regionListInfo.setUserModif(list.getUserModif());
		return regionListInfo;
	}

}
