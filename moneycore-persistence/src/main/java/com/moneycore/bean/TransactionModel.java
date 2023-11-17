package com.moneycore.bean;

import java.util.Date;

import com.moneycore.entity.TransactionList;
import com.moneycore.entity.WalletBalance;

public class TransactionModel {

	/**
	 *
	 * 'C' - CREDIT
	 * 'D' - DEBIT
	 * Microfilm Reference Sequence : 1 - normal, 2 - reversal
	 * @param transactionType
	 * @param transactionAvailableBalance
	 *
	 */
	public static TransactionList createTransaction(WalletBalance walBal, String transactionCode, String reason,
			double amount, String currency, String typeOfTransfer, String referenceNumber, String name, char transactionType, double transactionAvailableBalance) {
		if(name == null) {
			name = "";
		}
		Date date = new Date();
		TransactionList transactionList = new TransactionList();
		transactionList.setMicrofilmRefNumber(referenceNumber);
		transactionList.setMicrofilmRefSeq(1L);
		if(walBal.getWalletId().getClientCode().getIsMerchant()) {
			transactionList.setMerchantNumber(walBal.getInstitutionCode().getInstitutionCode());
			transactionList.setMerchantAcronym(walBal.getInstitutionCode().getInstitutionName());
			transactionList.setMerchantCountryCode(walBal.getInstitutionCode().getBussinessAddress());
			transactionList.setMerchantCityCode(walBal.getInstitutionCode().getBussinessAddress());
			transactionList.setMerchantCityName(walBal.getInstitutionCode().getBussinessAddress());
		}
		if(walBal.getBranchCode()!=null)
			transactionList.setOutletNumber(walBal.getBranchCode().getBranchCode());
		transactionList.setTerminalId("Transaction webService");
		transactionList.setMcc("0000");
		transactionList.setTcc('0');
		transactionList.setMccGroup("0000");
		transactionList.setClientCode(Integer.toString(walBal.getClientCode().getClientCode()));
		transactionList.setWalletId(walBal.getWalletId().getWalletId());
		transactionList.setWalletAccountNbr(walBal.getAccountNumber().getAccountNumber());
		transactionList.setTransactionCode(transactionCode);
		transactionList.setReversalFlag('N');
		if (typeOfTransfer.equalsIgnoreCase("T2W")) {
			transactionList.setTransactionSign('C');
			transactionList.setTransactionWording("Top Up transaction " + name);
			transactionList.setTransactionDescription("Top Up transaction");
		} else if (typeOfTransfer.equalsIgnoreCase("W2W")) {
			transactionList.setTransactionSign('D');
			transactionList.setTransactionWording("Transfer transaction " + name);
			transactionList.setTransactionDescription("Transfer transaction");
		} else if (typeOfTransfer.equalsIgnoreCase("R2W")) {
			transactionList.setTransactionSign('C');
			transactionList.setTransactionWording("Received transaction " + name);
			transactionList.setTransactionDescription("Received transaction");
		} else if (typeOfTransfer.equalsIgnoreCase("A2W")) {
			transactionList.setTransactionSign(transactionType);
			transactionList.setTransactionWording("Adjust transaction " + name);
			transactionList.setTransactionDescription("Adjust transaction");
		} else if (typeOfTransfer.equalsIgnoreCase("PUR2W")) {
			transactionList.setTransactionSign('D');
			transactionList.setTransactionWording("Purchase transaction " + name);
			transactionList.setTransactionDescription("Purchase transaction");
		} else if (typeOfTransfer.equalsIgnoreCase("PAY2W")) {
			transactionList.setTransactionSign('C');
			transactionList.setTransactionWording("Payment transaction " + name);
			transactionList.setTransactionDescription("Payment transaction");
		} else if (typeOfTransfer.equalsIgnoreCase("W2S")) {
			transactionList.setTransactionSign('D');
			transactionList.setTransactionWording("Wallet To Space transaction " + name);
			transactionList.setTransactionDescription("Wallet To Space transaction");
		} else if (typeOfTransfer.equalsIgnoreCase("REG2W")) {
			transactionList.setTransactionSign('D');
			transactionList.setTransactionWording("Registration transaction " + name);
			transactionList.setTransactionDescription("Registration transaction");
		} else if (typeOfTransfer.equalsIgnoreCase("F2W")) {
			transactionList.setTransactionSign('D');
			transactionList.setTransactionWording("Top Up Fee transaction " + name);
			transactionList.setTransactionDescription("Top Up Fee transaction");
		} else if (typeOfTransfer.equalsIgnoreCase("FT2W")) {
			transactionList.setTransactionSign('D');
			transactionList.setTransactionWording("Transfer Fee transaction " + name);
			transactionList.setTransactionDescription("Transfer Fee transaction");
		} else if (typeOfTransfer.equalsIgnoreCase("FPUR2W")) {
			transactionList.setTransactionSign('D');
			transactionList.setTransactionWording("Purchase Fee transaction " + name);
			transactionList.setTransactionDescription("Purchase Fee transaction");
		} else if (typeOfTransfer.equalsIgnoreCase("S2W")) {
			transactionList.setTransactionSign('C');
			transactionList.setTransactionWording("Space To Wallet transaction " + name);
			transactionList.setTransactionDescription("Space To Wallet transaction");
		}
		transactionList.setTransactionDate(date);
		transactionList.setProcessingDate(date);
		transactionList.setRemittanceDate(date);
		transactionList.setDiscountAmountHt(0);
		transactionList.setDiscountAmountTtc(0);
		transactionList.setChTransactionFeesHt(0.0);
		transactionList.setChTransactionFeesTtc(0.0);
		transactionList.setTransactionAmount(amount);
		transactionList.setTransactionCurrency(currency);
		double precisionAmount = Double.parseDouble(String.format("%2f", amount));
		transactionList.setTransactionCurrencyExp(precisionAmount);
		transactionList.setBillingAmount(amount);
		transactionList.setBillingCurrency(currency);
		transactionList.setBillingCurrencyExp(precisionAmount);
		transactionList.setSettlementAmount(amount);
		transactionList.setSettlementCurrency(currency);
		transactionList.setSettlementCurrencyExp(precisionAmount);
		transactionList.setLocalAmount(amount);
		transactionList.setLocalCurrency(currency);
		transactionList.setLocalCurrencyExp(precisionAmount);
		transactionList.setIssuerCountry(walBal.getInstitutionCode().getBussinessAddress());
		transactionList.setClearingDate(date);
		if(walBal.getBalance() == null || walBal.getBalance() == 0.0) {
			transactionList.setAvailableBalance(0.0);
		}
		transactionList.setAvailableBalance(transactionAvailableBalance);

		return transactionList;
	}

	public static TransactionList createTransactionSplit(WalletBalance walBal, String transactionCode, String reason,
													double amount, String currency, String typeOfTransfer, String referenceNumber, String name, char transactionType, double transactionAvailableBalance,String referenceCode) {
		if(name == null) {
			name = "";
		}
		Date date = new Date();
		TransactionList transactionList = new TransactionList();
		transactionList.setMicrofilmRefNumber(referenceNumber);
		transactionList.setMicrofilmRefSeq(1L);
		if(walBal.getWalletId().getClientCode().getIsMerchant()) {
			transactionList.setMerchantNumber(walBal.getInstitutionCode().getInstitutionCode());
			transactionList.setMerchantAcronym(walBal.getInstitutionCode().getInstitutionName());
			transactionList.setMerchantCountryCode(walBal.getInstitutionCode().getBussinessAddress());
			transactionList.setMerchantCityCode(walBal.getInstitutionCode().getBussinessAddress());
			transactionList.setMerchantCityName(walBal.getInstitutionCode().getBussinessAddress());
		}
		if(walBal.getBranchCode()!=null)
			transactionList.setOutletNumber(walBal.getBranchCode().getBranchCode());
		transactionList.setTerminalId("Transaction webService");
		transactionList.setMcc("0000");
		transactionList.setTcc('0');
		transactionList.setMccGroup("0000");
		transactionList.setClientCode(Integer.toString(walBal.getClientCode().getClientCode()));
		transactionList.setWalletId(walBal.getWalletId().getWalletId());
		transactionList.setWalletAccountNbr(walBal.getAccountNumber().getAccountNumber());
		transactionList.setTransactionCode(transactionCode);
		transactionList.setReversalFlag('N');
		if (typeOfTransfer.equalsIgnoreCase("T2W")) {
			transactionList.setTransactionSign('C');
			transactionList.setTransactionWording("Top Up transaction " + name);
			transactionList.setTransactionDescription("Top Up transaction");
		} else if (typeOfTransfer.equalsIgnoreCase("W2W")) {
			transactionList.setTransactionSign('D');
			transactionList.setTransactionWording("Transfer transaction " + name);
			transactionList.setTransactionDescription("Transfer transaction");
		} else if (typeOfTransfer.equalsIgnoreCase("R2W")) {
			transactionList.setTransactionSign('C');
			transactionList.setTransactionWording("Received transaction " + name);
			transactionList.setTransactionDescription("Received transaction");
		} else if (typeOfTransfer.equalsIgnoreCase("A2W")) {
			transactionList.setTransactionSign(transactionType);
			transactionList.setTransactionWording("Adjust transaction " + name);
			transactionList.setTransactionDescription("Adjust transaction");
		} else if (typeOfTransfer.equalsIgnoreCase("PUR2W")) {
			transactionList.setTransactionSign('D');
			transactionList.setTransactionWording("Purchase transaction " + name);
			transactionList.setTransactionDescription("Purchase transaction");
		} else if (typeOfTransfer.equalsIgnoreCase("PAY2W")) {
			transactionList.setTransactionSign('C');
			transactionList.setTransactionWording("Payment transaction " + name);
			transactionList.setTransactionDescription("Payment transaction");
		} else if (typeOfTransfer.equalsIgnoreCase("W2S")) {
			transactionList.setTransactionSign('D');
			transactionList.setTransactionWording("Wallet To Space transaction " + name);
			transactionList.setTransactionDescription("Wallet To Space transaction");
		} else if (typeOfTransfer.equalsIgnoreCase("REG2W")) {
			transactionList.setTransactionSign('D');
			transactionList.setTransactionWording("Registration transaction " + name);
			transactionList.setTransactionDescription("Registration transaction");
		} else if (typeOfTransfer.equalsIgnoreCase("F2W")) {
			transactionList.setTransactionSign('D');
			transactionList.setTransactionWording("Top Up Fee transaction " + name);
			transactionList.setTransactionDescription("Top Up Fee transaction");
		} else if (typeOfTransfer.equalsIgnoreCase("FT2W")) {
			transactionList.setTransactionSign('D');
			transactionList.setTransactionWording("Transfer Fee transaction " + name);
			transactionList.setTransactionDescription("Transfer Fee transaction");
		} else if (typeOfTransfer.equalsIgnoreCase("FPUR2W")) {
			transactionList.setTransactionSign('D');
			transactionList.setTransactionWording("Purchase Fee transaction " + name);
			transactionList.setTransactionDescription("Purchase Fee transaction");
		} else if (typeOfTransfer.equalsIgnoreCase("S2W")) {
			transactionList.setTransactionSign('C');
			transactionList.setTransactionWording("Space To Wallet transaction " + name);
			transactionList.setTransactionDescription("Space To Wallet transaction");
		}
		transactionList.setTransactionDate(date);
		transactionList.setProcessingDate(date);
		transactionList.setRemittanceDate(date);
		transactionList.setDiscountAmountHt(0);
		transactionList.setDiscountAmountTtc(0);
		transactionList.setChTransactionFeesHt(0.0);
		transactionList.setChTransactionFeesTtc(0.0);
		transactionList.setTransactionAmount(amount);
		transactionList.setTransactionCurrency(currency);
		double precisionAmount = Double.parseDouble(String.format("%2f", amount));
		transactionList.setTransactionCurrencyExp(precisionAmount);
		transactionList.setBillingAmount(amount);
		transactionList.setBillingCurrency(currency);
		transactionList.setBillingCurrencyExp(precisionAmount);
		transactionList.setSettlementAmount(amount);
		transactionList.setSettlementCurrency(currency);
		transactionList.setSettlementCurrencyExp(precisionAmount);
		transactionList.setLocalAmount(amount);
		transactionList.setLocalCurrency(currency);
		transactionList.setLocalCurrencyExp(precisionAmount);
		transactionList.setIssuerCountry(walBal.getInstitutionCode().getBussinessAddress());
		transactionList.setClearingDate(date);
		transactionList.setReferenceCode(referenceCode);
		if(walBal.getBalance() == null || walBal.getBalance() == 0.0) {
			transactionList.setAvailableBalance(0.0);
		}
		transactionList.setAvailableBalance(transactionAvailableBalance);

		return transactionList;
	}
}
