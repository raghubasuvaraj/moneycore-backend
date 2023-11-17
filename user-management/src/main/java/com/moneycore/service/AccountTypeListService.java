package com.moneycore.service;

import java.util.List;

import com.moneycore.bean.AccountTypeListInfo;
import com.moneycore.entity.AccountTypeList;

public interface AccountTypeListService {

	AccountTypeList saveAccountTypeList(AccountTypeList accountTypeList);

	AccountTypeList findAccountType(String institutionCode, String accountTypeCode);

	AccountTypeList save(AccountTypeList accountTypeList, AccountTypeListInfo accountTypeListInfo);

	AccountTypeList update(AccountTypeList accountTypeList, AccountTypeListInfo accountTypeListInfo);

	List<AccountTypeList> fetchAccountType(String institutionCode);

	AccountTypeList findByAccountTypeCode(String accountTypeCode);
	public void delete(String accountTypeCode);
	public boolean findAccountTypDup(String accountTypeName,String institutionCode );
	public List<AccountTypeList> findAccountTypDup(String institutionCode,String accountTypeName,String accountTypeCode );

}
