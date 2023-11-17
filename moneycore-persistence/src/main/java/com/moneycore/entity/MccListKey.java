//package com.moneycore.entity;
//
//import java.io.Serializable;
//import java.util.Objects;
//
//public class MccListKey implements Serializable {
//
//	private static final long serialVersionUID = 1L;
//
//	private String institutionCode;
//	private String mccCode;
//
//	public MccListKey() {
//	}
//
//	public MccListKey(String institutionCode, String mccCode) {
//		this.institutionCode = institutionCode;
//		this.mccCode = mccCode;
//	}
//
//	@Override
//	public int hashCode() {
//		return Objects.hash(institutionCode, mccCode);
//	}
//
//	@Override
//	public boolean equals(Object o) {
//		if (this == o)
//			return true;
//		if (o == null || getClass() != o.getClass())
//			return false;
//		MccListKey other = (MccListKey) o;
//		return institutionCode.equals(other.institutionCode) && mccCode.equals(other.mccCode);
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
//	public String getMccCode() {
//		return mccCode;
//	}
//
//	public void setMccCode(String mccCode) {
//		this.mccCode = mccCode;
//	}
//
//}
