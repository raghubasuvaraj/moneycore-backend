package com.moneycore.entity.log;


import com.moneycore.entity.InstitutionList;
import com.moneycore.entity.Roles;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Setter
@Getter
@Entity
@Table(name = "log_roles")
public class RolesLog {

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

    public RolesLog(){
    }

    public RolesLog(Roles entityInst, String action, String user){
        // this.logId = 1L; // auto-increment
        this.logTimestamp = new Date();
        this.logOperation = action;
        this.logUser = user;
        // others
        this.roleid = entityInst.getRoleid();
        this.institute = entityInst.getInstitute();
        this.dateCreate = entityInst.getDateCreate();
        this.modifydate = entityInst.getModifydate();
        this.roleName = entityInst.getRoleName();
        this.roleCode = entityInst.getRoleCode();
        this.status = entityInst.getStatus();
        this.userCreate = entityInst.getUserCreate();
        this.modifyby = entityInst.getModifyby();
        this.wording = entityInst.getWording();
        this.bankDataAccess = entityInst.getBankDataAccess();
        this.admin = entityInst.getAdmin();
    }

}