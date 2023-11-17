package com.moneycore.bean;

import java.util.ArrayList;
import java.util.List;

import com.moneycore.entity.StatusList;

public class StatusListInfo {

	private String institutionCode;
	private String statusCode;
	private String statusName;
	private char blockLogin;
	private char blockTransact;
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

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public char getBlockLogin() {
		return blockLogin;
	}

	public void setBlockLogin(char blockLogin) {
		this.blockLogin = blockLogin;
	}

	public char getBlockTransact() {
		return blockTransact;
	}

	public void setBlockTransact(char blockTransact) {
		this.blockTransact = blockTransact;
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

	public static List<StatusListInfo> createResponse(List<StatusList> statusList) {
		List<StatusListInfo> infos = new ArrayList<StatusListInfo>();
		for (StatusList list : statusList) {
			StatusListInfo statusListInfo = new StatusListInfo();
			statusListInfo.setInstitutionCode(list.getInstitutionCode());
			statusListInfo.setStatusCode(list.getStatusCode());
			statusListInfo.setStatusName(list.getStatusName());
			statusListInfo.setBlockLogin(list.getBlockLogin());
			statusListInfo.setBlockTransact(list.getBlockTransact());
			statusListInfo.setAbbreviation(list.getAbrvWording());
			statusListInfo.setWording(list.getWording());
			statusListInfo.setUserCreate(list.getUserCreate());
			statusListInfo.setUserModif(list.getUserModif());
			infos.add(statusListInfo);
		}
		return infos;
	}

	public static StatusListInfo createSingleResponse(StatusList list) {
		StatusListInfo statusListInfo = new StatusListInfo();
		statusListInfo.setInstitutionCode(list.getInstitutionCode());
		statusListInfo.setStatusCode(list.getStatusCode());
		statusListInfo.setStatusName(list.getStatusName());
		statusListInfo.setBlockLogin(list.getBlockLogin());
		statusListInfo.setBlockTransact(list.getBlockTransact());
		statusListInfo.setAbbreviation(list.getAbrvWording());
		statusListInfo.setWording(list.getWording());
		statusListInfo.setUserCreate(list.getUserCreate());
		statusListInfo.setUserModif(list.getUserModif());
		return statusListInfo;
	}

}
