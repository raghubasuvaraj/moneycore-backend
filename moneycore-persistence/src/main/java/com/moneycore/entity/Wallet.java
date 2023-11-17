package com.moneycore.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.moneycore.bean.WalletBalanceInfo;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "wallet")
@SQLDelete(sql = "UPDATE wallet SET is_deleted = true WHERE wallet_id=?")
@Where(clause = "is_deleted=false")
public class Wallet implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "wallet_id")
	private String walletId;

	@OneToOne
	@JoinColumn(name = "client_code")
	private Client clientCode;

	@ManyToOne
	@JoinColumn(name = "institution_code")
	private InstitutionList institutionCode;
	@ManyToOne
	@JoinColumn(name = "branch_code")
	private Branch branchCode;
	@ManyToOne
	@JoinColumn(name = "control_profile", referencedColumnName = "control_index")
	private DomainControl controlProfile;
	@ManyToOne
	@JoinColumn(name = "pricing_profile", referencedColumnName = "pricing_index")
	private PricingProfile pricingProfile;
	@ManyToOne
	@JoinColumn(name = "wallet_type", referencedColumnName = "wallet_type_id")
	private WalletType walletType;
	@ManyToOne
	@JoinColumn(name = "title_code")
	private TitleList titleCode;
	@Column(name = "gender")
	private String gender;
	@Column(name = "family_name")
	private String familyName;
	@Column(name = "first_name")
	private String firstName;
	@Column(name = "middle_name")
	private String middelName;
	@Column(name = "birth_date")
	private Date birthDate;
	@Column(name = "document_code")
	private String documentCode;
	@Column(name = "issuing_docum_date")
	private Date issuingDocumentDate;
	@Column(name = "legal_id")
	private String legalId;
	@Column(name = "document_code1")
	private String documentCode1;
	@Column(name = "issuing_docum_date_1")
	private Date issuingDocuemntDate1;
	@Column(name = "legal_id_1")
	private String legalId1;
	@Column(name = "document_code2")
	private String documentCode2;
	@Column(name = "issuing_docum_date_2")
	private Date issuingDocumentDate2;
	@Column(name = "legal_id_2")
	private String legalId2;
	@Column(name = "document_code3")
	private String documentCode3;
	@Column(name = "issuing_docum_date_3")
	private Date issuingDocumentDate3;
	@Column(name = "legal_id_3")
	private String legalId3;
	@ManyToOne
	@JoinColumn(name = "national_code", referencedColumnName = "nationality_id")
	private NationalityList nationalCode;
	@ManyToOne
	@JoinColumn(name = "language_code")
	private LanguageList languageCode;
	@Column(name = "pr_line_1")
	private String prLine1;
	@Column(name = "pr_line_2")
	private String prLine2;
	@Column(name = "pr_line_3")
	private String prLine3;
	@Column(name = "pr_line_4")
	private String prLine4;
	@ManyToOne
	@JoinColumn(name = "pr_region_code", referencedColumnName = "region_code")
	private RegionList prRegionCode;
	@Column(name = "pr_region_name")
	private String prRegionName;
	@ManyToOne
	@JoinColumn(name = "pr_country_code", referencedColumnName = "country_code")
	private CountryList prCountryCode;
	@ManyToOne
	@JoinColumn(name = "pr_city_code", referencedColumnName = "city_code")
	private CityList prCityCode;
	@Column(name = "pr_email")
	private String prEmail;
	@Column(name = "pr_url")
	private String prUrl;
	@Column(name = "pr_phone_1_code", length = 4)
    private String prPhone1Code;
	@Column(name = "pr_phone_1")
	private String prPhone1;
	@Column(name = "pr_phone_1_type")
	private String prPhone1Type;
	@Column(name = "pr_phone_2_code", length = 4)
    private String prPhone2Code;
	@Column(name = "pr_phone_2")
	private String prPhone2;
	@Column(name = "pr_phone_2_type")
	private String prPhone2Type;
	@Column(name = "pr_phone_3_code", length = 4)
    private String prPhone3Code;
	@Column(name = "pr_phone_3")
	private String prPhone3;
	@Column(name = "pr_phone_3_type")
	private String prPhone3Type;
	@Column(name = "pr_phone_4_code", length = 4)
    private String prPhone4Code;
	@Column(name = "pr_phone_4")
	private String prPhone4;
	@Column(name = "pr_phone_4_type")
	private String prPhone4Type;
	@Column(name = "pr_city_name")
	private String prCityName;
	@ManyToOne
	@JoinColumn(name = "activity_code")
	private ActivityList activityCode;
	@ManyToOne
	@JoinColumn(name = "customer_segment_index", referencedColumnName = "customer_segment_code")
	private CustomerSegmentList customerSegmentIndex;
	@Column(name = "application_date")
	private Date applicationDate;
	@Column(name = "activation_flag")
	private Boolean activationFlag;
	@Column(name = "activation_date")
	private Date activationDate;
	@Column(name = "deactivation_date")
	private Date deactivationDate;
	@Column(name = "security_chk_datap_1")
	private String securityCheckDatap1;
	@Column(name = "security_chk_datap_2")
	private String securityCheckDatap2;
	@Column(name = "security_chk_datap_3")
	private String securityCheckDatap3;
	@Column(name = "security_chk_datap_4")
	private String securityCheckDatap4;
	@ManyToOne
	@JoinColumn(name = "status_code")
	private StatusList statusCode;
	@Column(name = "status_date")
	private Date statusDate;
	@ManyToOne
    @JoinColumn(name = "status_reason", referencedColumnName = "status_reason_code")
	private StatusReasonCode statusReason;
	@Column(name = "cancellation_request_date")
	private Date canncellationRequestDate;
	@Column(name = "cancellaion_user_code")
	private String cancellationUserCode;
	@Column(name = "private_data_1")
	private String privateData1;
	@Column(name = "private_data_2")
	private String privateData2;
	@Column(name = "private_data_3")
	private String privateData3;
	@Column(name = "private_data_4")
	private String privateData4;
	@Column(name = "private_data_5")
	private Date privateData5;
	@Column(name = "private_data_6")
	private Date privateData6;
	@Column(name = "private_data_7")
	private Date privateData7;
	@Column(name = "private_data_8")
	private Date privateData8;
	@Column(name = "private_data_9")
	private Long privateData9;
	@Column(name = "private_data_10")
	private Long privateData10;
	@Column(name = "private_data_11")
	private Long privateData11;
	@Column(name = "private_data_12")
	private Long privateData12;
	@Column(name = "private_tlv_data")
	private String privateTlvDate;
	@Column(name = "sensitive_operation_record")
	private String sensitiveOperationRecord;
	@Column(name = "user_create")
	private String userCreate;
	@Column(name = "date_create")
	private Date dateCreate;
	@Column(name = "user_modif")
	private String userModified;
	@Column(name = "date_modif")
	private Date dateModified;
	@Lob
	@Column(name = "qr_code", columnDefinition = "LONGBLOB")
	private byte[] qrCode;

	@Transient
	WalletBalanceInfo balance;

	@Transient
	private String currencyCode;

	@Transient
	private String accountTypeCode;
	@Transient
	private String accountTypeName;

	@Transient
	private String statusCodeT;
	@Transient
	private String walletTypeT;
	@Transient
	private String controlProfileT;
	@Transient
	private String pricingProfileT;

	@Column(name = "is_deleted")
	private boolean isDeleted = Boolean.FALSE;

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean deleted) {
		isDeleted = deleted;
	}

	public String getWalletId() {
		return walletId;
	}

	public void setWalletId(String walletId) {
		this.walletId = walletId;
	}

	public Client getClientCode() {
		return clientCode;
	}

	public void setClientCode(Client clientCode) {
		this.clientCode = clientCode;
	}

	public InstitutionList getInstitutionCode() {
		return institutionCode;
	}

	public void setInstitutionCode(InstitutionList institutionCode) {
		this.institutionCode = institutionCode;
	}

	public Branch getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(Branch branchCode) {
		this.branchCode = branchCode;
	}

	public DomainControl getControlProfile() {
		return controlProfile;
	}

	public void setControlProfile(DomainControl controlProfile) {
		this.controlProfile = controlProfile;
	}

	public PricingProfile getPricingProfile() {
		return pricingProfile;
	}

	public void setPricingProfile(PricingProfile pricingProfile) {
		this.pricingProfile = pricingProfile;
	}

	public WalletType getWalletType() {
		return walletType;
	}

	public void setWalletType(WalletType walletType) {
		this.walletType = walletType;
	}

	public TitleList getTitleCode() {
		return titleCode;
	}

	public void setTitleCode(TitleList titleCode) {
		this.titleCode = titleCode;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddelName() {
		return middelName;
	}

	public void setMiddelName(String middelName) {
		this.middelName = middelName;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getDocumentCode() {
		return documentCode;
	}

	public void setDocumentCode(String documentCode) {
		this.documentCode = documentCode;
	}

	public Date getIssuingDocumentDate() {
		return issuingDocumentDate;
	}

	public void setIssuingDocumentDate(Date issuingDocumentDate) {
		this.issuingDocumentDate = issuingDocumentDate;
	}

	public String getLegalId() {
		return legalId;
	}

	public void setLegalId(String legalId) {
		this.legalId = legalId;
	}

	public String getDocumentCode1() {
		return documentCode1;
	}

	public void setDocumentCode1(String documentCode1) {
		this.documentCode1 = documentCode1;
	}

	public Date getIssuingDocuemntDate1() {
		return issuingDocuemntDate1;
	}

	public void setIssuingDocuemntDate1(Date issuingDocuemntDate1) {
		this.issuingDocuemntDate1 = issuingDocuemntDate1;
	}

	public String getLegalId1() {
		return legalId1;
	}

	public void setLegalId1(String legalId1) {
		this.legalId1 = legalId1;
	}

	public String getDocumentCode2() {
		return documentCode2;
	}

	public void setDocumentCode2(String documentCode2) {
		this.documentCode2 = documentCode2;
	}

	public Date getIssuingDocumentDate2() {
		return issuingDocumentDate2;
	}

	public void setIssuingDocumentDate2(Date issuingDocumentDate2) {
		this.issuingDocumentDate2 = issuingDocumentDate2;
	}

	public String getLegalId2() {
		return legalId2;
	}

	public void setLegalId2(String legalId2) {
		this.legalId2 = legalId2;
	}

	public String getDocumentCode3() {
		return documentCode3;
	}

	public void setDocumentCode3(String documentCode3) {
		this.documentCode3 = documentCode3;
	}

	public Date getIssuingDocumentDate3() {
		return issuingDocumentDate3;
	}

	public void setIssuingDocumentDate3(Date issuingDocumentDate3) {
		this.issuingDocumentDate3 = issuingDocumentDate3;
	}

	public String getLegalId3() {
		return legalId3;
	}

	public void setLegalId3(String legalId3) {
		this.legalId3 = legalId3;
	}

	public NationalityList getNationalCode() {
		return nationalCode;
	}

	public void setNationalCode(NationalityList nationalCode) {
		this.nationalCode = nationalCode;
	}

	public LanguageList getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(LanguageList languageCode) {
		this.languageCode = languageCode;
	}

	public String getPrLine1() {
		return prLine1;
	}

	public void setPrLine1(String prLine1) {
		this.prLine1 = prLine1;
	}

	public String getPrLine2() {
		return prLine2;
	}

	public void setPrLine2(String prLine2) {
		this.prLine2 = prLine2;
	}

	public String getPrLine3() {
		return prLine3;
	}

	public void setPrLine3(String prLine3) {
		this.prLine3 = prLine3;
	}

	public String getPrLine4() {
		return prLine4;
	}

	public void setPrLine4(String prLine4) {
		this.prLine4 = prLine4;
	}

	public RegionList getPrRegionCode() {
		return prRegionCode;
	}

	public void setPrRegionCode(RegionList prRegionCode) {
		this.prRegionCode = prRegionCode;
	}

	public String getPrRegionName() {
		return prRegionName;
	}

	public void setPrRegionName(String prRegionName) {
		this.prRegionName = prRegionName;
	}

	public CountryList getPrCountryCode() {
		return prCountryCode;
	}

	public void setPrCountryCode(CountryList prCountryCode) {
		this.prCountryCode = prCountryCode;
	}

	public CityList getPrCityCode() {
		return prCityCode;
	}

	public void setPrCityCode(CityList prCityCode) {
		this.prCityCode = prCityCode;
	}

	public String getPrEmail() {
		return prEmail;
	}

	public void setPrEmail(String prEmail) {
		this.prEmail = prEmail;
	}

	public String getPrUrl() {
		return prUrl;
	}

	public void setPrUrl(String prUrl) {
		this.prUrl = prUrl;
	}

	public String getPrPhone1() {
		return prPhone1;
	}

	public void setPrPhone1(String prPhone1) {
		this.prPhone1 = prPhone1;
	}

	public String getPrPhone1Type() {
		return prPhone1Type;
	}

	public void setPrPhone1Type(String prPhone1Type) {
		this.prPhone1Type = prPhone1Type;
	}

	public String getPrPhone2() {
		return prPhone2;
	}

	public void setPrPhone2(String prPhone2) {
		this.prPhone2 = prPhone2;
	}

	public String getPrPhone2Type() {
		return prPhone2Type;
	}

	public void setPrPhone2Type(String prPhone2Type) {
		this.prPhone2Type = prPhone2Type;
	}

	public String getPrPhone3() {
		return prPhone3;
	}

	public void setPrPhone3(String prPhone3) {
		this.prPhone3 = prPhone3;
	}

	public String getPrPhone3Type() {
		return prPhone3Type;
	}

	public void setPrPhone3Type(String prPhone3Type) {
		this.prPhone3Type = prPhone3Type;
	}

	public String getPrPhone4() {
		return prPhone4;
	}

	public void setPrPhone4(String prPhone4) {
		this.prPhone4 = prPhone4;
	}

	public String getPrPhone4Type() {
		return prPhone4Type;
	}

	public void setPrPhone4Type(String prPhone4Type) {
		this.prPhone4Type = prPhone4Type;
	}

	public String getPrCityName() {
		return prCityName;
	}

	public void setPrCityName(String prCityName) {
		this.prCityName = prCityName;
	}

	public ActivityList getActivityCode() {
		return activityCode;
	}

	public void setActivityCode(ActivityList activityCode) {
		this.activityCode = activityCode;
	}

	public CustomerSegmentList getCustomerSegmentIndex() {
		return customerSegmentIndex;
	}

	public void setCustomerSegmentIndex(CustomerSegmentList customerSegmentIndex) {
		this.customerSegmentIndex = customerSegmentIndex;
	}

	public Date getApplicationDate() {
		return applicationDate;
	}

	public void setApplicationDate(Date applicationDate) {
		this.applicationDate = applicationDate;
	}

	public Boolean getActivationFlag() {
		return activationFlag;
	}

	public void setActivationFlag(Boolean activationFlag) {
		this.activationFlag = activationFlag;
	}

	public Date getActivationDate() {
		return activationDate;
	}

	public void setActivationDate(Date activationDate) {
		this.activationDate = activationDate;
	}

	public Date getDeactivationDate() {
		return deactivationDate;
	}

	public void setDeactivationDate(Date deactivationDate) {
		this.deactivationDate = deactivationDate;
	}

	public String getSecurityCheckDatap1() {
		return securityCheckDatap1;
	}

	public void setSecurityCheckDatap1(String securityCheckDatap1) {
		this.securityCheckDatap1 = securityCheckDatap1;
	}

	public String getSecurityCheckDatap2() {
		return securityCheckDatap2;
	}

	public void setSecurityCheckDatap2(String securityCheckDatap2) {
		this.securityCheckDatap2 = securityCheckDatap2;
	}

	public String getSecurityCheckDatap3() {
		return securityCheckDatap3;
	}

	public void setSecurityCheckDatap3(String securityCheckDatap3) {
		this.securityCheckDatap3 = securityCheckDatap3;
	}

	public String getSecurityCheckDatap4() {
		return securityCheckDatap4;
	}

	public void setSecurityCheckDatap4(String securityCheckDatap4) {
		this.securityCheckDatap4 = securityCheckDatap4;
	}

	public StatusList getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(StatusList statusCode) {
		this.statusCode = statusCode;
	}

	public Date getStatusDate() {
		return statusDate;
	}

	public void setStatusDate(Date statusDate) {
		this.statusDate = statusDate;
	}

	public StatusReasonCode getStatusReason() {
		return statusReason;
	}

	public void setStatusReason(StatusReasonCode statusReason) {
		this.statusReason = statusReason;
	}

	public Date getCanncellationRequestDate() {
		return canncellationRequestDate;
	}

	public void setCanncellationRequestDate(Date canncellationRequestDate) {
		this.canncellationRequestDate = canncellationRequestDate;
	}

	public String getCancellationUserCode() {
		return cancellationUserCode;
	}

	public void setCancellationUserCode(String cancellationUserCode) {
		this.cancellationUserCode = cancellationUserCode;
	}

	public String getPrivateData1() {
		return privateData1;
	}

	public void setPrivateData1(String privateData1) {
		this.privateData1 = privateData1;
	}

	public String getPrivateData2() {
		return privateData2;
	}

	public void setPrivateData2(String privateData2) {
		this.privateData2 = privateData2;
	}

	public String getPrivateData3() {
		return privateData3;
	}

	public void setPrivateData3(String privateData3) {
		this.privateData3 = privateData3;
	}

	public String getPrivateData4() {
		return privateData4;
	}

	public void setPrivateData4(String privateData4) {
		this.privateData4 = privateData4;
	}

	public Date getPrivateData5() {
		return privateData5;
	}

	public void setPrivateData5(Date privateData5) {
		this.privateData5 = privateData5;
	}

	public Date getPrivateData6() {
		return privateData6;
	}

	public void setPrivateData6(Date privateData6) {
		this.privateData6 = privateData6;
	}

	public Date getPrivateData7() {
		return privateData7;
	}

	public void setPrivateData7(Date privateData7) {
		this.privateData7 = privateData7;
	}

	public Date getPrivateData8() {
		return privateData8;
	}

	public void setPrivateData8(Date privateData8) {
		this.privateData8 = privateData8;
	}

	public Long getPrivateData9() {
		return privateData9;
	}

	public void setPrivateData9(Long privateData9) {
		this.privateData9 = privateData9;
	}

	public Long getPrivateData10() {
		return privateData10;
	}

	public void setPrivateData10(Long privateData10) {
		this.privateData10 = privateData10;
	}

	public Long getPrivateData11() {
		return privateData11;
	}

	public void setPrivateData11(Long privateData11) {
		this.privateData11 = privateData11;
	}

	public Long getPrivateData12() {
		return privateData12;
	}

	public void setPrivateData12(Long privateData12) {
		this.privateData12 = privateData12;
	}

	public String getPrivateTlvDate() {
		return privateTlvDate;
	}

	public void setPrivateTlvDate(String privateTlvDate) {
		this.privateTlvDate = privateTlvDate;
	}

	public String getSensitiveOperationRecord() {
		return sensitiveOperationRecord;
	}

	public void setSensitiveOperationRecord(String sensitiveOperationRecord) {
		this.sensitiveOperationRecord = sensitiveOperationRecord;
	}

	public String getUserCreate() {
		return userCreate;
	}

	public void setUserCreate(String userCreate) {
		this.userCreate = userCreate;
	}

	public Date getDateCreate() {
		return dateCreate;
	}

	public void setDateCreate(Date dateCreate) {
		this.dateCreate = dateCreate;
	}

	public String getUserModified() {
		return userModified;
	}

	public void setUserModified(String userModified) {
		this.userModified = userModified;
	}

	public Date getDateModified() {
		return dateModified;
	}

	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}

	public byte[] getQrCode() {
		return qrCode;
	}

	public void setQrCode(byte[] bs) {
		this.qrCode = bs;
	}

	public WalletBalanceInfo getBalance() {
		return balance;
	}

	public void setBalance(WalletBalanceInfo balance) {
		this.balance = balance;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getAccountTypeCode() {
		return accountTypeCode;
	}

	public void setAccountTypeCode(String accountTypeCode) {
		this.accountTypeCode = accountTypeCode;
	}

	public String getStatusCodeT() {
		return statusCodeT;
	}

	public void setStatusCodeT(String statusCodeT) {
		this.statusCodeT = statusCodeT;
	}

	public String getWalletTypeT() {
		return walletTypeT;
	}

	public void setWalletTypeT(String walletTypeT) {
		this.walletTypeT = walletTypeT;
	}

	public String getControlProfileT() {
		return controlProfileT;
	}

	public void setControlProfileT(String controlProfileT) {
		this.controlProfileT = controlProfileT;
	}

	public String getPricingProfileT() {
		return pricingProfileT;
	}

	public void setPricingProfileT(String pricingProfileT) {
		this.pricingProfileT = pricingProfileT;
	}

	public String getAccountTypeName() {
		return accountTypeName;
	}

	public void setAccountTypeName(String accountTypeName) {
		this.accountTypeName = accountTypeName;
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

}
