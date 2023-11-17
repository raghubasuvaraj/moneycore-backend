package com.moneycore.entity;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "beneficiary")
@SQLDelete(sql = "UPDATE  beneficiary SET is_deleted = true WHERE beneficiary_id=?")
@Where(clause = "is_deleted=false")
public class Beneficiary implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "beneficiary_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int beneficiaryId;

    @Column(name = "beneficiary_wallet_id")
    private String beneficiaryWalletId;

    @Transient
    private String walletId;

    @ManyToOne
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;

    @ManyToOne
    @JoinColumn(name = "client_code")
    private Client clientCode;

    @ManyToOne
    @JoinColumn(name = "institution_code")
    private InstitutionList institutionCode;

    @ManyToOne
    @JoinColumn(name = "branch_code")
    private Branch branchCode;

    @Column(name = "beneficiary_name")
    private String beneficiaryName;

    @Column(name = "user_create")
    private String userCreate;

    @Column(name = "date_create")
    @Temporal(TemporalType.DATE)
    private Date dateCreate;

    @Column(name = "user_modif")
    private String userModified;

    @Column(name = "date_modif")
    @Temporal(TemporalType.DATE)
    private Date dateModified;

    @Column(name = "is_deleted")
    private boolean isDeleted = Boolean.FALSE;

    @Column(name = "phone_number")
    private String phoneNumber;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }



    public int getBeneficiaryId() {
        return beneficiaryId;
    }

    public void setBeneficiaryId(int beneficiaryId) {
        this.beneficiaryId = beneficiaryId;
    }

    public String getBeneficiaryWalletId() {
        return beneficiaryWalletId;
    }

    public void setBeneficiaryWalletId(String beneficiaryWalletId) {
        this.beneficiaryWalletId = beneficiaryWalletId;
    }

    public String getWalletId() {
        return walletId;
    }

    public void setWalletId(String walletId) {
        this.walletId = walletId;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public Client getClientCode() {
        return clientCode;
    }

    public void setClientCode(Client clientCode) {
        this.clientCode = clientCode;
    }

    public InstitutionList getInstitutionCode() {
        return institutionCode;
    }

    public void setInstitutionCode(InstitutionList institutionCode) {
        this.institutionCode = institutionCode;
    }

    public Branch getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(Branch branchCode) {
        this.branchCode = branchCode;
    }

    public String getBeneficiaryName() {
        return beneficiaryName;
    }

    public void setBeneficiaryName(String beneficiaryName) {
        this.beneficiaryName = beneficiaryName;
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

    public String getUserModified() {
        return userModified;
    }

    public void setUserModified(String userModified) {
        this.userModified = userModified;
    }

    public Date getDateModified() {
        return dateModified;
    }

    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
