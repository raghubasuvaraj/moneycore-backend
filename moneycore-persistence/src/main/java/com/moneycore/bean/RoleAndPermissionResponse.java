package com.moneycore.bean;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.moneycore.entity.GrantPermission;
import com.moneycore.entity.InstitutionList;
import com.moneycore.entity.Roles;

public class RoleAndPermissionResponse {

	private Integer roleid;

	private InstitutionList institute;

	private Timestamp dateCreate;

	private Timestamp modifydate;

	private String roleName;

	private String roleCode;

	private String status;

	private String userCreate;

	private String modifyby;

	private String wording;

	private String bankDataAccess;

	private char admin;

	private List<GrantPermissionModel> permissions;

	public Integer getRoleid() {
		return roleid;
	}

	public void setRoleid(Integer roleid) {
		this.roleid = roleid;
	}

	public InstitutionList getInstitute() {
		return institute;
	}

	public void setInstitute(InstitutionList institute) {
		this.institute = institute;
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

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
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

	public String getBankDataAccess() {
		return bankDataAccess;
	}

	public void setBankDataAccess(String bankDataAccess) {
		this.bankDataAccess = bankDataAccess;
	}

	public char getAdmin() {
		return admin;
	}

	public void setAdmin(char admin) {
		this.admin = admin;
	}

	public List<GrantPermissionModel> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<GrantPermissionModel> permissions) {
		this.permissions = permissions;
	}

	public static RoleAndPermissionResponse generateResponse(Roles roles, List<GrantPermission> listGrantPermissions) {
		RoleAndPermissionResponse response = new RoleAndPermissionResponse();
		response.setAdmin(roles.getAdmin());
		response.setBankDataAccess(roles.getBankDataAccess());
		response.setDateCreate(roles.getDateCreate());
		response.setInstitute(roles.getInstitute());
		response.setModifyby(roles.getModifyby());
		response.setModifydate(roles.getModifydate());
		response.setRoleCode(roles.getRoleCode());
		response.setRoleid(roles.getRoleid());
		response.setRoleName(roles.getRoleName());
		response.setStatus(roles.getStatus());
		response.setUserCreate(roles.getUserCreate());
		response.setWording(roles.getWording());
		List<GrantPermissionModel> grantPermissionModels = new ArrayList<GrantPermissionModel>();
		for (GrantPermission grantPermission : listGrantPermissions) {
			GrantPermissionModel grantPermissionModel = new GrantPermissionModel();
			grantPermissionModel.setComponentFk((grantPermission.getComponentFk() == null) ? null
					: grantPermission.getComponentFk().getComponentCode());
			grantPermissionModel.setMenuFk(grantPermission.getMenuFk().getMenuCode());
			grantPermissionModel.setScreenFk(grantPermission.getScreenFk().getScreenCode());
			grantPermissionModel.setRoleFk(grantPermission.getRoleFk().getRoleid());
			List<String> listOfType = new ArrayList<String>();
			listOfType.add(grantPermission.getType());
			grantPermissionModel.setType(listOfType);
			grantPermissionModels.add(grantPermissionModel);
		}
		if (listGrantPermissions.isEmpty()) {
			response.setPermissions(null);
		} else {
			response.setPermissions(grantPermissionModels);
		}
		return response;
	}
}
