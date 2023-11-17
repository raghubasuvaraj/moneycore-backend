package com.moneycore.entity;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name = "domain_control")
//@IdClass(DomainControlKey.class)
@SQLDelete(sql = "UPDATE domain_control SET is_deleted = true WHERE control_index=?")
@Where(clause = "is_deleted=false")
public class DomainControl implements Serializable {

	private static final long serialVersionUID = 1L;

//	@Id
	@ManyToOne
	@JoinColumn(name = "institution_code")
	private InstitutionList institutionCode;
	@Id
	@Column(name = "control_index")
	private String controlIndex;
	@Column(name = "abrv_wording", length = 16)
	private String abrvWording;
	@Column(name = "wording", length = 32)
	private String wording;
	@Column(name = "currency_code")
	private String currencyCode;
	@Column(name = "mcc_code")
	private String mccCode;
	@Column(name = "country_code")
	private String countryCode;
	@Column(name = "merchant_id")
	private String merchantId;
	@Column(name = "transaction_code")
	private String transactionCode;
	@Column(name = "daily_onus_amnt", precision = 18, scale = 3)
	private double dailyOnusAmnt;
	@Column(name = "daily_onus_nbr", precision = 4, scale = 0)
	private double dailyOnusNbr;
	@Column(name = "daily_nat_amnt", precision = 18, scale = 3)
	private double dailyNatAmnt;
	@Column(name = "daily_nat_nbr", precision = 4, scale = 0)
	private double dailtNatNbr;
	@Column(name = "daily_internat_amnt", precision = 18, scale = 3)
	private double dailyInternatAmnt;
	@Column(name = "daily_internat_nbr", precision = 4, scale = 0)
	private double dailyInternatNbr;
	@Column(name = "daily_tot_amnt", precision = 18, scale = 3)
	private double dailyTotAmnt;
	@Column(name = "daily_tot_nbr", precision = 6, scale = 0)
	private double dailyTotNbr;
	@Column(name = "per_onus_amnt", precision = 18, scale = 3)
	private double perOnusAmnt;
	@Column(name = "per_onus_nbr", precision = 4, scale = 0)
	private double perOnusNbr;
	@Column(name = "per_nat_amnt", precision = 18, scale = 3)
	private double perNatAmnt;
	@Column(name = "per_nat_nbr", precision = 4, scale = 0)
	private double perNatNbr;
	@Column(name = "per_internat_amnt", precision = 18, scale = 3)
	private double perInternatAmnt;
	@Column(name = "per_internat_nbr", precision = 4, scale = 0)
	private double perInternatNbr;
	@Column(name = "per_tot_amnt", precision = 18, scale = 3)
	private double perTotAmnt;
	@Column(name = "per_tot_nbr", precision = 6, scale = 0)
	private double perTotNbr;
	@Column(name = "user_create", length = 15)
	private String userCreate;
	@Column(name = "date_create")
	@Temporal(TemporalType.DATE)
	private Date dateCreate;
	@Column(name = "user_modif", length = 15)
	private String userModif;
	@Column(name = "date_modif")
	@Temporal(TemporalType.DATE)
	private Date dateModif;


	@Column(name = "is_deleted")
	private boolean isDeleted = Boolean.FALSE;

	@Transient
	private boolean isDefault;

	@Transient
	private String walletTypeId;

	@Transient
	private String walletTypeName;

	public InstitutionList getInstitutionCode() {
		return institutionCode;
	}

	public void setInstitutionCode(InstitutionList institutionCode) {
		this.institutionCode = institutionCode;
	}

	public String getControlIndex() {
		return controlIndex;
	}

	public void setControlIndex(String controlIndex) {
		this.controlIndex = controlIndex;
	}

	public String getAbrvWording() {
		return abrvWording;
	}

	public void setAbrvWording(String abrvWording) {
		this.abrvWording = abrvWording;
	}

	public String getWording() {
		return wording;
	}

	public void setWording(String wording) {
		this.wording = wording;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getMccCode() {
		return mccCode;
	}

	public void setMccCode(String mccCode) {
		this.mccCode = mccCode;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getTransactionCode() {
		return transactionCode;
	}

	public void setTransactionCode(String transactionCode) {
		this.transactionCode = transactionCode;
	}

	public double getDailyOnusAmnt() {
		return dailyOnusAmnt;
	}

	public void setDailyOnusAmnt(double dailyOnusAmnt) {
		this.dailyOnusAmnt = dailyOnusAmnt;
	}

	public double getDailyOnusNbr() {
		return dailyOnusNbr;
	}

	public void setDailyOnusNbr(double dailyOnusNbr) {
		this.dailyOnusNbr = dailyOnusNbr;
	}

	public double getDailyNatAmnt() {
		return dailyNatAmnt;
	}

	public void setDailyNatAmnt(double dailyNatAmnt) {
		this.dailyNatAmnt = dailyNatAmnt;
	}

	public double getDailtNatNbr() {
		return dailtNatNbr;
	}

	public void setDailtNatNbr(double dailtNatNbr) {
		this.dailtNatNbr = dailtNatNbr;
	}

	public double getDailyInternatAmnt() {
		return dailyInternatAmnt;
	}

	public void setDailyInternatAmnt(double dailyInternatAmnt) {
		this.dailyInternatAmnt = dailyInternatAmnt;
	}

	public double getDailyInternatNbr() {
		return dailyInternatNbr;
	}

	public void setDailyInternatNbr(double dailyInternatNbr) {
		this.dailyInternatNbr = dailyInternatNbr;
	}

	public double getDailyTotAmnt() {
		return dailyTotAmnt;
	}

	public void setDailyTotAmnt(double dailyTotAmnt) {
		this.dailyTotAmnt = dailyTotAmnt;
	}

	public double getDailyTotNbr() {
		return dailyTotNbr;
	}

	public void setDailyTotNbr(double dailyTotNbr) {
		this.dailyTotNbr = dailyTotNbr;
	}

	public double getPerOnusAmnt() {
		return perOnusAmnt;
	}

	public void setPerOnusAmnt(double perOnusAmnt) {
		this.perOnusAmnt = perOnusAmnt;
	}

	public double getPerOnusNbr() {
		return perOnusNbr;
	}

	public void setPerOnusNbr(double perOnusNbr) {
		this.perOnusNbr = perOnusNbr;
	}

	public double getPerNatAmnt() {
		return perNatAmnt;
	}

	public void setPerNatAmnt(double perNatAmnt) {
		this.perNatAmnt = perNatAmnt;
	}

	public double getPerNatNbr() {
		return perNatNbr;
	}

	public void setPerNatNbr(double perNatNbr) {
		this.perNatNbr = perNatNbr;
	}

	public double getPerInternatAmnt() {
		return perInternatAmnt;
	}

	public void setPerInternatAmnt(double perInternatAmnt) {
		this.perInternatAmnt = perInternatAmnt;
	}

	public double getPerInternatNbr() {
		return perInternatNbr;
	}

	public void setPerInternatNbr(double perInternatNbr) {
		this.perInternatNbr = perInternatNbr;
	}

	public double getPerTotAmnt() {
		return perTotAmnt;
	}

	public void setPerTotAmnt(double perTotAmnt) {
		this.perTotAmnt = perTotAmnt;
	}

	public double getPerTotNbr() {
		return perTotNbr;
	}

	public void setPerTotNbr(double perTotNbr) {
		this.perTotNbr = perTotNbr;
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

	public boolean getIsDefault() {
		return isDefault;
	}

	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
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

}
