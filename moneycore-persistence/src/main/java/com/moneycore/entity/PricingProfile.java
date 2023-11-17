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
@Table(name = "pricing_profile")
//@IdClass(PricingProfileKey.class)
@SQLDelete(sql = "UPDATE pricing_profile SET is_deleted = true WHERE pricing_index=?")
@Where(clause = "is_deleted=false")
public class PricingProfile implements Serializable {

	private static final long serialVersionUID = 1L;

//	@Id
	@ManyToOne
	@JoinColumn(name = "institution_code")
	private InstitutionList institutionCode;
	@Id
	@Column(name = "pricing_index")
	private String pricingIndex;
	@Column(name = "abrv_description", length = 16)
	private String abrvDescription;
	@Column(name = "description", length = 32)
	private String description;
	@Column(name = "currency_code")
	private String currencyCode;
	@Column(name = "subscription_amount", precision = 18, scale = 3)
	private double subscriptionAmount;
	@Column(name = "fees_amount_first", precision = 18, scale = 3)
	private double feesAmountFirst;
	@Column(name = "toup_fees", precision = 18, scale = 3)
	private double toupFees;
	@Column(name = "reload_fees", precision = 18, scale = 3)
	private double reloadFees;
	@Column(name = "ops_fees", precision = 18, scale = 3)
	private double opsFees;
	@Column(name = "service_fees", precision = 18, scale = 3)
	private double serviceFees;
	@Column(name = "promotion_starting_date")
	@Temporal(TemporalType.DATE)
	private Date promotionStartingDate;
	@Column(name = "promotion_ending_date")
	@Temporal(TemporalType.DATE)
	private Date promotionEndingDate;
	@Column(name = "promotion_fees_amount", precision = 18, scale = 3)
	private double promotionFeesAmount;
	@Column(name = "other_fees")
	private String otherFees;
	@Column(name = "other_fees_indicator")
	private String otherFeesIndicator;
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

	public String getPricingIndex() {
		return pricingIndex;
	}

	public void setPricingIndex(String pricingIndex) {
		this.pricingIndex = pricingIndex;
	}

	public String getAbrvDescription() {
		return abrvDescription;
	}

	public void setAbrvDescription(String abrvDescription) {
		this.abrvDescription = abrvDescription;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public double getSubscriptionAmount() {
		return subscriptionAmount;
	}

	public void setSubscriptionAmount(double subscriptionAmount) {
		this.subscriptionAmount = subscriptionAmount;
	}

	public double getFeesAmountFirst() {
		return feesAmountFirst;
	}

	public void setFeesAmountFirst(double feesAmountFirst) {
		this.feesAmountFirst = feesAmountFirst;
	}

	public double getToupFees() {
		return toupFees;
	}

	public void setToupFees(double toupFees) {
		this.toupFees = toupFees;
	}

	public double getReloadFees() {
		return reloadFees;
	}

	public void setReloadFees(double reloadFees) {
		this.reloadFees = reloadFees;
	}

	public double getOpsFees() {
		return opsFees;
	}

	public void setOpsFees(double opsFees) {
		this.opsFees = opsFees;
	}

	public double getServiceFees() {
		return serviceFees;
	}

	public void setServiceFees(double serviceFees) {
		this.serviceFees = serviceFees;
	}

	public Date getPromotionStartingDate() {
		return promotionStartingDate;
	}

	public void setPromotionStartingDate(Date promotionStartingDate) {
		this.promotionStartingDate = promotionStartingDate;
	}

	public Date getPromotionEndingDate() {
		return promotionEndingDate;
	}

	public void setPromotionEndingDate(Date promotionEndingDate) {
		this.promotionEndingDate = promotionEndingDate;
	}

	public double getPromotionFeesAmount() {
		return promotionFeesAmount;
	}

	public void setPromotionFeesAmount(double promotionFeesAmount) {
		this.promotionFeesAmount = promotionFeesAmount;
	}

	public String getOtherFees() {
		return otherFees;
	}

	public void setOtherFees(String otherFees) {
		this.otherFees = otherFees;
	}

	public String getOtherFeesIndicator() {
		return otherFeesIndicator;
	}

	public void setOtherFeesIndicator(String otherFeesIndicator) {
		this.otherFeesIndicator = otherFeesIndicator;
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
