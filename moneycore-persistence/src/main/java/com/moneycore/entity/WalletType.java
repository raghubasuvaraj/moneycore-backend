package com.moneycore.entity;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "wallet_type")

//@IdClass(WalletTypeKey.class)
public class WalletType implements Serializable {

	private static final long serialVersionUID = 1L;

//	@Id
	@Column(name = "institution_code", length = 6)
	private String institutionCode;
	@Id
	@Column(name = "wallet_type_id")
	private String walletTypeId;
	@Column(name = "wallet_type_name")
	private String walletTypeName;
	@Column(name = "abrv_wording", length = 16)
	private String abrvWording;
	@Column(name = "wording", length = 32)
	private String wording;
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
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "wallet_type_domain_control")
	private WalletTypeDomainControl walletTypeDomainControl;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "wallet_type_pricing_profile")
	private WalletTypePricingProfile walletTypePricingProfile;




	public String getInstitutionCode() {
		return institutionCode;
	}

	public void setInstitutionCode(String institutionCode) {
		this.institutionCode = institutionCode;
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

	public WalletTypeDomainControl getWalletTypeDomainControl() {
		return walletTypeDomainControl;
	}

	public void setWalletTypeDomainControl(WalletTypeDomainControl walletTypeDomainControl) {
		this.walletTypeDomainControl = walletTypeDomainControl;
	}

	public WalletTypePricingProfile getWalletTypePricingProfile() {
		return walletTypePricingProfile;
	}

	public void setWalletTypePricingProfile(WalletTypePricingProfile walletTypePricingProfile) {
		this.walletTypePricingProfile = walletTypePricingProfile;
	}

}
