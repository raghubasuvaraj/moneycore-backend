package com.moneycore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.moneycore.entity.InstitutionRegister;

@Repository("institutionRegisterRepository")
public interface InstitutionRegisterRepository extends JpaRepository<InstitutionRegister, String> {

	@Query(value = "SELECT * FROM institution_register", nativeQuery = true)
	List<InstitutionRegister> findAll();

	@Query(value = "SELECT * FROM institution_register WHERE is_approved = :status", nativeQuery = true)
	List<InstitutionRegister> findAll(char status);

}
