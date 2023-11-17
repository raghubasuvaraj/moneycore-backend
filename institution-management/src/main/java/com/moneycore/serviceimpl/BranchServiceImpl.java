package com.moneycore.serviceimpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.moneycore.entity.InstitutionList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moneycore.entity.Branch;
import com.moneycore.repository.BranchRepository;
import com.moneycore.service.BranchService;

@Service("branchService")
public class BranchServiceImpl implements BranchService {

	@Autowired
	private BranchRepository branchRepository;

	@PersistenceContext
	private EntityManager em;

	@Override
	public Branch insert(Branch branch) {
		Date date = new Date();
		Branch addBranch = new Branch();
		addBranch.setBranchCode(branch.getBranchCode());
		addBranch.setBranchName(branch.getBranchName());
		addBranch.setInstitutionList(branch.getInstitutionList());
		addBranch.setAbrvWording(branch.getAbrvWording());
		addBranch.setWording(branch.getWording());
		addBranch.setWebsite(branch.getWebsite());
		addBranch.setNoOfEmployees(branch.getNoOfEmployees());
		addBranch.setBussinessAddress(branch.getBussinessAddress());
		addBranch.setContactPerson(branch.getContactPerson());
		addBranch.setpersonDesignation(branch.getpersonDesignation());
		addBranch.setPhoneNumber(branch.getPhoneNumber());
		addBranch.setAlternateNumber(branch.getAlternateNumber());
		addBranch.setTradeLicenseNumber(branch.getTradeLicenseNumber());
		addBranch.setUserCreate(branch.getUserCreate());
		addBranch.setDateCreate(date);
		addBranch.setCountry(branch.getCountry());
//		addBranch.setUserModif("1000");
//		addBranch.setDateModif(date);
		Branch b = branchRepository.save(addBranch);
		return b;
	}

	@Override
	public List<Branch> findByBranchCode(String branchCode) {
		List<Branch> branchList = em.createNativeQuery("select i.* from branch_list i where branch_code=:branchCode and is_deleted=false")
				.setParameter("branchCode", branchCode).getResultList();
		return branchList;
	}

	@Override
	public Optional<Branch> find(String code) {
		return branchRepository.findById(code);
	}

	@Override
	public List<Branch> findAll() {

		return branchRepository.findAllBranch();
	}

	@Override
	public Branch update(Branch branch) {
		Branch response = branchRepository.save(branch);
		return response;
	}

	@Override
	public List<Branch> findByBranchBYCode(String branchCode) {
		List<Branch> branchList = em
				.createNativeQuery("select i.* from branch_list i where branch_code=:branchCode and is_deleted=false", Branch.class)
				.setParameter("branchCode", branchCode).getResultList();
		return branchList;
	}

	@Override
	public List<Branch> getDefaultBranchByIC(String institutionCode) {
		List<Branch> branchList = em
				.createNativeQuery("select i.* from branch_list i where institution_code='" + institutionCode + "' and is_deleted=false",
						Branch.class)
				.getResultList();
		return branchList;
	}

	@Override
	public List<Branch> findByIC(String institutionCode) {
		if(institutionCode == null) {
			return findAll();
		}
		List<Branch> branchList = em
				.createNativeQuery("select * from branch_list where institution_code='" + institutionCode + "' and is_deleted=false", Branch.class)
				.getResultList();
		return branchList;
	}

	public Branch findbranchCode(String branchCode) {
		Branch branch = null;
		Optional<Branch> optional = branchRepository.findById(branchCode);
		if (optional.isPresent()) {
			branch = optional.get();
		}
		return branch;

	}
	public boolean findbranchdup(String branchName){

		return  branchRepository.existsByBranchName(branchName);

	}
	public List<Branch> findbranchdup(String branchName,String institutionCode,String branchCode){

		return  branchRepository.findByName(branchName,institutionCode,branchCode);

	}
	public void delete(String branchCode) {
		branchRepository.deleteById(branchCode);
	}

}
