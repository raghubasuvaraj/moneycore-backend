package com.moneycore.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.moneycore.entity.GrantPermission;

public interface GrantPermissionService {

	List<GrantPermission> findByRole(Integer roleId);

	GrantPermission addGP(@Valid GrantPermission gp);

	List<GrantPermission> findOne(Integer roleid, String componentCode, String menuCode, String screenCode);

	List<GrantPermission> findGrantByRole(Integer roleId);

	GrantPermission findByScreenUrl(String uri);

	GrantPermission findByMenuUrl(String uri);

	GrantPermission update(GrantPermission permission);

	Optional<GrantPermission> find(int id);

	void deleteByRole(Integer roleid);
}
