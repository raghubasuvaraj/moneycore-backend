//package com.moneycore.entity;
//
//import java.io.Serializable;
//import java.util.Objects;
//
//public class LanguageListKey implements Serializable {
//
//	private static final long serialVersionUID = 1L;
//
//	private String institutionCode;
//	private String languageCode;
//
//	public LanguageListKey() {
//	}
//
//	public LanguageListKey(String institutionCode, String languageCode) {
//		this.institutionCode = institutionCode;
//		this.languageCode = languageCode;
//	}
//
//	@Override
//	public int hashCode() {
//		return Objects.hash(institutionCode, languageCode);
//	}
//
//	@Override
//	public boolean equals(Object o) {
//		if (this == o)
//			return true;
//		if (o == null || getClass() != o.getClass())
//			return false;
//		LanguageListKey other = (LanguageListKey) o;
//		return institutionCode.equals(other.institutionCode) && languageCode.equals(other.languageCode);
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
//	public String getLanguageCode() {
//		return languageCode;
//	}
//
//	public void setLanguageCode(String languageCode) {
//		this.languageCode = languageCode;
//	}
//
//}
