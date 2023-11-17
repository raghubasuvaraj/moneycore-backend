package com.moneycore.service;

import java.util.List;

import com.moneycore.bean.CountryListInfo;
import com.moneycore.entity.CountryList;

public interface CountryListService {

	CountryList findCountry(String institutionCode, String countryCode);

	CountryList findCountryByCountryName(String institutionCode, String countryName);

	CountryList save(CountryList countryList, CountryListInfo countryListInfo);

	CountryList update(CountryList countryList, CountryListInfo countryListInfo);

	public void countryMap();

	List<CountryList> fetchCountryList(String institutionCode, String regionCode);

	public void delete(String countryCode);

	CountryList findCountryCode( String countryCode);

	List<CountryList> fetchRegionList(String regionCode);

	public boolean findcoutrydup(String countryName,String institutionCode );

	List<CountryList> findcoutrydup(String institutionCode, String countryName,String countryCode);


}
