package com.moneycore.bean;

public class BeneficiaryInfo {
    private int id;
    private String beneficiaryWalletId;
    private String institutionCode;
    private String beneficiaryName;
    private String phoneNumber;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBeneficiaryWalletId() {
        return beneficiaryWalletId;
    }

    public void setBeneficiaryWalletId(String beneficiaryWalletId) {
        this.beneficiaryWalletId = beneficiaryWalletId;
    }


    public String getInstitutionCode() {
        return institutionCode;
    }

    public void setInstitutionCode(String institutionCode) {
        this.institutionCode = institutionCode;
    }

    public String getBeneficiaryName() {
        return beneficiaryName;
    }

    public void setBeneficiaryName(String beneficiaryName) {
        this.beneficiaryName = beneficiaryName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public BeneficiaryInfo(int id, String beneficiaryWalletId,String institutionCode, String beneficiaryName, String phoneNumber) {
        this.id = id;
        this.beneficiaryWalletId = beneficiaryWalletId;
        this.institutionCode = institutionCode;
        this.beneficiaryName = beneficiaryName;
        this.phoneNumber = phoneNumber;
    }
}
