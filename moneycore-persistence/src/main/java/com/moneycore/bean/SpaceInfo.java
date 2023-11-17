package com.moneycore.bean;

import java.util.List;

public class SpaceInfo {

	private String walletId;
	private String iban;
	private String spaceName;
	private List<SpaceAccountDetailsInfo> spaceAccountDetails;

	public String getWalletId() {
		return walletId;
	}

	public void setWalletId(String walletId) {
		this.walletId = walletId;
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

	public List<SpaceAccountDetailsInfo> getSpaceAccountDetails() {
		return spaceAccountDetails;
	}

	public void setSpaceAccountDetails(List<SpaceAccountDetailsInfo> spaceAccountDetails) {
		this.spaceAccountDetails = spaceAccountDetails;
	}

}
