package com.moneycore.bean;

import java.util.ArrayList;
import java.util.List;

import com.moneycore.entity.CustomerSegmentList;

public class CustomerSegmentListInfo {

	private String institutionCode;
	private String customerSegmentCode;
	private String customerSegmentName;
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

	public String getCustomerSegmentCode() {
		return customerSegmentCode;
	}

	public void setCustomerSegmentCode(String customerSegmentCode) {
		this.customerSegmentCode = customerSegmentCode;
	}

	public String getCustomerSegmentName() {
		return customerSegmentName;
	}

	public void setCustomerSegmentName(String customerSegmentName) {
		this.customerSegmentName = customerSegmentName;
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

	public static List<CustomerSegmentListInfo> createResponse(List<CustomerSegmentList> customerSegmentList) {
		List<CustomerSegmentListInfo> customerSegmentListInfos = new ArrayList<CustomerSegmentListInfo>();
		for (CustomerSegmentList list : customerSegmentList) {
			CustomerSegmentListInfo customerSegmentListInfo = new CustomerSegmentListInfo();
			customerSegmentListInfo.setInstitutionCode(list.getInstitutionCode());
			customerSegmentListInfo.setCustomerSegmentCode(list.getCustomerSegmentCode());
			customerSegmentListInfo.setCustomerSegmentName(list.getCustomerSegmentName());
			customerSegmentListInfo.setAbbreviation(list.getAbrvWording());
			customerSegmentListInfo.setWording(list.getWording());
			customerSegmentListInfo.setUserCreate(list.getUserCreate());
			customerSegmentListInfo.setUserModif(list.getUserModif());
			customerSegmentListInfos.add(customerSegmentListInfo);
		}
		return customerSegmentListInfos;
	}

	public static CustomerSegmentListInfo createSingleResponse(CustomerSegmentList list) {
		CustomerSegmentListInfo customerSegmentListInfo = new CustomerSegmentListInfo();
		customerSegmentListInfo.setInstitutionCode(list.getInstitutionCode());
		customerSegmentListInfo.setCustomerSegmentCode(list.getCustomerSegmentCode());
		customerSegmentListInfo.setCustomerSegmentName(list.getCustomerSegmentName());
		customerSegmentListInfo.setAbbreviation(list.getAbrvWording());
		customerSegmentListInfo.setWording(list.getWording());
		customerSegmentListInfo.setUserCreate(list.getUserCreate());
		customerSegmentListInfo.setUserModif(list.getUserModif());
		return customerSegmentListInfo;
	}

}
