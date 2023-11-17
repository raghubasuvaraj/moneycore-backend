package com.moneycore.bean;

public class ClientDocument {

	private String idType;
	private String idNo;
	private String idValidityDate;
	private String idIssuingDate;

	private String idFrontImage;

	private String idBackImage;

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getIdFrontImage() {
		return idFrontImage;
	}

	public void setIdFrontImage(String idFrontImage) {
		this.idFrontImage = idFrontImage;
	}

	public String getIdBackImage() {
		return idBackImage;
	}

	public void setIdBackImage(String idBackImage) {
		this.idBackImage = idBackImage;
	}

	public String getIdValidityDate() {
		return idValidityDate;
	}

	public void setIdValidityDate(String idValidityDate) {
		this.idValidityDate = idValidityDate;
	}

	public String getIdIssuingDate() {
		return idIssuingDate;
	}

	public void setIdIssuingDate(String idIssuingDate) {
		this.idIssuingDate = idIssuingDate;
	}
}
