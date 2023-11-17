package com.moneycore.bean;

import java.util.List;

public class NotoficationDetails {

    private String notificationTitle;
    private String description;
    private List<String> institutionCode;
    private List<String> branchCode;
    private List<String> clientCode;
    private String userCreate;
    private String image;

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

    public List<String> getInstitutionCode() {
        return institutionCode;
    }

    public void setInstitutionCode(List<String> institutionCode) {
        this.institutionCode = institutionCode;
    }

    public List<String> getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(List<String> branchCode) {
        this.branchCode = branchCode;
    }

    public List<String> getClientCode() {
        return clientCode;
    }

    public void setClientCode(List<String> clientCode) {
        this.clientCode = clientCode;
    }

    public String getUserCreate() {
        return userCreate;
    }

    public void setUserCreate(String userCreate) {
        this.userCreate = userCreate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public NotoficationDetails(String notificationTitle, String description, List<String> institutionCode, List<String> branchCode, List<String> clientCode, String userCreate, String image) {
        this.notificationTitle = notificationTitle;
        this.description = description;
        this.institutionCode = institutionCode;
        this.branchCode = branchCode;
        this.clientCode = clientCode;
        this.userCreate = userCreate;
        this.image = image;
    }

    public NotoficationDetails() {
    }
}
