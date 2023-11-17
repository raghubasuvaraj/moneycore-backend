package com.moneycore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.moneycore.entity.Component;

@Repository("componentRepository")
public interface ComponentRepository extends JpaRepository<Component, Integer>{

}
