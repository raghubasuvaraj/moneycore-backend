package com.moneycore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.moneycore.entity.GrantPermission;

@Repository("grantPermissionRepository")
public interface GrantPermissionRepository extends JpaRepository<GrantPermission, Integer>{

	@Query(value = "DELETE FROM grant_permission where role_fk = :roleId", nativeQuery = true)
	public void deleteByRole(@Param("roleId") Integer roleId);
}
