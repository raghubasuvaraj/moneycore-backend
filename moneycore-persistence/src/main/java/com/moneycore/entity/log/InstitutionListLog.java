package com.moneycore.entity.log;

import com.moneycore.entity.InstitutionList;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.util.Date;

@Setter
@Getter
@Entity
@Table(name = "log_institution_list")
public class InstitutionListLog {

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
    @Column(name = "institution_code")
    private String institutionCode;

    @Column(name = "institution_name")
    private String institutionName;

    @Column(name = "abrv_wording")
    private String abrvWording;

    @Column(name = "currency_code")
    private String currencyCode;

    @Column(name = "wording")
    private String wording;

    @Column(name = "trade_name")
    private String tradeName;

    @Column(name = "trade_as")
    private String tradeAs;

    @Column(name = "bussiness_type")
    private String bussinessType;

    @Column(name = "website")
    private String website;

    @Column(name = "no_of_employees")
    private Long noOfEmployees;

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

    @Column(name = "default_branch")
    private String defaultBranch;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_create")
    private Date dateCreate;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_modif")
    private Date dateModif;

    @Column(name = "user_create")
    private String userCreate;

    @Column(name = "user_modif")
    private String userModif;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_country_code")
    private String phoneCountryCode;

    @Column(name = "alternate_phone_country_code")
    private String alternatePhoneCountryCode;

    @Column(name = "country")
    private String country;

    public InstitutionListLog(){
    }

    public InstitutionListLog(InstitutionList entityInst, String action, String loginUser) {
        // this.logId = 1L; // auto-increment
        this.logTimestamp = new Date();
        this.logOperation = action;
        this.logUser = loginUser;
        // others
        this.institutionCode = entityInst.getInstitutionCode();
        this.institutionName = entityInst.getInstitutionName();
        this.abrvWording = entityInst.getAbrvWording();
        this.currencyCode = entityInst.getCurrencyCode();
        this.wording = entityInst.getWording();
        this.tradeName = entityInst.getTradeName();
        this.tradeAs = entityInst.getTradeAs();
        this.bussinessType = entityInst.getBussinessType();
        this.website = entityInst.getWebsite();
        this.noOfEmployees = entityInst.getNoOfEmployees();
        this.bussinessAddress = entityInst.getBussinessAddress();
        this.contactPerson = entityInst.getContactPerson();
        this.personDesignation = entityInst.getPersonDesignation();
        this.phoneNumber = entityInst.getPhoneNumber();
        this.alternateNumber = entityInst.getAlternateNumber();
        this.tradeLicenseNumber = entityInst.getTradeLicenseNumber();
        this.defaultBranch = entityInst.getDefaultBranch();
        this.dateCreate = entityInst.getDateCreate();
        this.dateModif = entityInst.getDateModif();
        this.userCreate = entityInst.getUserCreate();
        this.userModif = entityInst.getUserModif();
        this.email = entityInst.getEmail();
        this.phoneCountryCode = entityInst.getPhoneCountryCode();
        this.alternatePhoneCountryCode = entityInst.getAlternatePhoneCountryCode();
        this.country = entityInst.getCountry();
    }

}
