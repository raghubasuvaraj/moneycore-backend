package com.moneycore.serviceimpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.moneycore.entity.GrantPermission;
import com.moneycore.entity.Roles;
import com.moneycore.repository.GrantPermissionRepository;
import com.moneycore.service.GrantPermissionService;
import com.moneycore.service.RolesService;
import com.moneycore.util.CommonUtil;

@Service("GrantPermissionService")
@Slf4j
public class GrantPermissionServiceImpl implements GrantPermissionService {

	@Autowired
	private GrantPermissionRepository grantPermissionrepository;

	@Autowired
	private RolesService rolesService;

	@PersistenceContext
	private EntityManager em;

	@Override
	public Optional<GrantPermission> find(int id) {
		return grantPermissionrepository.findById(id);
	}

	@Override
	public GrantPermission update(GrantPermission permission) {
		return grantPermissionrepository.save(permission);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GrantPermission> findByRole(Integer roleId) {
		List<GrantPermission> grant = em
				.createNativeQuery("select * from grant_permission where role_fk='" + roleId + "' order by menu_fk",
						GrantPermission.class)
				.getResultList();
		return grant;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GrantPermission> findGrantByRole(Integer roleId) {
		List<GrantPermission> permissions=null;
		try {
			permissions= (List<GrantPermission>) em
					.createNativeQuery("SELECT grant_permission_id,date_create,date_modif,user_create,user_modif,\n"
							+ "menu_fk,role_fk,component_fk,screen_fk,GROUP_CONCAT(type_grant) as type_grant FROM grant_permission where role_fk='"
							+ roleId + "' GROUP BY menu_fk", GrantPermission.class)
					.getResultList();
		}catch(Exception e){
			log.error("GrantPermissionServiceImpl.findGrantByRole ",e.getMessage());
		}
		return permissions;
	}

	@Override
	public GrantPermission addGP(GrantPermission gp) {
		return grantPermissionrepository.save(gp);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GrantPermission> findOne(Integer roleid, String componentCode, String menuCode, String screenCode) {
		List<GrantPermission> grant = em.createNativeQuery(
				"SELECT * FROM grant_permission where role_fk='" + roleid + "' and screen_fk='" + screenCode
						+ "' and component_fk='" + componentCode + "' and menu_fk='" + menuCode + "' LIMIT 1",
				GrantPermission.class).getResultList();
		return grant;
	}

	@SuppressWarnings("unchecked")
	@Override
	public GrantPermission findByScreenUrl(String uri) {
		GrantPermission grantPermission = null;
		List<GrantPermission> grant = em.createNativeQuery(
				"SELECT gp.* FROM grant_permission gp INNER JOIN screen s on gp.screen_fk = s.screen_id WHERE gp.role_fk in ("
						+ getCurrentUserRolesQueryString() + ") AND s.url ='" + uri + "'; ",
				GrantPermission.class).getResultList();
		if (grant != null && !grant.isEmpty()) {
			grantPermission = grant.get(0);
		}
		return grantPermission;
	}

	@SuppressWarnings("unchecked")
	@Override
	public GrantPermission findByMenuUrl(String uri) {
		GrantPermission grantPermission = null;
		List<GrantPermission> grant = em.createNativeQuery(
				"SELECT gp.* FROM grant_permission gp INNER JOIN menu m on gp.menu_fk = m.menu_id WHERE gp.role_fk in ("
						+ getCurrentUserRolesQueryString() + ") AND m.url ='" + uri + "'; ",
				GrantPermission.class).getResultList();
		if (grant != null && !grant.isEmpty()) {
			grantPermission = grant.get(0);
		}
		return grantPermission;
	}

	@Override
	public void deleteByRole(Integer roleid) {
		grantPermissionrepository.deleteByRole(roleid);
	}


	private String getCurrentUserRolesQueryString() {
		String rolesString = "";
		List<Roles> roles = null;
		Collection<SimpleGrantedAuthority> grantedAuthorities = CommonUtil.getCurrentUserAuthority();
		if (grantedAuthorities != null && !grantedAuthorities.isEmpty()) {
			roles = new ArrayList<>();
			for (SimpleGrantedAuthority grantedAuthority : grantedAuthorities) {
				roles.add(rolesService.getRoleByName(grantedAuthority.getAuthority()));
			}
		}
		if (roles != null && !roles.isEmpty()) {
			StringBuffer sb = new StringBuffer();
			for (Roles role : roles) {
				sb.append(role.getRoleid()).append(",");
			}
			rolesString = sb.toString();
			rolesString = rolesString.substring(0, rolesString.lastIndexOf(","));
		} else {
			rolesString = "0";
		}
		return rolesString;
	}
}
