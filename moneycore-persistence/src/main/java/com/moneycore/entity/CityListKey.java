//package com.moneycore.entity;
//
//import java.io.Serializable;
//import java.util.Objects;
//
//public class CityListKey implements Serializable {
//
//	private static final long serialVersionUID = 1L;
//
//	private String institutionCode;
//	private String cityCode;
//
//	public CityListKey() {
//	}
//
//	public CityListKey(String institutionCode, String cityCode) {
//		this.institutionCode = institutionCode;
//		this.cityCode = cityCode;
//	}
//
//	@Override
//	public int hashCode() {
//		return Objects.hash(institutionCode, cityCode);
//	}
//
//	@Override
//	public boolean equals(Object o) {
//		if (this == o)
//			return true;
//		if (o == null || getClass() != o.getClass())
//			return false;
//		CityListKey other = (CityListKey) o;
//		return institutionCode.equals(other.institutionCode) && cityCode.equals(other.cityCode);
//	}
//
//	public String getInstitutionCode() {
//		return institutionCode;
//	}
//
//	public void setInstitutionCode(String institutionCode) {
//		this.institutionCode = institutionCode;
//	}
//
//	public String getCityCode() {
//		return cityCode;
//	}
//
//	public void setCityCode(String cityCode) {
//		this.cityCode = cityCode;
//	}
//
//}
