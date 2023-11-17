package com.moneycore.bean;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraints.Digits;

public class MpinUpdateInfo {

	@NotNull
	private int clientCode;

//	@Digits(integer = 4, fraction = 0, message="Number should contain 4 digits.")
//	@Size(min=4, max = 4, message="4 digit required")
	private String mpin;

	public int getClientCode() {
		return clientCode;
	}

	public void setClientCode(int clientCode) {
		this.clientCode = clientCode;
	}

	public String getMpin() {
		return mpin;
	}

	public void setMpin(String mpin) {
		this.mpin = mpin;
	}
}
