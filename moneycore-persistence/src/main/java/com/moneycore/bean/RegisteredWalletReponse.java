package com.moneycore.bean;

import com.moneycore.entity.Client;

public class RegisteredWalletReponse {

	private String firstName;
	private String middleName;
	private String lastName;
	private String walletId;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getWalletId() {
		return walletId;
	}

	public void setWalletId(String walletId) {
		this.walletId = walletId;
	}

	public static RegisteredWalletReponse createResponse(Client client, String walletId2) {
		RegisteredWalletReponse registeredWalletReponse = new RegisteredWalletReponse();
		registeredWalletReponse.setFirstName(client.getFirstName());
		registeredWalletReponse.setMiddleName(client.getMiddleName());
		registeredWalletReponse.setLastName(client.getLastName());
		registeredWalletReponse.setWalletId(walletId2);
		return registeredWalletReponse;
	}

}
