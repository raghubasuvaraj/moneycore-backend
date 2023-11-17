package com.moneycore.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "transaction_list")
@IdClass(TransactionListKey.class)
public class TransactionList implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "microfilm_ref_number")
	private String microfilmRefNumber;
	@Id
	@Column(name = "microfilm_ref_seq")
	private Long microfilmRefSeq;
	@Column(name = "merchant_number")
	private String merchantNumber;
	@Column(name = "outlet_number")
	private String outletNumber;
	@Column(name = "terminal_id")
	private String terminalId;
	@Column(name = "merchant_acronym")
	private String merchantAcronym;
	@Column(name = "merchant_country_code")
	private String merchantCountryCode;
	@Column(name = "merchant_city_code")
	private String merchantCityCode;
	@Column(name = "merchant_city_name")
	private String merchantCityName;
	@Column(name = "mcc")
	private String mcc;
	@Column(name = "tcc")
	private char tcc;
	@Column(name = "mcc_group")
	private String mccGroup;
	@Column(name = "client_code")
	private String clientCode;
	@Column(name = "wallet_id")
	private String walletId;
	@Column(name = "wallet_account_nbr")
	private String walletAccountNbr;
	@Column(name = "transaction_code")
	private String transactionCode;
	@Column(name = "transaction_sign")
	private char transactionSign;
	@Column(name = "reversal_flag")
	private char reversalFlag;
	@Column(name = "transaction_wording")
	private String transactionWording;
	@Column(name = "transaction_description")
	private String transactionDescription;
	@Column(name = "transaction_date")
	private Date transactionDate;
	@Column(name = "processing_date")
	private Date processingDate;
	@Column(name = "remittance_date")
	private Date remittanceDate;
	@Column(name = "discount_amount_ht")
	private double discountAmountHt;
	@Column(name = "discount_amount_ttc")
	private double discountAmountTtc;
	@Column(name = "ch_transaction_fees_ht")
	private double chTransactionFeesHt;
	@Column(name = "ch_transaction_fees_ttc")
	private double chTransactionFeesTtc;
	@Column(name = "transaction_amount")
	private double transactionAmount;
	@Column(name = "transaction_currency")
	private String transactionCurrency;
	@Column(name = "transaction_currency_exp")
	private double transactionCurrencyExp;
	@Column(name = "billing_amount")
	private double billingAmount;
	@Column(name = "billing_currency")
	private String billingCurrency;
	@Column(name = "billing_currency_exp")
	private double billingCurrencyExp;
	@Column(name = "settlement_amount")
	private double settlementAmount;
	@Column(name = "settlement_currency")
	private String settlementCurrency;
	@Column(name = "settlement_currency_exp")
	private double settlementCurrencyExp;
	@Column(name = "local_amount")
	private double localAmount;
	@Column(name = "local_currency")
	private String localCurrency;
	@Column(name = "local_currency_exp")
	private double localCurrencyExp;
	@Column(name = "issuer_country")
	private String issuerCountry;
	@Column(name = "private_data_1")
	private String privateData1;
	@Column(name = "private_data_2")
	private String privateData2;
	@Column(name = "private_data_3")
	private Date privateData3;
	@Column(name = "private_data_4")
	private Date privateData4;
	@Column(name = "private_data_5")
	private double privateData5;
	@Column(name = "private_data_6")
	private double privateData6;
	@Column(name = "private_tlv_data_autho")
	private String privateTlvDataAutho;
	@Column(name = "private_tlv_data")
	private String privateTlvData;
	@Column(name = "clearing_date")
	private Date clearingDate;
	@Column(name = "clearing_ref")
	private String clearingRef;
	@Column(name = "user_create")
	private String userCreate;
	@Column(name = "date_create")
	private Date dateCreate;
	@Column(name = "user_modif")
	private String userModif;
	@Column(name = "date_modif")
	private Date dateModif;
	@Column(name = "available_Balance")
	private double availableBalance;
	@Column(name = "reference_code")
	private String referenceCode;

	public String getReferenceCode() {
		return referenceCode;
	}

	public void setReferenceCode(String referenceCode) {
		this.referenceCode = referenceCode;
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

	public String getMerchantNumber() {
		return merchantNumber;
	}

	public void setMerchantNumber(String merchantNumber) {
		this.merchantNumber = merchantNumber;
	}

	public String getOutletNumber() {
		return outletNumber;
	}

	public void setOutletNumber(String outletNumber) {
		this.outletNumber = outletNumber;
	}

	public String getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	public String getMerchantAcronym() {
		return merchantAcronym;
	}

	public void setMerchantAcronym(String merchantAcronym) {
		this.merchantAcronym = merchantAcronym;
	}

	public String getMerchantCountryCode() {
		return merchantCountryCode;
	}

	public void setMerchantCountryCode(String merchantCountryCode) {
		this.merchantCountryCode = merchantCountryCode;
	}

	public String getMerchantCityCode() {
		return merchantCityCode;
	}

	public void setMerchantCityCode(String merchantCityCode) {
		this.merchantCityCode = merchantCityCode;
	}

	public String getMerchantCityName() {
		return merchantCityName;
	}

	public void setMerchantCityName(String merchantCityName) {
		this.merchantCityName = merchantCityName;
	}

	public String getMcc() {
		return mcc;
	}

	public void setMcc(String mcc) {
		this.mcc = mcc;
	}

	public char getTcc() {
		return tcc;
	}

	public void setTcc(char tcc) {
		this.tcc = tcc;
	}

	public String getMccGroup() {
		return mccGroup;
	}

	public void setMccGroup(String mccGroup) {
		this.mccGroup = mccGroup;
	}

	public String getClientCode() {
		return clientCode;
	}

	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}

	public String getWalletId() {
		return walletId;
	}

	public void setWalletId(String walletId) {
		this.walletId = walletId;
	}

	public String getWalletAccountNbr() {
		return walletAccountNbr;
	}

	public void setWalletAccountNbr(String walletAccountNbr) {
		this.walletAccountNbr = walletAccountNbr;
	}

	public String getTransactionCode() {
		return transactionCode;
	}

	public void setTransactionCode(String transactionCode) {
		this.transactionCode = transactionCode;
	}

	public char getTransactionSign() {
		return transactionSign;
	}

	public void setTransactionSign(char transactionSign) {
		this.transactionSign = transactionSign;
	}

	public char getReversalFlag() {
		return reversalFlag;
	}

	public void setReversalFlag(char reversalFlag) {
		this.reversalFlag = reversalFlag;
	}

	public String getTransactionWording() {
		return transactionWording;
	}

	public void setTransactionWording(String transactionWording) {
		this.transactionWording = transactionWording;
	}

	public String getTransactionDescription() {
		return transactionDescription;
	}

	public void setTransactionDescription(String transactionDescription) {
		this.transactionDescription = transactionDescription;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public Date getProcessingDate() {
		return processingDate;
	}

	public void setProcessingDate(Date processingDate) {
		this.processingDate = processingDate;
	}

	public Date getRemittanceDate() {
		return remittanceDate;
	}

	public void setRemittanceDate(Date remittanceDate) {
		this.remittanceDate = remittanceDate;
	}

	public double getDiscountAmountHt() {
		return discountAmountHt;
	}

	public void setDiscountAmountHt(double discountAmountHt) {
		this.discountAmountHt = discountAmountHt;
	}

	public double getDiscountAmountTtc() {
		return discountAmountTtc;
	}

	public void setDiscountAmountTtc(double discountAmountTtc) {
		this.discountAmountTtc = discountAmountTtc;
	}

	public double getChTransactionFeesHt() {
		return chTransactionFeesHt;
	}

	public void setChTransactionFeesHt(double chTransactionFeesHt) {
		this.chTransactionFeesHt = chTransactionFeesHt;
	}

	public double getChTransactionFeesTtc() {
		return chTransactionFeesTtc;
	}

	public void setChTransactionFeesTtc(double chTransactionFeesTtc) {
		this.chTransactionFeesTtc = chTransactionFeesTtc;
	}

	public double getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(double transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public String getTransactionCurrency() {
		return transactionCurrency;
	}

	public void setTransactionCurrency(String transactionCurrency) {
		this.transactionCurrency = transactionCurrency;
	}

	public double getTransactionCurrencyExp() {
		return transactionCurrencyExp;
	}

	public void setTransactionCurrencyExp(double transactionCurrencyExp) {
		this.transactionCurrencyExp = transactionCurrencyExp;
	}

	public double getBillingAmount() {
		return billingAmount;
	}

	public void setBillingAmount(double billingAmount) {
		this.billingAmount = billingAmount;
	}

	public String getBillingCurrency() {
		return billingCurrency;
	}

	public void setBillingCurrency(String billingCurrency) {
		this.billingCurrency = billingCurrency;
	}

	public double getBillingCurrencyExp() {
		return billingCurrencyExp;
	}

	public void setBillingCurrencyExp(double billingCurrencyExp) {
		this.billingCurrencyExp = billingCurrencyExp;
	}

	public double getSettlementAmount() {
		return settlementAmount;
	}

	public void setSettlementAmount(double settlementAmount) {
		this.settlementAmount = settlementAmount;
	}

	public String getSettlementCurrency() {
		return settlementCurrency;
	}

	public void setSettlementCurrency(String settlementCurrency) {
		this.settlementCurrency = settlementCurrency;
	}

	public double getSettlementCurrencyExp() {
		return settlementCurrencyExp;
	}

	public void setSettlementCurrencyExp(double settlementCurrencyExp) {
		this.settlementCurrencyExp = settlementCurrencyExp;
	}

	public double getLocalAmount() {
		return localAmount;
	}

	public void setLocalAmount(double localAmount) {
		this.localAmount = localAmount;
	}

	public String getLocalCurrency() {
		return localCurrency;
	}

	public void setLocalCurrency(String localCurrency) {
		this.localCurrency = localCurrency;
	}

	public double getLocalCurrencyExp() {
		return localCurrencyExp;
	}

	public void setLocalCurrencyExp(double localCurrencyExp) {
		this.localCurrencyExp = localCurrencyExp;
	}

	public String getIssuerCountry() {
		return issuerCountry;
	}

	public void setIssuerCountry(String issuerCountry) {
		this.issuerCountry = issuerCountry;
	}

	public String getPrivateData1() {
		return privateData1;
	}

	public void setPrivateData1(String privateData1) {
		this.privateData1 = privateData1;
	}

	public String getPrivateData2() {
		return privateData2;
	}

	public void setPrivateData2(String privateData2) {
		this.privateData2 = privateData2;
	}

	public Date getPrivateData3() {
		return privateData3;
	}

	public void setPrivateData3(Date privateData3) {
		this.privateData3 = privateData3;
	}

	public Date getPrivateData4() {
		return privateData4;
	}

	public void setPrivateData4(Date privateData4) {
		this.privateData4 = privateData4;
	}

	public double getPrivateData5() {
		return privateData5;
	}

	public void setPrivateData5(double privateData5) {
		this.privateData5 = privateData5;
	}

	public double getPrivateData6() {
		return privateData6;
	}

	public void setPrivateData6(double privateData6) {
		this.privateData6 = privateData6;
	}

	public String getPrivateTlvDataAutho() {
		return privateTlvDataAutho;
	}

	public void setPrivateTlvDataAutho(String privateTlvDataAutho) {
		this.privateTlvDataAutho = privateTlvDataAutho;
	}

	public String getPrivateTlvData() {
		return privateTlvData;
	}

	public void setPrivateTlvData(String privateTlvData) {
		this.privateTlvData = privateTlvData;
	}

	public Date getClearingDate() {
		return clearingDate;
	}

	public void setClearingDate(Date clearingDate) {
		this.clearingDate = clearingDate;
	}

	public String getClearingRef() {
		return clearingRef;
	}

	public void setClearingRef(String clearingRef) {
		this.clearingRef = clearingRef;
	}

	public String getUserCreate() {
		return userCreate;
	}

	public void setUserCreate(String userCreate) {
		this.userCreate = userCreate;
	}

	public Date getDateCreate() {
		return dateCreate;
	}

	public void setDateCreate(Date dateCreate) {
		this.dateCreate = dateCreate;
	}

	public String getUserModif() {
		return userModif;
	}

	public void setUserModif(String userModif) {
		this.userModif = userModif;
	}

	public Date getDateModif() {
		return dateModif;
	}

	public void setDateModif(Date dateModif) {
		this.dateModif = dateModif;
	}

	public double getAvailableBalance() {
		return availableBalance;
	}

	public void setAvailableBalance(double availableBalance) {
		this.availableBalance = availableBalance;
	}

}
