package com.moneycore.entity;

import com.moneycore.entityListener.RolesListener;
import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.*;

/**
 * The persistent class for the roles database table.
 * 
 */
@Entity
@Table(name = "roles")
@EntityListeners(RolesListener.class)
public class Roles implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "roleid")
	private Integer roleid;
	
	@ManyToOne
    @JoinColumn(name="institution_code")
    private InstitutionList institute;

	@Transient
    private String institutionCode;

	@Column(name = "createddate")
	private Timestamp dateCreate;

	@Column(name = "modifydate")
	private Timestamp modifydate;

	@Column(name = "role_name")
	private String roleName;

	@Column(name = "role_code")
	private String roleCode;

	@Column(name = "status")
	private String status;

	@Column(name = "createdby")
	private String userCreate;

	@Column(name = "modifyby")
	private String modifyby;

	@Column(name = "wording")
	private String wording;

	@Column(name = "bank_data_access")
    private String bankDataAccess;

	@Column(name="admin")
	private char admin;


	/**
	 * @return the institution_code
	 */
	public String getInstitutionCode() {
		return institutionCode;
	}

	/**
	 * @param institutionCode the institution_code to set
	 */
	public void setInstitutionCode(String institutionCode) {
		this.institutionCode = institutionCode;
	}

	/**
	 * @return the dateCreate
	 */
	public Timestamp getDateCreate() {
		return dateCreate;
	}

	/**
	 * @param dateCreate the dateCreate to set
	 */
	public void setDateCreate(Timestamp dateCreate) {
		this.dateCreate = dateCreate;
	}

	/**
	 * @return the modifydate
	 */
	public Timestamp getModifydate() {
		return modifydate;
	}

	/**
	 * @param modifydate the modifydate to set
	 */
	public void setModifydate(Timestamp modifydate) {
		this.modifydate = modifydate;
	}

	/**
	 * @return the name
	 */
	public String getRoleName() {
		return roleName;
	}

	/**
	 * @param roleName the name to set
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	/**
	 * @return the role_code
	 */
	public String getRoleCode() {
		return roleCode;
	}

	/**
	 * @param roleCode the role_code to set
	 */
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the userCreate
	 */
	public String getUserCreate() {
		return userCreate;
	}

	/**
	 * @param userCreate the userCreate to set
	 */
	public void setUserCreate(String userCreate) {
		this.userCreate = userCreate;
	}

	/**
	 * @return the modifyby
	 */
	public String getModifyby() {
		return modifyby;
	}

	/**
	 * @param modifyby the modifyby to set
	 */
	public void setModifyby(String modifyby) {
		this.modifyby = modifyby;
	}

	/**
	 * @return the wording
	 */
	public String getWording() {
		return wording;
	}

	/**
	 * @param wording the wording to set
	 */
	public void setWording(String wording) {
		this.wording = wording;
	}

	public String getBankDataAccess() {
		return bankDataAccess;
	}

	public void setBankDataAccess(String bankDataAccess) {
		this.bankDataAccess = bankDataAccess;
	}

	public Integer getRoleid() {
		return roleid;
	}

	public void setRoleid(Integer roleid) {
		this.roleid = roleid;
	}

	public InstitutionList getInstitute() {
		return institute;
	}

	public void setInstitute(InstitutionList institute) {
		this.institute = institute;
	}

	public char getAdmin() {
		return admin;
	}

	public void setAdmin(char admin) {
		this.admin = admin;
	}

}