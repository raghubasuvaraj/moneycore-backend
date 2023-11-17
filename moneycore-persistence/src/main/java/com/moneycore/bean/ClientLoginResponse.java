package com.moneycore.bean;

import java.util.Date;
import java.util.List;
import com.moneycore.entity.CountryList;
import com.moneycore.entity.MccList;
import com.moneycore.entity.NationalityList;
import com.moneycore.entity.ServiceList;
import com.moneycore.entity.WalletBalance;

public class ClientLoginResponse {

	private String jwtToken;
	private String accountNumber;
	private String entityCode;
	private String walletTypeId;
	private String walletTypeName;
	private String currencyCode;
	private int clientCode;
	private String firstName;
	private String lastName;
	private String middleName;
	private String email;
	private String prPhone1Code;
	private String prPhone1;
	private String institutionCode;
	private Date dateCreate;
	private CountryList country;
	private NationalityList nationality;
	private String statusCode;
	private boolean isMerchant;
	private String walletId;
	private String mPin;
	private String entityName;
	private String licenceNo;
	private MccList mccList;
	private List<ServiceList> serviceList;

	public String getJwtToken() {
		return jwtToken;
	}

	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getEntityCode() {
		return entityCode;
	}

	public void setEntityCode(String entityCode) {
		this.entityCode = entityCode;
	}

	public String getWalletTypeId() {
		return walletTypeId;
	}

	public void setWalletTypeId(String walletTypeId) {
		this.walletTypeId = walletTypeId;
	}

	public String getWalletTypeName() {
		return walletTypeName;
	}

	public void setWalletTypeName(String walletTypeName) {
		this.walletTypeName = walletTypeName;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public int getClientCode() {
		return clientCode;
	}

	public void setClientCode(int clientCode) {
		this.clientCode = clientCode;
	}

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPrPhone1Code() {
		return prPhone1Code;
	}

	public void setPrPhone1Code(String prPhone1Code) {
		this.prPhone1Code = prPhone1Code;
	}

	public String getPrPhone1() {
		return prPhone1;
	}

	public void setPrPhone1(String prPhone1) {
		this.prPhone1 = prPhone1;
	}

	public String getInstitutionCode() {
		return institutionCode;
	}

	public void setInstitutionCode(String institutionCode) {
		this.institutionCode = institutionCode;
	}

	public Date getDateCreate() {
		return dateCreate;
	}

	public void setDateCreate(Date dateCreate) {
		this.dateCreate = dateCreate;
	}

	public CountryList getCountry() {
		return country;
	}

	public void setCountry(CountryList country) {
		this.country = country;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public boolean isMerchant() {
		return isMerchant;
	}

	public void setIsMerchant(boolean isMerchant) {
		this.isMerchant = isMerchant;
	}

	public String getWalletId() {
		return walletId;
	}

	public void setWalletId(String walletId) {
		this.walletId = walletId;
	}

	public String getmPin() {
		return mPin;
	}

	public void setmPin(String mPin) {
		this.mPin = mPin;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public String getLicenceNo() {
		return licenceNo;
	}

	public void setLicenceNo(String licenceNo) {
		this.licenceNo = licenceNo;
	}

	public MccList getMccList() {
		return mccList;
	}

	public void setMccList(MccList mccList) {
		this.mccList = mccList;
	}

	public List<ServiceList> getServiceList() {
		return serviceList;
	}

	public void setServiceList(List<ServiceList> serviceList) {
		this.serviceList = serviceList;
	}

	public NationalityList getNationality() {
		return nationality;
	}

	public void setNationality(NationalityList nationality) {
		this.nationality = nationality;
	}

	public static ClientLoginResponse createResponse(WalletBalance walletBalance) {
		ClientLoginResponse clientLoginResponse = new ClientLoginResponse();
		clientLoginResponse.setAccountNumber(walletBalance.getAccountNumber().getAccountNumber());
		clientLoginResponse.setEntityCode(walletBalance.getAccountNumber().getEntityCode());
		clientLoginResponse.setWalletTypeId(walletBalance.getWalletId().getWalletType().getWalletTypeId());
		clientLoginResponse.setWalletTypeName(walletBalance.getWalletId().getWalletType().getWalletTypeName());
		clientLoginResponse.setCurrencyCode(walletBalance.getClientCode().getCurrencyCode().getCurrencyCode());
		clientLoginResponse.setClientCode(walletBalance.getClientCode().getClientCode());
		clientLoginResponse.setFirstName(walletBalance.getClientCode().getFirstName());
		clientLoginResponse.setLastName(walletBalance.getClientCode().getLastName());
		clientLoginResponse.setMiddleName(walletBalance.getClientCode().getMiddleName());
		clientLoginResponse.setEmail(walletBalance.getClientCode().getEmail());
		clientLoginResponse.setPrPhone1Code(walletBalance.getClientCode().getPrPhone1Code());
		clientLoginResponse.setPrPhone1(walletBalance.getClientCode().getPrPhone1());
		clientLoginResponse.setInstitutionCode(walletBalance.getClientCode().getInstitutionList().getInstitutionCode());
		clientLoginResponse.setDateCreate(walletBalance.getClientCode().getDateCreate());
		if(walletBalance.getClientCode().getCountryCode() != null)
			clientLoginResponse.setCountry(walletBalance.getClientCode().getCountryCode());
		if(walletBalance.getClientCode().getNationalityCode() != null)
			clientLoginResponse.setNationality(walletBalance.getClientCode().getNationalityCode());
		if(walletBalance.getClientCode().getStatusCode() != null)
			clientLoginResponse.setStatusCode(walletBalance.getClientCode().getStatusCode().getStatusCode());
		clientLoginResponse.setIsMerchant(walletBalance.getClientCode().getIsMerchant());
		clientLoginResponse.setWalletId(walletBalance.getWalletId().getWalletId());
		clientLoginResponse.setmPin(walletBalance.getClientCode().getMpin());

		if(walletBalance.getClientCode().getIsMerchant()){
			clientLoginResponse.setEntityName(walletBalance.getClientCode().getEntityName());
			clientLoginResponse.setLicenceNo(walletBalance.getClientCode().getLicenceNo());
			clientLoginResponse.setMccList(walletBalance.getClientCode().getMccCode());
			clientLoginResponse.setMccList(walletBalance.getClientCode().getMccCode());
		}
		return clientLoginResponse;
	}

}
