package com.moneycore.bean;

import java.util.List;

public class UserResponse {

	private int userId;
	private String userName;
	private String email;
	private String institutionCode;
	private String institutionName;
	private List<Menues> menues;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getInstitutionCode() {
		return institutionCode;
	}

	public void setInstitutionCode(String institutionCode) {
		this.institutionCode = institutionCode;
	}

	public String getInstitutionName() {
		return institutionName;
	}

	public void setInstitutionName(String institutionName) {
		this.institutionName = institutionName;
	}

	public List<Menues> getMenues() {
		return menues;
	}

	public void setMenues(List<Menues> menues) {
		this.menues = menues;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
