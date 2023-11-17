package com.moneycore.entity.log;

import com.moneycore.entity.Branch;
import com.moneycore.entity.InstitutionList;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.util.Date;

@Setter
@Getter
@Entity
@Table(name = "log_branch")
public class BranchLog {

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
    @Column(name = "branch_code")
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private String branchCode;
    @Column(name = "branch_name")
    private String branchName;
    @Column(name = "abrv_wording")
    private String abrvWording;
    @Column(name = "wording")
    private String wording;
    @Column(name = "website")
    private String website;

    @Column(name = "no_of_employees")
    private int noOfEmployees;

    @Column(name = "bussiness_address")
    private String bussinessAddress;

    @Column(name = "contact_person")
    private String contactPerson;

    @Column(name = "contact_person_designation")
    private String personDesignation;

    @Column(name = "phone_number")
    private Long phoneNumber;

    @Column(name = "alternate_number")
    private Long alternateNumber;

    @Column(name = "trade_license_number")
    private String tradeLicenseNumber;

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
    @ManyToOne
    @JoinColumn(name = "institution_code")
    private InstitutionList institutionList;

    @Column(name = "country")
    private String country;

    public BranchLog(){
    }
    public BranchLog(Branch entityInst, String action, String loginUser) {
        // this.logId = 1L; // auto-increment
        this.logTimestamp = new Date();
        this.logOperation = action;
        this.logUser = loginUser;
        // others
        this.branchCode = entityInst.getBranchCode();
        this.branchName = entityInst.getBranchName();
        this.abrvWording = entityInst.getAbrvWording();
        this.wording = entityInst.getWording();
        this.website = entityInst.getWebsite();
        this.noOfEmployees = entityInst.getNoOfEmployees();
        this.bussinessAddress = entityInst.getBussinessAddress();
        this.contactPerson = entityInst.getContactPerson();
        this.personDesignation = entityInst.getpersonDesignation();
        this.phoneNumber = entityInst.getPhoneNumber();
        this.alternateNumber = entityInst.getAlternateNumber();
        this.tradeLicenseNumber = entityInst.getTradeLicenseNumber();
        this.userCreate = entityInst.getUserCreate();
        this.dateCreate = entityInst.getDateCreate();
        this.userModif = entityInst.getUserModif();
        this.dateModif = entityInst.getDateModif();
        this.institutionList = entityInst.getInstitutionList();
        this.country = entityInst.getCountry();
    }

}
