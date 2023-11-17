package com.moneycore.bean;

import java.util.ArrayList;
import java.util.List;

import com.moneycore.entity.SpaceAccounts;

public class SpaceAccountsResponse {

	private int id;
	private String iban;
	private String spaceName;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public String getSpaceName() {
		return spaceName;
	}

	public void setSpaceName(String spaceName) {
		this.spaceName = spaceName;
	}

	public static List<SpaceAccountsResponse> createResponse(List<SpaceAccounts> spaceAccounts) {
		List<SpaceAccountsResponse> spaceAccountsResponses = new ArrayList<SpaceAccountsResponse>();
		for (SpaceAccounts accounts : spaceAccounts) {
			SpaceAccountsResponse spaceAccountsResponse = new SpaceAccountsResponse();
			spaceAccountsResponse.setId(accounts.getSpaceAccountPk());
			spaceAccountsResponse.setIban(accounts.getIBAN());
			spaceAccountsResponse.setSpaceName(accounts.getSpaceName());
			spaceAccountsResponses.add(spaceAccountsResponse);
		}
		return spaceAccountsResponses;
	}

}
