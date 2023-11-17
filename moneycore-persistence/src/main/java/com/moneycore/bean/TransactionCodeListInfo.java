package com.moneycore.bean;

import java.util.ArrayList;
import java.util.List;

import com.moneycore.entity.TransactionCodeList;

public class TransactionCodeListInfo {

	private String institutionCode;
	private String transactionCode;
	private String transactionName;
	private String abbreviation;
	private String wording;
	private String userCreate;
	private String userModif;
	private Integer serviceCode;

	public String getInstitutionCode() {
		return institutionCode;
	}

	public void setInstitutionCode(String institutionCode) {
		this.institutionCode = institutionCode;
	}

	public String getTransactionCode() {
		return transactionCode;
	}

	public void setTransactionCode(String transactionCode) {
		this.transactionCode = transactionCode;
	}

	public String getTransactionName() {
		return transactionName;
	}

	public void setTransactionName(String transactionName) {
		this.transactionName = transactionName;
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

	public Integer getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(Integer serviceCode) {
		this.serviceCode = serviceCode;
	}

	public static List<TransactionCodeListInfo> createResponse(List<TransactionCodeList> transactionCodeLists) {
		List<TransactionCodeListInfo> transactionCodeListInfos = new ArrayList<TransactionCodeListInfo>();
		for (TransactionCodeList list : transactionCodeLists) {
			TransactionCodeListInfo transactionCodeListInfo = new TransactionCodeListInfo();
			transactionCodeListInfo.setInstitutionCode(list.getInstitutionCode());
			transactionCodeListInfo.setTransactionCode(list.getTransactionCode());
			transactionCodeListInfo.setTransactionName(list.getTransactionName());
			transactionCodeListInfo.setAbbreviation(list.getAbrvWording());
			transactionCodeListInfo.setWording(list.getWording());
			transactionCodeListInfo.setUserCreate(list.getUserCreate());
			transactionCodeListInfo.setUserModif(list.getUserModif());
			if(list.getServiceList()!=null) {
				transactionCodeListInfo.setServiceCode(list.getServiceList().getServiceCode());
			}
			transactionCodeListInfos.add(transactionCodeListInfo);
		}
		return transactionCodeListInfos;
	}

}
