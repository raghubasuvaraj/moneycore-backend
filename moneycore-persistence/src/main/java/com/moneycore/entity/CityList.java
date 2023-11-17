package com.moneycore.entity;


import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "city_list")
//@IdClass(CityListKey.class)

public class CityList implements Serializable {

	private static final long serialVersionUID = 1L;

//	@Id
	@Column(name = "institution_code", length = 6)
	private String institutionCode;
	@Id
	@Column(name = "city_code", length = 3)
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String cityCode;
	@Column(name = "city_name", length = 60)
	private String cityName;
	@ManyToOne
	@JoinColumn(name = "region_code")
	private RegionList regionCode;
	@ManyToOne
	@JoinColumn(name = "country_code")
	private CountryList countryCode;
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



	public String getInstitutionCode() {
		return institutionCode;
	}

	public void setInstitutionCode(String institutionCode) {
		this.institutionCode = institutionCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public RegionList getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(RegionList regionCode) {
		this.regionCode = regionCode;
	}

	public CountryList getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(CountryList countryCode) {
		this.countryCode = countryCode;
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
}
