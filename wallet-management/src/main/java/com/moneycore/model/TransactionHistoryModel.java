package com.moneycore.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TransactionHistoryModel {

	private String name;
	private Date transactionDate;
	private char status;
	private char transactionType;
	private double transactionAmount;
	private String description;
	private String transactionCode;
	private String transactionId;

}
