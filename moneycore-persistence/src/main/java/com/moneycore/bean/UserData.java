package com.moneycore.bean;

import java.util.Date;

import com.moneycore.entity.User;

public class UserData {
	private int userId;

	private Date accountEndDate;

	private Date accountStartDate;

	private String activEmail;

	private String bankCodeAccessList;

	private String countryFk;

	private Date dateCreate;

	private Date dateModif;

	private String email;

	private String employeNumber;

	private String ipAddressAccess;

	private String jobTitle;

	private String language;

	private Date lastDateConnect;

	private String password;

	private String phoneNumberCode;

	private String phoneNumber;

	private int profileFk;

	private String sensitiveOperationRecord;

	private String status;

	private double timerBrowserDiscount;

	private String userCreate;

	private String userModif;

	private String userName;

	private String institutionFk;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Date getAccountEndDate() {
		return accountEndDate;
	}

	public void setAccountEndDate(Date accountEndDate) {
		this.accountEndDate = accountEndDate;
	}

	public Date getAccountStartDate() {
		return accountStartDate;
	}

	public void setAccountStartDate(Date accountStartDate) {
		this.accountStartDate = accountStartDate;
	}

	public String getActivEmail() {
		return activEmail;
	}

	public void setActivEmail(String activEmail) {
		this.activEmail = activEmail;
	}

	public String getBankCodeAccessList() {
		return bankCodeAccessList;
	}

	public void setBankCodeAccessList(String bankCodeAccessList) {
		this.bankCodeAccessList = bankCodeAccessList;
	}

	public String getCountryFk() {
		return countryFk;
	}

	public void setCountryFk(String countryFk) {
		this.countryFk = countryFk;
	}

	public Date getDateCreate() {
		return dateCreate;
	}

	public void setDateCreate(Date dateCreate) {
		this.dateCreate = dateCreate;
	}

	public Date getDateModif() {
		return dateModif;
	}

	public void setDateModif(Date dateModif) {
		this.dateModif = dateModif;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmployeNumber() {
		return employeNumber;
	}

	public void setEmployeNumber(String employeNumber) {
		this.employeNumber = employeNumber;
	}

	public String getIpAddressAccess() {
		return ipAddressAccess;
	}

	public void setIpAddressAccess(String ipAddressAccess) {
		this.ipAddressAccess = ipAddressAccess;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Date getLastDateConnect() {
		return lastDateConnect;
	}

	public void setLastDateConnect(Date lastDateConnect) {
		this.lastDateConnect = lastDateConnect;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhoneNumberCode() {
		return phoneNumberCode;
	}

	public void setPhoneNumberCode(String phoneNumberCode) {
		this.phoneNumberCode = phoneNumberCode;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public int getProfileFk() {
		return profileFk;
	}

	public void setProfileFk(int profileFk) {
		this.profileFk = profileFk;
	}

	public String getSensitiveOperationRecord() {
		return sensitiveOperationRecord;
	}

	public void setSensitiveOperationRecord(String sensitiveOperationRecord) {
		this.sensitiveOperationRecord = sensitiveOperationRecord;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public double getTimerBrowserDiscount() {
		return timerBrowserDiscount;
	}

	public void setTimerBrowserDiscount(double timerBrowserDiscount) {
		this.timerBrowserDiscount = timerBrowserDiscount;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getInstitutionFk() {
		return institutionFk;
	}

	public void setInstitutionFk(String institutionFk) {
		this.institutionFk = institutionFk;
	}

	public static UserData getUser(User user) {
		UserData userData = new UserData();
		userData.setUserId(user.getUserId());
		userData.setAccountStartDate(user.getAccountStartDate());
		userData.setAccountEndDate(user.getAccountEndDate());
		userData.setActivEmail(user.getActivEmail());
		userData.setBankCodeAccessList(user.getBankCodeAccessList());
		userData.setCountryFk(user.getCountryFk());
		userData.setDateCreate(user.getDateCreate());
		userData.setDateModif(user.getDateModif());
		userData.setEmail(user.getEmail());
		userData.setEmployeNumber(user.getEmployeNumber());
		userData.setIpAddressAccess(user.getIpAddressAccess());
		userData.setJobTitle(user.getJobTitle());
		userData.setLanguage(user.getLanguage());
		userData.setLastDateConnect(user.getLastDateConnect());
		userData.setPassword(user.getPassword());
		userData.setPhoneNumberCode(user.getPhoneNumberCode());
		userData.setPhoneNumber(user.getPhoneNumber());
		if (user.getProfileFk() != null) {
			userData.setProfileFk(user.getProfileFk().getProfileId());
		}
		userData.setSensitiveOperationRecord(user.getSensitiveOperationRecord());
		userData.setStatus(user.getStatus());
		userData.setTimerBrowserDiscount(user.getTimerBrowserDiscount());
		userData.setUserCreate(user.getUserCreate());
		userData.setUserModif(user.getUserModif());
		userData.setUserName(user.getUserName());
		userData.setInstitutionFk(user.getInstitutionFk());
		return userData;
	}
}
