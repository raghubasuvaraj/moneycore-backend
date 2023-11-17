package com.moneycore.model;

public class ApiResponse {

	public ApiResponse() {

	}

	public ApiResponse(int statusCode, String status, String message, Object result) {
		this.statusCode = statusCode;
		this.status = status;
		this.message = message;
		this.result = result;
	}

	private int statusCode;
	private String status;
	private String message;
	private Object result;

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}
}
