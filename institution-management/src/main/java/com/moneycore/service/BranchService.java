package com.moneycore.service;

import java.util.List;
import java.util.Optional;

import com.moneycore.entity.Branch;

public interface BranchService {

	public Branch insert(Branch branch);

	public List<Branch> findByBranchCode(String branchCode);

	public Optional<Branch> find(String code);

	public List<Branch> findAll();

	public Branch update(Branch branch);

	List<Branch> findByBranchBYCode(String branchCode);

	public List<Branch> getDefaultBranchByIC(String institutionCode);

	public List<Branch> findByIC(String institutionCode);

	public void delete(String instituteCode);

	public Branch findbranchCode(String code);
	public boolean findbranchdup(String branchName);
	public List<Branch>  findbranchdup(String branchName,String institutionCode,String branchCode);


}
