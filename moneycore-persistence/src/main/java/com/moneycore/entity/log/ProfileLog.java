package com.moneycore.entity.log;

import com.moneycore.entity.InstitutionList;
import com.moneycore.entity.Profile;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "log_profile")
public class ProfileLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id")
    private Long logId;                 // primary key auto increment
    @Column(name = "log_timestamp")
    private Date logTimestamp;
    @Column(name = "log_operation")
    private String logOperation;        // [ INSERT, UPDATE, DELETE]
    @Column(name = "log_user")
    private String logUser;             // operation user

    // other log attributes
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

    public ProfileLog(){
    }

    public ProfileLog(Profile entityInst, String action, String loginUser) {
        // this.logId = 1L; // auto-increment
        this.logTimestamp = new Date();
        this.logOperation = action;
        this.logUser = loginUser;
        // others
        this.profileId = entityInst.getProfileId();
        this.name = entityInst.getName();
        this.status = entityInst.getStatus();
        this.wording = entityInst.getWording();
        this.profileCode = entityInst.getProfileCode();
        this.admin = entityInst.getAdmin();
        this.institute = entityInst.getInstitute();
        this.passwordComplexity = entityInst.getPasswordComplexity();
        this.bankDataAccess = entityInst.getBankDataAccess();
        this.sensitiveOperationRecord = entityInst.getSensitiveOperationRecord();
        this.userCreate = entityInst.getUserCreate();
        this.dateCreate = entityInst.getDateCreate();
        this.userModif = entityInst.getUserModif();
        this.dateModif = entityInst.getDateModif();
    }


}
