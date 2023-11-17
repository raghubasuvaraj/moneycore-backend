package com.moneycore.bean;

import java.util.List;

public class StatementInfo {

	private int totalPages;
	private double openingBalance;
	private double closingBalance;
	private double totalDebit;
	private double totalCredit;
	private List<StatementTransactionInfo> statement;

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public double getOpeningBalance() {
		return openingBalance;
	}

	public void setOpeningBalance(double openingBalance) {
		this.openingBalance = openingBalance;
	}

	public double getClosingBalance() {
		return closingBalance;
	}

	public void setClosingBalance(double closingBalance) {
		this.closingBalance = closingBalance;
	}

	public double getTotalDebit() {
		return totalDebit;
	}

	public void setTotalDebit(double totalDebit) {
		this.totalDebit = totalDebit;
	}

	public double getTotalCredit() {
		return totalCredit;
	}

	public void setTotalCredit(double totalCredit) {
		this.totalCredit = totalCredit;
	}

	public List<StatementTransactionInfo> getStatement() {
		return statement;
	}

	public void setStatement(List<StatementTransactionInfo> statement) {
		this.statement = statement;
	}

}
