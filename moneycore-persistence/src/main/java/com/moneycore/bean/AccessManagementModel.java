package com.moneycore.bean;

import java.util.List;

public class AccessManagementModel {

	private String roleName;
	private char status;
	private String roleCode;
	private String institutionCode;
	private String wording;
	private char admin;
	private String userCreate;
	private String bankDataAccess;
	private String userModif;
	private List<GrantPermissionModel> permissions;

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public char getStatus() {
		return status;
	}

	public void setStatus(char status) {
		this.status = status;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getInstitutionCode() {
		return institutionCode;
	}

	public void setInstitutionCode(String institutionCode) {
		this.institutionCode = institutionCode;
	}

	public String getWording() {
		return wording;
	}

	public void setWording(String wording) {
		this.wording = wording;
	}

	public char getAdmin() {
		return admin;
	}

	public void setAdmin(char admin) {
		this.admin = admin;
	}

	public String getUserCreate() {
		return userCreate;
	}

	public void setUserCreate(String userCreate) {
		this.userCreate = userCreate;
	}

	public String getBankDataAccess() {
		return bankDataAccess;
	}

	public void setBankDataAccess(String bankDataAccess) {
		this.bankDataAccess = bankDataAccess;
	}

	public String getUserModif() {
		return userModif;
	}

	public void setUserModif(String userModif) {
		this.userModif = userModif;
	}

	public List<GrantPermissionModel> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<GrantPermissionModel> permissions) {
		this.permissions = permissions;
	}

}
