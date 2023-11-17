package com.moneycore.service;

import java.util.List;

import com.moneycore.bean.CurrencyListInfo;
import com.moneycore.entity.CurrencyList;

public interface CurrencyListService {

	CurrencyList saveCurrencyList(CurrencyList currencyList);

	CurrencyList findCurrency(String institutionCode, String currencyCode);

	CurrencyList save(CurrencyList currencyList, CurrencyListInfo currencyListInfo);

	CurrencyList update(CurrencyList currencyList, CurrencyListInfo currencyListInfo);

	List<CurrencyList> fetchCurrencyList(String institutionCode);

	public void delete(String currencyCode);

	CurrencyList findcurrencyCode( String currencyCode);

}