package com.moneycore.serviceimpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moneycore.bean.AccountTypeListInfo;
import com.moneycore.entity.AccountTypeList;
import com.moneycore.repository.AccountTypeListRepository;
import com.moneycore.service.AccountTypeListService;

@Service("accountTypeListService")
public class AccountTypeListServiceImpl implements AccountTypeListService {

	@Autowired
	AccountTypeListRepository accountTypeListRepository;



	public boolean findAccountTypDup(String accountTypeName,String institutionCode ){

		return  accountTypeListRepository.existsByAccountTypeNameAndInstitutionCode(accountTypeName,institutionCode);

	}

	public List<AccountTypeList> findAccountTypDup(String institutionCode,String accountTypeName,String accountTypeCode ){

		return  accountTypeListRepository.findByAccountType(institutionCode,accountTypeName,accountTypeCode);

	}


	@Override
	public AccountTypeList saveAccountTypeList(AccountTypeList accountTypeList) {
		return accountTypeListRepository.save(accountTypeList);
	}

	@Override
	public AccountTypeList findAccountType(String institutionCode, String accountTypeCode) {
		AccountTypeList accountTypeList = null;
		Optional<AccountTypeList> optional = accountTypeListRepository.find(institutionCode, accountTypeCode);
		if (optional.isPresent()) {
			accountTypeList = optional.get();
		}
		return accountTypeList;
	}

	@Override
	public AccountTypeList save(AccountTypeList accountTypeList, AccountTypeListInfo accountTypeListInfo) {
		accountTypeList = new AccountTypeList();
		accountTypeList.setInstitutionCode(accountTypeListInfo.getInstitutionCode());
		accountTypeList.setAccountTypeCode(accountTypeListInfo.getAccountTypeCode());
		accountTypeList.setAccountTypeName(accountTypeListInfo.getAccountTypeName());
		accountTypeList.setAbrvWording(accountTypeListInfo.getAbbreviation());
		accountTypeList.setWording(accountTypeListInfo.getWording());
		accountTypeList.setUserCreate(accountTypeListInfo.getUserCreate());
		accountTypeList.setDateCreate(new Date());
		return accountTypeListRepository.save(accountTypeList);
	}

	@Override
	public AccountTypeList update(AccountTypeList accountTypeList, AccountTypeListInfo accountTypeListInfo) {
		AccountTypeList accountTypeList2 = new AccountTypeList();
		accountTypeList2.setInstitutionCode(accountTypeList.getInstitutionCode());
		accountTypeList2.setAccountTypeCode(accountTypeListInfo.getAccountTypeCode());
		accountTypeList2.setAccountTypeName(accountTypeListInfo.getAccountTypeName());
		accountTypeList2.setAbrvWording(accountTypeListInfo.getAbbreviation());
		accountTypeList2.setWording(accountTypeListInfo.getWording());
		accountTypeList2.setUserCreate(accountTypeList.getUserCreate());
		accountTypeList2.setDateCreate(accountTypeList.getDateCreate());
		accountTypeList2.setUserModif(accountTypeListInfo.getUserModif());
		accountTypeList2.setDateModif(new Date());
		accountTypeListRepository.delete(accountTypeList);
		return accountTypeListRepository.save(accountTypeList2);
	}

	@Override
	public List<AccountTypeList> fetchAccountType(String institutionCode) {
		return accountTypeListRepository.findList(institutionCode);
	}

	@Override
	public AccountTypeList findByAccountTypeCode(String accountTypeCode) {
		return accountTypeListRepository.find(accountTypeCode);
	}
	public void delete(String accountTypeCode) {
		accountTypeListRepository.deleteById(accountTypeCode);
	}

}
