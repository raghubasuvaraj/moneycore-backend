//package com.moneycore.entity;
//
//import java.io.Serializable;
//import java.util.Objects;
//
//public class ActivityListKey implements Serializable {
//
//	private static final long serialVersionUID = 1L;
//
//	private String institutionCode;
//	private String activityCode;
//
//	public ActivityListKey() {
//	}
//
//	public ActivityListKey(String institutionCode, String activityCode) {
//		this.institutionCode = institutionCode;
//		this.activityCode = activityCode;
//	}
//
//	@Override
//	public int hashCode() {
//		return Objects.hash(institutionCode, activityCode);
//	}
//
//	@Override
//	public boolean equals(Object o) {
//		if (this == o)
//			return true;
//		if (o == null || getClass() != o.getClass())
//			return false;
//		ActivityListKey other = (ActivityListKey) o;
//		return institutionCode.equals(other.institutionCode) && activityCode.equals(other.activityCode);
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
//	public String getActivityCode() {
//		return activityCode;
//	}
//
//	public void setActivityCode(String activityCode) {
//		this.activityCode = activityCode;
//	}
//
//}
