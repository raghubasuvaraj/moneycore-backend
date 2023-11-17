package com.moneycore.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "notification_list")
public class NotoficationService {

    @Id
    @Column(name = "notification_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int notificationId;

    @Column(name = "notification_title")
    private String notificationTitle;

    @Column(name = "description")
    private String description;

    @Column(name = "institution_code")
    private String institutionCode;

    @Column(name = "branch_code")
    private String branchCode;

    @Column(name = "client_code")
    private String clientCode;

    @Column(name = "user_create")
    private String userCreate;

    @Column(name = "date_create")
    @Temporal(TemporalType.DATE)
    private Date dateCreate;

    @Column(name = "status")
    private String status;

    @Column(name = "read_status")
    private String readStatus;

    @Column(name = "error_msg")
    private String errorMsg;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(String readStatus) {
        this.readStatus = readStatus;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public String getNotificationTitle() {
        return notificationTitle;
    }

    public void setNotificationTitle(String notificationTitle) {
        this.notificationTitle = notificationTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInstitutionCode() {
        return institutionCode;
    }

    public void setInstitutionCode(String institutionCode) {
        this.institutionCode = institutionCode;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getClientCode() {
        return clientCode;
    }

    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
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

    public NotoficationService(String notificationTitle, String description, String institutionCode, String branchCode, String clientCode, String userCreate, Date dateCreate,String status,String viewStatus,String errorMsg) {
        this.notificationId = notificationId;
        this.notificationTitle = notificationTitle;
        this.description = description;
        this.institutionCode = institutionCode;
        this.branchCode = branchCode;
        this.clientCode = clientCode;
        this.userCreate = userCreate;
        this.dateCreate = dateCreate;
        this.status=status;
        this.readStatus=viewStatus;
        this.errorMsg=errorMsg;
    }

    public NotoficationService() {
    }
}
