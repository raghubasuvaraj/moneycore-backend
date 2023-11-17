package com.moneycore.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.moneycore.entity.Wallet;
import com.moneycore.entity.WalletSpace;

public class WalletSpaceInfoResponse {

	private InstitutionListInfo institutionCode;
	private String spaceId;
	private String spaceName;
	private Wallet walletId;
	private String description;
	private String accountNumber;
	private Date targetDate;
	private double amount;

	public InstitutionListInfo getInstitutionCode() {
		return institutionCode;
	}

	public void setInstitutionCode(InstitutionListInfo institutionCode) {
		this.institutionCode = institutionCode;
	}

	public String getSpaceId() {
		return spaceId;
	}

	public void setSpaceId(String spaceId) {
		this.spaceId = spaceId;
	}

	public String getSpaceName() {
		return spaceName;
	}

	public void setSpaceName(String spaceName) {
		this.spaceName = spaceName;
	}

	public Wallet getWalletId() {
		return walletId;
	}

	public void setWalletId(Wallet walletId) {
		this.walletId = walletId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public Date getTargetDate() {
		return targetDate;
	}

	public void setTargetDate(Date targetDate) {
		this.targetDate = targetDate;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public static List<WalletSpaceInfoResponse> createResponse(List<WalletSpace> walletSpaces) {
		List<WalletSpaceInfoResponse> walletSpaceInfoResponses = new ArrayList<WalletSpaceInfoResponse>();
		for (WalletSpace space : walletSpaces) {
			WalletSpaceInfoResponse walletSpaceInfoResponse = new WalletSpaceInfoResponse();
			InstitutionListInfo info = InstitutionListInfo.createSingleResponse(space.getInstitutionCode());
			walletSpaceInfoResponse.setInstitutionCode(info);
			walletSpaceInfoResponse.setWalletId(space.getWalletId());
			walletSpaceInfoResponse.setSpaceName(space.getSpaceName());
			walletSpaceInfoResponse.setSpaceId(space.getSpaceId());
			walletSpaceInfoResponse.setAmount(space.getAmount());
			walletSpaceInfoResponse.setTargetDate(space.getTargetDate());
			walletSpaceInfoResponse.setDescription(space.getDescription());
			walletSpaceInfoResponse.setAccountNumber(space.getAccountNumber());
			walletSpaceInfoResponses.add(walletSpaceInfoResponse);
		}
		return walletSpaceInfoResponses;
	}

}
