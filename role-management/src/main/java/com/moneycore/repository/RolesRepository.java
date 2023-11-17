package com.moneycore.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.moneycore.entity.Roles;

@Repository("rolesRepository")
public interface RolesRepository extends JpaRepository<Roles, Integer> {

	Optional<Roles> findByRoleName(String roleName);

	@Query(value = "select a.* from roles a  where a.role_name=:roleName and  a.institution_code=:institutionCode",nativeQuery = true)
	List<Roles> checkRoleNameDuplicate(@Param("roleName") String roleName, @Param("institutionCode") String institutionCode);

	@Query(value = "select a.* from roles a  where a.role_name=:roleName and  a.institution_code=:institutionCode and a.roleid !=:roleid",nativeQuery = true)
	List<Roles> checkDuplicateRoleUpdate(@Param("roleName") String roleName, @Param("institutionCode") String institutionCode, @Param("roleid") Integer roleid);

	@Query(value="select u.* from roles u where u.role_name !='superadmin' order by u.roleid DESC",nativeQuery = true)
    List<Roles> findAllRoles();
}
