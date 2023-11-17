package com.moneycore.service;

import java.util.List;

import com.moneycore.entity.Permissions;

public interface PermissionsService {
	public void savePermissions(Permissions permissions);
	public void deleteByProfile(int profileID);
	public List<Permissions> getPermissionsByProfile(int profileID);
	public List<Integer> findRole(int profileId);
	public Boolean ifRolePresent(int profileId, int roleId);
	public Boolean ifRolePresentCheck(int roleId);
}
