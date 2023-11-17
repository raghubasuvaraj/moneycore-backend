package com.moneycore.bean;

import com.moneycore.entity.ServiceList;
import com.moneycore.entity.TransactionCodeList;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

public class ServiceListInfo {

	private int serviceCode;
	private String description;
	private String institutionCode;
	private String serviceName;
	private String status;
	private String abrvWording;
	private String userCreate;
	private String userModif;

	public int getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(int serviceCode) {
		this.serviceCode = serviceCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getInstitutionCode() {
		return institutionCode;
	}

	public void setInstitutionCode(String institutionCode) {
		this.institutionCode = institutionCode;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAbrvWording() {
		return abrvWording;
	}

	public void setAbrvWording(String abrvWording) {
		this.abrvWording = abrvWording;
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

	public static List<ServiceListInfo> createResponse(List<ServiceList> serviceLists) {
		List<ServiceListInfo> serviceListInfos = new ArrayList<ServiceListInfo>();
		for (ServiceList list : serviceLists) {
			ServiceListInfo servieListInfo = new ServiceListInfo();
			servieListInfo.setInstitutionCode(list.getInstitutionCode());
			servieListInfo.setDescription(list.getDescription());
			servieListInfo.setServiceName(list.getServiceName());
			servieListInfo.setStatus(list.getStatus());
			servieListInfo.setAbrvWording(list.getAbrvWording());
			servieListInfo.setUserCreate(list.getUserCreate());
			servieListInfo.setUserModif(list.getUserModif());
			servieListInfo.setServiceCode(list.getServiceCode());
			serviceListInfos.add(servieListInfo);
		}
		return serviceListInfos;
	}

}
