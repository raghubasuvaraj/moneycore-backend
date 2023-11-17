package com.moneycore.bean;

import java.util.ArrayList;
import java.util.List;

import com.moneycore.entity.StatusReasonCode;

public class StatusReasonCodeInfoResponse {

	private String institutionCode;
	private StatusListInfo statusCode;
	private String statusReasonCode;
	private String statusReasonName;
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

	public StatusListInfo getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(StatusListInfo statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusReasonCode() {
		return statusReasonCode;
	}

	public void setStatusReasonCode(String statusReasonCode) {
		this.statusReasonCode = statusReasonCode;
	}

	public String getStatusReasonName() {
		return statusReasonName;
	}

	public void setStatusReasonName(String statusReasonName) {
		this.statusReasonName = statusReasonName;
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

	public static List<StatusReasonCodeInfoResponse> createResponse(List<StatusReasonCode> statusReasonCode) {
		List<StatusReasonCodeInfoResponse> infos = new ArrayList<StatusReasonCodeInfoResponse>();
		for (StatusReasonCode list : statusReasonCode) {
			StatusReasonCodeInfoResponse statusListCodeInfo = new StatusReasonCodeInfoResponse();
			statusListCodeInfo.setInstitutionCode(list.getInstitutionCode());
			StatusListInfo statusListInfo  = StatusListInfo.createSingleResponse(list.getStatusCode());
			statusListCodeInfo.setStatusCode(statusListInfo);
			statusListCodeInfo.setStatusReasonCode(list.getStatusReasonCode());
			statusListCodeInfo.setStatusReasonName(list.getStatusReasonName());
			statusListCodeInfo.setAbbreviation(list.getAbrvWording());
			statusListCodeInfo.setWording(list.getWording());
			statusListCodeInfo.setUserCreate(list.getUserCreate());
			statusListCodeInfo.setUserModif(list.getUserModif());
			infos.add(statusListCodeInfo);
		}
		return infos;
	}

	public static StatusReasonCodeInfoResponse createSingleResponse(StatusReasonCode list) {
		StatusReasonCodeInfoResponse statusListCodeInfo = new StatusReasonCodeInfoResponse();
		statusListCodeInfo.setInstitutionCode(list.getInstitutionCode());
		StatusListInfo statusListInfo  = StatusListInfo.createSingleResponse(list.getStatusCode());
		statusListCodeInfo.setStatusCode(statusListInfo);
		statusListCodeInfo.setStatusReasonCode(list.getStatusReasonCode());
		statusListCodeInfo.setStatusReasonName(list.getStatusReasonName());
		statusListCodeInfo.setAbbreviation(list.getAbrvWording());
		statusListCodeInfo.setWording(list.getWording());
		statusListCodeInfo.setUserCreate(list.getUserCreate());
		statusListCodeInfo.setUserModif(list.getUserModif());
		return statusListCodeInfo;
	}

}
