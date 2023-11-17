package com.moneycore.entity;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;


@Entity
@Table(name = "client")
@NamedQueries({
	@NamedQuery(query = "select c from Client c where c.isDeleted=false", name = "Client.query_find_all_clients"),
    @NamedQuery(name="Client.findByContact",query="SELECT c.email FROM Client c WHERE c.prPhone1 = :prPhone1 and c.isDeleted=false")
})
@SQLDelete(sql = "UPDATE client SET is_deleted = true WHERE client_code=?")
@Where(clause = "is_deleted=false")
public class Client implements Serializable {

	    private static final long serialVersionUID = 1L;
	    @Id
	    @Column(name = "client_code")
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    protected int clientCode;
	    
	    @Column(name = "family_name", length = 40)
	    private String familyName;
	    
	    @Column(name = "email")
	    private String email;	    
	    
	    @Column(name = "middle_name", length = 40)
	    private String middleName;
	    
	    @Column(name = "first_name", length = 40)
	    private String firstName;
	    
	    @Column(name = "last_name", length = 40)
	    private String lastName;

	    @Column(name = "gender", length = 1)
	    private String gender;
	    
	    @Column(name = "date_of_birth")	 
	    private String dateOfBirth;
	    
	    @Column(name = "id_type")
	    private String idType;
	    
	    @Column(name = "id_number")
	    private String idNumber;
	    
	    @Column(name = "pr_region_name")
	    private String prRegionName;
	    
	    @Column(name = "pr_email", length = 64)
	    private String prEmail;
	    
	    @Column(name = "pr_url", length = 60)
	    private String prUrl;
	    
	    @Column(name="password")
		private String password;

	    @Column(name = "pr_phone_1_code", length = 4)
	    private String prPhone1Code;

	    @Column(name = "pr_phone_1", length = 20)
	    private String prPhone1;
	    
	    @Column(name = "pr_phone_1_type")
	    private Character prPhone1Type;

	    @Column(name = "pr_phone_2_code", length = 4)
	    private String prPhone2Code;

	    @Column(name = "pr_phone_2", length = 20)
	    private String prPhone2;
	    
	    @Column(name = "pr_phone_2_type")
	    private Character prPhone2Type;

	    @Column(name = "pr_phone_3_code", length = 4)
	    private String prPhone3Code;

	    @Column(name = "pr_phone_3", length = 20)
	    private String prPhone3;
	    
	    @Column(name = "pr_phone_3_type")
	    private Character prPhone3Type;

	    @Column(name = "pr_phone_4_code", length = 4)
	    private String prPhone4Code;

	    @Column(name = "pr_phone_4", length = 20)
	    private String prPhone4;
	    
	    @Column(name = "pr_phone_4_type")
	    private Character prPhone4Type;
	    
	    @Temporal(TemporalType.DATE)
	    @Column(name = "status_date")	 
	    private Date statusDate;
	    
	    @Column(name = "private_data_1")
	    private String privateData1;
	    
	    @Column(name = "private_data_2")
	    private String privateData2;
	    
	    @Column(name = "private_data_3")
	    private String privateData3;
	    
	    @Column(name = "private_data_4")
	    private String privateData4;
	    
	    @Temporal(TemporalType.DATE)
	    @Column(name = "private_data_5") 
	    private Date privateData5;
	   
	    @Temporal(TemporalType.DATE)
	    @Column(name = "private_data_6")
	    private Date privateData6;
	   
	    @Temporal(TemporalType.DATE)
	    @Column(name = "private_data_7")	   
	    private Date privateData7;
	  
	    @Temporal(TemporalType.DATE)
	    @Column(name = "private_data_8")
	    private Date privateData8;
	   
	    @Column(name = "private_data_9")
	    private Double privateData9;
	    
	    @Column(name = "private_data_10")
	    private Double privateData10;
	    
	    @Column(name = "private_data_11")
	    private Double privateData11;
	    
	    @Column(name = "private_data_12")
	    private Double privateData12;
	    
	    @Column(name = "private_tlv_data")
	    private String privateTlvData;
	    
	    @Column(name = "sensitive_operation_record")
	    private Character sensitiveOperationRecord;
	    
	    @Column(name = "user_create")
	    private String userCreate;
	    
	    @Temporal(TemporalType.DATE)
	    @Column(name = "date_create")
	    private Date dateCreate;
	    
	    @Column(name = "user_modif")
	    private String userModif;
	    
	    @Temporal(TemporalType.DATE)
	    @Column(name = "date_modif")
	    private Date dateModif;


		@Column(name = "device_token")
		private String deviceToken;



	@Column(name = "is_deleted")
		private boolean isDeleted = Boolean.FALSE;

	@ManyToOne
		@JoinColumn(name = "nationality_code", referencedColumnName = "nationality_id")
		private NationalityList nationalityCode;
	    @ManyToOne
	    @JoinColumn(name = "pr_country_code", referencedColumnName = "country_code")
	    private CountryList countryCode;
	    @ManyToOne
	    @JoinColumn(name = "activity_code")
	    private ActivityList activityCode;
	    @ManyToOne
	    @JoinColumn(name = "branch_code")
	    private Branch branchList;

	    @ManyToOne
	    @JoinColumn(name = "vip_level", referencedColumnName = "vip_code")
	    private VipList vipLevel;
	    @ManyToOne
	    @JoinColumn(name = "currency_code")
	    private CurrencyList currencyCode;
	    @ManyToOne
	    @JoinColumn(name = "title_code")
	    private TitleList titleCode;
	    @ManyToOne
	    @JoinColumn(name = "customer_segment", referencedColumnName = "customer_segment_code")
	    private CustomerSegmentList customerSegment;
	    
	    @ManyToOne
	    @JoinColumn(name="institution_code")
	    private InstitutionList institutionList;
	    @ManyToOne
	    @JoinColumn(name = "language_code")
	    private LanguageList languageCode;
	    @ManyToOne
	    @JoinColumn(name = "pr_region_code", referencedColumnName = "region_code")
	    private RegionList prRegionCode;
	    @ManyToOne
	    @JoinColumn(name = "status_reason", referencedColumnName = "status_reason_code")
	    private StatusReasonCode statusReason;
	    @ManyToOne
	    @JoinColumn(name = "status_code")
	    private StatusList statusCode;
	    
	    @Column(name="logout")
	    private boolean logout;

	    @Column(name="is_merchant")
	    private boolean isMerchant;

		@Column(name="mpin")
		private String mpin;

		@Column(name="entity_name")
		private String entityName;

		@Column(name="licence_no")
		private String licenceNo;

		@Column(name="licence_image")
		private String licenceImage;

		@ManyToOne
		@JoinColumn(name = "mcc_code")
		private MccList mccCode;

		@Column(name = "status_from_date")
		private String statusFromDate;

		@Column(name = "status_to_date")
		private String statusToDate;

		@ManyToOne
		@JoinColumn(name = "address", referencedColumnName = "id")
		private Address address;

		@Column(name = "create_source")
		private String createSource;

		@Transient
	    private String WalletId;

		public boolean isLogout() {
			return logout;
		}

		public void setLogout(boolean logout) {
			this.logout = logout;
		}

		public String getLastName() {
			return lastName;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}

		public String getDateOfBirth() {
			return dateOfBirth;
		}

		public void setDateOfBirth(String dateOfBirth) {
			this.dateOfBirth = dateOfBirth;
		}

		public String getIdType() {
			return idType;
		}

		public void setIdType(String idType) {
			this.idType = idType;
		}

		public String getIdNumber() {
			return idNumber;
		}

		public void setIdNumber(String idNumber) {
			this.idNumber = idNumber;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public boolean isDeleted() {
			return isDeleted;
		}

		public void setDeleted(boolean deleted) {
			isDeleted = deleted;
		}
		public int getClientCode() {
			return clientCode;
		}

		public void setClientCode(int clientCode) {
			this.clientCode = clientCode;
		}

		public String getFamilyName() {
			return familyName;
		}

		public void setFamilyName(String familyName) {
			this.familyName = familyName;
		}

		public String getMiddleName() {
			return middleName;
		}

		public void setMiddleName(String middleName) {
			this.middleName = middleName;
		}

		public String getFirstName() {
			return firstName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		public String getGender() {
			return gender;
		}

		public void setGender(String gender) {
			this.gender = gender;
		}

		public String getPrRegionName() {
			return prRegionName;
		}

		public void setPrRegionName(String prRegionName) {
			this.prRegionName = prRegionName;
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

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getPrPhone1() {
			return prPhone1;
		}

		public void setPrPhone1(String prPhone1) {
			this.prPhone1 = prPhone1;
		}

		public Character getPrPhone1Type() {
			return prPhone1Type;
		}

		public void setPrPhone1Type(Character prPhone1Type) {
			this.prPhone1Type = prPhone1Type;
		}

		public String getPrPhone2() {
			return prPhone2;
		}

		public void setPrPhone2(String prPhone2) {
			this.prPhone2 = prPhone2;
		}

		public Character getPrPhone2Type() {
			return prPhone2Type;
		}

		public void setPrPhone2Type(Character prPhone2Type) {
			this.prPhone2Type = prPhone2Type;
		}

		public String getPrPhone3() {
			return prPhone3;
		}

		public void setPrPhone3(String prPhone3) {
			this.prPhone3 = prPhone3;
		}

		public Character getPrPhone3Type() {
			return prPhone3Type;
		}

		public void setPrPhone3Type(Character prPhone3Type) {
			this.prPhone3Type = prPhone3Type;
		}

		public String getPrPhone4() {
			return prPhone4;
		}

		public void setPrPhone4(String prPhone4) {
			this.prPhone4 = prPhone4;
		}

		public Character getPrPhone4Type() {
			return prPhone4Type;
		}

		public void setPrPhone4Type(Character prPhone4Type) {
			this.prPhone4Type = prPhone4Type;
		}

		public Date getStatusDate() {
			return statusDate;
		}

		public void setStatusDate(Date statusDate) {
			this.statusDate = statusDate;
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

		public Double getPrivateData9() {
			return privateData9;
		}

		public void setPrivateData9(Double privateData9) {
			this.privateData9 = privateData9;
		}

		public Double getPrivateData10() {
			return privateData10;
		}

		public void setPrivateData10(Double privateData10) {
			this.privateData10 = privateData10;
		}

		public Double getPrivateData11() {
			return privateData11;
		}

		public void setPrivateData11(Double privateData11) {
			this.privateData11 = privateData11;
		}

		public Double getPrivateData12() {
			return privateData12;
		}

		public void setPrivateData12(Double privateData12) {
			this.privateData12 = privateData12;
		}

		public String getPrivateTlvData() {
			return privateTlvData;
		}

		public void setPrivateTlvData(String privateTlvData) {
			this.privateTlvData = privateTlvData;
		}

		public Character getSensitiveOperationRecord() {
			return sensitiveOperationRecord;
		}

		public void setSensitiveOperationRecord(Character sensitiveOperationRecord) {
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

		public String getUserModif() {
			return userModif;
		}

		public void setUserModif(String userModif) {
			this.userModif = userModif;
		}

		public Date getDateModif() {
			return dateModif;
		}

		public void setDateModif(Date dateModif) {
			this.dateModif = dateModif;
		}

		public ActivityList getActivityCode() {
			return activityCode;
		}

		public void setActivityCode(ActivityList activityCode) {
			this.activityCode = activityCode;
		}

		public Branch getBranchList() {
			return branchList;
		}

		public void setBranchList(Branch branchList) {
			this.branchList = branchList;
		}

		public VipList getVipLevel() {
			return vipLevel;
		}

		public void setVipLevel(VipList vipLevel) {
			this.vipLevel = vipLevel;
		}

		public CurrencyList getCurrencyCode() {
			return currencyCode;
		}

		public void setCurrencyCode(CurrencyList currencyCode) {
			this.currencyCode = currencyCode;
		}

		public TitleList getTitleCode() {
			return titleCode;
		}

		public void setTitleCode(TitleList titleCode) {
			this.titleCode = titleCode;
		}

		public CustomerSegmentList getCustomerSegment() {
			return customerSegment;
		}

		public void setCustomerSegment(CustomerSegmentList customerSegment) {
			this.customerSegment = customerSegment;
		}

		public InstitutionList getInstitutionList() {
			return institutionList;
		}

		public void setInstitutionList(InstitutionList institutionList) {
			this.institutionList = institutionList;
		}

		public LanguageList getLanguageCode() {
			return languageCode;
		}

		public void setLanguageCode(LanguageList languageCode) {
			this.languageCode = languageCode;
		}

		public RegionList getPrRegionCode() {
			return prRegionCode;
		}

		public void setPrRegionCode(RegionList prRegionCode) {
			this.prRegionCode = prRegionCode;
		}

		public StatusReasonCode getStatusReason() {
			return statusReason;
		}

		public void setStatusReason(StatusReasonCode statusReason) {
			this.statusReason = statusReason;
		}

		public StatusList getStatusCode() {
			return statusCode;
		}

		public void setStatusCode(StatusList statusCode) {
			this.statusCode = statusCode;
		}

		public boolean getIsMerchant() {
			return isMerchant;
		}

		public void setMerchant(boolean isMerchant) {
			this.isMerchant = isMerchant;
		}

		public String getWalletId() {
			return WalletId;
		}

		public void setWalletId(String walletId) {
			WalletId = walletId;
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

		public String getMpin() {
			return mpin;
		}

		public void setMpin(String mpin) {
			this.mpin = mpin;
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

		public String getLicenceImage() {
			return licenceImage;
		}

		public void setLicenceImage(String licenceImage) {
			this.licenceImage = licenceImage;
		}

		public MccList getMccCode() {
			return mccCode;
		}

		public void setMccCode(MccList mccCode) {
			this.mccCode = mccCode;
		}

		public String getStatusFromDate() {
			return statusFromDate;
		}

		public void setStatusFromDate(String statusFromDate) {
			this.statusFromDate = statusFromDate;
		}

		public String getStatusToDate() {
			return statusToDate;
		}

		public void setStatusToDate(String statusToDate) {
			this.statusToDate = statusToDate;
		}

	    public NationalityList getNationalityCode() {
			return nationalityCode;
		}

		public void setNationalityCode(NationalityList nationalityCode) {
			this.nationalityCode = nationalityCode;
		}

		public CountryList getCountryCode() {
			return countryCode;
		}

		public void setCountryCode(CountryList countryCode) {
			this.countryCode = countryCode;
		}

		public Address getAddress() {
			return address;
		}

		public void setAddress(Address address) {
			this.address = address;
		}

		public String getDeviceToken() {
			return deviceToken;
		}

		public void setDeviceToken(String deviceToken) {
			this.deviceToken = deviceToken;
		}

		public String getCreateSource() {
			return createSource;
		}

		public void setCreateSource(String createSource) {
			this.createSource = createSource;
		}
}
