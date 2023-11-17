//package com.moneycore.entity;
//
//import java.io.Serializable;
//import java.util.Objects;
//
//public class VipListKey implements Serializable {
//
//	private static final long serialVersionUID = 1L;
//
//	private String institutionCode;
//	private String vipCode;
//
//	public VipListKey() {
//	}
//
//	public VipListKey(String institutionCode, String vipCode) {
//		this.institutionCode = institutionCode;
//		this.vipCode = vipCode;
//	}
//
//	@Override
//	public int hashCode() {
//		return Objects.hash(institutionCode, vipCode);
//	}
//
//	@Override
//	public boolean equals(Object o) {
//		if (this == o)
//			return true;
//		if (o == null || getClass() != o.getClass())
//			return false;
//		VipListKey other = (VipListKey) o;
//		return institutionCode.equals(other.institutionCode) && vipCode.equals(other.vipCode);
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
//	public String getVipCode() {
//		return vipCode;
//	}
//
//	public void setVipCode(String vipCode) {
//		this.vipCode = vipCode;
//	}
//
//}
