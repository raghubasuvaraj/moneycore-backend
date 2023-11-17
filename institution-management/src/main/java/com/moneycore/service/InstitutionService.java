package com.moneycore.service;

import java.util.List;

import javax.validation.Valid;

import com.moneycore.entity.InstitutionList;

public interface InstitutionService {
	
	public InstitutionList insert(InstitutionList institute);

	public InstitutionList findByInstitutionCode(String instituteCode);

	public List<InstitutionList> findAll();

	public InstitutionList update(InstitutionList institute);

	public InstitutionList insertInstitution(@Valid InstitutionList newInstitute);

	public InstitutionList findByEmail(String emailAddress);
	public void delete(String instituteCode);
	public boolean finddup(String institutionName);
	public List<InstitutionList>  finddup(String institutionName,String instituteCode);

}