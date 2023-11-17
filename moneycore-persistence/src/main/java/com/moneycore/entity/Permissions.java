package com.moneycore.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "assigned_roles2permissions")
public class Permissions implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;

	@Column(name = "profile_fk")
	private int profileId;

	@Column(name="assigned_role_fk")
	private Integer roleId;
	
	@Column(name="sansitive_operation_record")
	private String sansitiveOperationRecord;
	
	@Column(name="create_user")
	private String createUser;
	
	@Temporal(TemporalType.DATE)
	@Column(name="date_create")
	private Date dateCreate;
	
	@Column(name="user_modif")
	private String userModif;
	
	@Column(name="is_deleted")
	private boolean isDeleted;
	
	@Temporal(TemporalType.DATE)
	@Column(name="date_modif")
	private Date dateModif;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getProfileId() {
		return profileId;
	}
	
	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public void setProfileId(int profileId) {
		this.profileId = profileId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
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

	public String getSansitiveOperationRecord() {
		return sansitiveOperationRecord;
	}

	public void setSansitiveOperationRecord(String sansitiveOperationRecord) {
		this.sansitiveOperationRecord = sansitiveOperationRecord;
	}

    

	

}
