package com.moneycore.serviceimpl;

import java.security.Permission;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moneycore.entity.Permissions;
import com.moneycore.entity.Roles;
import com.moneycore.repository.PermissionsRepository;
import com.moneycore.service.PermissionsService;

@Service("permissionsService")
public class PermissionsServiceImpl implements PermissionsService {

	@Autowired
	PermissionsRepository permissionRepository;

	@PersistenceContext
	private EntityManager em;

	@Override
	public void savePermissions(Permissions permissions) {
		permissionRepository.save(permissions);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Permissions> getPermissionsByProfile(int profileID) {
		List<Permissions> profile = em.createNativeQuery(
				"select p.* from assigned_roles2permissions p where p.profile_fk=:profileID AND is_deleted = '0'",
				Permissions.class).setParameter("profileID", profileID).getResultList();
		return profile;
	}

	@Override
	@Transactional
	public void deleteByProfile(int profileID) {
		em.createNativeQuery("DELETE FROM assigned_roles2permissions where profile_fk=:profileID")
				.setParameter("profileID", profileID).executeUpdate();
	}

	@Override
	public List<Integer> findRole(int profileId) {
		List<Integer> list = em
				.createNativeQuery("SELECT assigned_role_fk FROM assigned_roles2permissions WHERE profile_fk="
						+ profileId + " AND is_deleted = '0'")
				.getResultList();
		return list.size() > 0 ? list : null;
	}

	@Override
	public Boolean ifRolePresent(int profileId, int roleId) {
		List<Permission> list = em
				.createQuery("FROM Permissions WHERE profileId=" + profileId + " AND roleId=" + roleId + "")
				.getResultList();
		if (list.isEmpty()) {
			return true;
		}
		return false;
	}

	@Override
	public Boolean ifRolePresentCheck(int roleId) {
		List<Permission> list = em
				.createQuery("FROM Permissions WHERE  roleId=" + roleId + "")
				.getResultList();
		if (list.isEmpty()) {
			return true;
		}
		return false;
	}

}