package com.moneycore.model;

public class AuthenticationResponse {

	private String jwt;

	private Object userDetail;

	public AuthenticationResponse(String jwt, Object userDetail) {
		this.jwt = jwt;
		this.userDetail = userDetail;
	}

	public String getJwt() {
		return jwt;
	}

	public void setJwt(String jwt) {
		this.jwt = jwt;
	}

	public Object getUserDetail() {
		return userDetail;
	}

	public void setUserDetail(Object userDetail) {
		this.userDetail = userDetail;
	}
}
