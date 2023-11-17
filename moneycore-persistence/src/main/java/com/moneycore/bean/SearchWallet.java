package com.moneycore.bean;

public class SearchWallet {

	private String institutionCode;
	private String walletId;
	private int clientCode;
	private String firstName;
	private String familyName;
	private String legalId;
	private String statusCode;
	private String prPhone1;
	private int pageNo;
	private int pageSize;
	private String isMerchant;
	private String statusFromDate;
	private String getStatusToDate;

	public SearchWallet() {

	}

	public SearchWallet(String institutionCode, String walletId, int clientCode, String firstName, String familyName,
			String legalId, String statusCode, String prPhone1, int pageNo, int pageSize, String isMerchant, String statusFromDate, String getStatusToDate) {
		this.institutionCode = institutionCode;
		this.walletId = walletId;
		this.clientCode = clientCode;
		this.firstName = firstName;
		this.familyName = familyName;
		this.legalId = legalId;
		this.statusCode = statusCode;
		this.prPhone1 = prPhone1;
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.isMerchant = isMerchant;
		this.statusFromDate = statusFromDate;
		this.getStatusToDate = getStatusToDate;
	}

	public String getInstitutionCode() {
		return institutionCode;
	}

	public void setInstitutionCode(String institutionCode) {
		this.institutionCode = institutionCode;
	}

	public String getWalletId() {
		return walletId;
	}

	public void setWalletId(String walletId) {
		this.walletId = walletId;
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

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public String getLegalId() {
		return legalId;
	}

	public void setLegalId(String legalId) {
		this.legalId = legalId;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getPrPhone1() {
		return prPhone1;
	}

	public void setPrPhone1(String prPhone1) {
		this.prPhone1 = prPhone1;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getIsMerchant() {
		return isMerchant;
	}

	public void setIsMerchant(String isMerchant) {
		this.isMerchant = isMerchant;
	}

	public String getStatusFromDate() {
		return statusFromDate;
	}

	public void setStatusFromDate(String statusFromDate) {
		this.statusFromDate = statusFromDate;
	}

	public String getGetStatusToDate() {
		return getStatusToDate;
	}

	public void setGetStatusToDate(String getStatusToDate) {
		this.getStatusToDate = getStatusToDate;
	}
}
