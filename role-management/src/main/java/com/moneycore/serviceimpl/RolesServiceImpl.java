package com.moneycore.serviceimpl;

import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moneycore.entity.Roles;
import com.moneycore.repository.RolesRepository;
import com.moneycore.service.RolesService;

@Service("rolesService")
@Slf4j
public class RolesServiceImpl implements RolesService {
    @Autowired
    RolesRepository rolesRepository;

    @PersistenceContext
    private EntityManager em;

    public List<Roles> checkRoleNameduplication(String roleName, String institutionCode) {
        List<Roles> roles = null;
        try {
            roles = rolesRepository.checkRoleNameDuplicate(roleName, institutionCode);
        } catch (Exception e) {
            log.debug("RolesServiceImpl.checkRoleNameduplication debug", e.getMessage());
        }
        return roles;
    }

    public List<Roles> checkUpdateDuplication(String roleName, String institutionCode, Integer roleid) {
        List<Roles> roles = null;
        try {
            roles = rolesRepository.checkDuplicateRoleUpdate(roleName, institutionCode, roleid);
        } catch (Exception e) {
            log.error("RolesServiceImpl.checkUpdateDuplication", e.getLocalizedMessage());
        }
        return roles;
    }

    @Override
    public List<Roles> getAllRoles() {
        List<Roles> list = null;
        try {
            list = em.createNativeQuery(
                    "select r.roleid , r.institution_code , r.role_name ,IF(r.status = 1, 'Active' , 'InActive') as status ,r.role_code , r.wording , (select user_name from users u where user_id=r.createdby) as createdby ,r.createddate , r.modifyby , r.modifydate from roles r where modifyby != 0",
                    Roles.class).getResultList();
        } catch (Exception e) {
            log.error("RolesServiceImpl.getAllRoles", e.getLocalizedMessage());
        }
        return list;
    }

    public String loadRoles() {
        String roleString = null;
        try {
            List<String> roleEntity = em.createNativeQuery("select role_name from roles").getResultList();
            roleString = String.join(", ", roleEntity);
        } catch (Exception e) {
            log.error("RolesServiceImpl.loadRoles", e.getLocalizedMessage());
        }
        return roleString;
    }

    @Override
    public Optional<Roles> find(int id) {
        Optional<Roles> roles = null;
        try {
            roles = rolesRepository.findById(id);
        } catch (Exception e) {
            log.error("RolesServiceImpl.find ", e.getLocalizedMessage());
        }
        return roles;
    }

    @Override
    public Roles update(Roles role) {
        Roles response = null;
        try {
            response = rolesRepository.save(role);
        } catch (Exception e) {
            log.error("RolesServiceImpl.update ", e.getLocalizedMessage());
        }
        return response;
    }

    @Override
    public List<Roles> findByRoleCode(String roleCode) {
        List<Roles> roles = null;
        try {
            roles = em
                    .createNativeQuery("select r.role_code,r.role_name from roles r where role_code=:roleCode")
                    .setParameter("roleCode", roleCode).getResultList();
        } catch (Exception e) {
            log.error("RolesServiceImpl.findByRoleCode ", e.getLocalizedMessage());
        }
        return roles;
    }

    @Override
    public Roles insertRoles(Roles role) {
        Roles newRoles = null;
        try {
            role.setRoleCode(getAlphaNumericString(8));
            role.setDateCreate(new Timestamp(new Date().getTime()));
            newRoles = rolesRepository.save(role);
        } catch (Exception e) {
            log.error("RolesServiceImpl.insertRoles ", e.getLocalizedMessage());
        }
        return newRoles;
    }

    public static String getAlphaNumericString(int n)// length of string n
    {
        byte[] array = new byte[256];
        new Random().nextBytes(array);
        String randomString = new String(array, Charset.forName("UTF-8"));
        StringBuffer r = new StringBuffer();
        for (int k = 0; k < randomString.length(); k++) {
            char ch = randomString.charAt(k);
            if (((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') || (ch >= '0' && ch <= '9')) && (n > 0)) {
                r.append(ch);
                n--;
            }
        }
        return r.toString();
    }

    @Override
    public List<Roles> findAll() {
        List<Roles> list = null;
        try {
        list=rolesRepository.findAllRoles();
        } catch (Exception e) {
            log.error("RolesServiceImpl.findAll ", e.getMessage());
            log.info("RolesServiceImpl.findAll ", e.getMessage());
            log.debug("RolesServiceImpl.findAll ", e.getMessage());
        }
        return list;
    }

    @Override
    public Roles findRole(String roleCode) {
        Roles roles = null;
        try {
            roles = (Roles) em.createQuery("FROM Roles WHERE roleCode='" + roleCode + "'").getSingleResult();
        } catch (Exception e) {
            log.error("RolesServiceImpl.findRole ", e.getLocalizedMessage());
        }
        return roles;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Roles> getRolesForProfile(int profileId) {
        List<Roles> roles = null;
        try {
            roles = em.createNativeQuery(
                    "SELECT r.* from roles r INNER JOIN assigned_roles2permissions arp on arp.assigned_role_fk = r.roleid and arp.profile_fk = :profileId AND arp.is_deleted = false",
                    Roles.class).setParameter("profileId", profileId).getResultList();
        } catch (Exception e) {
            log.error("RolesServiceImpl.getRolesForProfile ", e.getLocalizedMessage());
        }
        return roles;
    }

    @Override
    public Roles getRoleByName(String name) {
        Roles role = null;
        try {
            Optional<Roles> optional = rolesRepository.findByRoleName(name);
            if (optional.isPresent()) {
                role = optional.get();
            }
        } catch (Exception e) {
            log.error("RolesServiceImpl.getRoleByName ", e.getLocalizedMessage());
        }
        return role;
    }

    @Override
    public void delete(int roleid) {
        try {
            rolesRepository.deleteById(roleid);
        } catch (Exception e) {
            log.error("RolesServiceImpl.delete ", e.getLocalizedMessage());
        }
    }
}
