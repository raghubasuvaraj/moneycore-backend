//package com.moneycore.entity;
//
//import java.io.Serializable;
//import java.util.Objects;
//
//public class WalletTypeKey implements Serializable {
//
//	private static final long serialVersionUID = 1L;
//
//	private String institutionCode;
//	private String walletTypeId;
//
//	public WalletTypeKey() {
//	}
//
//	public WalletTypeKey(String institutionCode, String walletTypeId) {
//		this.institutionCode = institutionCode;
//		this.walletTypeId = walletTypeId;
//	}
//
//	@Override
//	public int hashCode() {
//		return Objects.hash(institutionCode, walletTypeId);
//	}
//
//	@Override
//	public boolean equals(Object o) {
//		if (this == o)
//			return true;
//		if (o == null || getClass() != o.getClass())
//			return false;
//		WalletTypeKey other = (WalletTypeKey) o;
//		return institutionCode.equals(other.institutionCode) && walletTypeId.equals(other.walletTypeId);
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
//	public String getWalletTypeId() {
//		return walletTypeId;
//	}
//
//	public void setWalletTypeId(String walletTypeId) {
//		this.walletTypeId = walletTypeId;
//	}
//
//}
