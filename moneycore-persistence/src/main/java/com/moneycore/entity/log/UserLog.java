package com.moneycore.entity.log;

import com.moneycore.entity.Profile;
import com.moneycore.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import java.util.Date;

@Setter
@Getter
@Entity
@Table(name = "log_users")
public class UserLog {

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
    @Column(name="user_id")
    private int userId;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name="account_end_date")
    private Date accountEndDate;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name="account_start_date")
    private Date accountStartDate;

    @Column(name="activ_email")
    private String activEmail;

    @Column(name="bank_code_access_list")
    private String bankCodeAccessList;

    @Column(name="country_fk")
    private String countryFk;


    @Column(name="date_create")
    private Date dateCreate;

    @Temporal(TemporalType.DATE)
    @Column(name="date_modif")
    private Date dateModif;

    @Column(name="email")
    private String email;

    @Column(name="employe_number")
    private String employeNumber;

    @Column(name="ip_address_access")
    private String ipAddressAccess;

    @Column(name="job_title")
    private String jobTitle;

    @Column(name="language")
    private String language;

    @Temporal(TemporalType.DATE)
    @Column(name="last_date_connect")
    private Date lastDateConnect;

    @Column(name="password")
    private String password;

    @Column(name="phone_number_code")
    private String phoneNumberCode;

    @Column(name="phone_number")
    private String phoneNumber;

    @OneToOne
    @JoinColumn(name="profile_fk", referencedColumnName = "profile_id")
    private Profile profileFk;

    @Column(name="sensitive_operation_record")
    private String sensitiveOperationRecord;

    @Column(name="status")
    private String status;

    @Column(name="timer_browser_discount")
    private double timerBrowserDiscount;

    @Column(name="user_create")
    private String userCreate;

    @Column(name="user_modif")
    private String userModif;

    @Column(name="user_name")
    private String userName;


    @Column(name="institution_fk")
    private String institutionFk;

    public UserLog(){
    }

    public UserLog(User entityInst, String action, String loginUser) {
        // this.logId = 1L; // auto-increment
        this.logTimestamp = new Date();
        this.logOperation = action;
        this.logUser = loginUser;
        // others
        this.userId = entityInst.getUserId();
        this.accountEndDate = entityInst.getAccountEndDate();
        this.accountStartDate = entityInst.getAccountStartDate();
        this.activEmail = entityInst.getActivEmail();
        this.bankCodeAccessList = entityInst.getBankCodeAccessList();
        this.countryFk = entityInst.getCountryFk();
        this.dateCreate = entityInst.getDateCreate();
        this.dateModif = entityInst.getDateModif();
        this.email = entityInst.getEmail();
        this.employeNumber = entityInst.getEmployeNumber();
        this.ipAddressAccess = entityInst.getIpAddressAccess();
        this.jobTitle = entityInst.getJobTitle();
        this.language = entityInst.getLanguage();
        this.lastDateConnect = entityInst.getLastDateConnect();
        this.password = entityInst.getPassword();
        this.phoneNumberCode = entityInst.getPhoneNumberCode();
        this.phoneNumber = entityInst.getPhoneNumber();
        this.profileFk = entityInst.getProfileFk();
        this.sensitiveOperationRecord = entityInst.getSensitiveOperationRecord();
        this.status = entityInst.getStatus();
        this.timerBrowserDiscount = entityInst.getTimerBrowserDiscount();
        this.userCreate = entityInst.getUserCreate();
        this.userModif = entityInst.getUserModif();
        this.userName = entityInst.getUserName();
        this.institutionFk = entityInst.getInstitutionFk();
    }

}
