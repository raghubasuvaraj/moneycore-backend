package com.moneycore.serviceimpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.moneycore.bean.CityListInfo;
import com.moneycore.entity.CityList;
import com.moneycore.entity.CountryList;
import com.moneycore.entity.RegionList;
import com.moneycore.model.ResponseModel;
import com.moneycore.repository.CityListRepository;
import com.moneycore.service.CityListService;
import com.moneycore.util.CommonUtil;

@Service("cityListService")
public class CityListServiceImpl implements CityListService {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	CityListRepository cityListRepository;

	@Override
	public CityList findCity(String institutionCode, String cityCode) {
		CityList cityList = null;
		Optional<CityList> optional = cityListRepository.find(institutionCode, cityCode);
		if (optional.isPresent()) {
			cityList = optional.get();
		}
		return cityList;
	}

	@Override
	public CityList findCity(String cityCode) {
		CityList cityList = null;
		Optional<CityList> optional = cityListRepository.find( cityCode);
		if (optional.isPresent()) {
			cityList = optional.get();
		}
		return cityList;
	}

	@Override
	public CityList save(CityList cityList, CityListInfo cityListInfo) {
		cityList = new CityList();
		cityList.setInstitutionCode(cityListInfo.getInstitutionCode());
		cityList.setCityCode(cityListInfo.getCityCode());
		cityList.setCityName(cityListInfo.getCityName());
		cityList.setRegionCode(getRegion(cityListInfo.getRegionCode()));
		cityList.setCountryCode(getCountry(cityListInfo.getCountryCode()));
		cityList.setAbrvWording(cityListInfo.getAbbrevation());
		cityList.setWording(cityListInfo.getWording());
		cityList.setUserCreate(cityListInfo.getUserCreate());
		cityList.setDateCreate(new Date());
		return cityListRepository.save(cityList);
	}

	@Override
	public CityList update(CityList cityList, CityListInfo cityListInfo) {
		CityList cityList2 = new CityList();
		cityList2.setInstitutionCode(cityList.getInstitutionCode());
		cityList2.setCityCode(cityListInfo.getCityCode());
		cityList2.setCityName(cityListInfo.getCityName());
		cityList2.setRegionCode(getRegion(cityListInfo.getRegionCode()));
		cityList2.setCountryCode(getCountry(cityListInfo.getCountryCode()));
		cityList2.setAbrvWording(cityListInfo.getAbbrevation());
		cityList2.setWording(cityListInfo.getWording());
		cityList2.setUserCreate(cityList.getUserCreate());
		cityList2.setDateCreate(cityList.getDateCreate());
		cityList2.setUserModif(cityListInfo.getUserModif());
		cityList2.setDateModif(new Date());
		cityListRepository.delete(cityList);
		return cityListRepository.save(cityList2);
	}

	@Override
	public List<CityList> fetchCountryList(String institutionCode, String regionCode, String countryCode) {
		if(regionCode != null && countryCode != null)
			return cityListRepository.findListByCountryCode(institutionCode, regionCode, countryCode);
		else
			return cityListRepository.findList(institutionCode);
	}

	private RegionList getRegion(String regionCode) {
		ResponseModel responseModel = null;
		RegionList regionList = null;
		String url = CommonUtil.getApplicationBaseUrl() + "/api/usermanagement/internal/user/region/" + regionCode;
		ResponseEntity<ResponseModel> responseEntity = CommonUtil.getDataFromOtherService(restTemplate, url, null);
		if (responseEntity.getStatusCode().equals(HttpStatus.ACCEPTED)) {
			responseModel = responseEntity.getBody();
			regionList = CommonUtil.convertToOriginalObject(responseModel.getResult(), RegionList.class);
		}
		return regionList;
	}

	private CountryList getCountry(String countryCode) {
		ResponseModel responseModel = null;
		CountryList countryList = null;
		String url = CommonUtil.getApplicationBaseUrl() + "/api/usermanagement/internal/user/country/" + countryCode;
		ResponseEntity<ResponseModel> responseEntity = CommonUtil.getDataFromOtherService(restTemplate, url, null);
		if (responseEntity.getStatusCode().equals(HttpStatus.ACCEPTED)) {
			responseModel = responseEntity.getBody();
			countryList = CommonUtil.convertToOriginalObject(responseModel.getResult(), CountryList.class);
		}
		return countryList;
	}

	public void delete(String countryCode) {
		cityListRepository.deleteById(countryCode);
	}

	public boolean findcitydup(String cityName,String institutionCode ){

		return  cityListRepository.existsByCityNameAndInstitutionCode(cityName,institutionCode);

	}
	public List<CityList> findcitydup( String institutionCode ,String cityName, String cityCode){

		return  cityListRepository.findcity(institutionCode,cityName,cityCode);

	}

}
