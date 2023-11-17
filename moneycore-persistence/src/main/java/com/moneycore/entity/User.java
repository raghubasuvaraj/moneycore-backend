package com.moneycore.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import com.moneycore.entityListener.UserListener;
import org.springframework.format.annotation.DateTimeFormat;


/**
 * The persistent class for the users database table.
 * 
 */
@Entity
@Table(name="users")
@NamedQuery(query = "select u from User u where user_create IS NOT NULL order by u.userId desc", name = "User.query_find_all_users")
@EntityListeners(UserListener.class)
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4192945032139927228L;

	@Id
	@Column(name="user_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userId;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name="account_end_date")
	private Date accountEndDate;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name="account_start_date")
	private Date accountStartDate;

	@Column(name="activ_email")
	private String activEmail;

	@Column(name="bank_code_access_list")
	private String bankCodeAccessList;

	@Column(name="country_fk")
	private String countryFk;

	
	@Column(name="date_create")
	private Date dateCreate;

	@Temporal(TemporalType.DATE)
	@Column(name="date_modif")
	private Date dateModif;
	
	@Column(name="email")
	private @NotBlank String email;

	@Column(name="employe_number")
	private String employeNumber;

	@Column(name="ip_address_access")
	private String ipAddressAccess;

	@Column(name="job_title")
	private String jobTitle;

	@Column(name="language")
	private String language;

	@Temporal(TemporalType.DATE)
	@Column(name="last_date_connect")
	private Date lastDateConnect;
	
	@Column(name="password")
	 private String password;

	@Column(name="phone_number_code")
	private String phoneNumberCode;

	@Column(name="phone_number")
	private String phoneNumber;

	@OneToOne
	@JoinColumn(name="profile_fk", referencedColumnName = "profile_id")
	private Profile profileFk;

	@Column(name="sensitive_operation_record")
	private String sensitiveOperationRecord;

	@Column(name="status")
	private String status;

	@Column(name="timer_browser_discount")
	private double timerBrowserDiscount;

	@Column(name="user_create")
	private String userCreate;

	@Column(name="user_modif")
	private String userModif;

	@Column(name="user_name")
	private String userName;

	
	@Column(name="institution_fk")
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

	public Profile getProfileFk() {
		return profileFk;
	}

	public void setProfileFk(Profile profileFk) {
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


	/**
	 * @return the institutionList
	 */

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(email, user.email) &&
                Objects.equals(password, user.password);
    }
}