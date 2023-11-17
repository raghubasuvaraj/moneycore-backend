package com.moneycore.bean;

import com.moneycore.entity.Wallet;

public class WalletUpdateInfo {

	private String statusCode;
	private String statusReason;
	private String controlProfile;
	private String pricingProfile;
	private String walletType;
	private String userModif;

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusReason() {
		return statusReason;
	}

	public void setStatusReason(String statusReason) {
		this.statusReason = statusReason;
	}

	public String getControlProfile() {
		return controlProfile;
	}

	public void setControlProfile(String controlProfile) {
		this.controlProfile = controlProfile;
	}

	public String getPricingProfile() {
		return pricingProfile;
	}

	public void setPricingProfile(String pricingProfile) {
		this.pricingProfile = pricingProfile;
	}

	public String getWalletType() {
		return walletType;
	}

	public void setWalletType(String walletType) {
		this.walletType = walletType;
	}

	public String getUserModif() {
		return userModif;
	}

	public void setUserModif(String userModif) {
		this.userModif = userModif;
	}

	public static WalletUpdateInfo createResponse(Wallet walletResponse) {
		WalletUpdateInfo walletUpdateInfo = new WalletUpdateInfo();
		walletUpdateInfo.setControlProfile(walletResponse.getControlProfile().getControlIndex());
		walletUpdateInfo.setPricingProfile(walletResponse.getPricingProfile().getPricingIndex());
		walletUpdateInfo.setWalletType(walletResponse.getWalletType().getWalletTypeId());
		walletUpdateInfo.setStatusCode(walletResponse.getStatusCode().getStatusCode());
		walletUpdateInfo.setStatusReason(walletResponse.getStatusReason().getStatusReasonCode());
		walletUpdateInfo.setUserModif(walletResponse.getUserModified());
		return walletUpdateInfo;
	}

}
