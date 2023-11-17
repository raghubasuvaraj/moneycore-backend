package com.moneycore.bean;

import java.util.List;

import com.moneycore.entity.Client;

public class ClientResponse {

	private String firstName;
	private String lastName;
	private String middleName;
	private BranchInfo branchCode;
	private TitleListInfo titleCode;
	private CountryListInfoResponse nationalityCode;
	private LanguageListInfo languageCode;
	private StatusListInfo statusCode;
	private StatusReasonCodeInfoResponse statusReason;
	private ActivityListInfo activityCode;
	private CustomerSegmentListInfo customerSegment;
	private CurrencyListInfo currencyCode;
	private String prPhone1Code;
	private String prPhone1;
	private String prPhone2Code;
	private String prPhone2;
	private String prPhone3Code;
	private String prPhone3;
	private String prPhone4Code;
	private String prPhone4;
	private String password;
	private String email;
	private String dateOfBirth;
	private InstitutionListInfo institutionCode;
	private String gender;
	private List<ClientDocument> id;
	private List<ClientAddress> address;
	private String userCreate;
	private String userModif;
	private boolean isMerchant;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public BranchInfo getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(BranchInfo branchCode) {
		this.branchCode = branchCode;
	}

	public TitleListInfo getTitleCode() {
		return titleCode;
	}

	public void setTitleCode(TitleListInfo titleCode) {
		this.titleCode = titleCode;
	}

	public CountryListInfoResponse getNationalityCode() {
		return nationalityCode;
	}

	public void setNationalityCode(CountryListInfoResponse nationalityCode) {
		this.nationalityCode = nationalityCode;
	}

	public LanguageListInfo getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(LanguageListInfo languageCode) {
		this.languageCode = languageCode;
	}

	public StatusListInfo getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(StatusListInfo statusCode) {
		this.statusCode = statusCode;
	}

	public StatusReasonCodeInfoResponse getStatusReason() {
		return statusReason;
	}

	public void setStatusReason(StatusReasonCodeInfoResponse statusReason) {
		this.statusReason = statusReason;
	}

	public ActivityListInfo getActivityCode() {
		return activityCode;
	}

	public void setActivityCode(ActivityListInfo activityCode) {
		this.activityCode = activityCode;
	}

	public CustomerSegmentListInfo getCustomerSegment() {
		return customerSegment;
	}

	public void setCustomerSegment(CustomerSegmentListInfo customerSegment) {
		this.customerSegment = customerSegment;
	}

	public CurrencyListInfo getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(CurrencyListInfo currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getPrPhone1() {
		return prPhone1;
	}

	public void setPrPhone1(String prPhone1) {
		this.prPhone1 = prPhone1;
	}

	public String getPrPhone2() {
		return prPhone2;
	}

	public void setPrPhone2(String prPhone2) {
		this.prPhone2 = prPhone2;
	}

	public String getPrPhone3() {
		return prPhone3;
	}

	public void setPrPhone3(String prPhone3) {
		this.prPhone3 = prPhone3;
	}

	public String getPrPhone4() {
		return prPhone4;
	}

	public void setPrPhone4(String prPhone4) {
		this.prPhone4 = prPhone4;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public InstitutionListInfo getInstitutionCode() {
		return institutionCode;
	}

	public void setInstitutionCode(InstitutionListInfo institutionCode) {
		this.institutionCode = institutionCode;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public List<ClientDocument> getId() {
		return id;
	}

	public void setId(List<ClientDocument> id) {
		this.id = id;
	}

	public List<ClientAddress> getAddress() {
		return address;
	}

	public void setAddress(List<ClientAddress> address) {
		this.address = address;
	}

	public String getUserCreate() {
		return userCreate;
	}

	public void setUserCreate(String userCreate) {
		this.userCreate = userCreate;
	}

	public String getUserModif() {
		return userModif;
	}

	public void setUserModif(String userModif) {
		this.userModif = userModif;
	}

	public boolean getIsMerchant() {
		return isMerchant;
	}

	public void setMerchant(boolean isMerchant) {
		this.isMerchant = isMerchant;
	}

	public String getPrPhone1Code() {
		return prPhone1Code;
	}

	public void setPrPhone1Code(String prPhone1Code) {
		this.prPhone1Code = prPhone1Code;
	}

	public String getPrPhone2Code() {
		return prPhone2Code;
	}

	public void setPrPhone2Code(String prPhone2Code) {
		this.prPhone2Code = prPhone2Code;
	}

	public String getPrPhone3Code() {
		return prPhone3Code;
	}

	public void setPrPhone3Code(String prPhone3Code) {
		this.prPhone3Code = prPhone3Code;
	}

	public String getPrPhone4Code() {
		return prPhone4Code;
	}

	public void setPrPhone4Code(String prPhone4Code) {
		this.prPhone4Code = prPhone4Code;
	}

	public static ClientResponse createResponse(Client client) {
		ClientResponse clientResponse = new ClientResponse();
		clientResponse.setFirstName(client.getFirstName());
		clientResponse.setMiddleName(client.getMiddleName());
		clientResponse.setLastName(client.getLastName());
		if (client.getBranchList() != null) {
			BranchInfo branchInfo = BranchInfo.createResponse(client.getBranchList());
			clientResponse.setBranchCode(branchInfo);
		}
		if (client.getTitleCode() != null) {
			TitleListInfo titleListInfo = TitleListInfo.createSingleResponse(client.getTitleCode());
			clientResponse.setTitleCode(titleListInfo);
		}
		if (client.getCountryCode() != null) {
			CountryListInfoResponse countryListInfoResponse = CountryListInfoResponse
					.createSingleResponse(client.getCountryCode());
			clientResponse.setNationalityCode(countryListInfoResponse);
		}
		if (client.getLanguageCode() != null) {
			LanguageListInfo languageListInfo = LanguageListInfo.createSingleResponse(client.getLanguageCode());
			clientResponse.setLanguageCode(languageListInfo);
		}
		if (client.getStatusCode() != null) {
			StatusListInfo statusListInfo = StatusListInfo.createSingleResponse(client.getStatusCode());
			clientResponse.setStatusCode(statusListInfo);
		}
		if (client.getStatusReason() != null) {
			StatusReasonCodeInfoResponse statusReasonCodeInfoResponse = StatusReasonCodeInfoResponse
					.createSingleResponse(client.getStatusReason());
			clientResponse.setStatusReason(statusReasonCodeInfoResponse);
		}
		if (client.getActivityCode() != null) {
			ActivityListInfo activityListInfo = ActivityListInfo.createSingleResponse(client.getActivityCode());
			clientResponse.setActivityCode(activityListInfo);
		}
		if (client.getCustomerSegment() != null) {
			CustomerSegmentListInfo customerSegmentListInfo = CustomerSegmentListInfo
					.createSingleResponse(client.getCustomerSegment());
			clientResponse.setCustomerSegment(customerSegmentListInfo);
		}
		if (client.getCurrencyCode() != null) {
			CurrencyListInfo currencyListInfo = CurrencyListInfo.createSingleResponse(client.getCurrencyCode());
			clientResponse.setCurrencyCode(currencyListInfo);
		}
		clientResponse.setPrPhone1Code(client.getPrPhone1Code());
		clientResponse.setPrPhone1(client.getPrPhone1());
		clientResponse.setPrPhone2Code(client.getPrPhone2Code());
		clientResponse.setPrPhone2(client.getPrPhone2());
		clientResponse.setPrPhone3Code(client.getPrPhone3Code());
		clientResponse.setPrPhone3(client.getPrPhone3());
		clientResponse.setPrPhone4Code(client.getPrPhone4Code());
		clientResponse.setPrPhone4(client.getPrPhone4());
		clientResponse.setEmail(client.getEmail());
		clientResponse.setDateOfBirth(client.getDateOfBirth());
		if (client.getInstitutionList() != null) {
			InstitutionListInfo institutionListInfo = InstitutionListInfo
					.createSingleResponse(client.getInstitutionList());
			clientResponse.setInstitutionCode(institutionListInfo);
		}
		clientResponse.setGender(client.getGender());
		clientResponse.setUserCreate(client.getUserCreate());
		clientResponse.setUserModif(client.getUserModif());
		clientResponse.setMerchant(client.getIsMerchant());

		return clientResponse;
	}

}
