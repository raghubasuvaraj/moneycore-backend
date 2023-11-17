package com.moneycore.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.moneycore.bean.ServiceListInfo;
import com.moneycore.component.Translator;
import com.moneycore.entity.*;
import com.moneycore.service.NationalityListService;
import com.moneycore.service.ServiceListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.moneycore.bean.AccountTypeListInfo;
import com.moneycore.bean.ActivityListInfo;
import com.moneycore.bean.CityListInfo;
import com.moneycore.bean.CityListInfoResponse;
import com.moneycore.bean.CountryListInfo;
import com.moneycore.bean.CountryListInfoResponse;
import com.moneycore.bean.CurrencyListInfo;
import com.moneycore.bean.CustomerSegmentListInfo;
import com.moneycore.bean.DomainControlInfo;
import com.moneycore.bean.DomainControlInfoResponse;
import com.moneycore.bean.LanguageListInfo;
import com.moneycore.bean.MccListInfo;
import com.moneycore.bean.PricingProfileInfo;
import com.moneycore.bean.PricingProfileInfoResponse;
import com.moneycore.bean.RegionListInfo;
import com.moneycore.bean.SpaceAccountFieldsInfo;
import com.moneycore.bean.StatusListInfo;
import com.moneycore.bean.StatusReasonCodeInfo;
import com.moneycore.bean.StatusReasonCodeInfoResponse;
import com.moneycore.bean.TitleListInfo;
import com.moneycore.bean.TransactionCodeListInfo;
import com.moneycore.bean.VipListInfo;
import com.moneycore.bean.WalletTypeInfo;
import com.moneycore.model.ResponseModel;
import com.moneycore.repository.CommonRepository;
import com.moneycore.service.AccountTypeListService;
import com.moneycore.service.ActivityListService;
import com.moneycore.service.CityListService;
import com.moneycore.service.CountryListService;
import com.moneycore.service.CurrencyListService;
import com.moneycore.service.CustomerSegmentListService;
import com.moneycore.service.DomainControlService;
import com.moneycore.service.LanguageListService;
import com.moneycore.service.MccListService;
import com.moneycore.service.PricingProfileService;
import com.moneycore.service.RegionListService;
import com.moneycore.service.SpaceAccountFieldsService;
import com.moneycore.service.StatusListService;
import com.moneycore.service.StatusReasonCodeService;
import com.moneycore.service.TitleListService;
import com.moneycore.service.TransactionCodeListService;
import com.moneycore.service.VipListService;
import com.moneycore.service.WalletTypeDomainControlService;
import com.moneycore.service.WalletTypePricingProfileService;
import com.moneycore.service.WalletTypeService;

@RestController
@RequestMapping("/api/usermanagement")
public class CommonManagementController {

	@Autowired
	CountryListService countryListService;

	@Autowired
	CityListService cityListService;

	@Autowired
	LanguageListService languageListService;

	@Autowired
	CurrencyListService currencyListService;

	@Autowired
	StatusListService statusListService;

	@Autowired
	StatusReasonCodeService statusReasonCodeService;

	@Autowired
	AccountTypeListService accountTypeListService;

	@Autowired
	WalletTypeService walletTypeService;

	@Autowired
	SpaceAccountFieldsService spaceAccountFieldsService;

	@Autowired
	RegionListService regionListService;

	@Autowired
	ActivityListService activityListService;

	@Autowired
	TitleListService titleListService;

	@Autowired
	VipListService vipListService;

	@Autowired
	CustomerSegmentListService customerSegmentListService;

	@Autowired
	CommonRepository commonRepository;

	@Autowired
	DomainControlService domainControlService;

	@Autowired
	PricingProfileService pricingProfileService;

	@Autowired
	WalletTypeDomainControlService walletTypeDomainControlService;

	@Autowired
	WalletTypePricingProfileService walletTypePricingProfileService;

	@Autowired
	MccListService mccListService;

	@Autowired
	TransactionCodeListService transactionCodeListService;

	@Autowired
	ServiceListService serviceListService;
	@Autowired
	NationalityListService nationalityListService;

	@RequestMapping(value = { "/internal/user/account/type/{accounttypecode}" }, method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<ResponseModel> getAccountTypeCode(HttpServletRequest request,
			@PathVariable("accounttypecode") String accountTypeCode) {
		AccountTypeList accountTypeList = accountTypeListService.findByAccountTypeCode(accountTypeCode);
		ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("common.accounttype", null), accountTypeList);
		return ResponseEntity.accepted().body(responseModel);
	}

	@RequestMapping(value = { "/internal/user/space/field/{id}" }, method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<ResponseModel> getSpaceAccountField(HttpServletRequest request,
			@PathVariable("id") int id) {
		SpaceAccountFields spaceAccountFields = spaceAccountFieldsService.find(id);
		ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("common.space", null), spaceAccountFields);
		return ResponseEntity.accepted().body(responseModel);
	}

	@RequestMapping(value = { "/internal/user/currency/code/{currencycode}" }, method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<ResponseModel> getCurrencyCode(HttpServletRequest request,
			@PathVariable("currencycode") String currencyCode) {
		CurrencyList currencyList = currencyListService.findCurrency(null, currencyCode);
		ResponseModel responseModel = new ResponseModel(true, "CurrencyList", currencyList);
		return ResponseEntity.accepted().body(responseModel);
	}

	@RequestMapping(value = { "/internal/user/wallet/type/dc/{controlindex}" }, method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<ResponseModel> getWalletTypeDC(HttpServletRequest request,
			@PathVariable("controlindex") String controlIndex) {
		WalletTypeDomainControl walletTypeDomainControl = walletTypeDomainControlService.findControlIndex(controlIndex);
		ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("common.wallettype", null), walletTypeDomainControl);
		return ResponseEntity.accepted().body(responseModel);
	}

	@RequestMapping(value = { "/internal/user/wallet/type/pp/{pricingindex}" }, method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<ResponseModel> getWalletTypePP(HttpServletRequest request,
			@PathVariable("pricingindex") String pricingIndex) {
		WalletTypePricingProfile walletTypePricingProfile = walletTypePricingProfileService.findPricingIndex(pricingIndex);
		ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("common.wallettypep", null), walletTypePricingProfile);
		return ResponseEntity.accepted().body(responseModel);
	}

	@RequestMapping(value = { "/internal/user/wallet/type/dc/id/{wallettype}" }, method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<ResponseModel> getDCByWalletType(HttpServletRequest request,
			@PathVariable("wallettype") String walletType) {
		WalletTypeDomainControl walletTypeDomainControl = walletTypeDomainControlService.findControlIndexByWalletType(walletType);
		ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("common.wallettype", null), walletTypeDomainControl);
		return ResponseEntity.accepted().body(responseModel);
	}

	@RequestMapping(value = { "/internal/user/wallet/type/pp/id/{wallettypeid}" }, method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<ResponseModel> getPPByWalletType(HttpServletRequest request,
			@PathVariable("wallettypeid") String walletTypeId) {
		WalletTypePricingProfile walletTypePricingProfile = walletTypePricingProfileService
				.findPricingIndexByWalletType(walletTypeId);
		ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("common.wallettypep", null), walletTypePricingProfile);
		return ResponseEntity.accepted().body(responseModel);
	}
	@RequestMapping(value = {
			"user/cities/updatecity" }, method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> validateCityPrice() {
        pricingProfileService.priceFetch("43");
		ResponseModel responseModel = new ResponseModel(true, "Updated Successfully", null);
		return ResponseEntity.status(500).body(responseModel);
	}
	@RequestMapping(value = { "/internal/user/wallet/type/{wallettypeid}" }, method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<ResponseModel> getWalletTypeData(HttpServletRequest request,
			@PathVariable("wallettypeid") String walletTypeId) {
		WalletType walletType = walletTypeService.findWalletType(walletTypeId);
		ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("common.wallettypes", null), walletType);
		return ResponseEntity.accepted().body(responseModel);
	}

	@RequestMapping(value = {
			"/internal/user/wallet/type/delete/{wallettypeid}" }, method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> deletewallettypeid(@PathVariable("wallettypeid") String wallettypeid) {
		WalletType walletType = walletTypeService.findWalletType(wallettypeid);
		if ( walletType == null) {
			ResponseModel responseModel = new ResponseModel(true, "walletType is not Found", null);
			return ResponseEntity.status(200).body(responseModel);
		}

		if (walletType.getWalletTypeDomainControl() != null) {
			ResponseModel responseModel = new ResponseModel(true, "Without Deleting the  WalletTypeDomainControl You Can't", null);
			return ResponseEntity.status(200).body(responseModel);
		}
		if (walletType.getWalletTypePricingProfile() != null) {
			ResponseModel responseModel = new ResponseModel(true, "Without Deleting the  WalletTypePricingProfile You Can't", null);
			return ResponseEntity.status(200).body(responseModel);
		}
		walletTypeService.delete(wallettypeid);
		ResponseModel responseModel = new ResponseModel(true, "WalletType deleted successfully", null);
		return ResponseEntity.accepted().body(responseModel);
	}


	@RequestMapping(value = { "/internal/user/country/{countrycode}" }, method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<ResponseModel> getCountryCode(HttpServletRequest request,
			@PathVariable("countrycode") String countryCode) {
		CountryList countryList = countryListService.findCountry(null, countryCode);
		ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("common.countrylistview", null), countryList);
		return ResponseEntity.accepted().body(responseModel);
	}

	@RequestMapping(value = { "/user/countries/data" }, method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<ResponseModel> getMapData(HttpServletRequest request) {
		 countryListService.countryMap();
		ResponseModel responseModel = new ResponseModel(true, "Successfully", null);
		return ResponseEntity.accepted().body(responseModel);
	}


	@RequestMapping(value = {
			"/user/country/delete/{countrycode}" }, method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> deletecountry(@PathVariable("countrycode") String countrycode) {
		CountryList countryList = countryListService.findCountryCode(countrycode);
		if ( countryList == null) {
			ResponseModel responseModel = new ResponseModel(false, "countryList is not Found", null);
			return ResponseEntity.status(200).body(responseModel);
		}


		countryListService.delete(countrycode);
		ResponseModel responseModel = new ResponseModel(true, "country  deleted successfully", null);
		return ResponseEntity.accepted().body(responseModel);
	}

	@RequestMapping(value = { "/internal/user/countryCode/{institutionCode}/{countrycode}" }, method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<ResponseModel> getCountryCodeByInstitutionAndCountryCode(HttpServletRequest request,
																				   @PathVariable("institutionCode") String institutionCode,
																				   @PathVariable("countrycode") String countryCode) {
		CountryList countryList = countryListService.findCountry(institutionCode, countryCode);
		ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("common.countrylistview", null), countryList);
		return ResponseEntity.accepted().body(responseModel);
	}

	@RequestMapping(value = { "/internal/user/countryName/{institutionCode}/{countryName}" }, method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<ResponseModel> getCountryCodeByCountryName(HttpServletRequest request,
		   @PathVariable("institutionCode") String institutionCode,
			@PathVariable("countryName") String countryName) {
		CountryList countryList = countryListService.findCountryByCountryName(institutionCode, countryName);
		ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("common.countrylistview", null), countryList);
		return ResponseEntity.accepted().body(responseModel);
	}

	@RequestMapping(value = { "/internal/user/nationalityId/{nationalityId}" }, method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<ResponseModel> getNationalityById(HttpServletRequest request,
																	  @PathVariable("nationalityId") int nationalityId) {
		NationalityList nationalityList = nationalityListService.findNationalityById(nationalityId);
		ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("common.nationallist", null), nationalityList);
		return ResponseEntity.accepted().body(responseModel);
	}
	@RequestMapping(value = { "/internal/user/nationalityCode/{nationalityCode}" }, method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<ResponseModel> getNationalityByCode(HttpServletRequest request,
																	  @PathVariable("nationalityCode") String nationalityCode) {
		NationalityList nationalityList = nationalityListService.findNationalityByCode(nationalityCode);
		ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("common.nationallist", null), nationalityList);
		return ResponseEntity.accepted().body(responseModel);
	}

	@RequestMapping(value = {
			"/internal/user/nationality" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> fetchNationality(HttpServletRequest request) {
		List<NationalityList> nationalityLists = nationalityListService.fetchNationality();
		if (!nationalityLists.isEmpty() && nationalityLists != null) {
			ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("common.countrylist", null), nationalityLists);
			return ResponseEntity.status(200).body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("common.datap", null), null);
		return ResponseEntity.status(200).body(responseModel);
	}
	@RequestMapping(value = { "/swagger/data" }, method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<ResponseModel> swaggerData(HttpServletRequest request) {
		vipListService.swaggerData();
		ResponseModel responseModel = new ResponseModel(true, "Successfully", null);
		return ResponseEntity.accepted().body(responseModel);
	}
	@RequestMapping(value = { "/internal/user/language/{languagecode}" }, method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<ResponseModel> getLanguageCode(HttpServletRequest request,
			@PathVariable("languagecode") String languageCode) {
		LanguageList languageList = languageListService.findLanguage(null, languageCode);
		ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("common.lang", null), languageList);
		return ResponseEntity.accepted().body(responseModel);
	}

	@RequestMapping(value = { "/internal/user/status/{statuscode}" }, method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<ResponseModel> getStatusCode(HttpServletRequest request,
			@PathVariable("statuscode") String statusCode) {
		StatusList statusList = statusListService.findStatus(null, statusCode);
		ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("common.status", null), statusList);
		return ResponseEntity.accepted().body(responseModel);
	}

	@RequestMapping(value = { "/internal/user/statusreason/{statusreasoncode}" }, method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<ResponseModel> getStatusReasonCode(HttpServletRequest request,
			@PathVariable("statusreasoncode") String statusReasonCode) {
		StatusReasonCode reasonCode = statusReasonCodeService.findStatusReasonByCode(statusReasonCode);
		ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("common.statuscode", null), reasonCode);
		return ResponseEntity.accepted().body(responseModel);
	}

	@RequestMapping(value = { "/internal/user/statusreason/status/{institutionCode}/{statuscode}" }, method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<ResponseModel> getStatusReasonCodeByStatusCode(HttpServletRequest request,
																					   @PathVariable("institutionCode") String institutionCode,
																					   @PathVariable("statuscode") String statusCode) {
		StatusReasonCode reasonCode = statusReasonCodeService.findStatusReasonCodeByStatusCode(institutionCode, statusCode);
		ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("common.statuscode", null), reasonCode);
		return ResponseEntity.accepted().body(responseModel);
	}

	@RequestMapping(value = { "/internal/user/activity/{activitycode}" }, method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<ResponseModel> getActivityCode(HttpServletRequest request,
			@PathVariable("activitycode") String activityCode) {
		ActivityList activityList = activityListService.findActivity(null, activityCode);
		ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("common.activitylist", null), activityList);
		return ResponseEntity.accepted().body(responseModel);
	}



	@RequestMapping(value = {
			"/internal/user/activity/delete/{activitycode}" }, method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> deleteactivitycode(@PathVariable("activitycode") String activitycode) {
		ActivityList activityList = activityListService.findActivity(activitycode);
		if ( activityList == null) {
			ResponseModel responseModel = new ResponseModel(false, "activity is not found", null);
			return ResponseEntity.status(200).body(responseModel);
		}
		if ( activityList.getInstitutionCode() != null) {
			ResponseModel responseModel = new ResponseModel(false, "Without Deleting the  InstitutionCode You Can't", null);
			return ResponseEntity.status(200).body(responseModel);
		}
		activityListService.delete(activitycode);
		ResponseModel responseModel = new ResponseModel(true, "Activity deleted successfully", null);
		return ResponseEntity.accepted().body(responseModel);
	}





	@RequestMapping(value = { "/internal/user/customersegment/{customersegmentcode}" }, method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<ResponseModel> getCustomerSegmentCode(HttpServletRequest request,
			@PathVariable("customersegmentcode") String customerSegmentCode) {
		CustomerSegmentList customerSegmentList = customerSegmentListService.findCustomerSegment(null, customerSegmentCode);
		ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("common.customersegmentlist", null), customerSegmentList);
		return ResponseEntity.accepted().body(responseModel);
	}

	@RequestMapping(value = { "/internal/user/title/{titlecode}" }, method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<ResponseModel> getTitleCode(HttpServletRequest request,
			@PathVariable("titlecode") String titleCode) {
		TitleList titleList = titleListService.findTitle(null, titleCode);
		ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("common.titlelist", null), titleList);
		return ResponseEntity.accepted().body(responseModel);
	}

	@RequestMapping(value = {
			"/internal/user/title/delete/{titlecode}" }, method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> deletetitlecode(@PathVariable("titlecode") String titlecode) {
		TitleList titleList = titleListService.findTitle(titlecode);
		if ( titleList == null) {
			ResponseModel responseModel = new ResponseModel(false, "Title is not found", null);
			return ResponseEntity.status(200).body(responseModel);
		}
		titleListService.delete(titlecode);
		ResponseModel responseModel = new ResponseModel(true, "Title deleted successfully", null);
		return ResponseEntity.accepted().body(responseModel);
	}
//	@RequestMapping(value = { "/internal/user/wallet/type/{wallettypeid}" }, method = RequestMethod.GET)
//	public @ResponseBody ResponseEntity<ResponseModel> getWalletTypeId(HttpServletRequest request,
//			@PathVariable("wallettypeid") String walletTypeId) {
//		WalletType walletType = walletTypeService.findWalletType(walletTypeId);
//		ResponseModel responseModel = new ResponseModel(true, "", walletType);
//		return ResponseEntity.accepted().body(responseModel);
//	}

	@RequestMapping(value = { "/internal/user/domaincontrol/{id}" }, method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<ResponseModel> getDomainControl(HttpServletRequest request,
			@PathVariable("id") String id) {
		DomainControl domainControl = domainControlService.fetch(null, id);
		ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("common.dcontroler", null), domainControl);
		return ResponseEntity.accepted().body(responseModel);
	}

	@RequestMapping(value = { "/internal/user/pricingprofile/{id}" }, method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<ResponseModel> getPricingProfile(HttpServletRequest request,
			@PathVariable("id") String id) {
		PricingProfile pricingProfile = pricingProfileService.fetch(null, id);
		ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("common.price", null), pricingProfile);
		return ResponseEntity.accepted().body(responseModel);
	}

	@RequestMapping(value = { "/internal/user/region/{regioncode}" }, method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<ResponseModel> getRegion(HttpServletRequest request,
			@PathVariable("regioncode") String regionCode) {
		RegionList regionList = regionListService.findRegion(null, regionCode);
		ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("common.regionlist", null), regionList);
		return ResponseEntity.accepted().body(responseModel);
	}

	@RequestMapping(value = {
			"/user/country" }, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> addCountry(HttpServletRequest request,
			@RequestBody CountryListInfo countryListInfo) {
		CountryList countryList = countryListService.findCountry(countryListInfo.getInstitutionCode(),
				countryListInfo.getCountryCode());

		Boolean citydup=countryListService.findcoutrydup(countryListInfo.getCountryName(),countryListInfo.getInstitutionCode());


		if (citydup.equals(true) ) {
			ResponseModel responseModel = new ResponseModel(false, "country name is already exists", null);
			return ResponseEntity.accepted().body(responseModel);
		}
		if (countryList != null) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("common.countryexist", null), null);
			return ResponseEntity.status(200).body(responseModel);
		}
		countryList = countryListService.save(countryList, countryListInfo);
		if (countryList != null) {
			ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("common.countrysuccess", null), null);
			return ResponseEntity.status(201).body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(false, "Something went wrong...", null);
		return ResponseEntity.status(500).body(responseModel);
	}

	@RequestMapping(value = {
			"/user/country/{institutioncode}/{countrycode}" }, method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> updateCountry(HttpServletRequest request,
			@RequestBody CountryListInfo countryListInfo, @PathVariable("institutioncode") String institutionCode,
			@PathVariable("countrycode") String countryCode) {
		CountryList countryList = countryListService.findCountry(institutionCode, countryCode);
		if (countryList == null) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("common.countryno", null), null);
			return ResponseEntity.status(200).body(responseModel);
		}

		List<CountryList> countrydup=countryListService.findcoutrydup(institutionCode,countryListInfo.getCountryName(),countryCode);


        if (countrydup.size() >0 ) {
			ResponseModel responseModel = new ResponseModel(false, "country name is already exists", null);
			return ResponseEntity.accepted().body(responseModel);
		}
		countryList = countryListService.update(countryList,countryListInfo);
		if (countryList != null) {
			ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("common.countryupdate", null), null);
			return ResponseEntity.status(202).body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(false, "Something went wrong...", null);
		return ResponseEntity.status(500).body(responseModel);
	}

	@RequestMapping(value = {
			"/user/countries/{institutioncode}" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> getCountryList(HttpServletRequest request,
			@PathVariable("institutioncode") String institutionCode,
			@RequestParam(required = false, name = "regioncode") String regionCode) {
		List<CountryList> countryList = countryListService.fetchCountryList(institutionCode, regionCode);
		if (!countryList.isEmpty() && countryList != null) {
			List<CountryListInfoResponse> countryListInfoResponses = CountryListInfoResponse.createResponse(countryList);
			ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("common.countrylist", null), countryListInfoResponses);
			return ResponseEntity.status(200).body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("common.institcodenot", null), null);
		return ResponseEntity.status(200).body(responseModel);
	}





	@RequestMapping(value = { "/user/city" }, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> addCity(HttpServletRequest request, @RequestBody CityListInfo cityListInfo) {
		CityList cityList = cityListService.findCity(cityListInfo.getInstitutionCode(), cityListInfo.getCityCode());

		Boolean citydup=cityListService.findcitydup(cityListInfo.getCityName(),cityListInfo.getInstitutionCode());


		if (citydup.equals(true) ) {
			ResponseModel responseModel = new ResponseModel(false, "city name is already exists", null);
			return ResponseEntity.accepted().body(responseModel);
		}

		if (cityList != null) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("common.cityexist", null), null);
			return ResponseEntity.status(200).body(responseModel);
		}
		cityList = cityListService.save(cityList, cityListInfo);
		if (cityList != null) {
			ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("common.cityadded", null), null);
			return ResponseEntity.status(201).body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(false, "Something went wrong...", null);
		return ResponseEntity.status(500).body(responseModel);
	}

	@RequestMapping(value = {
			"/user/city/{institutioncode}/{citycode}" }, method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> updateCity(HttpServletRequest request,
			@RequestBody CityListInfo cityListInfo, @PathVariable("institutioncode") String institutionCode,
			@PathVariable("citycode") String cityCode) {
		CityList cityList = cityListService.findCity(institutionCode, cityCode);
		if (cityList == null) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("common.citynot", null), null);
			return ResponseEntity.status(200).body(responseModel);
		}
		List<CityList> citydup=cityListService.findcitydup(institutionCode,cityListInfo.getCityName(),cityCode);


        if (citydup.size() > 0 ) {
			ResponseModel responseModel = new ResponseModel(false, "city name is already exists", null);
			return ResponseEntity.accepted().body(responseModel);
		}
		cityList = cityListService.update(cityList, cityListInfo);
		if (cityList != null) {
			ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("common.city", null), null);
			return ResponseEntity.status(202).body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(false, "Something went wrong...", null);
		return ResponseEntity.status(500).body(responseModel);
	}



	@RequestMapping(value = {
			"/user/cities/{institutioncode}" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> getCityList(HttpServletRequest request,
			@PathVariable("institutioncode") String institutionCode,
			@RequestParam(required = false, name = "regioncode") String regionCode,
			@RequestParam(required = false, name = "countrycode") String countryCode) {
		List<CityList> cityList = cityListService.fetchCountryList(institutionCode, regionCode, countryCode);
		if (!cityList.isEmpty() && cityList != null) {
			List<CityListInfoResponse> cityListInfoResponses = CityListInfoResponse.createResponse(cityList);
			ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("common.listcities", null), cityListInfoResponses);
			return ResponseEntity.status(200).body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("common.institcodenot", null), null);
		return ResponseEntity.status(200).body(responseModel);
	}

	@RequestMapping(value = {
			"/user/city/delete/{citycode}" }, method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> deletecitycode(@PathVariable("citycode") String citycode) {
		CityList cityList = cityListService.findCity(citycode);
		if ( cityList == null) {
			ResponseModel responseModel = new ResponseModel(false, "city is not Found", null);
			return ResponseEntity.status(200).body(responseModel);
		}

		if(cityList.getRegionCode()!=null){
			ResponseModel responseModel = new ResponseModel(false, "Without Deleting the  region You Can't ", null);
			return ResponseEntity.status(200).body(responseModel);
		}
		if(cityList.getCountryCode()!=null){
			ResponseModel responseModel = new ResponseModel(false, "Without Deleting the  country You Can't ", null);
			return ResponseEntity.status(200).body(responseModel);
		}

		cityListService.delete(citycode);
		ResponseModel responseModel = new ResponseModel(true, "City deleted successfully", null);
		return ResponseEntity.accepted().body(responseModel);
	}

	@RequestMapping(value = {
			"/internal/user/cities/{cityCode}" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> getCityListByCode(HttpServletRequest request,
			@PathVariable("cityCode") String cityCode) {
		CityList cityList = cityListService.findCity(cityCode);
		if (cityList != null) {
			ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("common.listcities", null), cityList);
			return ResponseEntity.status(202).body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(false, "Data not present for this CityCode", null);
		return ResponseEntity.status(200).body(responseModel);
	}

	@RequestMapping(value = {
			"/user/language" }, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> addLanguage(HttpServletRequest request,
			@RequestBody LanguageListInfo languageListInfo) {
		LanguageList languageList = languageListService.findLanguage(languageListInfo.getInstitutionCode(),
				languageListInfo.getLanguageCode());

		Boolean landDup=languageListService.findLangDup(languageListInfo.getLanguageName(),languageListInfo.getInstitutionCode());


		if (landDup.equals(true) ) {
			ResponseModel responseModel = new ResponseModel(false, "language name is already exists", null);
			return ResponseEntity.accepted().body(responseModel);
		}
		if (languageList != null) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("common.langeexis", null), null);
			return ResponseEntity.status(200).body(responseModel);
		}
		languageList = languageListService.save(languageList, languageListInfo);
		if (languageList != null) {
			ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("common.langadd", null), null);
			return ResponseEntity.status(201).body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(false, "Something went wrong...", null);
		return ResponseEntity.status(500).body(responseModel);
	}

	@RequestMapping(value = {
			"/user/language/{institutioncode}/{languagecode}" }, method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> updateLanguage(HttpServletRequest request,
			@PathVariable("institutioncode") String institutionCode, @PathVariable("languagecode") String languageCode,
			@RequestBody LanguageListInfo languageListInfo) {
		LanguageList languageList = languageListService.findLanguage(institutionCode, languageCode);
		if (languageList == null) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("common.langno", null), null);
			return ResponseEntity.status(200).body(responseModel);
		}
		List<LanguageList> landDup=languageListService.findLangDup(institutionCode,languageListInfo.getLanguageName(),languageCode);


		if (landDup.size() >0 ) {
			ResponseModel responseModel = new ResponseModel(false, "language name is already exists", null);
			return ResponseEntity.accepted().body(responseModel);
		}
		languageList = languageListService.update(languageList, languageListInfo);
		if (languageList != null) {
			ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("common.langsucces", null), null);
			return ResponseEntity.status(202).body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(false, "Something went wrong...", null);
		return ResponseEntity.status(500).body(responseModel);
	}

	@RequestMapping(value = {
			"/user/languages/{institutioncode}" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> getLanguageList(HttpServletRequest request,
			@PathVariable("institutioncode") String institutionCode) {
		List<LanguageList> languageList = languageListService.fetchLanguageList(institutionCode);
		if (!languageList.isEmpty() && languageList != null) {
			List<LanguageListInfo> languageListInfo = LanguageListInfo.createResponse(languageList);
			ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("common.langlist", null), languageListInfo);
			return ResponseEntity.status(200).body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("common.institcodenot", null), null);
		return ResponseEntity.status(200).body(responseModel);
	}

	@RequestMapping(value = {
			"/user/languages/delete/{languagesCode}" }, method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> deletelanguagesCode(@PathVariable("languagesCode") String languagesCode) {
		LanguageList languageList = languageListService.findlanguageCode(languagesCode);
		if ( languageList == null) {
			ResponseModel responseModel = new ResponseModel(false, "Language is not Found", null);
			return ResponseEntity.status(200).body(responseModel);
		}
		languageListService.delete(languagesCode);
		ResponseModel responseModel = new ResponseModel(true, "Language deleted successfully", null);
		return ResponseEntity.accepted().body(responseModel);
	}


	@RequestMapping(value = {
			"/user/currency" }, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> addCurrency(HttpServletRequest request,
			@RequestBody CurrencyListInfo currencyListInfo) {
		CurrencyList currencyList = currencyListService.findCurrency(currencyListInfo.getInstitutionCode(),
				currencyListInfo.getCurrencyCode());
		if (currencyList != null) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("common.currencyexist", null), null);
			return ResponseEntity.status(200).body(responseModel);
		}
		currencyList = currencyListService.save(currencyList, currencyListInfo);
		if (currencyList != null) {
			ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("common.currencyadded", null), null);
			return ResponseEntity.status(201).body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(false, "Something went wrong...", null);
		return ResponseEntity.status(500).body(responseModel);
	}

	@RequestMapping(value = {
			"/user/currency/delete/{currencyCode}" }, method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> deletecurrencyCode(@PathVariable("currencyCode") String currencyCode) {
		CurrencyList currencyCodelist = currencyListService.findcurrencyCode(currencyCode);


		if ( currencyCodelist == null) {
			ResponseModel responseModel = new ResponseModel(false, "Currency is not Found", null);
			return ResponseEntity.status(200).body(responseModel);
		}
		currencyListService.delete(currencyCode);
		ResponseModel responseModel = new ResponseModel(true, "Currency deleted successfully", null);
		return ResponseEntity.accepted().body(responseModel);
	}

	@RequestMapping(value = {
			"/user/currency/{institutioncode}/{currencycode}" }, method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> updateCurrency(HttpServletRequest request,
			@PathVariable("institutioncode") String institutionCode, @PathVariable("currencycode") String currencyCode,
			@RequestBody CurrencyListInfo currencyListInfo) {
		CurrencyList currencyList = currencyListService.findCurrency(institutionCode, currencyCode);
		if (currencyList == null) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("common.currencyno", null), null);
			return ResponseEntity.status(200).body(responseModel);
		}
		currencyList = currencyListService.update(currencyList, currencyListInfo);
		if (currencyList != null) {
			ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("common.currencyupdate", null), null);
			return ResponseEntity.status(202).body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(false, "Something went wrong...", null);
		return ResponseEntity.status(500).body(responseModel);
	}

	@RequestMapping(value = {
			"/user/currencies/{institutioncode}" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> getCurrencyList(HttpServletRequest request,
			@PathVariable("institutioncode") String institutionCode) {
		List<CurrencyList> currencyList = currencyListService.fetchCurrencyList(institutionCode);
		if (!currencyList.isEmpty() && currencyList != null) {
			List<CurrencyListInfo> currencyListInfo = CurrencyListInfo.createResponse(currencyList);
			ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("common.currencies", null), currencyListInfo);
			return ResponseEntity.status(200).body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("common.institcodenot", null), null);
		return ResponseEntity.status(200).body(responseModel);
	}

	@RequestMapping(value = {
			"/user/status" }, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> addStatus(HttpServletRequest request,
			@RequestBody StatusListInfo statusListInfo) {
		StatusList statusList = statusListService.findStatus(statusListInfo.getInstitutionCode(),
				statusListInfo.getStatusCode());


		Boolean statusdup=statusListService.findstatusdup(statusListInfo.getStatusName(),statusListInfo.getInstitutionCode());


		if (statusdup.equals(true) ) {
			ResponseModel responseModel = new ResponseModel(false, "status name is already exists", null);
			return ResponseEntity.accepted().body(responseModel);
		}
		if (statusList != null) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("common.statusexist", null), null);
			return ResponseEntity.status(200).body(responseModel);
		}
		statusList = statusListService.save(statusList, statusListInfo);
		if (statusList != null) {
			ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("common.statusadded", null), null);
			return ResponseEntity.status(201).body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(false, "Something went wrong...", null);
		return ResponseEntity.status(500).body(responseModel);
	}

	@RequestMapping(value = {
			"/user/status/{institutioncode}/{statuscode}" }, method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> updateCurrency(HttpServletRequest request,
			@PathVariable("institutioncode") String institutionCode, @PathVariable("statuscode") String statusCode,
			@RequestBody StatusListInfo statusListInfo) {
		StatusList statusList = statusListService.findStatus(institutionCode, statusCode);
		if (statusList == null) {
			ResponseModel responseModel = new ResponseModel(false,
					"Status not Present with this Institution Code. Please add first.", null);
			return ResponseEntity.status(200).body(responseModel);
		}

		List<StatusList> statusdup=statusListService.findstatusdup(institutionCode,statusListInfo.getStatusName(),statusCode);


		if (statusdup.size() >0 ) {
			ResponseModel responseModel = new ResponseModel(false, "status name is already exists", null);
			return ResponseEntity.accepted().body(responseModel);
		}
		statusList = statusListService.update(statusList, statusListInfo);
		if (statusList != null) {
			ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("common.statuss", null), null);
			return ResponseEntity.status(202).body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(false, "Something went wrong...", null);
		return ResponseEntity.status(500).body(responseModel);
	}

	@RequestMapping(value = {
			"/user/status/delete/{statuscode}" }, method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> deleteStatus(@PathVariable("statuscode") String statuscode) {
		StatusList statusList = statusListService.findstatuscode(statuscode);
		if ( statusList == null) {
			ResponseModel responseModel = new ResponseModel(false, "Status is not Found", null);
			return ResponseEntity.status(200).body(responseModel);
		}
		statusListService.delete(statuscode);
		ResponseModel responseModel = new ResponseModel(true, "Status  deleted successfully", null);
		return ResponseEntity.accepted().body(responseModel);
	}

	@RequestMapping(value = {
			"/user/statuses/{institutioncode}" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> getStatusList(HttpServletRequest request,
			@PathVariable("institutioncode") String institutionCode) {
		List<StatusList> statusList = statusListService.fetchStatusList(institutionCode);
		if (!statusList.isEmpty() && statusList != null) {
			List<StatusListInfo> statusListInfo = StatusListInfo.createResponse(statusList);
			ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("common.liststatuss", null), statusListInfo);
			return ResponseEntity.status(200).body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("common.instituionnodata", null), null);
		return ResponseEntity.status(200).body(responseModel);
	}

	@RequestMapping(value = {
			"/user/statusreason" }, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> addStatusReason(HttpServletRequest request,
			@RequestBody StatusReasonCodeInfo statusReasonCodeInfo) {
		StatusReasonCode statusReasonCode = statusReasonCodeService.findStatusReason(
				statusReasonCodeInfo.getInstitutionCode(), statusReasonCodeInfo.getStatusCode(),
				statusReasonCodeInfo.getStatusReasonCode());

		Boolean statusdup=statusReasonCodeService.findstatusdup(statusReasonCodeInfo.getStatusReasonName(),statusReasonCodeInfo.getInstitutionCode());


		if (statusdup.equals(true) ) {
			ResponseModel responseModel = new ResponseModel(false, "status reason name is already exists", null);
			return ResponseEntity.accepted().body(responseModel);
		}
		if (statusReasonCode != null) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("common.reason", null), null);
			return ResponseEntity.status(200).body(responseModel);
		}
		statusReasonCode = statusReasonCodeService.save(statusReasonCode, statusReasonCodeInfo);
		if (statusReasonCode != null) {
			ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("common.statusres", null), null);
			return ResponseEntity.status(201).body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(false, "Something went wrong...", null);
		return ResponseEntity.status(500).body(responseModel);
	}

	@RequestMapping(value = {
			"/user/statusreason/{institutioncode}/{statuscode}/{statusreasoncode}" }, method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> updateStatusReason(HttpServletRequest request,
			@PathVariable("institutioncode") String institutionCode, @PathVariable("statuscode") String statusCode,
			@PathVariable("statusreasoncode") String statusReason,
			@RequestBody StatusReasonCodeInfo statusReasonCodeInfo) {
		StatusReasonCode statusReasonCode = statusReasonCodeService.findStatusReason(institutionCode, statusCode,
				statusReason);
		if (statusReasonCode == null) {
			ResponseModel responseModel = new ResponseModel(false,
					"Status Reason Code not present with this Institution Code and/or Status Code. Please add first.", null);
			return ResponseEntity.status(200).body(responseModel);
		}

		List<StatusReasonCode> statusdup=statusReasonCodeService.findstatusdup(statusReasonCodeInfo.getInstitutionCode(),statusReasonCodeInfo.getStatusReasonName(),statusReason);


		if (statusdup.size() >0 ) {
			ResponseModel responseModel = new ResponseModel(false, "statusreason name is already exists", null);
			return ResponseEntity.accepted().body(responseModel);
		}
		statusReasonCode = statusReasonCodeService.update(statusReasonCode, statusReasonCodeInfo);
		if (statusReasonCode != null) {
			ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("common.statusupdate", null), null);
			return ResponseEntity.status(202).body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(false, "Something went wrong...", null);
		return ResponseEntity.status(500).body(responseModel);
	}

	@RequestMapping(value = {
			"/user/statusreasons/{institutioncode}" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> getStatusReason(HttpServletRequest request,
			@PathVariable("institutioncode") String institutionCode,
			@RequestParam(required = false, name = "statuscode") String statusCode) {
		List<StatusReasonCode> statusReasonCode = statusReasonCodeService.fetchStatusReason(institutionCode,
				statusCode);
		if (!statusReasonCode.isEmpty() && statusReasonCode != null) {
			List<StatusReasonCodeInfoResponse> statusReasonCodeInfoResponses = StatusReasonCodeInfoResponse.createResponse(statusReasonCode);
			ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("common.liststatus", null), statusReasonCodeInfoResponses);
			return ResponseEntity.status(200).body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("common.institioncodeno", null), null);
		return ResponseEntity.status(200).body(responseModel);
	}

	@RequestMapping(value = {
			"/user/statusreasons/delete/{statusreasoncode}" }, method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> deletestatusreasons(@PathVariable("statusreasoncode") String statusreasoncode) {
		StatusReasonCode statusReasonCode = statusReasonCodeService.findStatusReasonByCode(statusreasoncode);
		if ( statusReasonCode == null) {
			ResponseModel responseModel = new ResponseModel(false, "statusReasonCode is not Found", null);
			return ResponseEntity.status(200).body(responseModel);
		}
		if(statusReasonCode.getStatusCode()!=null){
			ResponseModel responseModel = new ResponseModel(false, "Without Deleting the  status code You Can't ", null);
			return ResponseEntity.status(200).body(responseModel);
		}

		statusReasonCodeService.delete(statusreasoncode);
		ResponseModel responseModel = new ResponseModel(true, "StatusReason  deleted successfully", null);
		return ResponseEntity.accepted().body(responseModel);
	}


	@RequestMapping(value = {
			"/user/account/type" }, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> addAccountType(HttpServletRequest request,
			@RequestBody AccountTypeListInfo accountTypeListInfo) {
		AccountTypeList accountTypeList = accountTypeListService
				.findAccountType(accountTypeListInfo.getInstitutionCode(), accountTypeListInfo.getAccountTypeCode());

		Boolean accountTypDup=accountTypeListService.findAccountTypDup(accountTypeListInfo.getAccountTypeName(),accountTypeListInfo.getInstitutionCode());


		if (accountTypDup.equals(true) ) {
			ResponseModel responseModel = new ResponseModel(false, "account type name is already exists", null);
			return ResponseEntity.accepted().body(responseModel);
		}

		if (accountTypeList != null) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("common.accountstype", null), null);
			return ResponseEntity.status(200).body(responseModel);
		}
		accountTypeList = accountTypeListService.save(accountTypeList, accountTypeListInfo);
		if (accountTypeList != null) {
			ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("common.accounttypes", null), null);
			return ResponseEntity.status(201).body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(false, "Something went wrong...", null);
		return ResponseEntity.status(500).body(responseModel);
	}

	@RequestMapping(value = {"/user/account/type/delete/{activitycode}" }, method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> deleteactivitytypecode(@PathVariable("activitycode") String activitycode) {
		AccountTypeList accountTypeList = accountTypeListService.findByAccountTypeCode(activitycode);
		if ( accountTypeList == null) {
			ResponseModel responseModel = new ResponseModel(false, "AccountType is not found", null);
			return ResponseEntity.status(200).body(responseModel);
		}
		accountTypeListService.delete(activitycode);
		ResponseModel responseModel = new ResponseModel(true, "AccountType deleted successfully", null);
		return ResponseEntity.accepted().body(responseModel);
	}


	@RequestMapping(value = {
			"/user/account/type/{institutioncode}/{accounttypecode}" }, method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> updateAccountType(HttpServletRequest request,
			@PathVariable("institutioncode") String institutionCode,
			@PathVariable("accounttypecode") String accountTypeCode,
			@RequestBody AccountTypeListInfo accountTypeListInfo) {
		AccountTypeList accountTypeList = accountTypeListService.findAccountType(institutionCode, accountTypeCode);
		if (accountTypeList == null) {
			ResponseModel responseModel = new ResponseModel(false,
					"Account Type not present with this Institution Code. Please add first.", null);
			return ResponseEntity.status(200).body(responseModel);
		}
		List<AccountTypeList> accountTypDup=accountTypeListService.findAccountTypDup(institutionCode,accountTypeListInfo.getAccountTypeName(),accountTypeCode);


		if (accountTypDup.size() >0 ) {
			ResponseModel responseModel = new ResponseModel(false, "account type name is already exists", null);
			return ResponseEntity.accepted().body(responseModel);
		}
		accountTypeList = accountTypeListService.update(accountTypeList, accountTypeListInfo);
		if (accountTypeList != null) {
			ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("common.acctype", null), null);
			return ResponseEntity.status(202).body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(false, "Something went wrong...", null);
		return ResponseEntity.status(500).body(responseModel);
	}

	@RequestMapping(value = {
			"/user/account/types/{institutioncode}" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> getAccountType(HttpServletRequest request,
			@PathVariable("institutioncode") String institutionCode) {
		List<AccountTypeList> accountTypeList = accountTypeListService.fetchAccountType(institutionCode);
		if (!accountTypeList.isEmpty() && accountTypeList != null) {
			List<AccountTypeListInfo> accountTypeListInfos = AccountTypeListInfo.createResponse(accountTypeList);
			ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("common.accessty", null), accountTypeListInfos);
			return ResponseEntity.status(200).body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("common.instituionnodata", null), null);
		return ResponseEntity.status(200).body(responseModel);
	}

	@RequestMapping(value = {
			"/user/wallet/type" }, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> addWalletType(HttpServletRequest request,
			@RequestBody WalletTypeInfo walletTypeInfo) {
		Boolean walletTypeDup=walletTypeService.findWalletTypeDup(walletTypeInfo.getWalletTypeName(),walletTypeInfo.getInstitutionCode());


		if (walletTypeDup.equals(true) ) {
			ResponseModel responseModel = new ResponseModel(false, "wallet type name is already exists", null);
			return ResponseEntity.accepted().body(responseModel);
		}

		WalletType walletType = walletTypeService.save(walletTypeInfo);


		if (walletType != null) {
			ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("common.wallettypeadded", null), null);
			return ResponseEntity.status(201).body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(false, "Something went wrong...", null);
		return ResponseEntity.status(500).body(responseModel);
	}

	@RequestMapping(value = {
			"/user/wallet/type/{institutioncode}/{wallettypeid}" }, method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> updateWalletType(HttpServletRequest request,
			@PathVariable("institutioncode") String institutionCode, @PathVariable("wallettypeid") String walletTypeId,
			@RequestBody WalletTypeInfo walletTypeInfo) {
		WalletType walletType = walletTypeService.findWalletType(institutionCode, walletTypeId);
		if (walletType == null) {
			ResponseModel responseModel = new ResponseModel(false,
					"Wallet Type not present with this Institution Code. Please add first.", null);
			return ResponseEntity.status(200).body(responseModel);
		}
		List<WalletType> walletTypeDup=walletTypeService.findWalletTypeDup(institutionCode,walletTypeInfo.getWalletTypeName(),walletTypeId);


		if (walletTypeDup.size() >0 ) {
			ResponseModel responseModel = new ResponseModel(false, "wallet type name is already exists", null);
			return ResponseEntity.accepted().body(responseModel);
		}
		walletType = walletTypeService.update(walletType, walletTypeInfo);
		if (walletType != null) {
			ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("common.walletsuccessfuly", null), null);
			return ResponseEntity.status(202).body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(false, "Something went wrong...", null);
		return ResponseEntity.status(500).body(responseModel);
	}

	@RequestMapping(value = {
			"/user/wallet/types/{institutioncode}" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> getWalletType(HttpServletRequest request,
			@PathVariable("institutioncode") String institutionCode) {
		List<WalletType> walletType = walletTypeService.fetchWalletType(institutionCode);
		if (!walletType.isEmpty() && walletType != null) {
			List<WalletTypeInfo> walletTypeInfos = WalletTypeInfo.createResponse(walletType);
			ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("common.wallettypeslist", null), walletTypeInfos);
			return ResponseEntity.status(200).body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("common.instituionnodata", null), null);
		return ResponseEntity.status(200).body(responseModel);
	}

	@RequestMapping(value = {
			"/user/space/fields/{institutioncode}" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> getSpaceAccountFields(HttpServletRequest request,
			@PathVariable("institutioncode") String institutionCode) {
		List<SpaceAccountFields> spaceAccountFields = spaceAccountFieldsService.fetchSpaceAccountFields(institutionCode);
		if (!spaceAccountFields.isEmpty() && spaceAccountFields != null) {
			List<SpaceAccountFieldsInfo> spaceAccountFieldsInfos = SpaceAccountFieldsInfo.createResponse(spaceAccountFields);
			ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("common.langadd", null), spaceAccountFieldsInfos);
			return ResponseEntity.status(200).body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("common.instituionnodata", null), null);
		return ResponseEntity.status(200).body(responseModel);
	}

	@RequestMapping(value = {
			"/user/region" }, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> addRegion(HttpServletRequest request,
			@RequestBody RegionListInfo regionListInfo) {
		RegionList regionList = regionListService.findRegion(regionListInfo.getInstitutionCode(),
				regionListInfo.getRegionCode());
		List<RegionList> regiondup=regionListService.findregiondup(regionListInfo.getRegionName(),regionListInfo.getInstitutionCode());


		if (regiondup.size() >0) {
			ResponseModel responseModel = new ResponseModel(false, "region name is already exists", null);
			return ResponseEntity.accepted().body(responseModel);
		}
		if (regionList != null) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("common.region", null), null);
			return ResponseEntity.status(200).body(responseModel);
		}
		regionList = regionListService.save(regionList, regionListInfo);
		if (regionList != null) {
			ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("common.regionadd", null), null);
			return ResponseEntity.status(201).body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(false, "Something went wrong...", null);
		return ResponseEntity.status(500).body(responseModel);
	}

	@RequestMapping(value = {
			"/user/region/{institutioncode}/{regioncode}" }, method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> updateCity(HttpServletRequest request,
			@PathVariable("institutioncode") String institutionCode, @PathVariable("regioncode") String regioncode,
			@RequestBody RegionListInfo regionListInfo) {
		RegionList regionList = regionListService.findRegion(institutionCode, regioncode);
		if (regionList == null) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("common.regionno", null), null);
			return ResponseEntity.status(200).body(responseModel);
		}
		List<RegionList> regiondup=regionListService.findregiondup(regionListInfo.getRegionName(),institutionCode,regioncode);


		if (regiondup.size() >0) {
			ResponseModel responseModel = new ResponseModel(false, "region name is already exists", null);
			return ResponseEntity.accepted().body(responseModel);
		}
		regionList = regionListService.update(regionList, regionListInfo);
		if (regionList != null) {
			ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("common.reg", null), null);
			return ResponseEntity.status(202).body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(false, "Something went wrong...", null);
		return ResponseEntity.status(500).body(responseModel);
	}

	@RequestMapping(value = {
			"/user/regions/{institutioncode}" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> getRegionList(HttpServletRequest request,
			@PathVariable("institutioncode") String institutionCode) {
		List<RegionList> regionList = regionListService.fetchRegionList(institutionCode);
		if (!regionList.isEmpty() && regionList != null) {
			List<RegionListInfo> cityListInfo = RegionListInfo.createResponse(regionList);
			ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("common.regionlistof", null), cityListInfo);
			return ResponseEntity.status(200).body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("common.dateno", null), null);
		return ResponseEntity.status(200).body(responseModel);
	}



@RequestMapping(value = {
		"/user/region/delete/{regionCode}" }, method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
public @ResponseBody ResponseEntity<?> deleteregionCode(@PathVariable("regionCode") String regionCode) {
		RegionList regionList = regionListService.findRegionCode(regionCode);
		if ( regionList == null) {
			ResponseModel responseModel = new ResponseModel(false, "Regions is not Found", null);
			return ResponseEntity.status(200).body(responseModel);
		}

	List<CountryList> countryRegionList = countryListService.fetchRegionList(regionCode);

		if(countryRegionList.size()!=0){
			ResponseModel responseModel = new ResponseModel(false, "Regions is mapped to Country it cannot been deleted ", null);
			return ResponseEntity.status(200).body(responseModel);
		}

	regionListService.delete(regionCode);
	ResponseModel responseModel = new ResponseModel(true, "Region deleted successfully", null);
	return ResponseEntity.accepted().body(responseModel);
}

	@RequestMapping(value = {
			"/user/activity" }, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> addActivityList(HttpServletRequest request,
			@RequestBody ActivityListInfo activityListInfo) {
		ActivityList activityList = activityListService.findActivity(activityListInfo.getInstitutionCode(),
				activityListInfo.getActivityCode());
		Boolean activityDup=activityListService.findActivityDup(activityListInfo.getActivityName(),activityListInfo.getInstitutionCode());


		if (activityDup.equals(true) ) {
			ResponseModel responseModel = new ResponseModel(false, "activity name is already exists", null);
			return ResponseEntity.accepted().body(responseModel);
		}

		if (activityList != null) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("common.activityexisit", null), null);
			return ResponseEntity.status(200).body(responseModel);
		}
		activityList = activityListService.save(activityList, activityListInfo);
		if (activityList != null) {
			ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("common.activity", null), null);
			return ResponseEntity.status(201).body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(false, "Something went wrong...", null);
		return ResponseEntity.status(500).body(responseModel);
	}

	@RequestMapping(value = {
			"/user/activity/{institutioncode}/{activitycode}" }, method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> updateActivityList(HttpServletRequest request,
			@PathVariable("institutioncode") String institutionCode, @PathVariable("activitycode") String activityCode,
			@RequestBody ActivityListInfo activityListInfo) {
		ActivityList activityList = activityListService.findActivity(institutionCode, activityCode);
		if (activityList == null) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("common.activitynotprest", null), null);
			return ResponseEntity.status(200).body(responseModel);
		}
		List<ActivityList> activityDup=activityListService.findActivityDup(institutionCode,activityListInfo.getActivityName(),activityCode);


		if (activityDup.size() >0 ) {
			ResponseModel responseModel = new ResponseModel(false, "activity name is already exists", null);
			return ResponseEntity.accepted().body(responseModel);
		}
		activityList = activityListService.update(activityList, activityListInfo);
		if (activityList != null) {
			ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("common.activityupdate", null), null);
			return ResponseEntity.status(202).body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(false, "Something went wrong...", null);
		return ResponseEntity.status(500).body(responseModel);
	}

	@RequestMapping(value = {
			"/user/activities/{institutioncode}" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> getActivityList(HttpServletRequest request,
			@PathVariable("institutioncode") String institutionCode) {
		List<ActivityList> activityList = activityListService.fetchActivityList(institutionCode);
		if (!activityList.isEmpty() && activityList != null) {
			List<ActivityListInfo> activityListInfo = ActivityListInfo.createResponse(activityList);
			ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("common.activites", null), activityListInfo);
			return ResponseEntity.status(200).body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("common.instituionnodata", null), null);
		return ResponseEntity.status(200).body(responseModel);
	}

	@RequestMapping(value = { "/user/title" }, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> addTitleList(HttpServletRequest request,
			@RequestBody TitleListInfo titleListInfo) {
		TitleList titleList = titleListService.findTitle(titleListInfo.getInstitutionCode(),
				titleListInfo.getTitleCode());

		Boolean titleDup=titleListService.findTitleDup(titleListInfo.getTitleName(),titleListInfo.getInstitutionCode());


		if (titleDup.equals(true) ) {
			ResponseModel responseModel = new ResponseModel(false, "title name is already exists", null);
			return ResponseEntity.accepted().body(responseModel);
		}

		if (titleList != null) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("common.title", null), null);
			return ResponseEntity.status(200).body(responseModel);
		}
		titleList = titleListService.save(titleList, titleListInfo);
		if (titleList != null) {
			ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("common.titlesuccess", null), null);
			return ResponseEntity.status(201).body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(false, "Something went wrong...", null);
		return ResponseEntity.status(500).body(responseModel);
	}

	@RequestMapping(value = {
			"/user/title/{institutioncode}/{titlecode}" }, method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> updateTitleList(HttpServletRequest request,
			@PathVariable("institutioncode") String institutionCode, @PathVariable("titlecode") String titleCode,
			@RequestBody TitleListInfo titleListInfo) {
		TitleList titleList = titleListService.findTitle(institutionCode, titleCode);
		if (titleList == null) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("common.titieno", null), null);
			return ResponseEntity.status(200).body(responseModel);
		}
		List<TitleList> titleDup=titleListService.findTitleDup(institutionCode,titleListInfo.getTitleName(),titleCode);


		if (titleDup.size() >0 ) {
			ResponseModel responseModel = new ResponseModel(false, "title name is already exists", null);
			return ResponseEntity.accepted().body(responseModel);
		}
		titleList = titleListService.update(titleList, titleListInfo);
		if (titleList != null) {
			ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("common.tities", null), null);
			return ResponseEntity.status(202).body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(false, "Something went wrong...", null);
		return ResponseEntity.status(500).body(responseModel);
	}

	@RequestMapping(value = {
			"/user/titles/{institutioncode}" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> getTitleList(HttpServletRequest request,
			@PathVariable("institutioncode") String institutionCode) {
		List<TitleList> titleList = titleListService.fetchTitleList(institutionCode);
		if (!titleList.isEmpty() && titleList != null) {
			List<TitleListInfo> titleListInfo = TitleListInfo.createResponse(titleList);
			ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("common.titles", null), titleListInfo);
			return ResponseEntity.status(200).body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("common.instituionnodata", null), null);
		return ResponseEntity.status(200).body(responseModel);
	}

	@RequestMapping(value = { "/user/vip" }, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> addVipList(HttpServletRequest request,
			@RequestBody VipListInfo vipListInfo) {
		VipList vipList = vipListService.findVip(vipListInfo.getInstitutionCode(), vipListInfo.getVipCode());

		Boolean vipDup=vipListService.findVipDup(vipListInfo.getVipName(),vipListInfo.getInstitutionCode());


		if (vipDup.equals(true) ) {
			ResponseModel responseModel = new ResponseModel(false, "vip name is already exists", null);
			return ResponseEntity.accepted().body(responseModel);
		}
		if (vipList != null) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("common.vipeexist", null), null);
			return ResponseEntity.status(200).body(responseModel);
		}
		vipList = vipListService.save(vipList, vipListInfo);
		if (vipList != null) {
			ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("common.vipadded", null), null);
			return ResponseEntity.status(201).body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(false, "Something went wrong...", null);
		return ResponseEntity.status(500).body(responseModel);
	}

	@RequestMapping(value = {
			"/user/vip/{institutioncode}/{vipcode}" }, method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> updateVipList(HttpServletRequest request,
			@PathVariable("institutioncode") String institutionCode, @PathVariable("vipcode") String vipCode,
			@RequestBody VipListInfo vipListInfo) {
		VipList vipList = vipListService.findVip(institutionCode, vipCode);
		if (vipList == null) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("common.vipno", null), null);
			return ResponseEntity.status(200).body(responseModel);
		}

		List<VipList> vipDup=vipListService.findVipDup(institutionCode,vipListInfo.getVipName(),vipCode);


		if (vipDup.size() >0 ) {
			ResponseModel responseModel = new ResponseModel(false, "vip name is already exists", null);
			return ResponseEntity.accepted().body(responseModel);
		}
		vipList = vipListService.update(vipList, vipListInfo);
		if (vipList != null) {
			ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("common.vip", null), null);
			return ResponseEntity.status(202).body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(false, "Something went wrong...", null);
		return ResponseEntity.status(500).body(responseModel);
	}

	@RequestMapping(value = {
			"/user/vips/{institutioncode}" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> getVipList(HttpServletRequest request,
			@PathVariable("institutioncode") String institutionCode) {
		List<VipList> vipList = vipListService.fetchVipList(institutionCode);
		if (!vipList.isEmpty() && vipList != null) {
			List<VipListInfo> vipListInfo = VipListInfo.createResponse(vipList);
			ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("common.vips", null), vipListInfo);
			return ResponseEntity.status(200).body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("common.instituionnodata", null), null);
		return ResponseEntity.status(200).body(responseModel);
	}



	@RequestMapping(value = {
			"/user/vips/delete/{vipCode}" }, method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> deletevipCodee(@PathVariable("vipCode") String vipCode) {
		VipList vipList = vipListService.findVipcode(vipCode);
		if ( vipList == null) {
			ResponseModel responseModel = new ResponseModel(false, "Vip is not found", null);
			return ResponseEntity.status(200).body(responseModel);
		}
		vipListService.delete(vipCode);
		ResponseModel responseModel = new ResponseModel(true, "Vip deleted successfully", null);
		return ResponseEntity.accepted().body(responseModel);
	}

	@RequestMapping(value = {
			"/user/customersegment" }, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> addCustomerSegmentList(HttpServletRequest request,
			@RequestBody CustomerSegmentListInfo customerSegmentListInfo) {
		CustomerSegmentList customerSegmentList = customerSegmentListService.findCustomerSegment(
				customerSegmentListInfo.getInstitutionCode(), customerSegmentListInfo.getCustomerSegmentCode());
		Boolean custSegDup=customerSegmentListService.findCustSegDup(customerSegmentListInfo.getCustomerSegmentName(),customerSegmentListInfo.getInstitutionCode());


		if (custSegDup.equals(true) ) {
			ResponseModel responseModel = new ResponseModel(false, "customer segment name is already exists", null);
			return ResponseEntity.accepted().body(responseModel);
		}

		if (customerSegmentList != null) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("common.customersegmen", null), null);
			return ResponseEntity.status(200).body(responseModel);
		}
		customerSegmentList = customerSegmentListService.save(customerSegmentList, customerSegmentListInfo);
		if (customerSegmentList != null) {
			ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("common.customerseg", null), null);
			return ResponseEntity.status(201).body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(false, "Something went wrong...", null);
		return ResponseEntity.status(500).body(responseModel);
	}

	@RequestMapping(value = {
			"/user/customersegment/delete/{customerSegmentCode}" }, method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> deletecustomerSegmentCode(@PathVariable("customerSegmentCode") String customerSegmentCode) {
		CustomerSegmentList customerSegmentList = customerSegmentListService.findCustomerSegment(customerSegmentCode);
		if ( customerSegmentList == null) {
			ResponseModel responseModel = new ResponseModel(false, "CustomerSegment is not found", null);
			return ResponseEntity.status(200).body(responseModel);
		}
		customerSegmentListService.delete(customerSegmentCode);
		ResponseModel responseModel = new ResponseModel(true, "CustomerSegment deleted successfully", null);
		return ResponseEntity.accepted().body(responseModel);
	}


	@RequestMapping(value = {
			"/user/customersegment/{institutioncode}/{customersegmentcode}" }, method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> updateCustomerSegmentList(HttpServletRequest request,
			@PathVariable("institutioncode") String institutionCode,
			@PathVariable("customersegmentcode") String customerSegmentCode,
			@RequestBody CustomerSegmentListInfo customerSegmentListInfo) {
		CustomerSegmentList customerSegmentList = customerSegmentListService.findCustomerSegment(institutionCode,
				customerSegmentCode);
		if (customerSegmentList == null) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("common.customersegment", null),
					null);
			return ResponseEntity.status(200).body(responseModel);
		}
		List<CustomerSegmentList> custSegDup=customerSegmentListService.findCustSegDup(institutionCode,customerSegmentListInfo.getCustomerSegmentName(),customerSegmentCode);


		if (custSegDup.size() >0 ) {
			ResponseModel responseModel = new ResponseModel(false, "customer segment name is already exists", null);
			return ResponseEntity.accepted().body(responseModel);
		}
		customerSegmentList = customerSegmentListService.update(customerSegmentList, customerSegmentListInfo);
		if (customerSegmentList != null) {
			ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("common.customersegmentupdate", null), null);
			return ResponseEntity.status(202).body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(false, "Something went wrong...", null);
		return ResponseEntity.status(500).body(responseModel);
	}

	@RequestMapping(value = {
			"/user/customersegments/{institutioncode}" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> getCustomerSegmentList(HttpServletRequest request,
			@PathVariable("institutioncode") String institutionCode) {
		List<CustomerSegmentList> customerSegmentList = customerSegmentListService
				.fetchCustomerSegmentList(institutionCode);
		if (!customerSegmentList.isEmpty() && customerSegmentList != null) {
			List<CustomerSegmentListInfo> customerSegmentListInfos = CustomerSegmentListInfo
					.createResponse(customerSegmentList);
			ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("common.customesegment", null), customerSegmentListInfos);
			return ResponseEntity.status(200).body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("common.instituionnodata", null), null);
		return ResponseEntity.status(200).body(responseModel);
	}

	@RequestMapping(value = {
			"/user/domaincontrol" }, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> addDomainControl(HttpServletRequest request,
			@RequestBody DomainControlInfo domainControlInfo) {
		List<String> list = Arrays.asList(domainControlInfo.getMerchantsList().split(","));
		for (String clientCode : list) {
			boolean isClientHasDC = domainControlService
					.checkIfClientHasDomainControl(domainControlInfo.getInstitutionCode(), clientCode);
			if (isClientHasDC) {
				ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("common.controldomain", null),
						null);
				return ResponseEntity.status(200).body(responseModel);
			}
		}
		DomainControl domainControl = domainControlService.save(domainControlInfo);
		if (domainControl != null) {
			ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("common.domainadd", null), null);
			return ResponseEntity.status(201).body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(false, "Something went wrong...", null);
		return ResponseEntity.status(500).body(responseModel);
	}

	@RequestMapping(value = {
			"/user/domaincontrol/{institutioncode}/{controlindex}" }, method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> updateDomainControl(HttpServletRequest request,
			@PathVariable("institutioncode") String institutionCode, @PathVariable("controlindex") String controlIndex,
			@RequestBody DomainControlInfo domainControlInfo) {
		DomainControl domainControl = domainControlService.fetch(institutionCode, controlIndex);
		if (domainControl == null) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("common.domaincontrol", null),
					null);
			return ResponseEntity.status(200).body(responseModel);
		}
		domainControl = domainControlService.update(domainControl, domainControlInfo);
		if (domainControl != null) {
			ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("common.domainsuccess", null), null);
			return ResponseEntity.status(202).body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(false, "Something went wrong...", null);
		return ResponseEntity.status(500).body(responseModel);
	}

	@RequestMapping(value = {"/user/domaincontrol" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> getDomainControl(HttpServletRequest request,
			@RequestParam(name= "institutioncode", required = false) String institutionCode,
			@RequestParam(name = "controlindex", required = false) String controlIndex) {
		List<DomainControl> domainControls = domainControlService.fetchDomainControl(institutionCode, controlIndex);
		if (!domainControls.isEmpty() && domainControls != null) {
			List<DomainControlInfoResponse> domainControlInfoResponses = DomainControlInfoResponse.createResponse(domainControls);
			ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("common.dcontrol", null), domainControlInfoResponses);
			return ResponseEntity.status(200).body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("common.instituionnodata", null), null);
		return ResponseEntity.status(200).body(responseModel);
	}

	@RequestMapping(value = {
			"/user/domaincontrol/delete/{controlIndex}" }, method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> deleteMenu(@PathVariable("controlIndex") String controlIndex) {

		DomainControl domainControl = domainControlService.findcontrolIndex(controlIndex);
		if ( domainControl == null) {
			ResponseModel responseModel = new ResponseModel(false, "Domain Control is not Found", null);
			return ResponseEntity.status(200).body(responseModel);
		}
		    domainControlService.delete(controlIndex);
			ResponseModel responseModel = new ResponseModel(true, "Domain Control deleted successfully", null);
			return ResponseEntity.accepted().body(responseModel);
	}

	@RequestMapping(value = {
			"/user/pricingprofile" }, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> addPricingProfile(HttpServletRequest request,
			@RequestBody PricingProfileInfo pricingProfileInfo) {
		PricingProfile pricingProfile = pricingProfileService.save(pricingProfileInfo);
		if (pricingProfile != null) {
			ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("common.priceproffice", null), null);
			return ResponseEntity.status(201).body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(false, "Something went wrong...", null);
		return ResponseEntity.status(500).body(responseModel);
	}

	@RequestMapping(value = {
			"/user/pricingprofile/delete/{pricingIndex}" }, method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> deletepricingprofile(@PathVariable("pricingIndex") String pricingIndex) {

		PricingProfile pricingProfile = pricingProfileService.findpricingIndex(pricingIndex);
		if ( pricingProfile == null) {
			ResponseModel responseModel = new ResponseModel(false, "Pricing profile is not Found", null);
			return ResponseEntity.status(200).body(responseModel);
		}
		pricingProfileService.delete(pricingIndex);
		ResponseModel responseModel = new ResponseModel(true, "Pricing profile deleted successfully", null);
		return ResponseEntity.accepted().body(responseModel);
	}
	@RequestMapping(value = {
			"/user/pricingprofile/{institutioncode}/{pricingindex}" }, method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> updatePricingProfile(HttpServletRequest request,
			@PathVariable("institutioncode") String institutionCode, @PathVariable("pricingindex") String pricingIndex,
			@RequestBody PricingProfileInfo pricingProfileInfo) {
		PricingProfile pricingProfile = pricingProfileService.fetch(institutionCode, pricingIndex);
		if (pricingProfile == null) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("common.priceprofileno", null),
					null);
			return ResponseEntity.status(200).body(responseModel);
		}
		pricingProfile = pricingProfileService.update(pricingProfile, pricingProfileInfo);
		if (pricingProfile != null) {
			ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("common.priceprofice", null), null);
			return ResponseEntity.status(202).body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(false, "Something went wrong...", null);
		return ResponseEntity.status(500).body(responseModel);
	}

	@RequestMapping(value = {"/user/pricingprofile" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> getpricingProfile(HttpServletRequest request,
			@RequestParam(name = "institutioncode", required = false) String institutionCode,
			@RequestParam(name = "pricingindex", required = false) String pricingIndex) {
		List<PricingProfile> pricingProfiles = pricingProfileService.fetchPricingProfile(institutionCode, pricingIndex);
		if (!pricingProfiles.isEmpty() && pricingProfiles != null) {
			System.out.println(pricingProfiles);
			List<PricingProfileInfoResponse> pricingProfileInfoResponses = PricingProfileInfoResponse.createResponse(pricingProfiles);
			ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("common.pricep", null), pricingProfileInfoResponses);
			return ResponseEntity.status(200).body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("common.instituionnodata", null), null);
		return ResponseEntity.status(200).body(responseModel);
	}

	@RequestMapping(value = { "/user/mcc" }, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> addMccList(HttpServletRequest request,
			@RequestBody MccListInfo mccListInfo) {
		MccList mccList = mccListService.findMcc(mccListInfo.getInstitutionCode(), mccListInfo.getMccCode());

		Boolean mccDup=mccListService.findMccDup(mccListInfo.getMccName(),mccListInfo.getInstitutionCode());


		if (mccDup.equals(true) ) {
			ResponseModel responseModel = new ResponseModel(false, "mcc name is already exists", null);
			return ResponseEntity.accepted().body(responseModel);
		}
		if (mccList != null) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("common.mccadd", null), null);
			return ResponseEntity.status(200).body(responseModel);
		}
		mccList = mccListService.save(mccList, mccListInfo);
		if (mccList != null) {
			ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("common.mccadded", null), null);
			return ResponseEntity.status(201).body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(false, "Something went wrong...", null);
		return ResponseEntity.status(500).body(responseModel);
	}


	@RequestMapping(value = {
			"/user/mcc/delete/{mccCode}" }, method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> deletemccCodee(@PathVariable("mccCode") String mccCode) {
		MccList mccList = mccListService.findMcc(mccCode);
		if ( mccList == null) {
			ResponseModel responseModel = new ResponseModel(false, "MccList is not found", null);
			return ResponseEntity.status(200).body(responseModel);
		}
		mccListService.delete(mccCode);
		ResponseModel responseModel = new ResponseModel(true, "Mcc deleted successfully", null);
		return ResponseEntity.accepted().body(responseModel);
	}

	@RequestMapping(value = {
			"/user/mcc/{institutioncode}/{mcccode}" }, method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> updateMccList(HttpServletRequest request,
			@PathVariable("institutioncode") String institutionCode, @PathVariable("mcccode") String mccCode,
			@RequestBody MccListInfo mccListInfo) {
		MccList mccList = mccListService.findMcc(institutionCode, mccCode);
		if (mccList == null) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("common.mccno", null), null);
			return ResponseEntity.status(200).body(responseModel);
		}
		List<MccList> mccDup=mccListService.findMccDup(institutionCode,mccListInfo.getMccName(),mccCode);


		if (mccDup.size() >0 ) {
			ResponseModel responseModel = new ResponseModel(false, "mcc name is already exists", null);
			return ResponseEntity.accepted().body(responseModel);
		}
		mccList = mccListService.update(mccList, mccListInfo);
		if (mccList != null) {
			ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("common.mcc", null), null);
			return ResponseEntity.status(202).body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(false, "Something went wrong...", null);
		return ResponseEntity.status(500).body(responseModel);
	}

	@RequestMapping(value = {
			"/user/mccs/{institutioncode}" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> getMccList(HttpServletRequest request,
			@PathVariable("institutioncode") String institutionCode) {
		List<MccList> mLists = mccListService.fetchMccList(institutionCode);
		if (!mLists.isEmpty() && mLists != null) {
			List<MccListInfo> mccListInfos = MccListInfo.createResponse(mLists);
			ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("common.mcclist", null), mccListInfos);
			return ResponseEntity.status(200).body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("common.instituionnodata", null), null);
		return ResponseEntity.status(200).body(responseModel);
	}

	@RequestMapping(value = {
			"/user/transactioncode" }, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> addTransactionCodeList(HttpServletRequest request,
			@RequestBody TransactionCodeListInfo transactionCodeListInfo) {
		TransactionCodeList transactionCodeList = transactionCodeListService.findTransactionCode(
				transactionCodeListInfo.getInstitutionCode(), transactionCodeListInfo.getTransactionCode());

		Boolean transDup=transactionCodeListService.findTransDup(transactionCodeListInfo.getTransactionName(),transactionCodeListInfo.getInstitutionCode());


		if (transDup.equals(true) ) {
			ResponseModel responseModel = new ResponseModel(false, "transaction name is already exists", null);
			return ResponseEntity.accepted().body(responseModel);
		}
		if (transactionCodeList != null) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("common.trancode", null), null);
			return ResponseEntity.status(200).body(responseModel);
		}

		if(transactionCodeListInfo.getServiceCode()!=null) {
			List<TransactionCodeList> transactionCodeList1 = transactionCodeListService.findTransactionCodeWithServiceCode(
					transactionCodeListInfo.getInstitutionCode(), transactionCodeListInfo.getServiceCode());
			if (transactionCodeList1 != null && transactionCodeList1.size() > 0) {
				ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("common.trancode", null), null);
				return ResponseEntity.status(200).body(responseModel);
			}
		}
		transactionCodeList = transactionCodeListService.save(transactionCodeList, transactionCodeListInfo);
		if (transactionCodeList != null) {
			ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("common.transactioncode", null), null);
			return ResponseEntity.status(201).body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(false, "Something went wrong...", null);
		return ResponseEntity.status(500).body(responseModel);
	}


	@RequestMapping(value = {
			"/user/transactioncode/delete/{transactionCode}" }, method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> deletetransactionCode(@PathVariable("transactionCode") String transactionCode) {
		TransactionCodeList transactionCodeList = transactionCodeListService.findTransactionCode(transactionCode);
		if ( transactionCodeList == null) {
			ResponseModel responseModel = new ResponseModel(false, "TransactionCode is not found", null);
			return ResponseEntity.status(200).body(responseModel);
		}
		if ( transactionCodeList.getServiceList() != null) {
			ResponseModel responseModel = new ResponseModel(false, "Without Deleting the  ServiceList You Can't", null);
			return ResponseEntity.status(200).body(responseModel);
		}
		transactionCodeListService.delete(transactionCode);
		ResponseModel responseModel = new ResponseModel(true, "TransactionCode deleted successfully", null);
		return ResponseEntity.accepted().body(responseModel);
	}
	@RequestMapping(value = {
			"/user/transactioncode/{institutioncode}/{transactioncode}" }, method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> updateTransactionCodeList(HttpServletRequest request,
			@PathVariable("institutioncode") String institutionCode,
			@PathVariable("transactioncode") String transactionCode,
			@RequestBody TransactionCodeListInfo transactionCodeListInfo) {
		TransactionCodeList transactionCodeList = transactionCodeListService.findTransactionCode(institutionCode,
				transactionCode);
		if (transactionCodeList == null) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("common.tanscationcode", null),
					null);
			return ResponseEntity.status(200).body(responseModel);
		}
		List<TransactionCodeList> transDup=transactionCodeListService.findTransDup(institutionCode,transactionCodeListInfo.getTransactionName(),transactionCode);


		if (transDup.size() >0 ) {
			ResponseModel responseModel = new ResponseModel(false, "transaction name is already exists", null);
			return ResponseEntity.accepted().body(responseModel);
		}
		if(transactionCodeListInfo.getServiceCode()!=null) {
			List<TransactionCodeList> transactionCodeList1 = transactionCodeListService.findTransactionCodeWithServiceCode(
					institutionCode, transactionCodeListInfo.getServiceCode());
			if (transactionCodeList1 != null && transactionCodeList1.size() > 0) {
				ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("common.trancode", null), null);
				return ResponseEntity.status(200).body(responseModel);
			}
		}

		transactionCodeList = transactionCodeListService.update(transactionCodeList, transactionCodeListInfo);
		if (transactionCodeList != null) {
			ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("common.tanscationcodeupdate", null), null);
			return ResponseEntity.status(202).body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(false, "Something went wrong...", null);
		return ResponseEntity.status(500).body(responseModel);
	}

	@RequestMapping(value = {
			"/user/transactioncode/{institutioncode}" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> getTransactionCodeList(HttpServletRequest request,
			@PathVariable("institutioncode") String institutionCode) {
		List<TransactionCodeList> transactionCodeLists = transactionCodeListService.fetchMccList(institutionCode);
		if (!transactionCodeLists.isEmpty() && transactionCodeLists != null) {
			List<TransactionCodeListInfo> mccListInfos = TransactionCodeListInfo.createResponse(transactionCodeLists);
			ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("common.listtrans", null), mccListInfos);
			return ResponseEntity.status(200).body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("common.instituionnodata", null), null);
		return ResponseEntity.status(200).body(responseModel);
	}

	@RequestMapping(value = {
			"/internal/user/currency/{currencycode}" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> getCurrency(HttpServletRequest request,
			@PathVariable("currencycode") String currencyCode) {
		CurrencyList currencyList = currencyListService.findCurrency(null, currencyCode);
		if (currencyList == null) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("common.currencyp", null), null);
			return ResponseEntity.status(200).body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("common.currency", null), currencyList);
		return ResponseEntity.accepted().body(responseModel);
	}

	@RequestMapping(value = {
			"/user/saveServiceList" }, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> addServiceList(HttpServletRequest request,
																  @RequestBody ServiceListInfo serviceListInfo) {
		ServiceList serviceList = serviceListService.findServiceName(serviceListInfo.getInstitutionCode(), serviceListInfo.getServiceName());


		Boolean checkDuplication=serviceListService.findServiceDup(serviceListInfo.getServiceName(),serviceListInfo.getInstitutionCode());


		if (checkDuplication.equals(true) ) {
			ResponseModel responseModel = new ResponseModel(false, "service name is already exists", null);
			return ResponseEntity.accepted().body(responseModel);
		}

		if (serviceList != null) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("common.services", null), null);
			return ResponseEntity.status(200).body(responseModel);
		}
		serviceList = serviceListService.save(serviceList, serviceListInfo);
		if (serviceList != null) {
			ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("common.service", null), null);
			return ResponseEntity.status(201).body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(false, "Something went wrong...", null);
		return ResponseEntity.status(500).body(responseModel);
	}

	@RequestMapping(value = {
			"/internal/user/getServiceList/{institutioncode}" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> getServiceList(HttpServletRequest request,
																  @PathVariable("institutioncode") String institutionCode) {
		List<ServiceList> serviceLists = serviceListService.fetchMccList(institutionCode);
		if (!serviceLists.isEmpty() && serviceLists != null) {
			ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("common.sericelist", null), serviceLists);
			return ResponseEntity.status(200).body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("common.instituionnodata", null), null);
		return ResponseEntity.status(200).body(responseModel);
	}
	@RequestMapping(value = {
			"/user/updateServiceList/{institutioncode}/{servicecode}" }, method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> updateServiceList(HttpServletRequest request,
																	 @PathVariable("institutioncode") String institutionCode,
																	 @PathVariable("servicecode") int servicecode,
																	 @RequestBody ServiceListInfo serviceListInfo) {
		ServiceList serviceList = serviceListService.findServiceCode(institutionCode, servicecode);
		if (serviceList == null) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("common.sericepresent", null),
					null);
			return ResponseEntity.status(200).body(responseModel);
		}

		List<ServiceList> serviceDup=serviceListService.findServiceDup(serviceListInfo.getInstitutionCode(),serviceListInfo.getServiceName(),servicecode);

		if (serviceDup.size() >0 ) {
			ResponseModel responseModel = new ResponseModel(false, "service name is already exists", null);
			return ResponseEntity.ok().body(responseModel);
		}


		if (!serviceListInfo.getServiceName().equals(serviceList.getServiceName())) {
			ServiceList serviceList1 = serviceListService.findServiceName(serviceListInfo.getInstitutionCode(), serviceListInfo.getServiceName());
			if (serviceList1 != null) {
				ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("common.sericese", null), null);
				return ResponseEntity.status(200).body(responseModel);
			}
		}
		ServiceList serviceList2 = serviceListService.update(serviceList, serviceListInfo);
		if (serviceList2 != null) {
			ResponseModel responseModel = new ResponseModel(true, "service Updated Successfully", null);
			return ResponseEntity.status(202).body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(false, "Something went wrong...", null);
		return ResponseEntity.status(500).body(responseModel);
	}

	@RequestMapping(value = {
			"/user/ServiceList/delete/{servicecode}" }, method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> deleteservicecode(@PathVariable("servicecode") Integer servicecode) {
		ServiceList serviceList1 = serviceListService.findServiceCode(servicecode);
		if ( serviceList1 == null) {
			ResponseModel responseModel = new ResponseModel(false, "Service is not found", null);
			return ResponseEntity.status(200).body(responseModel);
		}
		serviceListService.delete(servicecode);
		ResponseModel responseModel = new ResponseModel(true, "Service deleted successfully", null);
		return ResponseEntity.accepted().body(responseModel);
	}



	@RequestMapping(value = {
			"/internal/user/mccbyCode/{institutioncode}/{mcccode}" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> getMccByCode(HttpServletRequest request,
														@PathVariable("institutioncode") String institutionCode, @PathVariable("mcccode") String mccCode) {
		MccList mLists = mccListService.findMcc(institutionCode,mccCode);
		if (mLists != null) {
			ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("common.mccs", null), mLists);
			return ResponseEntity.status(202).body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("common.mccscode", null), null);
		return ResponseEntity.status(200).body(responseModel);
	}
}
