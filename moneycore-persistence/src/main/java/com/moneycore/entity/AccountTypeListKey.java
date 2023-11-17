package com.moneycore.entity;

import java.io.Serializable;
import java.util.Objects;

//@Embeddable
public class AccountTypeListKey implements Serializable {

/**
	 * 
	 */
	private static final long serialVersionUID = -6686187487248227135L;
	//	private static final long serialVersionUID = 1L;
	private String institutionCode;
	private String accountTypeCode;

	public AccountTypeListKey() {
	}

	public AccountTypeListKey(String institutionCode, String accountTypeCode) {
		this.institutionCode = institutionCode;
		this.accountTypeCode = accountTypeCode;
	}

	@Override
	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + ((accountTypeCode == null) ? 0 : accountTypeCode.hashCode());
//		result = prime * result + ((institutionCode == null) ? 0 : institutionCode.hashCode());
		return Objects.hash(institutionCode,accountTypeCode);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass()) return false;
//		if (getClass() != obj.getClass())
//			return false;
		AccountTypeListKey other = (AccountTypeListKey) o;
		return institutionCode.equals(other.institutionCode) && accountTypeCode.equals(other.accountTypeCode);
//		if (accountTypeCode == null) {
//			if (other.accountTypeCode != null)
//				return false;
//		} else if (!accountTypeCode.equals(other.accountTypeCode))
//			return false;
//		if (institutionCode == null) {
//			if (other.institutionCode != null)
//				return false;
//		} else if (!institutionCode.equals(other.institutionCode))
//			return false;
//		return true;
	}

	public String getInstitutionCode() {
		return institutionCode;
	}

	public void setInstitutionCode(String institutionCode) {
		this.institutionCode = institutionCode;
	}

	public String getAccountTypeCode() {
		return accountTypeCode;
	}

	public void setAccountTypeCode(String accountTypeCode) {
		this.accountTypeCode = accountTypeCode;
	}

}
