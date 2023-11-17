package com.moneycore.service;

import java.util.List;
import java.util.Optional;

import com.moneycore.entity.Roles;

public interface RolesService {

    public List<Roles> findByRoleCode(String roleCode);

    public Roles insertRoles(Roles role);

    public List<Roles> getAllRoles();

    String loadRoles();

    Optional<Roles> find(int id);

    public Roles update(Roles role);

    public List<Roles> findAll();

    public Roles findRole(String roleCode);

    public List<Roles> getRolesForProfile(int profileId);

    public Roles getRoleByName(String name);

    public void delete(int roleid);

    public List<Roles> checkRoleNameduplication(String roleName, String institutionCode);

    public List<Roles> checkUpdateDuplication(String roleName, String institutionCode, Integer roleid);


}
