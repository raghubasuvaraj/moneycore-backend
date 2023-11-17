//package com.moneycore.entity;
//
//import java.io.Serializable;
//import java.util.Objects;
//
//public class TitleListKey implements Serializable {
//
//	private static final long serialVersionUID = 1L;
//
//	private String institutionCode;
//	private String titleCode;
//
//	public TitleListKey() {
//	}
//
//	public TitleListKey(String institutionCode, String titleCode) {
//		this.institutionCode = institutionCode;
//		this.titleCode = titleCode;
//	}
//
//	@Override
//	public int hashCode() {
//		return Objects.hash(institutionCode, titleCode);
//	}
//
//	@Override
//	public boolean equals(Object o) {
//		if (this == o)
//			return true;
//		if (o == null || getClass() != o.getClass())
//			return false;
//		TitleListKey other = (TitleListKey) o;
//		return institutionCode.equals(other.institutionCode) && titleCode.equals(other.titleCode);
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
//	public String getTitleCode() {
//		return titleCode;
//	}
//
//	public void setTitleCode(String titleCode) {
//		this.titleCode = titleCode;
//	}
//
//}
