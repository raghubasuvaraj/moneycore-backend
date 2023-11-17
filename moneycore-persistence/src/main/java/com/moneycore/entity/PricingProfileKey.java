//package com.moneycore.entity;
//
//import java.io.Serializable;
//import java.util.Objects;
//
//public class PricingProfileKey implements Serializable {
//
//	private static final long serialVersionUID = 1L;
//
//	private String institutionCode;
//	private String pricingIndex;
//
//	public PricingProfileKey() {
//	}
//
//	public PricingProfileKey(String institutionCode, String pricingIndex) {
//		this.institutionCode = institutionCode;
//		this.pricingIndex = pricingIndex;
//	}
//
//	@Override
//	public int hashCode() {
//		return Objects.hash(institutionCode, pricingIndex);
//	}
//
//	@Override
//	public boolean equals(Object o) {
//		if (this == o)
//			return true;
//		if (o == null || getClass() != o.getClass())
//			return false;
//		PricingProfileKey other = (PricingProfileKey) o;
//		return institutionCode.equals(other.institutionCode) && pricingIndex.equals(other.pricingIndex);
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
//	public String getPricingIndex() {
//		return pricingIndex;
//	}
//
//	public void setPricingIndex(String pricingIndex) {
//		this.pricingIndex = pricingIndex;
//	}
//
//}
