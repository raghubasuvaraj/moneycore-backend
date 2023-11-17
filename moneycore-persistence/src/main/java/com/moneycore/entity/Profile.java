package com.moneycore.entity;

import com.moneycore.entityListener.ProfileListener;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name="profile")
@EntityListeners(ProfileListener.class)
public class Profile  implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "profile_id")
    private int profileId;
  
    @Column(name = "name")
    private String name;
   
    @Column(name = "status")
    private String status;

    @Column(name = "wording")
    private String wording;
 
    @Column(name = "profile_code")
    private String profileCode;
   
    @Column(name = "admin")
    private String admin;

    @ManyToOne
    @JoinColumn(name="institution_fk")
    private InstitutionList institute;
    
    @Transient
    private String institutionFk;
    
    @Transient
    private List<Integer> roles;

	@Column(name = "password_complexity")
    private String passwordComplexity;

  
    @Column(name = "bank_data_access")
    private String bankDataAccess;
  
    @Column(name = "sensitive_operation_record")
    private Character sensitiveOperationRecord;

    @Column(name = "user_create")
    private String userCreate;

    @Column(name = "date_create") 
    @Temporal(TemporalType.DATE)
    private Date dateCreate;

    @Column(name = "user_modif")
    private String userModif;
   
    @Column(name = "date_modif")
    @Temporal(TemporalType.DATE)
    private Date dateModif;




	@Transient
    private List<Roles> permissions;

	public List<Roles> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Roles> permissions) {
		this.permissions = permissions;
	}
	

	public int getProfileId() {
		return profileId;
	}

	public void setProfileId(int profileId) {
		this.profileId = profileId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getWording() {
		return wording;
	}

	public void setWording(String wording) {
		this.wording = wording;
	}

	public String getProfileCode() {
		return profileCode;
	}

	public void setProfileCode(String profileCode) {
		this.profileCode = profileCode;
	}

	public String getAdmin() {
		return admin;
	}

	public void setAdmin(String admin) {
		this.admin = admin;
	}

	public String getInstitutionFk() {
		return institutionFk;
	}

	public void setInstitutionFk(String institutionFk) {
		this.institutionFk = institutionFk;
	}

	public String getPasswordComplexity() {
		return passwordComplexity;
	}

	public void setPasswordComplexity(String passwordComplexity) {
		this.passwordComplexity = passwordComplexity;
	}

	public String getBankDataAccess() {
		return bankDataAccess;
	}

	public void setBankDataAccess(String bankDataAccess) {
		this.bankDataAccess = bankDataAccess;
	}

	public Character getSensitiveOperationRecord() {
		return sensitiveOperationRecord;
	}

	public void setSensitiveOperationRecord(Character sensitiveOperationRecord) {
		this.sensitiveOperationRecord = sensitiveOperationRecord;
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

	public InstitutionList getInstitute() {
		return institute;
	}

	public void setInstitute(InstitutionList institute) {
		this.institute = institute;
	}

	public List<Integer> getRoles() {
		return roles;
	}

	public void setRoles(List<Integer> roles) {
		this.roles = roles;
	}


}
