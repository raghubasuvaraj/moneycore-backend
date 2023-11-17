package com.moneycore.bean;

import java.util.ArrayList;
import java.util.List;

import com.moneycore.entity.AccountTypeList;

public class AccountTypeListInfo {

	private String institutionCode;
	private String accountTypeCode;
	private String accountTypeName;
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

	public String getAccountTypeCode() {
		return accountTypeCode;
	}

	public void setAccountTypeCode(String accountTypeCode) {
		this.accountTypeCode = accountTypeCode;
	}

	public String getAccountTypeName() {
		return accountTypeName;
	}

	public void setAccountTypeName(String accountTypeName) {
		this.accountTypeName = accountTypeName;
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

	public static List<AccountTypeListInfo> createResponse(List<AccountTypeList> accountTypeList) {
		List<AccountTypeListInfo> accountTypeListInfos = new ArrayList<AccountTypeListInfo>();
		for (AccountTypeList list : accountTypeList) {
			AccountTypeListInfo accountTypeListInfo = new AccountTypeListInfo();
			accountTypeListInfo.setInstitutionCode(list.getInstitutionCode());
			accountTypeListInfo.setAccountTypeCode(list.getAccountTypeCode());
			accountTypeListInfo.setAccountTypeName(list.getAccountTypeName());
			accountTypeListInfo.setAbbreviation(list.getAbrvWording());
			accountTypeListInfo.setWording(list.getWording());
			accountTypeListInfo.setUserCreate(list.getUserCreate());
			accountTypeListInfo.setUserModif(list.getUserModif());
			accountTypeListInfos.add(accountTypeListInfo);
		}
		return accountTypeListInfos;
	}

}
