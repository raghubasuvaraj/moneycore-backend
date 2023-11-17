package com.moneycore.service;

import java.util.List;

import com.moneycore.bean.CityListInfo;
import com.moneycore.entity.CityList;

public interface CityListService {

	CityList findCity(String institutionCode, String cityCode);

	CityList findCity(String cityCode);

	CityList save(CityList cityList, CityListInfo cityListInfo);

	CityList update(CityList cityList, CityListInfo cityListInfo);

	List<CityList> fetchCountryList(String institutionCode, String regionCode, String countryCode);

	public void delete(String cityCode);
	public boolean findcitydup(String cityName,String institutionCode );
	public List<CityList> findcitydup( String institutionCode ,String cityName, String cityCode);

}
