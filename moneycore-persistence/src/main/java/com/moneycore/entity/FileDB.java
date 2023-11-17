package com.moneycore.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "files")
public class FileDB {
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;

	private String frontName;

	private String backName;

	private String type;

	private int clientId;

	@Column(columnDefinition = "LONGTEXT")
	private String frontData;

	@Column(columnDefinition = "LONGTEXT")
	private String backData;

	private String idType;

	private String idNumber;
	private String idIssuingDate;
	private String idValidityDate;

	public FileDB() {
	}

	public FileDB(String name, String type, byte[] data, int id) {
//		this.name = name;
		this.type = type;
//		this.data = data;
		this.clientId = id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public String getFrontName() {
		return frontName;
	}

	public void setFrontName(String frontName) {
		this.frontName = frontName;
	}

	public String getBackName() {
		return backName;
	}

	public void setBackName(String backName) {
		this.backName = backName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFrontData() {
		return frontData;
	}

	public void setFrontData(String frontData) {
		this.frontData = frontData;
	}

	public String getBackData() {
		return backData;
	}

	public void setBackData(String backData) {
		this.backData = backData;
	}

	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getIdIssuingDate() {
		return idIssuingDate;
	}

	public void setIdIssuingDate(String idIssuingDate) {
		this.idIssuingDate = idIssuingDate;
	}

	public String getIdValidityDate() {
		return idValidityDate;
	}

	public void setIdValidityDate(String idValidityDate) {
		this.idValidityDate = idValidityDate;
	}
}
