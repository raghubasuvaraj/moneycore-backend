package com.moneycore.service;

import java.util.List;

import javax.validation.Valid;

import com.moneycore.bean.InstitutionRegisterInfo;
import com.moneycore.entity.InstitutionList;
import com.moneycore.entity.InstitutionRegister;

public interface InstitutionRegisterService {

	InstitutionRegister findByInstitutionCode(String institutionCode);

	InstitutionRegister insertInstitution(@Valid InstitutionRegisterInfo institutionRegisterInfo,
			InstitutionRegister institutionRegister);

	InstitutionList deleteAndCopyToInstitutionList(InstitutionRegister institutionRegister);

	List<InstitutionRegister> findAll(String status);

	InstitutionRegister update(InstitutionRegister institutionRegister, String reason);

}
