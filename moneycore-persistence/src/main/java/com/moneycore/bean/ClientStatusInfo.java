package com.moneycore.bean;

import javax.validation.constraints.Size;
import java.util.Date;

public class ClientStatusInfo {

	@Size(min = 1, max = 1)
	private String statusCode;
	@Size(min = 1, max = 20)
	private String statusReason;
	private String userModif;
	private String statusFromDate;
	private String statusToDate;

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

	public String getUserModif() {
		return userModif;
	}

	public void setUserModif(String userModif) {
		this.userModif = userModif;
	}

	public String getStatusFromDate() {
		return statusFromDate;
	}

	public void setStatusFromDate(String statusFromDate) {
		this.statusFromDate = statusFromDate;
	}

	public String getStatusToDate() {
		return statusToDate;
	}

	public void setStatusToDate(String statusToDate) {
		this.statusToDate = statusToDate;
	}
}
