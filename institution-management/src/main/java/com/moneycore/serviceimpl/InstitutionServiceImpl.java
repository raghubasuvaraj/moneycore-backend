package com.moneycore.serviceimpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moneycore.entity.InstitutionList;
import com.moneycore.repository.InstitutionRepository;
import com.moneycore.service.InstitutionService;

@Service("institutionService")
public class InstitutionServiceImpl implements InstitutionService {

	@Autowired
	private InstitutionRepository institutionRepository;

	@PersistenceContext
	private EntityManager em;

	@Override
	public InstitutionList insert(InstitutionList institute) {
		Date date = new Date();
		InstitutionList addInstitute = new InstitutionList();
		addInstitute.setInstitutionCode(institute.getInstitutionCode());
		addInstitute.setInstitutionName(institute.getInstitutionName());
		addInstitute.setWording(institute.getWording());
		addInstitute.setCurrencyCode(institute.getCurrencyCode());
		addInstitute.setAbrvWording(institute.getAbrvWording());
		addInstitute.setTradeLicenseNumber(institute.getTradeLicenseNumber());
		addInstitute.setWebsite(institute.getWebsite());
		addInstitute.setNoOfEmployees(institute.getNoOfEmployees());
		addInstitute.setBussinessAddress(institute.getBussinessAddress());
		addInstitute.setBussinessType(institute.getBussinessType());
		addInstitute.setTradeAs(institute.getTradeAs());
		addInstitute.setTradeName(institute.getTradeName());
		addInstitute.setAlternateNumber(institute.getAlternateNumber());
		addInstitute.setPhoneNumber(institute.getPhoneNumber());
		addInstitute.setPersonDesignation(institute.getPersonDesignation());
		addInstitute.setContactPerson(institute.getContactPerson());
		addInstitute.setUserCreate(institute.getUserCreate());
		addInstitute.setDateCreate(date);
//		addInstitute.setUserModif("1000");
//		addInstitute.setDateModif(date);
		InstitutionList e = institutionRepository.save(addInstitute);
		return e;
	}

	@Override
	public InstitutionList insertInstitution(InstitutionList institute) {
		institute.setUserCreate("NA");
		institute.setDateCreate(new Date());
		return institutionRepository.save(institute);
	}

	@Override
	public List<InstitutionList> findAll() {

		return institutionRepository.findAllInstitut();
	}

	@Override
	public InstitutionList update(InstitutionList institute) {
		InstitutionList response = institutionRepository.save(institute);
		return response;
	}

	public boolean finddup(String institutionName){

		return  institutionRepository.existsByInstitutionName(institutionName);

	}
	@Override
	public InstitutionList findByInstitutionCode(String instituteCode) {
		InstitutionList institutionList = null;
		Optional<InstitutionList> optional = institutionRepository.findById(instituteCode);
		if (optional.isPresent()) {
			institutionList = optional.get();
		}
		return institutionList;
//		return (InstitutionList) em
//				.createNativeQuery("select i.* from institution_list i where institution_code=:instituteCode",InstitutionList.class)
//				.setParameter("instituteCode", instituteCode).getSingleResult();
	}

	@Override
	public InstitutionList findByEmail(String emailAddress) {
		InstitutionList institutionList = null;
		Optional<InstitutionList> optional = institutionRepository.findByEmailId(emailAddress);
		if (optional.isPresent()) {
			institutionList = optional.get();
		}
		return institutionList;
	}
	public void delete(String instituteCode) {
		institutionRepository.deleteById(instituteCode);
	}

	public List<InstitutionList>  finddup(String institutionName,String instituteCode){
		return institutionRepository.FindByName(institutionName,instituteCode);
	};
}