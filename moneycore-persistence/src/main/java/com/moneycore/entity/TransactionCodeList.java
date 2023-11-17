package com.moneycore.entity;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "transaction_code_list")
//@IdClass(TransactionCodeListKey.class)

public class TransactionCodeList implements Serializable {

	private static final long serialVersionUID = 1L;

//	@Id
	@Column(name = "institution_code", length = 6)
	private String institutionCode;
	@Id
	@Column(name = "transaction_code", length = 3)
	private String transactionCode;
	@Column(name = "transaction_name", length = 35)
	private String transactionName;
	@Column(name = "abrv_wording", length = 16)
	private String abrvWording;
	@Column(name = "wording", length = 32)
	private String wording;
	@Column(name = "user_create", length = 15)
	private String userCreate;
	@Column(name = "date_create")
	@Temporal(TemporalType.DATE)
	private Date dateCreate;
	@Column(name = "user_modif", length = 15)
	private String userModif;
	@Column(name = "date_modif")
	@Temporal(TemporalType.DATE)
	private Date dateModif;
	@ManyToOne
	@JoinColumn(name = "service_code")
	private ServiceList serviceList;




	public String getInstitutionCode() {
		return institutionCode;
	}

	public void setInstitutionCode(String institutionCode) {
		this.institutionCode = institutionCode;
	}

	public String getTransactionCode() {
		return transactionCode;
	}

	public void setTransactionCode(String transactionCode) {
		this.transactionCode = transactionCode;
	}

	public String getTransactionName() {
		return transactionName;
	}

	public void setTransactionName(String transactionName) {
		this.transactionName = transactionName;
	}

	public String getAbrvWording() {
		return abrvWording;
	}

	public void setAbrvWording(String abrvWording) {
		this.abrvWording = abrvWording;
	}

	public String getWording() {
		return wording;
	}

	public void setWording(String wording) {
		this.wording = wording;
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

	public ServiceList getServiceList() {
		return serviceList;
	}

	public void setServiceList(ServiceList serviceList) {
		this.serviceList = serviceList;
	}
}
