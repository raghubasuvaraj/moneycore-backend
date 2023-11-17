//package com.moneycore.entity;
//
//import java.io.Serializable;
//import java.util.Objects;
//
//public class RegionListKey implements Serializable {
//
//	private static final long serialVersionUID = 1L;
//
//	private String institutionCode;
//	private String regionCode;
//
//	public RegionListKey() {
//	}
//
//	public RegionListKey(String institutionCode, String regionCode) {
//		this.institutionCode = institutionCode;
//		this.regionCode = regionCode;
//	}
//
//	@Override
//	public int hashCode() {
//		return Objects.hash(institutionCode, regionCode);
//	}
//
//	@Override
//	public boolean equals(Object o) {
//		if (this == o)
//			return true;
//		if (o == null || getClass() != o.getClass())
//			return false;
//		RegionListKey other = (RegionListKey) o;
//		return institutionCode.equals(other.institutionCode) && regionCode.equals(other.regionCode);
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
//	public String getRegionCode() {
//		return regionCode;
//	}
//
//	public void setRegionCode(String regionCode) {
//		this.regionCode = regionCode;
//	}
//
//}
