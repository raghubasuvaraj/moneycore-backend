//package com.moneycore.entity;
//
//import java.io.Serializable;
//import java.util.Objects;
//
//public class CustomerSegmentListKey implements Serializable {
//
//	private static final long serialVersionUID = 1L;
//
//	private String institutionCode;
//	private String customerSegmentCode;
//
//	public CustomerSegmentListKey() {
//	}
//
//	public CustomerSegmentListKey(String institutionCode, String customerSegmentCode) {
//		this.institutionCode = institutionCode;
//		this.customerSegmentCode = customerSegmentCode;
//	}
//
//	@Override
//	public int hashCode() {
//		return Objects.hash(institutionCode, customerSegmentCode);
//	}
//
//	@Override
//	public boolean equals(Object o) {
//		if (this == o)
//			return true;
//		if (o == null || getClass() != o.getClass())
//			return false;
//		CustomerSegmentListKey other = (CustomerSegmentListKey) o;
//		return institutionCode.equals(other.institutionCode) && customerSegmentCode.equals(other.customerSegmentCode);
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
//	public String getCustomerSegmentCode() {
//		return customerSegmentCode;
//	}
//
//	public void setCustomerSegmentCode(String customerSegmentCode) {
//		this.customerSegmentCode = customerSegmentCode;
//	}
//
//}
