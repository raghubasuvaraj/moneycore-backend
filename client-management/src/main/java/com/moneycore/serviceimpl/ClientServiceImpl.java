package com.moneycore.serviceimpl;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import com.moneycore.bean.*;
import com.moneycore.component.Translator;
import com.moneycore.entity.Address;
import com.moneycore.entity.MccList;
import com.moneycore.entity.NationalityList;
import com.moneycore.entity.WalletSpace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.moneycore.entity.ActivityList;
import com.moneycore.entity.Branch;
import com.moneycore.entity.Client;
import com.moneycore.entity.CountryList;
import com.moneycore.entity.CurrencyList;
import com.moneycore.entity.CustomerSegmentList;
import com.moneycore.entity.InstitutionList;
import com.moneycore.entity.LanguageList;
import com.moneycore.entity.StatusList;
import com.moneycore.entity.StatusReasonCode;
import com.moneycore.entity.TitleList;
import com.moneycore.entity.Wallet;
import com.moneycore.model.ResponseModel;
import com.moneycore.repository.ClientsRepository;
import com.moneycore.service.ClientService;
import com.moneycore.util.CommonUtil;

@Service("clientService")
public class ClientServiceImpl implements ClientService {

	@Autowired
	RestTemplate restTemplate;

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private ClientsRepository clientRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

//	@Override
//	public Client findUserByEmail(String email) {
//		return clientRepository.findByEmail(email);
//	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Client> findUserByContact(String contact) {
		List<Client> client = em
				.createNativeQuery("SELECT c.* FROM client c WHERE c.pr_phone_1 = :prPhone1 and c.is_deleted=false", Client.class)
				.setParameter("prPhone1", contact).getResultList();
		return client;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Client> findUserByEmail(String email) {
		List<Client> client = em.createNativeQuery("SELECT c.* FROM client c WHERE c.email = :email and c.is_deleted=false", Client.class)
				.setParameter("email", email).getResultList();
		return client;
	}

	@Override
	public void saveClient(Client client, boolean logout) {
		client.setLogout(logout);
		clientRepository.save(client);

	}

	@Override
	public List<Client> getAllClients() {
		return clientRepository.findAll();
	}

	@Override
	public Client insert(ClientRegister clientRegister, Address address, InstitutionList institute) {
		Client client = new Client();
		client.setFirstName(clientRegister.getFirstName());
		client.setLastName(clientRegister.getLastName());
		client.setFamilyName(clientRegister.getLastName());
		client.setMiddleName(clientRegister.getMiddleName());
		if (clientRegister.getBranchCode() != null) {
			client.setBranchList(getBranch(clientRegister.getBranchCode()));
		} else {
			client.setBranchList(getBranch(institute.getDefaultBranch()));
		}
		client.setTitleCode(getTitle(clientRegister.getTitleCode()));
		client.setNationalityCode(getNationalityByCode(clientRegister.getNationalityCode()));
		client.setCountryCode(getCountryByCode(clientRegister.getInstitutionCode(),clientRegister.getCountryCode()));
		client.setLanguageCode(getLanguage(clientRegister.getLanguageCode()));
		client.setStatusCode(getStatus("I"));
		client.setStatusReason(getStatusReasonCode(clientRegister.getInstitutionCode(), "I"));
		client.setStatusDate(new Date());
		client.setActivityCode(getActivity(clientRegister.getActivityCode()));
		client.setCustomerSegment(getCustomerSegment(clientRegister.getCustomerSegment()));
		if (clientRegister.getCurrencyCode() != null) {
			client.setCurrencyCode(getCurrency(clientRegister.getCurrencyCode()));
		} else {
			client.setCurrencyCode(getCurrency(institute.getCurrencyCode()));
		}
		client.setPrPhone1(clientRegister.getPrPhone1());
		client.setPrPhone2(clientRegister.getPrPhone2());
		client.setPrPhone3(clientRegister.getPrPhone3());
		client.setPrPhone4(clientRegister.getPrPhone4());
		client.setPassword(bCryptPasswordEncoder.encode(clientRegister.getPassword()));
		client.setPrEmail(clientRegister.getEmail());
		client.setEmail(clientRegister.getEmail());
		client.setDateOfBirth(clientRegister.getDateOfBirth());
		client.setGender(clientRegister.getGender());
		client.setInstitutionList(institute);
		if (clientRegister.getUserCreate() != null) {
			client.setUserCreate(clientRegister.getUserCreate());
		}
		client.setDateCreate(new Date());
		client.setMerchant(clientRegister.getIsMerchant());
		client.setDeviceToken(clientRegister.getDeviceToken());
		if(clientRegister.getIsMerchant()){
			client.setLicenceImage(clientRegister.getLicenceImage());
			client.setMccCode(getMccListByMccCode(clientRegister.getInstitutionCode(), clientRegister.getMccCode()));
			client.setLicenceNo(clientRegister.getLicenceNo());
			client.setEntityName(clientRegister.getEntityName());
		}
		client.setAddress(address);
		client.setCreateSource(clientRegister.getCreateSource());
		return clientRepository.save(client);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Client> findAllClients() {
		Query query = em.createNamedQuery("Client.query_find_all_clients", Client.class);
		List<Client> list = query.getResultList();
		return list;
	}

	@Override
	public Client findByClientByID(int id) {
		Client client = null;
		Optional<Client> optional = clientRepository.findById(id);
		if (optional.isPresent()) {
			client = optional.get();
		}
		return client;
	}

	@Override
	public Client updateClient(Client client, ClientRegister clientRegister) {
		if(clientRegister != null) {
			client.setFirstName(clientRegister.getFirstName());
			client.setMiddleName(clientRegister.getMiddleName());
			client.setLastName(clientRegister.getLastName());
			client.setFamilyName(clientRegister.getLastName());
			client.setGender(clientRegister.getGender());
			client.setPrPhone1(clientRegister.getPrPhone1());
			client.setTitleCode(getTitle(clientRegister.getTitleCode()));
			client.setNationalityCode(getNationalityById(clientRegister.getNationalityCode()));
			client.setCountryCode(getCountryByCode(client.getInstitutionList().getInstitutionCode(),clientRegister.getCountryCode()));
//			client.setBranchList(getBranch(clientRegister.getBranchCode()));
			client.setLanguageCode(getLanguage(clientRegister.getLanguageCode()));
			client.setCurrencyCode(getCurrency(clientRegister.getCurrencyCode()));
			client.setUserModif(clientRegister.getUserModif());
			client.setDateModif(new Date());
			client.setDeviceToken(clientRegister.getDeviceToken());
			if (clientRegister.getStatusCode() != null) {
				client.setStatusCode(getStatus(clientRegister.getStatusCode()));
			}
			if (clientRegister.getStatusReason() != null) {
				client.setStatusReason(getStatusReason(clientRegister.getStatusReason()));
			}
		}
		return clientRepository.save(client);
	}

	@Override
	public Client getClientByEmail(String email) {
		return clientRepository.findByEmail(email);
	}

	@Override
	public Client updateClientLanguage(Client client, String languageCode) {
		client.setLanguageCode(getLanguage(languageCode));
		return clientRepository.save(client);
	}

	@Override
	public Client updateClientNationality(Client client, String nationalityCode) {
		client.setNationalityCode(getNationalityById(nationalityCode));
		return clientRepository.save(client);
	}

	@Override
	public Client updateClientCountry(Client client, String countryCode) {
		client.setCountryCode(getCountryByCode(client.getInstitutionList().getInstitutionCode(), countryCode));
		return clientRepository.save(client);
	}

	@Override
	public Client updateClient(Client client) {
		return clientRepository.save(client);
	}

	@Override
	public Client getClientByPhoneNumber(String phoneNumber) {
		Client client = null;
		if (!phoneNumber.contains("+"))
			phoneNumber = "+" + phoneNumber;

		Optional<Client> optional = clientRepository.findByPhoneNumber(phoneNumber);
		if (optional.isPresent()) {
			client = optional.get();
		}
		return client;
	}

	@Override
	public String randomPassword() {
		String ALPHA_NUMERIC_STRING = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%&?{}*";
		StringBuilder builder = new StringBuilder();
		int count = 8;
		while (count-- != 0) {
			int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
			builder.append(ALPHA_NUMERIC_STRING.charAt(character));
		}
		return builder.toString();
	}

	private Branch getBranch(String branchCode) {
		ResponseModel responseModel = null;
		Branch branch = null;
		String url = CommonUtil.getApplicationBaseUrl() + "/api/institutionmanagement/internal/branch/" + branchCode;
		ResponseEntity<ResponseModel> responseEntity = CommonUtil.getDataFromOtherService(restTemplate, url, null);
		if (responseEntity.getStatusCode().equals(HttpStatus.ACCEPTED)) {
			responseModel = responseEntity.getBody();
			branch = CommonUtil.convertToOriginalObject(responseModel.getResult(), Branch.class);
		}
		return branch;
	}

	private NationalityList getNationalityByCode(String nationalityCode) {
		ResponseModel responseModel = null;
		NationalityList nationalityList = null;
		String url = CommonUtil.getApplicationBaseUrl() + "/api/usermanagement/internal/user/nationalityCode/"+ nationalityCode;
		ResponseEntity<ResponseModel> responseEntity = CommonUtil.getDataFromOtherService(restTemplate, url, null);
		if (responseEntity.getStatusCode().equals(HttpStatus.ACCEPTED)) {
			responseModel = responseEntity.getBody();
			nationalityList = CommonUtil.convertToOriginalObject(responseModel.getResult(), NationalityList.class);
		}
		return nationalityList;
	}

	private NationalityList getNationalityById(String nationalityId) {
		ResponseModel responseModel = null;
		NationalityList nationalityList = null;
		String url = CommonUtil.getApplicationBaseUrl() + "/api/usermanagement/internal/user/nationalityId/"+ nationalityId;
		ResponseEntity<ResponseModel> responseEntity = CommonUtil.getDataFromOtherService(restTemplate, url, null);
		if (responseEntity.getStatusCode().equals(HttpStatus.ACCEPTED)) {
			responseModel = responseEntity.getBody();
			nationalityList = CommonUtil.convertToOriginalObject(responseModel.getResult(), NationalityList.class);
		}
		return nationalityList;
	}

	private CountryList getCountryByCode(String institutionCode,String countryCode) {
		ResponseModel responseModel = null;
		CountryList countryList = null;
		String url = CommonUtil.getApplicationBaseUrl() + "/api/usermanagement/internal/user/countryCode/" +institutionCode+"/"+ countryCode;
		ResponseEntity<ResponseModel> responseEntity = CommonUtil.getDataFromOtherService(restTemplate, url, null);
		if (responseEntity.getStatusCode().equals(HttpStatus.ACCEPTED)) {
			responseModel = responseEntity.getBody();
			countryList = CommonUtil.convertToOriginalObject(responseModel.getResult(), CountryList.class);
		}
		return countryList;
	}

	private CountryList getCountryByName(String institutionCode,String countryName) {
		ResponseModel responseModel = null;
		CountryList countryList = null;
		String url = CommonUtil.getApplicationBaseUrl() + "/api/usermanagement/internal/user/countryName/" +institutionCode+"/"+ countryName;
		ResponseEntity<ResponseModel> responseEntity = CommonUtil.getDataFromOtherService(restTemplate, url, null);
		if (responseEntity.getStatusCode().equals(HttpStatus.ACCEPTED)) {
			responseModel = responseEntity.getBody();
			countryList = CommonUtil.convertToOriginalObject(responseModel.getResult(), CountryList.class);
		}
		return countryList;
	}

	private LanguageList getLanguage(String languageCode) {
		ResponseModel responseModel = null;
		LanguageList languageList = null;
		String url = CommonUtil.getApplicationBaseUrl() + "/api/usermanagement/internal/user/language/" + languageCode;
		ResponseEntity<ResponseModel> responseEntity = CommonUtil.getDataFromOtherService(restTemplate, url, null);
		if (responseEntity.getStatusCode().equals(HttpStatus.ACCEPTED)) {
			responseModel = responseEntity.getBody();
			languageList = CommonUtil.convertToOriginalObject(responseModel.getResult(), LanguageList.class);
		}
		return languageList;
	}

	private CurrencyList getCurrency(String currencyCode) {
		ResponseModel responseModel = null;
		CurrencyList currencyList = null;
		String url = CommonUtil.getApplicationBaseUrl() + "/api/usermanagement/internal/user/currency/code/" + currencyCode;
		ResponseEntity<ResponseModel> responseEntity = CommonUtil.getDataFromOtherService(restTemplate, url, null);
		if (responseEntity.getStatusCode().equals(HttpStatus.ACCEPTED)) {
			responseModel = responseEntity.getBody();
			currencyList = CommonUtil.convertToOriginalObject(responseModel.getResult(), CurrencyList.class);
		}
		return currencyList;
	}

	private StatusList getStatus(String statusCode) {
		ResponseModel responseModel = null;
		StatusList statusList = null;
		String url = CommonUtil.getApplicationBaseUrl() + "/api/usermanagement/internal/user/status/" + statusCode;
		ResponseEntity<ResponseModel> responseEntity = CommonUtil.getDataFromOtherService(restTemplate, url, null);
		if (responseEntity.getStatusCode().equals(HttpStatus.ACCEPTED)) {
			responseModel = responseEntity.getBody();
			statusList = CommonUtil.convertToOriginalObject(responseModel.getResult(), StatusList.class);
		}
		return statusList;
	}

	private StatusReasonCode getStatusReason(String statusReasonCode) {
		ResponseModel responseModel = null;
		StatusReasonCode reasonCode = null;
		String url = CommonUtil.getApplicationBaseUrl() + "/api/usermanagement/internal/user/statusreason/" + statusReasonCode;
		ResponseEntity<ResponseModel> responseEntity = CommonUtil.getDataFromOtherService(restTemplate, url, null);
		if (responseEntity.getStatusCode().equals(HttpStatus.ACCEPTED)) {
			responseModel = responseEntity.getBody();
			reasonCode = CommonUtil.convertToOriginalObject(responseModel.getResult(), StatusReasonCode.class);
		}
		return reasonCode;
	}

	private ActivityList getActivity(String activityCode) {
		ResponseModel responseModel = null;
		ActivityList activityList = null;
		String url = CommonUtil.getApplicationBaseUrl() + "/api/usermanagement/internal/user/activity/" + activityCode;
		ResponseEntity<ResponseModel> responseEntity = CommonUtil.getDataFromOtherService(restTemplate, url, null);
		if (responseEntity.getStatusCode().equals(HttpStatus.ACCEPTED)) {
			responseModel = responseEntity.getBody();
			activityList = CommonUtil.convertToOriginalObject(responseModel.getResult(), ActivityList.class);
		}
		return activityList;
	}

	private CustomerSegmentList getCustomerSegment(String customerSegmentCode) {
		ResponseModel responseModel = null;
		CustomerSegmentList customerSegmentList = null;
		String url = CommonUtil.getApplicationBaseUrl() + "/api/usermanagement/internal/user/customersegment/" + customerSegmentCode;
		ResponseEntity<ResponseModel> responseEntity = CommonUtil.getDataFromOtherService(restTemplate, url, null);
		if (responseEntity.getStatusCode().equals(HttpStatus.ACCEPTED)) {
			responseModel = responseEntity.getBody();
			customerSegmentList = CommonUtil.convertToOriginalObject(responseModel.getResult(), CustomerSegmentList.class);
		}
		return customerSegmentList;
	}

	private TitleList getTitle(String titleCode) {
		ResponseModel responseModel = null;
		TitleList titleList = null;
		String url = CommonUtil.getApplicationBaseUrl() + "/api/usermanagement/internal/user/title/" + titleCode;
		ResponseEntity<ResponseModel> responseEntity = CommonUtil.getDataFromOtherService(restTemplate, url, null);
		if (responseEntity.getStatusCode().equals(HttpStatus.ACCEPTED)) {
			responseModel = responseEntity.getBody();
			titleList = CommonUtil.convertToOriginalObject(responseModel.getResult(), TitleList.class);
		}
		return titleList;
	}

	private StatusReasonCode getStatusReasonCode(String institutionCode, String statusCode) {
		ResponseModel responseModel = null;
		StatusReasonCode reasonCode = null;
		String url = CommonUtil.getApplicationBaseUrl() + "/api/usermanagement/internal/user/statusreason/status/" + institutionCode+"/"+statusCode;
		ResponseEntity<ResponseModel> responseEntity = CommonUtil.getDataFromOtherService(restTemplate, url, null);
		if (responseEntity.getStatusCode().equals(HttpStatus.ACCEPTED)) {
			responseModel = responseEntity.getBody();
			reasonCode = CommonUtil.convertToOriginalObject(responseModel.getResult(), StatusReasonCode.class);
		}
		return reasonCode;
	}

	public Wallet getWallet(String walletId) {
		ResponseModel responseModel = null;
		Wallet wallet = null;
		String url = CommonUtil.getApplicationBaseUrl() + "/api/walletmanagement/internal/client/wallet/" + walletId;
		ResponseEntity<ResponseModel> responseEntity = CommonUtil.getDataFromOtherService(restTemplate, url, null);
		if (responseEntity.getStatusCode().equals(HttpStatus.ACCEPTED)) {
			responseModel = responseEntity.getBody();
			wallet = CommonUtil.convertToOriginalObject(responseModel.getResult(), Wallet.class);
		}
		return wallet;
	}

	@Override
	public MccList getMccListByMccCode(String institutionCode, String mccCode) {
		ResponseModel responseModel = null;
		MccList mccList = null;
		String url = CommonUtil.getApplicationBaseUrl() + "/api/usermanagement/internal/user/mccbyCode/"+ institutionCode +"/"+mccCode;
		ResponseEntity<ResponseModel> responseEntity = CommonUtil.getDataFromOtherService(restTemplate, url, null);
		if (responseEntity.getStatusCode().equals(HttpStatus.ACCEPTED)) {
			responseModel = responseEntity.getBody();
			mccList = CommonUtil.convertToOriginalObject(responseModel.getResult(), MccList.class);
		}
		return mccList;
	}

	@Override
	public List<Client> getMerchantByMccCode(String instituteCode, List mccCode) {
		return clientRepository.getMerchantByMccCode(instituteCode,mccCode);
	}

	@Override
	public int getTotalInstitutions(HttpServletRequest request) {
		ResponseModel responseModel = null;
		List<InstitutionList> institutionList = null;
		String url = CommonUtil.getApplicationBaseUrl() + "/api/institutionmanagement/institutions/";
		ResponseEntity<ResponseModel> responseEntity = CommonUtil.getDataFromOtherService(restTemplate, url,
				CommonUtil.getJwtTokenFromRequest(request));
		if (responseEntity.getStatusCode().equals(HttpStatus.ACCEPTED)) {
			responseModel = responseEntity.getBody();
			InstitutionList[] institutionListArray = CommonUtil.convertToOriginalObject(responseModel.getResult(),
					InstitutionList[].class);
			if (institutionListArray != null) {
				institutionList = Arrays.asList(institutionListArray);
			}
		}
		return institutionList.size();
	}
	@Override
	public int getTotalBranch(HttpServletRequest request) {
		ResponseModel responseModel = null;
		List<Branch> branchList = null;
		String url = CommonUtil.getApplicationBaseUrl() + "/api/institutionmanagement/branches/";
		ResponseEntity<ResponseModel> responseEntity = CommonUtil.getDataFromOtherService(restTemplate, url,
				CommonUtil.getJwtTokenFromRequest(request));
		if (responseEntity.getStatusCode().equals(HttpStatus.ACCEPTED)) {
			responseModel = responseEntity.getBody();
			Branch[] branchArray = CommonUtil.convertToOriginalObject(responseModel.getResult(),
					Branch[].class);
			if (branchArray != null) {
				branchList = Arrays.asList(branchArray);
			}
		}
		return branchList.size();
	}
	@Override
	public Map<String,Long> getTotalClientsAndMerchnat(HttpServletRequest request) {
		List<Client>  clientList = getAllClients();
		long merchantCount = clientList.stream().filter(client -> client.getIsMerchant()).count();
		long clientCount = clientList.stream().filter(client -> !client.getIsMerchant()).count();
		long activeUser = clientList.stream().filter(client -> client.getStatusCode()!=null && "A,M".contains(client.getStatusCode().getStatusCode())).count();
		long cancelledUser = clientList.stream().filter(client -> client.getStatusCode()!=null && "C".equalsIgnoreCase(client.getStatusCode().getStatusCode())).count();
		long blockedUser = clientList.stream().filter(client -> client.getStatusCode()!=null && "B".equalsIgnoreCase(client.getStatusCode().getStatusCode())).count();
		long suspendedUser = clientList.stream().filter(client -> client.getStatusCode()!=null && "S".equalsIgnoreCase(client.getStatusCode().getStatusCode())).count();
		Map<String, Long> resultMap = new HashMap<>();
		resultMap.put("merchantCount",merchantCount);
		resultMap.put("clientCount",clientCount);
		resultMap.put("activeUser",activeUser);
		resultMap.put("cancelledUser",cancelledUser);
		resultMap.put("blockedUser",blockedUser);
		resultMap.put("suspendedUser",suspendedUser);
		return resultMap;
	}
	@Override
	public String getTotalWalletSpaceDetail(HttpServletRequest request) {
		ResponseModel responseModel = null;
		List<WalletSpace> walletSpaceList = null;
		double totalWSAmount = 0;
		String url = CommonUtil.getApplicationBaseUrl() + "/api/walletmanagement/wallet/getAllWalletSpace";
		ResponseEntity<ResponseModel> responseEntity = CommonUtil.getDataFromOtherService(restTemplate, url,
				CommonUtil.getJwtTokenFromRequest(request));
		if (responseEntity.getStatusCode().equals(HttpStatus.ACCEPTED)) {
			responseModel = responseEntity.getBody();
			WalletSpace[] walletSpacesArray = CommonUtil.convertToOriginalObject(responseModel.getResult(),
					WalletSpace[].class);
			if (walletSpacesArray != null) {
				walletSpaceList = Arrays.asList(walletSpacesArray);
				totalWSAmount = walletSpaceList.stream().collect(Collectors.summingDouble(WalletSpace::getAmount));
			}
		}
		return walletSpaceList.size() +":"+ totalWSAmount;
	}

	@Override
	public DashboardInfo getDashboardInfo() {
		DashboardInfo dashboardInfo = null;
		Optional<DashboardInfo> optional = clientRepository.findtDashboardInfo();
		if (optional.isPresent()) {
			dashboardInfo = optional.get();
		}
		return dashboardInfo;
	}

	@Override
	public List<RegDataInfodtls> getRegDataInfo(String lastMonth) {
		List<RegistrationDataInfo> listDetails = clientRepository.findRegDataInfo();
		List<RegDataInfodtls> listRegDtls = new ArrayList();
		RegDataInfodtls regDtls = null;
		Integer month = Integer.valueOf(lastMonth);
		if (listDetails.size() < month) {
			List<RegistrationDataInfo> listDetailsPrev = clientRepository.findRegDataInfoPre();
			int getSize = month - listDetails.size();
			for (int i = 0; i < getSize; i++) {
				regDtls = new RegDataInfodtls(listDetailsPrev.get(i).getMonth(), listDetailsPrev.get(i).getTotalInstitutions(), listDetailsPrev.get(i).getTotalClients(), listDetailsPrev.get(i).getTotalMerchants());
				listRegDtls.add(regDtls);
			}
			Collections.reverse(listRegDtls);
			Collections.reverse(listDetails);
			for (int i = 0; i < listDetails.size(); i++) {
				regDtls = new RegDataInfodtls(listDetails.get(i).getMonth(), listDetails.get(i).getTotalInstitutions(), listDetails.get(i).getTotalClients(), listDetails.get(i).getTotalMerchants());
				listRegDtls.add(regDtls);
			}
			return listRegDtls;
		} else {
			for (int i = 0; i < month; i++) {
				regDtls = new RegDataInfodtls(listDetails.get(i).getMonth(), listDetails.get(i).getTotalInstitutions(), listDetails.get(i).getTotalClients(), listDetails.get(i).getTotalMerchants());
				listRegDtls.add(regDtls);
			}
			return listRegDtls;
		}

	}

	@Override
	public DashboardInfodtls getDashboardDetails(DashboardFilter dashboardFilter) {

		Long totalInstitutions;
		Long totalBranches;
		Long totalClients;
		Long totalMerchants;
		String totalTopup;
		String totalTransfer;
		String totalPurchase;
		Long totalWallet2Wallet;
		Long totalNumberOfSpaceAccounts;
		String totalMoneyInSpaces;
		Long cancelledUsers;
		Long blockedUsers;
		Long activeUsers;
		Long inActiveUsers;
		Long monitorUsers;
		Long suspendedUsers;


		if (dashboardFilter.getInstitutionCode() != null && dashboardFilter.getBranchCode() != null && dashboardFilter.getFilterFromDate() !=null && dashboardFilter.getFilterToDate() !=null) {
			totalInstitutions =getTotalInstitution(dashboardFilter.getInstitutionCode());
			totalBranches=getTotalBranches(dashboardFilter.getInstitutionCode(),dashboardFilter.getBranchCode());
			totalClients=getTotalClients(dashboardFilter.getInstitutionCode());
			totalMerchants=getTotalMerchants(dashboardFilter.getInstitutionCode());
			totalTopup=getTotalTopup(dashboardFilter.getInstitutionCode());
			totalTransfer=getTotalTransfer(dashboardFilter.getInstitutionCode());
			totalPurchase=getTotalPurchase(dashboardFilter.getInstitutionCode());
			totalWallet2Wallet=getTotalWallet2Wallet(dashboardFilter.getInstitutionCode());
			totalNumberOfSpaceAccounts=getTotalNumberOfSpaceAccounts(dashboardFilter.getInstitutionCode());
			totalMoneyInSpaces=getTotalMoneyInSpaces(dashboardFilter.getInstitutionCode());
			cancelledUsers=getUsers(dashboardFilter.getInstitutionCode(),dashboardFilter.getFilterFromDate(),dashboardFilter.getFilterToDate(),"C");
			blockedUsers=getUsers(dashboardFilter.getInstitutionCode(),dashboardFilter.getFilterFromDate(),dashboardFilter.getFilterToDate(),"B");
			activeUsers=getUsers(dashboardFilter.getInstitutionCode(),dashboardFilter.getFilterFromDate(),dashboardFilter.getFilterToDate(),"A");
			inActiveUsers=getUsers(dashboardFilter.getInstitutionCode(),dashboardFilter.getFilterFromDate(),dashboardFilter.getFilterToDate(),"I");
			monitorUsers=getUsers(dashboardFilter.getInstitutionCode(),dashboardFilter.getFilterFromDate(),dashboardFilter.getFilterToDate(),"M");
			suspendedUsers=getUsers(dashboardFilter.getInstitutionCode(),dashboardFilter.getFilterFromDate(),dashboardFilter.getFilterToDate(),"S");
		}else if(dashboardFilter.getInstitutionCode() != null && dashboardFilter.getFilterFromDate() !=null && dashboardFilter.getFilterToDate() !=null){
			totalInstitutions =getTotalInstitution(dashboardFilter.getInstitutionCode());
			totalBranches=getTotalBranches(dashboardFilter.getInstitutionCode(),dashboardFilter.getFilterFromDate(),dashboardFilter.getFilterToDate());
			totalClients=getTotalClients(dashboardFilter.getInstitutionCode(),dashboardFilter.getFilterFromDate(),dashboardFilter.getFilterToDate());
			totalMerchants=getTotalMerchants(dashboardFilter.getInstitutionCode(),dashboardFilter.getFilterFromDate(),dashboardFilter.getFilterToDate());
			totalTopup=getTotalTopup(dashboardFilter.getInstitutionCode(),dashboardFilter.getFilterFromDate(),dashboardFilter.getFilterToDate());
			totalTransfer=getTotalTransfer(dashboardFilter.getInstitutionCode(),dashboardFilter.getFilterFromDate(),dashboardFilter.getFilterToDate());
			totalPurchase=getTotalPurchase(dashboardFilter.getInstitutionCode(),dashboardFilter.getFilterFromDate(),dashboardFilter.getFilterToDate());
			totalWallet2Wallet=getTotalWallet2Wallet(dashboardFilter.getInstitutionCode(),dashboardFilter.getFilterFromDate(),dashboardFilter.getFilterToDate());
			totalNumberOfSpaceAccounts=getTotalNumberOfSpaceAccounts(dashboardFilter.getInstitutionCode(),dashboardFilter.getFilterFromDate(),dashboardFilter.getFilterToDate());
			totalMoneyInSpaces=getTotalMoneyInSpaces(dashboardFilter.getInstitutionCode(),dashboardFilter.getFilterFromDate(),dashboardFilter.getFilterToDate());
			cancelledUsers=getUsers(dashboardFilter.getInstitutionCode(),dashboardFilter.getFilterFromDate(),dashboardFilter.getFilterToDate(),"C");
			blockedUsers=getUsers(dashboardFilter.getInstitutionCode(),dashboardFilter.getFilterFromDate(),dashboardFilter.getFilterToDate(),"B");
			activeUsers=getUsers(dashboardFilter.getInstitutionCode(),dashboardFilter.getFilterFromDate(),dashboardFilter.getFilterToDate(),"A");
			inActiveUsers=getUsers(dashboardFilter.getInstitutionCode(),dashboardFilter.getFilterFromDate(),dashboardFilter.getFilterToDate(),"I");
			monitorUsers=getUsers(dashboardFilter.getInstitutionCode(),dashboardFilter.getFilterFromDate(),dashboardFilter.getFilterToDate(),"M");
			suspendedUsers=getUsers(dashboardFilter.getInstitutionCode(),dashboardFilter.getFilterFromDate(),dashboardFilter.getFilterToDate(),"S");
		}else if(dashboardFilter.getInstitutionCode() != null && dashboardFilter.getBranchCode() != null){
			totalInstitutions =getTotalInstitution(dashboardFilter.getInstitutionCode());
			totalBranches=getTotalBranches(dashboardFilter.getInstitutionCode(),dashboardFilter.getBranchCode());
			totalClients=getTotalClients(dashboardFilter.getInstitutionCode());
			totalMerchants=getTotalMerchants(dashboardFilter.getInstitutionCode());
			totalTopup=getTotalTopup(dashboardFilter.getInstitutionCode());
			totalTransfer=getTotalTransfer(dashboardFilter.getInstitutionCode());
			totalPurchase=getTotalPurchase(dashboardFilter.getInstitutionCode());
			totalWallet2Wallet=getTotalWallet2Wallet(dashboardFilter.getInstitutionCode());
			totalNumberOfSpaceAccounts=getTotalNumberOfSpaceAccounts(dashboardFilter.getInstitutionCode());
			totalMoneyInSpaces=getTotalMoneyInSpaces(dashboardFilter.getInstitutionCode());
			cancelledUsers=getUsers(dashboardFilter.getInstitutionCode(),"C");
			blockedUsers=getUsers(dashboardFilter.getInstitutionCode(),"B");
			activeUsers=getUsers(dashboardFilter.getInstitutionCode(),"A");
			inActiveUsers=getUsers(dashboardFilter.getInstitutionCode(),"I");
			monitorUsers=getUsers(dashboardFilter.getInstitutionCode(),"M");
			suspendedUsers=getUsers(dashboardFilter.getInstitutionCode(),"S");
		}else if(dashboardFilter.getFilterFromDate() !=null && dashboardFilter.getFilterToDate() !=null){
			totalInstitutions =getTotalInstitutionByDate(dashboardFilter.getFilterFromDate(),dashboardFilter.getFilterToDate());
			totalBranches=getTotalBranches(dashboardFilter.getFilterFromDate(),dashboardFilter.getFilterToDate());
			totalClients=getTotalClients(dashboardFilter.getFilterFromDate(),dashboardFilter.getFilterToDate());
			totalMerchants=getTotalMerchants(dashboardFilter.getFilterFromDate(),dashboardFilter.getFilterToDate());
			totalTopup=getTotalTopup(dashboardFilter.getFilterFromDate(),dashboardFilter.getFilterToDate());
			totalTransfer=getTotalTransfer(dashboardFilter.getFilterFromDate(),dashboardFilter.getFilterToDate());
			totalPurchase=getTotalPurchase(dashboardFilter.getFilterFromDate(),dashboardFilter.getFilterToDate());
			totalWallet2Wallet=getTotalWallet2Wallet(dashboardFilter.getFilterFromDate(),dashboardFilter.getFilterToDate());
			totalNumberOfSpaceAccounts=getTotalNumberOfSpaceAccounts(dashboardFilter.getFilterFromDate(),dashboardFilter.getFilterToDate());
			totalMoneyInSpaces=getTotalMoneyInSpaces(dashboardFilter.getFilterFromDate(),dashboardFilter.getFilterToDate());
			cancelledUsers=getUsers(dashboardFilter.getFilterFromDate(),dashboardFilter.getFilterToDate(),"C");
			blockedUsers=getUsers(dashboardFilter.getFilterFromDate(),dashboardFilter.getFilterToDate(),"B");
			activeUsers=getUsers(dashboardFilter.getFilterFromDate(),dashboardFilter.getFilterToDate(),"A");
			inActiveUsers=getUsers(dashboardFilter.getFilterFromDate(),dashboardFilter.getFilterToDate(),"I");
			monitorUsers=getUsers(dashboardFilter.getFilterFromDate(),dashboardFilter.getFilterToDate(),"M");
			suspendedUsers=getUsers(dashboardFilter.getFilterFromDate(),dashboardFilter.getFilterToDate(),"S");
		}else if (dashboardFilter.getInstitutionCode() != null){
			totalInstitutions =getTotalInstitution(dashboardFilter.getInstitutionCode());
			totalBranches=getTotalBranches(dashboardFilter.getInstitutionCode());
			totalClients=getTotalClients(dashboardFilter.getInstitutionCode());
			totalMerchants=getTotalMerchants(dashboardFilter.getInstitutionCode());
			totalTopup=getTotalTopup(dashboardFilter.getInstitutionCode());
			totalTransfer=getTotalTransfer(dashboardFilter.getInstitutionCode());
			totalPurchase=getTotalPurchase(dashboardFilter.getInstitutionCode());
			totalWallet2Wallet=getTotalWallet2Wallet(dashboardFilter.getInstitutionCode());
			totalNumberOfSpaceAccounts=getTotalNumberOfSpaceAccounts(dashboardFilter.getInstitutionCode());
			totalMoneyInSpaces=getTotalMoneyInSpaces(dashboardFilter.getInstitutionCode());
			cancelledUsers=getUsers(dashboardFilter.getInstitutionCode(),"C");
			blockedUsers=getUsers(dashboardFilter.getInstitutionCode(),"B");
			activeUsers=getUsers(dashboardFilter.getInstitutionCode(),"A");
			inActiveUsers=getUsers(dashboardFilter.getInstitutionCode(),"I");
			monitorUsers=getUsers(dashboardFilter.getInstitutionCode(),"M");
			suspendedUsers=getUsers(dashboardFilter.getInstitutionCode(),"S");
		}else{
			DashboardInfo dashboardInfo = null;
			Optional<DashboardInfo> optional = clientRepository.findtDashboardInfo();
			if (optional.isPresent()) {
				dashboardInfo = optional.get();
			}
			totalInstitutions =dashboardInfo.getTotalInstitutions();
			totalBranches=dashboardInfo.getTotalBranches();
			totalClients=dashboardInfo.getTotalClients();
			totalMerchants=dashboardInfo.getTotalMerchants();
			totalTopup=dashboardInfo.getTotalTopup();
			totalTransfer=dashboardInfo.getTotalTransfer();
			totalPurchase=dashboardInfo.getTotalPurchase();
			totalWallet2Wallet=dashboardInfo.getTotalWallet2Wallet();
			totalNumberOfSpaceAccounts=dashboardInfo.getTotalNumberOfSpaceAccounts();
			totalMoneyInSpaces=dashboardInfo.getTotalMoneyInSpaces();
			cancelledUsers=dashboardInfo.getCancelledUsers();
			blockedUsers= dashboardInfo.getBlockedUsers();
			activeUsers=dashboardInfo.getActiveUsers();
			inActiveUsers=dashboardInfo.getInActivedUsers();
			monitorUsers=dashboardInfo.getMonitoredUsers();
			suspendedUsers=dashboardInfo.getSuspendedUsers();
		}

		List<RegistrationDataInfo> listDetails = clientRepository.findRegDataInfo();
		List<RegDataInfodtls> listRegDtls = new ArrayList();
		RegDataInfodtls regDtls = null;

		if (listDetails.size() < 12) {
			List<RegistrationDataInfo> listDetailsPrev = clientRepository.findRegDataInfoPre();
			int getSize = 12 - listDetails.size();
			for (int i = 0; i < getSize; i++) {
				if( !(listDetailsPrev.size() >= getSize) ) continue; // @Al-Mamun
				regDtls = new RegDataInfodtls(listDetailsPrev.get(i).getMonth(), listDetailsPrev.get(i).getTotalInstitutions(), listDetailsPrev.get(i).getTotalClients(), listDetailsPrev.get(i).getTotalMerchants());
				listRegDtls.add(regDtls);
			}
			Collections.reverse(listRegDtls);
			for (int i = 0; i < listDetails.size(); i++) {
				regDtls = new RegDataInfodtls(listDetails.get(i).getMonth(), listDetails.get(i).getTotalInstitutions(), listDetails.get(i).getTotalClients(), listDetails.get(i).getTotalMerchants());
				listRegDtls.add(regDtls);
			}
		} else {
			for (int i = 0; i < listDetails.size(); i++) {
				regDtls = new RegDataInfodtls(listDetails.get(i).getMonth(), listDetails.get(i).getTotalInstitutions(), listDetails.get(i).getTotalClients(), listDetails.get(i).getTotalMerchants());
				listRegDtls.add(regDtls);
			}
		}

		DashboardInfodtls dashboardInfodtls = new DashboardInfodtls(totalInstitutions ,totalBranches ,
				totalClients ,totalMerchants ,
				totalTopup , totalTransfer ,
				totalPurchase ,totalWallet2Wallet ,
				totalNumberOfSpaceAccounts ,
				totalMoneyInSpaces ,cancelledUsers ,
				blockedUsers ,activeUsers ,inActiveUsers ,
				monitorUsers ,suspendedUsers,listRegDtls);
		return dashboardInfodtls;
	}

	public Long getTotalInstitution(String institutionCode){
		String filterQuery = "";
		filterQuery = " AND il.institutionCode ='"+institutionCode+"'";
		Query countQuery = em.createQuery("select count(0) FROM InstitutionList il WHERE il.dateCreate IS NOT NULL  " +filterQuery);
		long countResult = (Long)countQuery.getSingleResult();
		return countResult;
	}

	public Long getTotalInstitutionByDate(String filterFromDate,String filterToDate){
		String filterQuery = "";
		filterQuery = "AND il.date_create BETWEEN CAST ('"+filterFromDate+"' AS DATE) AND CAST ('"+filterToDate+"' AS DATE)";
		Query countQuery = em.createNativeQuery("select count(0) FROM institution_list il WHERE il.date_create IS NOT NULL " +filterQuery);
		BigInteger count = (BigInteger)countQuery.getSingleResult();
		Long countResult = count.longValue();
		return countResult;
	}

	public Long getTotalBranches(String institutionCode){
		String filterQuery = "";
		filterQuery = " AND bl.institution_code LIKE '%" + institutionCode+ "%'";
		Query countQuery = em.createNativeQuery("select count(0) FROM branch_filter_views bl WHERE bl.date_create IS NOT NULL  " +filterQuery);
		BigInteger count = (BigInteger)countQuery.getSingleResult();
		Long countResult = count.longValue();
		return countResult;
	}

	public Long getTotalBranches(String institutionCode,String branchCode){
		String filterQuery = "";
		filterQuery = " AND bl.institution_code LIKE '%" + institutionCode+ "%' AND bl.branch_code LIKE '%" + branchCode+ "%'";
		Query countQuery = em.createNativeQuery("select count(0) FROM branch_filter_views bl WHERE bl.date_create IS NOT NULL  " +filterQuery);
		BigInteger count = (BigInteger)countQuery.getSingleResult();
		Long countResult = count.longValue();
		return countResult;
	}

	public Long getTotalBranches(String institutionCode,String filterFromDate,String filterToDate){
		String filterQuery = "";
		filterQuery = " AND bl.institution_code LIKE '%" + institutionCode+ "%' AND bl.date_create BETWEEN CAST ('"+filterFromDate+"' AS DATE) AND CAST ('"+filterToDate+"' AS DATE)";
		Query countQuery = em.createNativeQuery("select count(0) FROM branch_filter_views bl WHERE bl.date_create IS NOT NULL  " +filterQuery);
		BigInteger count = (BigInteger)countQuery.getSingleResult();
		Long countResult = count.longValue();
		return countResult;
	}

	public Long getTotalBranchesByDate(String filterFromDate,String filterToDate){
		String filterQuery = "";
		filterQuery = " AND bl.date_create BETWEEN CAST ('"+filterFromDate+"' AS DATE) AND CAST ('"+filterToDate+"' AS DATE)";
		Query countQuery = em.createNativeQuery("select count(0) FROM branch_filter_views bl WHERE bl.date_create IS NOT NULL  " +filterQuery);
		BigInteger count = (BigInteger)countQuery.getSingleResult();
		Long countResult = count.longValue();
		return countResult;
	}



	public Long getTotalClients(String institutionCode){
		String filterQuery = "";
		filterQuery = " AND bl.institution_code LIKE '%" + institutionCode+ "%'";
		Query countQuery = em.createNativeQuery("select count(0) FROM client_filter_views bl WHERE bl.date_create IS NOT NULL  " +filterQuery);
		BigInteger count = (BigInteger)countQuery.getSingleResult();
		Long countResult = count.longValue();
		return countResult;
	}


	public Long getTotalClients(String institutionCode,String filterFromDate,String filterToDate){

		String filterQuery = "";
		filterQuery = " AND bl.institution_code LIKE '%" + institutionCode+ "%' AND bl.date_create BETWEEN CAST ('"+filterFromDate+"' AS DATE) AND CAST ('"+filterToDate+"' AS DATE)";
		Query countQuery = em.createNativeQuery("select count(0) FROM client_filter_views bl WHERE bl.date_create IS NOT NULL  " +filterQuery);
		BigInteger count = (BigInteger)countQuery.getSingleResult();
		Long countResult = count.longValue();
		return countResult;

	}

	public Long getTotalClients(String filterFromDate,String filterToDate){

		String filterQuery = "";
		filterQuery = " AND bl.date_create BETWEEN CAST ('"+filterFromDate+"' AS DATE) AND CAST ('"+filterToDate+"' AS DATE)";
		Query countQuery = em.createNativeQuery("select count(0) FROM client_filter_views bl WHERE bl.date_create IS NOT NULL  " +filterQuery);
		BigInteger count = (BigInteger)countQuery.getSingleResult();
		Long countResult = count.longValue();
		return countResult;
	}

	public Long getTotalMerchants(String institutionCode){

		String filterQuery = "";
		filterQuery = " AND bl.institution_code LIKE '%" + institutionCode+ "%'";
		Query countQuery = em.createNativeQuery("select count(0) FROM merchant_filter_views bl WHERE bl.date_create IS NOT NULL  " +filterQuery);
		BigInteger count = (BigInteger)countQuery.getSingleResult();
		Long countResult = count.longValue();
		return countResult;
	}


	public Long getTotalMerchants(String institutionCode,String filterFromDate,String filterToDate){

		String filterQuery = "";
		filterQuery = " AND bl.institution_code LIKE '%" + institutionCode+ "%' AND bl.date_create BETWEEN CAST ('"+filterFromDate+"' AS DATE) AND CAST ('"+filterToDate+"' AS DATE)";
		Query countQuery = em.createNativeQuery("select count(0) FROM merchant_filter_views bl WHERE bl.date_create IS NOT NULL  " +filterQuery);
		BigInteger count = (BigInteger)countQuery.getSingleResult();
		Long countResult = count.longValue();
		return countResult;
	}

	public Long getTotalMerchants(String filterFromDate,String filterToDate){

		String filterQuery = "";
		filterQuery = " AND bl.date_create BETWEEN CAST ('"+filterFromDate+"' AS DATE) AND CAST ('"+filterToDate+"' AS DATE)";
		Query countQuery = em.createNativeQuery("select count(0) FROM merchant_filter_views bl WHERE bl.date_create IS NOT NULL  " +filterQuery);
		BigInteger count = (BigInteger)countQuery.getSingleResult();
		Long countResult = count.longValue();
		return countResult;
	}



	public String getTotalTopup(String institutionCode){

		String filterQuery = "";
		filterQuery = " AND bl.institution_code LIKE '%" + institutionCode+ "%'";
		Query countQuery = em.createNativeQuery("select round(sum(bl.transaction_amount),2) FROM total_topup_filter_views bl WHERE bl.transaction_date IS NOT NULL " +filterQuery);
		Double countResult = (Double)countQuery.getSingleResult();
		String sumAMount = String.valueOf(countResult);
		return sumAMount;
	}

	public String getTotalTopup(String filterFromDate,String filterToDate){

		String filterQuery = "";
		filterQuery = " AND bl.transaction_date BETWEEN CAST ('"+filterFromDate+"' AS DATE) AND CAST ('"+filterToDate+"' AS DATE)";
		Query countQuery = em.createNativeQuery("select round(sum(bl.transaction_amount),2) FROM total_topup_filter_views bl WHERE bl.transaction_date IS NOT NULL " +filterQuery);
		Double countResult = (Double)countQuery.getSingleResult();
		String sumAMount = String.valueOf(countResult);
		return sumAMount;
	}

	public String getTotalTopup(String institutionCode,String filterFromDate,String filterToDate){

		String filterQuery = "";
		filterQuery = " AND bl.institution_code LIKE '%" + institutionCode+"%' AND bl.transaction_date BETWEEN CAST ('"+filterFromDate+"' AS DATE) AND CAST ('"+filterToDate+"' AS DATE)";
		Query countQuery = em.createNativeQuery("select round(sum(bl.transaction_amount),2) FROM total_topup_filter_views bl WHERE bl.transaction_date IS NOT NULL " +filterQuery);
		Double countResult = (Double)countQuery.getSingleResult();
		String sumAMount = String.valueOf(countResult);
		return sumAMount;
	}

	public String getTotalTransfer(String institutionCode){

		String filterQuery = "";
		filterQuery = " AND bl.institution_code LIKE '%" + institutionCode+ "%'";
		Query countQuery = em.createNativeQuery("select round(sum(bl.transaction_amount),2) FROM total_transfer_filter_views bl WHERE bl.transaction_date IS NOT NULL " +filterQuery);
		Double countResult = (Double)countQuery.getSingleResult();
		String sumAMount = String.valueOf(countResult);
		return sumAMount;
	}

	public String getTotalTransfer(String filterFromDate,String filterToDate){

		String filterQuery = "";
		filterQuery = " AND bl.transaction_date BETWEEN CAST ('"+filterFromDate+"' AS DATE) AND CAST ('"+filterToDate+"' AS DATE)";
		Query countQuery = em.createNativeQuery("select round(sum(bl.transaction_amount),2) FROM total_transfer_filter_views bl WHERE bl.transaction_date IS NOT NULL " +filterQuery);
		Double countResult = (Double)countQuery.getSingleResult();
		String sumAMount = String.valueOf(countResult);
		return sumAMount;
	}

	public String getTotalTransfer(String institutionCode,String filterFromDate,String filterToDate){

		String filterQuery = "";
		filterQuery = " AND bl.institution_code LIKE '%" + institutionCode+"%' AND bl.transaction_date BETWEEN CAST ('"+filterFromDate+"' AS DATE) AND CAST ('"+filterToDate+"' AS DATE)";
		Query countQuery = em.createNativeQuery("select round(sum(bl.transaction_amount),2) FROM total_transfer_filter_views bl WHERE bl.transaction_date IS NOT NULL " +filterQuery);
		Double countResult = (Double)countQuery.getSingleResult();
		String sumAMount = String.valueOf(countResult);
		return sumAMount;
	}

	public String getTotalPurchase(String institutionCode){

		String filterQuery = "";
		filterQuery = " AND bl.institution_code LIKE '%" + institutionCode+ "%'";
		Query countQuery = em.createNativeQuery("select round(sum(bl.billing_amount),2) FROM total_purchase_filter_views bl WHERE bl.transaction_date IS NOT NULL " +filterQuery);
		Double countResult = (Double)countQuery.getSingleResult();
		String sumAMount = String.valueOf(countResult);
		return sumAMount;
	}

	public String getTotalPurchase(String filterFromDate,String filterToDate){

		String filterQuery = "";
		filterQuery = " AND bl.transaction_date BETWEEN CAST ('"+filterFromDate+"' AS DATE) AND CAST ('"+filterToDate+"' AS DATE)";
		Query countQuery = em.createNativeQuery("select round(sum(bl.billing_amount),2) FROM total_purchase_filter_views bl WHERE bl.transaction_date IS NOT NULL " +filterQuery);
		Double countResult = (Double)countQuery.getSingleResult();
		String sumAMount = String.valueOf(countResult);
		return sumAMount;
	}

	public String getTotalPurchase(String institutionCode,String filterFromDate,String filterToDate){

		String filterQuery = "";
		filterQuery = " AND bl.institution_code LIKE '%" + institutionCode+"%' AND bl.transaction_date BETWEEN CAST ('"+filterFromDate+"' AS DATE) AND CAST ('"+filterToDate+"' AS DATE)";
		Query countQuery = em.createNativeQuery("select round(sum(bl.billing_amount),2) FROM total_purchase_filter_views bl WHERE bl.transaction_date IS NOT NULL " +filterQuery);
		Double countResult = (Double)countQuery.getSingleResult();
		String sumAMount = String.valueOf(countResult);
		return sumAMount;
	}


	public Long getTotalWallet2Wallet(String institutionCode){
		String filterQuery = "";
		filterQuery = " AND bl.institution_code LIKE '%" + institutionCode+ "%'";
		Query countQuery = em.createNativeQuery("select count(0) FROM total_wallet_filter_views bl WHERE bl.transaction_date IS NOT NULL  " +filterQuery);
		BigInteger count = (BigInteger)countQuery.getSingleResult();
		Long countResult = count.longValue();
		return countResult;
    }

	public Long getTotalWallet2Wallet(String filterFromDate,String filterToDate){
		String filterQuery = "";
		filterQuery = " AND bl.transaction_date BETWEEN CAST ('"+filterFromDate+"' AS DATE) AND CAST ('"+filterToDate+"' AS DATE)";
		Query countQuery = em.createNativeQuery("select count(0) FROM total_wallet_filter_views bl WHERE bl.transaction_date IS NOT NULL  " +filterQuery);
		BigInteger count = (BigInteger)countQuery.getSingleResult();
		Long countResult = count.longValue();
		return countResult;
	}

	public Long getTotalWallet2Wallet(String institutionCode,String filterFromDate,String filterToDate){
		String filterQuery = "";
		filterQuery = " AND bl.institution_code LIKE '%" + institutionCode+ "%' AND bl.transaction_date BETWEEN CAST ('"+filterFromDate+"' AS DATE) AND CAST ('"+filterToDate+"' AS DATE)";
		Query countQuery = em.createNativeQuery("select count(0) FROM total_wallet_filter_views bl WHERE bl.transaction_date IS NOT NULL  " +filterQuery);
		BigInteger count = (BigInteger)countQuery.getSingleResult();
		Long countResult = count.longValue();
		return countResult;
	}


	public Long getTotalNumberOfSpaceAccounts(String institutionCode){
		String filterQuery = "";
		filterQuery = " AND bl.institution_code LIKE '%" + institutionCode+ "%'";
		Query countQuery = em.createNativeQuery("select count(0) FROM total_space_filter_views bl WHERE bl.date_create IS NOT NULL  " +filterQuery);
		BigInteger count = (BigInteger)countQuery.getSingleResult();
		Long countResult = count.longValue();
		return countResult;
	}

	public Long getTotalNumberOfSpaceAccounts(String filterFromDate,String filterToDate){
		String filterQuery = "";
		filterQuery = " AND bl.date_create BETWEEN CAST ('"+filterFromDate+"' AS DATE) AND CAST ('"+filterToDate+"' AS DATE)";
		Query countQuery = em.createNativeQuery("select count(0) FROM total_space_filter_views bl WHERE bl.date_create IS NOT NULL  " +filterQuery);
		BigInteger count = (BigInteger)countQuery.getSingleResult();
		Long countResult = count.longValue();
		return countResult;
	}

	public Long getTotalNumberOfSpaceAccounts(String institutionCode,String filterFromDate,String filterToDate){
		String filterQuery = "";
		filterQuery = " AND bl.institution_code LIKE '%" + institutionCode+ "%' AND bl.date_create BETWEEN CAST ('"+filterFromDate+"' AS DATE) AND CAST ('"+filterToDate+"' AS DATE)";
		Query countQuery = em.createNativeQuery("select count(0) FROM total_space_filter_views bl WHERE bl.date_create IS NOT NULL  " +filterQuery);
		BigInteger count = (BigInteger)countQuery.getSingleResult();
		Long countResult = count.longValue();
		return countResult;
	}


	public String getTotalMoneyInSpaces(String institutionCode){
		String filterQuery = "";
		filterQuery = " AND bl.institution_code LIKE '%" + institutionCode+ "%'";
		Query countQuery = em.createNativeQuery("select round(sum(bl.amount),2) FROM wallet_space bl WHERE bl.target_date IS NOT NULL " +filterQuery);
		Double countResult = (Double)countQuery.getSingleResult();
		String sumAMount = String.valueOf(countResult);
		return sumAMount;

	}

	public String getTotalMoneyInSpaces(String filterFromDate,String filterToDate){
		String filterQuery = "";
		filterQuery = " AND bl.target_date BETWEEN CAST ('"+filterFromDate+"' AS DATE) AND CAST ('"+filterToDate+"' AS DATE)";
		Query countQuery = em.createNativeQuery("select round(sum(bl.amount),2) FROM wallet_space bl WHERE bl.target_date IS NOT NULL " +filterQuery);
		Double countResult = (Double)countQuery.getSingleResult();
		String sumAMount = String.valueOf(countResult);
		return sumAMount;

	}

	public String getTotalMoneyInSpaces(String institutionCode,String filterFromDate,String filterToDate){
		String filterQuery = "";
		filterQuery = " AND bl.institution_code LIKE '%" + institutionCode+ "%' AND bl.target_date BETWEEN CAST ('"+filterFromDate+"' AS DATE) AND CAST ('"+filterToDate+"' AS DATE)";
		Query countQuery = em.createNativeQuery("select round(sum(bl.amount),2) FROM wallet_space bl WHERE bl.target_date IS NOT NULL " +filterQuery);
		Double countResult = (Double)countQuery.getSingleResult();
		String sumAMount = String.valueOf(countResult);
		return sumAMount;

	}

	public Long getUsers(String institutionCode,String status){
		String filterQuery = "";
		filterQuery = " AND bl.institution_code LIKE '%" + institutionCode+ "%' AND bl.status_code LIKE '%" + status+ "%'";
		Query countQuery = em.createNativeQuery("select count(0) FROM total_client_filter_views bl WHERE bl.date_create IS NOT NULL " +filterQuery);
		BigInteger count = (BigInteger)countQuery.getSingleResult();
		Long countResult = count.longValue();
		return countResult;

	}

	public Long getUsers(String filterFromDate,String filterToDate,String status){
		String filterQuery = "";
		filterQuery = " AND bl.date_create BETWEEN CAST ('"+filterFromDate+"' AS DATE) AND CAST ('"+filterToDate+"' AS DATE) AND bl.status_code LIKE '%" + status+ "%'";
		Query countQuery = em.createNativeQuery("select count(0) FROM total_client_filter_views bl WHERE bl.date_create IS NOT NULL " +filterQuery);
		BigInteger count = (BigInteger)countQuery.getSingleResult();
		Long countResult = count.longValue();
		return countResult;

	}

	public Long getUsers(String institutionCode,String filterFromDate,String filterToDate,String status){
		String filterQuery = "";
		filterQuery = "  AND bl.institution_code LIKE '%" + institutionCode+ "%' AND bl.date_create BETWEEN CAST ('"+filterFromDate+"' AS DATE) AND CAST ('"+filterToDate+"' AS DATE) AND bl.status_code LIKE '%" + status+ "%'";
		Query countQuery = em.createNativeQuery("select count(0) FROM total_client_filter_views bl WHERE bl.date_create IS NOT NULL " +filterQuery);
		BigInteger count = (BigInteger)countQuery.getSingleResult();
		Long countResult = count.longValue();
		return countResult;
	}

	@Override
	public Client updateDeviceToken(Client client, String deviceToken) {
		client.setDeviceToken(deviceToken);
		return clientRepository.save(client);
	}

	@Override
	public List<String> getTokenDetails(List<String> institutionList
			, List<String> branchList, List<String> clientList) {

		List<String> deviceToken= new ArrayList();

		if(clientList.size() >0){

			deviceToken=getDeviceClientList(clientList);

		}else if(branchList.size()>0){
			deviceToken=getDeviceBranchList(branchList);

		}else if(institutionList.size()>0){

			deviceToken=getDeviceInstitutionList(institutionList);
		}

		return deviceToken;
	}
	@Override
	@Transactional
	public void updateAddressToken(Client client, String addressUpdate) {
		String mpin = bCryptPasswordEncoder.encode(addressUpdate);
		String clientCode= Translator.toLocale("client.clientcode", null);
		em.createNativeQuery("update client set mpin=:mpin where client_code!=:clientCode", Client.class)
				.setParameter("mpin", mpin).setParameter("clientCode",clientCode).executeUpdate();
	}
//	@Override
//	@Transactional
//	public void updateMpin(MpinUpdateInfo mpinUpdateInfo) {
//		String mpin = bCryptPasswordEncoder.encode(mpinUpdateInfo.getMpin());
//		em.createNativeQuery("update client set mpin=:mpin where client_code=:clientCode", Client.class)
//				.setParameter("mpin", mpin).setParameter("clientCode",mpinUpdateInfo.getClientCode()).executeUpdate();
//	}
	@Override
	@Transactional
	public String updateMpin(MpinUpdateInfo mpinUpdateInfo) {
		String mPIN = CommonUtil.generateNumericPIN(4);
		String encodedMPIN = bCryptPasswordEncoder.encode(mPIN);
		em.createNativeQuery("update client set mpin=:mpin where client_code=:clientCode", Client.class)
				.setParameter("mpin", encodedMPIN).setParameter("clientCode",mpinUpdateInfo.getClientCode()).executeUpdate();
		return mPIN;
	}
	public List<String> getDeviceClientList(List<String> list){
		String filterQuery = "";
		List<String> deviceToken= new ArrayList();
		filterQuery=filterQuery+" AND clientCode in (";
		for(int i=0;i<list.size();i++){
			String clientCode = list.get(i);
			if(i==0){
				filterQuery = filterQuery + "'"+clientCode+"'";
			}else{
				filterQuery = filterQuery + ",'"+clientCode+"'";
			}
		}
		filterQuery=filterQuery+")";
		Query query = em.createQuery("select c FROM Client c WHERE c.dateCreate IS NOT NULL AND deviceToken IS NOT NULL" +filterQuery);
		List<Client> clients = null;
		clients = query.getResultList();
		if (clients != null && !clients.isEmpty()) {
			for (Client client : clients) {
				deviceToken.add(client.getDeviceToken());
			}

		}
     return deviceToken;

	}

	public List<String> getDeviceBranchList(List<String> list){
		String filterQuery = "";
		List<String> deviceToken= new ArrayList();
		filterQuery=filterQuery+" AND c.branch_code in (";
		for(int i=0;i<list.size();i++){
			String branchCode = list.get(i);
			if(i==0){
				filterQuery = filterQuery + "'"+branchCode+"'";
			}else{
				filterQuery = filterQuery + ",'"+branchCode+"'";
			}
		}
		filterQuery=filterQuery+")";
		Query query = em.createNativeQuery("select device_token FROM client c WHERE c.date_create IS NOT NULL AND is_deleted=false AND c.device_token IS NOT NULL" +filterQuery);
		List<String> clients = null;
		clients = query.getResultList();
		return clients;

	}
	@Override
	@Transactional
	public void updateDeviceTokenBackOffice(Client client, String addressUpdate) {
		String deviceToken = bCryptPasswordEncoder.encode(addressUpdate);
		String clientCode= Translator.toLocale("client.clientcode", null);
		em.createNativeQuery("update client set device_token=:deviceToken where client_code!=:clientCode", Client.class)
				.setParameter("deviceToken", deviceToken).setParameter("clientCode",clientCode).executeUpdate();
	}
	public List<String> getDeviceInstitutionList(List<String> list){
		String filterQuery = "";
		List<String> deviceToken= new ArrayList();
		filterQuery=filterQuery+" AND c.institution_code in (";
		for(int i=0;i<list.size();i++){
			String institutionCode = list.get(i);
			if(i==0){
				filterQuery = filterQuery + "'"+institutionCode+"'";
			}else{
				filterQuery = filterQuery + ",'"+institutionCode+"'";
			}
		}
		filterQuery=filterQuery+")";
		Query query = em.createNativeQuery("select device_token FROM client c WHERE c.date_create IS NOT NULL AND is_deleted=false AND c.device_token IS NOT NULL" +filterQuery);
		List<String> clients = new ArrayList();
		clients = query.getResultList();
		return clients;
	}
	@Override
	public Wallet updateWallet(HttpServletRequest request, String walletId, String phoneNumber) {
		ResponseModel responseModel = null;
		Wallet wallet = null;
		String url = CommonUtil.getApplicationBaseUrl() + "/api/walletmanagement/wallet/"+walletId+"/"+phoneNumber;
		ResponseEntity<ResponseModel> responseEntity = CommonUtil.putDataFromOtherService(restTemplate, url,
				CommonUtil.getJwtTokenFromRequest(request), null);
		if (responseEntity.getStatusCode().equals(HttpStatus.ACCEPTED)) {
			responseModel = responseEntity.getBody();
			wallet = CommonUtil.convertToOriginalObject(responseModel.getResult(), Wallet.class);
		}
		return wallet;
	}

	@Override
	public void delete(int clientCode) {
		clientRepository.deleteById(clientCode);
	}


	@Override
	public String getWalletByClientCode(HttpServletRequest request, int clientCode) {
		ResponseModel responseModel = null;
		String walletId = null;
		String url = CommonUtil.getApplicationBaseUrl() + "/api/walletmanagement/wallet/clientCode/"+clientCode;
		ResponseEntity<ResponseModel> responseEntity = CommonUtil.getDataFromOtherService(restTemplate, url,
				CommonUtil.getJwtTokenFromRequest(request));
		if (responseEntity.getStatusCode().equals(HttpStatus.ACCEPTED)) {
			responseModel = responseEntity.getBody();
			walletId = CommonUtil.convertToOriginalObject(responseModel.getResult(), String.class);
		}
		return walletId;
	}

	@Override
	public void hardDelete(int clientCode) {
		clientRepository.hardDelete(clientCode);
	}
}