//package com.moneycore.entity;
//
//import java.io.Serializable;
//import java.util.Objects;
//
//public class CountryListKey implements Serializable {
//
//	private static final long serialVersionUID = 1L;
//
//	private String institutionCode;
//	private String countryCode;
//
//	public CountryListKey() {
//	}
//
//	public CountryListKey(String institutionCode, String countryCode) {
//		this.institutionCode = institutionCode;
//		this.countryCode = countryCode;
//	}
//
//	@Override
//	public int hashCode() {
//		return Objects.hash(institutionCode, countryCode);
//	}
//
//	@Override
//	public boolean equals(Object o) {
//		if (this == o)
//			return true;
//		if (o == null || getClass() != o.getClass())
//			return false;
//		CountryListKey other = (CountryListKey) o;
//		return institutionCode.equals(other.institutionCode) && countryCode.equals(other.countryCode);
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
//	public String getCountryCode() {
//		return countryCode;
//	}
//
//	public void setCountryCode(String countryCode) {
//		this.countryCode = countryCode;
//	}
//
//}
