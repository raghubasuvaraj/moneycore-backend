package com.moneycore.entity;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "status_reason_code")

public class StatusReasonCode implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "institution_code", length = 6)
	private String institutionCode;
	@OneToOne
	@JoinColumn(name = "status_code")
	private StatusList statusCode;
	@Id
	@Column(name = "status_reason_code")
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String statusReasonCode;
	@Column(name = "status_reason_name", length = 35)
	private String statusReasonName;
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



	public String getInstitutionCode() {
		return institutionCode;
	}

	public void setInstitutionCode(String institutionCode) {
		this.institutionCode = institutionCode;
	}

	public StatusList getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(StatusList statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusReasonCode() {
		return statusReasonCode;
	}

	public void setStatusReasonCode(String statusReasonCode) {
		this.statusReasonCode = statusReasonCode;
	}

	public String getStatusReasonName() {
		return statusReasonName;
	}

	public void setStatusReasonName(String statusReasonName) {
		this.statusReasonName = statusReasonName;
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

}
