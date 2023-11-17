package com.moneycore.exception;

public class UnauthorizedRequestException extends RuntimeException {

	private static final long serialVersionUID = 8072220927185332147L;

	public UnauthorizedRequestException(String string) {
		super(string);
	}
}
