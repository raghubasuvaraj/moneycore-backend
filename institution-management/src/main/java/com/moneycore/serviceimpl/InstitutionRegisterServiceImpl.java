package com.moneycore.serviceimpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moneycore.bean.InstitutionRegisterInfo;
import com.moneycore.entity.InstitutionList;
import com.moneycore.entity.InstitutionRegister;
import com.moneycore.repository.InstitutionRegisterRepository;
import com.moneycore.repository.InstitutionRepository;
import com.moneycore.service.InstitutionRegisterService;

@Service
@Transactional
public class InstitutionRegisterServiceImpl implements InstitutionRegisterService {

	@Autowired
	InstitutionRegisterRepository institutionRegisterRepository;

	@Autowired
	InstitutionRepository institutionRepository;

	@Override
	public InstitutionRegister findByInstitutionCode(String institutionCode) {
		InstitutionRegister institutionList = null;
		Optional<InstitutionRegister> optional = institutionRegisterRepository.findById(institutionCode);
		if (optional.isPresent()) {
			institutionList = optional.get();
		}
		return institutionList;
	}

	@Override
	public InstitutionRegister insertInstitution(@Valid InstitutionRegisterInfo institutionRegisterInfo,
			InstitutionRegister institutionRegister) {
		institutionRegister = new InstitutionRegister();
		institutionRegister.setInstitutionCode(institutionRegisterInfo.getInstitutionCode());
		institutionRegister.setInstitutionName(institutionRegisterInfo.getInstitutionName());
		institutionRegister.setAbrvWording(institutionRegisterInfo.getAbbreviation());
		institutionRegister.setWording(institutionRegisterInfo.getDescription());
		institutionRegister.setBussinessType(institutionRegisterInfo.getBussinessType());
		institutionRegister.setBussinessAddress(institutionRegisterInfo.getBusinessAddress());
		institutionRegister.setNoOfEmployees(institutionRegisterInfo.getNoOfEmployees());
		institutionRegister.setTradeName(institutionRegisterInfo.getTradeName());
		institutionRegister.setTradeAs(institutionRegisterInfo.getTradeAs());
		institutionRegister.setTradeLicenseNumber(institutionRegisterInfo.getTradeLicenseNumber());
		institutionRegister.setWebsite(institutionRegisterInfo.getWebsite());
		institutionRegister.setPhoneNumber(institutionRegisterInfo.getPhoneNumber());
		institutionRegister.setPhoneCountryCode(institutionRegisterInfo.getPhoneCountryCode());
		institutionRegister.setAlternateNumber(institutionRegisterInfo.getAlternatePhoneNumber());
		institutionRegister.setAlternatePhoneCountryCode(institutionRegisterInfo.getAlternatephoneCountryCode());
		institutionRegister.setContactPerson(institutionRegisterInfo.getContactPersonName());
		institutionRegister.setPersonDesignation(institutionRegisterInfo.getDesignation());
		institutionRegister.setEmail(institutionRegisterInfo.getEmailAddress());
		institutionRegister.setIsApproved('P');
		institutionRegister.setDateCreate(new Date());
		institutionRegister.setCountry(institutionRegisterInfo.getCountry());
		return institutionRegisterRepository.save(institutionRegister);
	}

	@Override
	public List<InstitutionRegister> findAll(String status) {
		if (status != null) {
			if (status.equalsIgnoreCase("pending"))
				return institutionRegisterRepository.findAll('P');
			if (status.equalsIgnoreCase("decline"))
				return institutionRegisterRepository.findAll('D');
		}
		return institutionRegisterRepository.findAll();
	}

	@Override
	public InstitutionList deleteAndCopyToInstitutionList(InstitutionRegister institutionRegister) {
		InstitutionList institutionList = new InstitutionList();
		institutionList.setInstitutionCode(institutionRegister.getInstitutionCode());
		institutionList.setInstitutionName(institutionRegister.getInstitutionName());
		institutionList.setAbrvWording(institutionRegister.getAbrvWording());
		institutionList.setWording(institutionRegister.getWording());
		institutionList.setBussinessType(institutionRegister.getBussinessType());
		institutionList.setBussinessAddress(institutionRegister.getBussinessAddress());
		institutionList.setNoOfEmployees(institutionRegister.getNoOfEmployees());
		institutionList.setTradeName(institutionRegister.getTradeName());
		institutionList.setTradeAs(institutionRegister.getTradeAs());
		institutionList.setTradeLicenseNumber(institutionRegister.getTradeLicenseNumber());
		institutionList.setWebsite(institutionRegister.getWebsite());
		institutionList.setPhoneNumber(institutionRegister.getPhoneNumber());
		institutionList.setPhoneCountryCode(institutionRegister.getPhoneCountryCode());
		institutionList.setAlternateNumber(institutionRegister.getAlternateNumber());
		institutionList.setAlternatePhoneCountryCode(institutionRegister.getAlternatePhoneCountryCode());
		institutionList.setContactPerson(institutionRegister.getContactPerson());
		institutionList.setPersonDesignation(institutionRegister.getPersonDesignation());
		institutionList.setEmail(institutionRegister.getEmail());
		institutionList.setUserCreate("NA");
		institutionList.setDateCreate(institutionRegister.getDateCreate());
		institutionList.setDateModif(new Date());
		institutionList.setCountry(institutionRegister.getCountry());
		institutionRegisterRepository.delete(institutionRegister);
		return institutionRepository.save(institutionList);
	}

	@Override
	public InstitutionRegister update(InstitutionRegister institutionRegister, String reason) {
		institutionRegister.setIsApproved('D');
		institutionRegister.setReason(reason);
		institutionRegister.setDateModif(new Date());
		return institutionRegisterRepository.save(institutionRegister);
	}



}
