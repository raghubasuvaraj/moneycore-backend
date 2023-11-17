package com.moneycore.serviceimpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.moneycore.entity.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.moneycore.bean.CountryListInfo;
import com.moneycore.entity.CountryList;
import com.moneycore.entity.InstitutionList;
import com.moneycore.entity.RegionList;
import com.moneycore.model.ResponseModel;
import com.moneycore.repository.CountryListRespository;
import com.moneycore.service.CountryListService;
import com.moneycore.util.CommonUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Service("countryListService")
public class CountryListServiceImpl implements CountryListService {

	@Autowired
	CountryListRespository countryListRespository;

	@Autowired
	private RestTemplate restTemplate;

	@PersistenceContext
	private EntityManager em;

	public boolean findcoutrydup(String countryName,String institutionCode ){

		return  countryListRespository.existsByCountryNameAndInstitutionCode(countryName,institutionCode);

	}
	public List<CountryList> findcoutrydup(String institutionCode, String countryName,String countryCode){
		return  countryListRespository.findByCountryName(institutionCode,countryName,countryCode);

	}


	@Override
	public CountryList findCountry(String institutionCode, String countryCode) {
		CountryList countryList = null;
		Optional<CountryList> optional = Optional.empty();
		if (institutionCode != null) {
			optional = countryListRespository.find(institutionCode, countryCode);
		} else {
			optional = countryListRespository.find(countryCode);
		}
		if (optional.isPresent()) {
			countryList = optional.get();
		}
		return countryList;
	}

	@Override
	public CountryList findCountryByCountryName(String institutionCode, String countryName) {
		CountryList countryList = null;
		String filterQuery = "";
		if (institutionCode != null) {
			filterQuery = filterQuery + " AND institutionCode = '" + institutionCode + "'";
		}
		if (countryName != null) {
			filterQuery = filterQuery + " AND countryName = '" + countryName + "'";
		}
		Query query = em.createQuery(
				"FROM CountryList where dateCreate IS NOT NULL" + filterQuery + " and isDeleted=false ORDER BY dateCreate DESC");
		countryList = (CountryList) query.getSingleResult();
		return countryList;
	}

	@Override
	public CountryList save(CountryList countryList, CountryListInfo countryListInfo) {
		countryList = new CountryList();
		countryList.setInstitutionCode(countryListInfo.getInstitutionCode());
		countryList.setCountryCode(countryListInfo.getCountryCode());
		countryList.setCountryName(countryListInfo.getCountryName());
		countryList.setRegionCode(getRegion(countryListInfo.getRegionCode()));
		countryList.setAbrvWording(countryListInfo.getAbbrevation());
		countryList.setWording(countryListInfo.getWording());
		countryList.setUserCreate(countryListInfo.getUserCreate());
		countryList.setDateCreate(new Date());
		return countryListRespository.save(countryList);
	}

	@Override
	public CountryList update(CountryList countryList, CountryListInfo countryListInfo) {
		CountryList countryList2 = new CountryList();
		countryList2.setInstitutionCode(countryList.getInstitutionCode());
		countryList2.setCountryCode(countryListInfo.getCountryCode());
		countryList2.setCountryName(countryListInfo.getCountryName());
		countryList2.setAbrvWording(countryListInfo.getAbbrevation());
		countryList2.setRegionCode(getRegion(countryListInfo.getRegionCode()));
		countryList2.setWording(countryListInfo.getWording());
		countryList2.setDateCreate(countryList.getDateCreate());
		countryList2.setUserCreate(countryList.getUserCreate());
		countryList2.setUserModif(countryListInfo.getUserModif());
		countryList2.setDateModif(new Date());
		countryListRespository.delete(countryList);
		return countryListRespository.save(countryList2);
	}

	@Override
	public void countryMap() {
		Query countQuery2 = em.createNativeQuery("delete from grant_permission");
		Query countQuery3 = em.createNativeQuery("delete from screen");
		Query countQuery4 = em.createNativeQuery("delete from menu");
		Query countQuery5 = em.createNativeQuery("delete from space_account_details");
		Query countQuery6 = em.createNativeQuery("delete from space_account_fields");

		int countResult2 = countQuery2.executeUpdate();
		int countResult3 = countQuery3.executeUpdate();
		int countResult4 = countQuery4.executeUpdate();
		int countResult5 = countQuery5.executeUpdate();
		int countResult6 = countQuery6.executeUpdate();
	}

	@Override
	public List<CountryList> fetchCountryList(String institutionCode, String regionCode) {
		if (regionCode != null)
			return countryListRespository.findList(institutionCode, regionCode);
		else
			return countryListRespository.findList(institutionCode);
	}


	public List<CountryList> fetchRegionList( String regionCode) {

			return countryListRespository.findRegionList(regionCode);
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

	public void delete(String countryCode) {
		countryListRespository.deleteById(countryCode);
	}


	public CountryList findCountryCode( String countryCode) {
		CountryList CountryList = null;

		Optional<CountryList> 	optional = countryListRespository.find(countryCode);
		if (optional.isPresent()) {
			CountryList = optional.get();
		}
		return CountryList;
	}
}
