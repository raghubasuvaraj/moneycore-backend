package com.moneycore.service;

import java.util.List;

import com.moneycore.bean.TransactionCodeListInfo;
import com.moneycore.entity.TransactionCodeList;

public interface TransactionCodeListService {

	TransactionCodeList findTransactionCode(String institutionCode, String transactionCode);

	List<TransactionCodeList> findTransactionCodeWithServiceCode(String institutionCode, Integer serviceCode);

	TransactionCodeList save(TransactionCodeList transactionCodeList, TransactionCodeListInfo transactionCodeListInfo);

	TransactionCodeList update(TransactionCodeList transactionCodeList,
			TransactionCodeListInfo transactionCodeListInfo);

	List<TransactionCodeList> fetchMccList(String institutionCode);

	TransactionCodeList findTransactionCode(String transactionCode);
	public void delete(String transactionCode);
	public boolean findTransDup(String transactionName,String institutionCode );
	public List<TransactionCodeList> findTransDup(String institutionCode,String transactionName,String transactionCode );

}
