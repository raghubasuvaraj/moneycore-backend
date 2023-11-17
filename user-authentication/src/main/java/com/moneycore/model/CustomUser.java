package com.moneycore.model;

import java.util.Collection;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class CustomUser {

	public CustomUser() {

	}

	public CustomUser(String userName, String password, Collection<SimpleGrantedAuthority> grantedAuthorities) {
		this.userName = userName;
		this.password = password;
		this.grantedAuthorities = grantedAuthorities;
	}

	private String userName;

	private String password;

	private Collection<SimpleGrantedAuthority> grantedAuthorities;

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

	public Collection<SimpleGrantedAuthority> getGrantedAuthorities() {
		return grantedAuthorities;
	}

	public void setGrantedAuthorities(Collection<SimpleGrantedAuthority> grantedAuthorities) {
		this.grantedAuthorities = grantedAuthorities;
	}
}
