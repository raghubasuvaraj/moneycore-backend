package com.moneycore.bean;

import java.sql.Timestamp;

public class RolesBean {
	private String roleid;
	private String institution_code;
	private Timestamp dateCreate;
	private Timestamp modifydate;
	private String name;
	private String role_code;
	private String status;
	private String userCreate;
	private String modifyby;
	private String wording;

	public String getRoleid() {
		return roleid;
	}

	public void setRoleId(String roleid) {
		this.roleid = roleid;
	}

	public String getInstitutionCode() {
		return institution_code;
	}

	public void setInstitutionCode(String institution_code) {
		this.institution_code = institution_code;
	}

	public Timestamp getDateCreate() {
		return dateCreate;
	}

	public void setDateCreate(Timestamp dateCreate) {
		this.dateCreate = dateCreate;
	}

	public Timestamp getModifydate() {
		return modifydate;
	}

	public void setModifydate(Timestamp modifydate) {
		this.modifydate = modifydate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRoleCode() {
		return role_code;
	}

	public void setRoleCode(String role_code) {
		this.role_code = role_code;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUserCreate() {
		return userCreate;
	}

	public void setUserCreate(String userCreate) {
		this.userCreate = userCreate;
	}

	public String getModifyby() {
		return modifyby;
	}

	public void setModifyby(String modifyby) {
		this.modifyby = modifyby;
	}

	public String getWording() {
		return wording;
	}

	public void setWording(String wording) {
		this.wording = wording;
	}

}
