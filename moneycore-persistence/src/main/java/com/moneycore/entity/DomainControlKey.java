//package com.moneycore.entity;
//
//import java.io.Serializable;
//import java.util.Objects;
//
//public class DomainControlKey implements Serializable {
//
//	private static final long serialVersionUID = 1L;
//
//	private String institutionCode;
//	private String controlIndex;
//
//	public DomainControlKey() {
//	}
//
//	public DomainControlKey(String institutionCode, String controlIndex) {
//		this.institutionCode = institutionCode;
//		this.controlIndex = controlIndex;
//	}
//
//	@Override
//	public int hashCode() {
//		return Objects.hash(institutionCode, controlIndex);
//	}
//
//	@Override
//	public boolean equals(Object o) {
//		if (this == o)
//			return true;
//		if (o == null || getClass() != o.getClass())
//			return false;
//		DomainControlKey other = (DomainControlKey) o;
//		return institutionCode.equals(other.institutionCode) && controlIndex.equals(other.controlIndex);
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
//	public String getControlIndex() {
//		return controlIndex;
//	}
//
//	public void setControlIndex(String controlIndex) {
//		this.controlIndex = controlIndex;
//	}
//
//}
