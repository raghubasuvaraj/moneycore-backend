package com.moneycore.entity;

import java.io.Serializable;
import java.util.Objects;

public class TransactionListKey implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5937378020893304025L;
	private String microfilmRefNumber;
	private Long microfilmRefSeq;

	public TransactionListKey() {
	}

	public TransactionListKey(String microfilmRefNumber, Long microfilmRefSeq) {
		this.microfilmRefNumber = microfilmRefNumber;
		this.microfilmRefSeq = microfilmRefSeq;
	}

	@Override
	public int hashCode() {
		return Objects.hash(microfilmRefNumber, microfilmRefSeq);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		TransactionListKey other = (TransactionListKey) o;
		return microfilmRefNumber.equals(other.microfilmRefNumber) && microfilmRefSeq.equals(other.microfilmRefSeq);
	}

	public String getMicrofilmRefNumber() {
		return microfilmRefNumber;
	}

	public void setMicrofilmRefNumber(String microfilmRefNumber) {
		this.microfilmRefNumber = microfilmRefNumber;
	}

	public Long getMicrofilmRefSeq() {
		return microfilmRefSeq;
	}

	public void setMicrofilmRefSeq(Long microfilmRefSeq) {
		this.microfilmRefSeq = microfilmRefSeq;
	}

}
