package com.moneycore.bean;

import java.util.ArrayList;
import java.util.List;

import com.moneycore.entity.VipList;

public class VipListInfo {

	private String institutionCode;
	private String vipCode;
	private String vipName;
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

	public String getVipCode() {
		return vipCode;
	}

	public void setVipCode(String vipCode) {
		this.vipCode = vipCode;
	}

	public String getVipName() {
		return vipName;
	}

	public void setVipName(String vipName) {
		this.vipName = vipName;
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

	public static List<VipListInfo> createResponse(List<VipList> vipList) {
		List<VipListInfo> vipListInfos = new ArrayList<VipListInfo>();
		for (VipList list : vipList) {
			VipListInfo vipListInfo = new VipListInfo();
			vipListInfo.setInstitutionCode(list.getInstitutionCode());
			vipListInfo.setVipCode(list.getVipCode());
			vipListInfo.setVipName(list.getVipName());
			vipListInfo.setAbbreviation(list.getAbrvWording());
			vipListInfo.setWording(list.getWording());
			vipListInfo.setUserCreate(list.getUserCreate());
			vipListInfo.setUserModif(list.getUserModif());
			vipListInfos.add(vipListInfo);
		}
		return vipListInfos;
	}

}
