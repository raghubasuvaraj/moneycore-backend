package com.moneycore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.moneycore.entity.Permissions;


@Repository("assigningRoleToPermissonsRepository")
public interface PermissionsRepository extends JpaRepository<Permissions, Integer>{

}
