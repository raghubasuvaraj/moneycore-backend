package com.moneycore.exception;

import javax.servlet.ServletException;

public class InvalidJwtToken extends ServletException {

	private static final long serialVersionUID = -4463347727685795368L;

	public InvalidJwtToken(String string) {
		super(string);
	}
}
