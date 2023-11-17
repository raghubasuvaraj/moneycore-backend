package com.moneycore.bean;

import java.util.ArrayList;
import java.util.List;

import com.moneycore.entity.PricingProfile;

public class TransactionFee {

	private double totalFeeAmount;
	private List<TransactionFeeInfo> fee;

	public double getTotalFeeAmount() {
		return totalFeeAmount;
	}

	public void setTotalFeeAmount(double totalFeeAmount) {
		this.totalFeeAmount = totalFeeAmount;
	}

	public List<TransactionFeeInfo> getFee() {
		return fee;
	}

	public void setFee(List<TransactionFeeInfo> fee) {
		this.fee = fee;
	}

	public static TransactionFee createResponse(PricingProfile pricingProfile, String transactionType) {
		TransactionFee transactionFee = new TransactionFee();
		List<TransactionFeeInfo> feeInfos = new ArrayList<TransactionFeeInfo>();
		TransactionFeeInfo transactionFeeInfo = new TransactionFeeInfo();
		if (transactionType.equalsIgnoreCase("cashin")) {
			transactionFee.setTotalFeeAmount(pricingProfile.getToupFees());
			transactionFeeInfo.setFeeName("TopUp Fee");
			transactionFeeInfo.setFeeAmount(pricingProfile.getToupFees());
		}
		if (transactionType.equalsIgnoreCase("transfer")) {
			transactionFee.setTotalFeeAmount(pricingProfile.getFeesAmountFirst());
			transactionFeeInfo.setFeeName("Fees Amount");
			transactionFeeInfo.setFeeAmount(pricingProfile.getFeesAmountFirst());
		}
		if (transactionType.equalsIgnoreCase("purchase")) {
			transactionFee.setTotalFeeAmount(pricingProfile.getOpsFees());
			transactionFeeInfo.setFeeName("Operation Fees");
			transactionFeeInfo.setFeeAmount(pricingProfile.getOpsFees());
		}
		feeInfos.add(transactionFeeInfo);
		transactionFee.setFee(feeInfos);
		return transactionFee;
	}

}
