package com.moneycore.model;

public class AuthenticationRequest {
	
	private String userName;
	
	private String password;
	
	public AuthenticationRequest() {
		
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
