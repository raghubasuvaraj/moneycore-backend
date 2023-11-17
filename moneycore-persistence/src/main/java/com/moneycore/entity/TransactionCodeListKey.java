//package com.moneycore.entity;
//
//import java.io.Serializable;
//import java.util.Objects;
//
//public class TransactionCodeListKey implements Serializable {
//
//	private static final long serialVersionUID = 1L;
//
//	private String institutionCode;
//	private String transactionCode;
//
//	public TransactionCodeListKey() {
//	}
//
//	public TransactionCodeListKey(String institutionCode, String transactionCode) {
//		this.institutionCode = institutionCode;
//		this.transactionCode = transactionCode;
//	}
//
//	@Override
//	public int hashCode() {
//		return Objects.hash(institutionCode, transactionCode);
//	}
//
//	@Override
//	public boolean equals(Object o) {
//		if (this == o)
//			return true;
//		if (o == null || getClass() != o.getClass())
//			return false;
//		TransactionCodeListKey other = (TransactionCodeListKey) o;
//		return institutionCode.equals(other.institutionCode) && transactionCode.equals(other.transactionCode);
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
//	public String getTransactionCode() {
//		return transactionCode;
//	}
//
//	public void setTransactionCode(String transactionCode) {
//		this.transactionCode = transactionCode;
//	}
//
//}
