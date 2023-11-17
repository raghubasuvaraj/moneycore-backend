package com.moneycore.entity;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "reference_code")
public class ReferenceCode implements Serializable {


    @Id
    @Column(name = "referencecode_id")
    private String referencecodeId;

    @Column(name = "merchant_wallet_id")
    private String merchantWalletId;

    @OneToOne
    @JoinColumn(name = "client_code")
    private Client clientCode;

    @Column(name = "entityName")
    private String entityName;

    @Column(name = "service")
    private String service;


    @Column(name = "amount_service")
    private double amountService ;

    @Column(name = "transaction_fee")
    private double transactionFee ;

    @Column(name = "transaction_code")
    private String transactionCode ;

    @CreatedDate
    @Column(name = "date_create")
    private Date dateCreate;

    @CreatedDate
    @Column(name = "date_update")
    private Date dateUpdate;

    public String getReferencecodeId() {
        return referencecodeId;
    }

    public void setReferencecodeId(String referencecodeId) {
        this.referencecodeId = referencecodeId;
    }

    public String getMerchantWalletId() {
        return merchantWalletId;
    }

    public void setMerchantWalletId(String merchantWalletId) {
        this.merchantWalletId = merchantWalletId;
    }

    public Client getClientCode() {
        return clientCode;
    }

    public void setClientCode(Client clientCode) {
        this.clientCode = clientCode;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public double getAmountService() {
        return amountService;
    }

    public void setAmountService(double amountService) {
        this.amountService = amountService;
    }

    public double getTransactionFee() {
        return transactionFee;
    }

    public void setTransactionFee(double transactionFee) {
        this.transactionFee = transactionFee;
    }

    public String getTransactionCode() {
        return transactionCode;
    }

    public void setTransactionCode(String transactionCode) {
        this.transactionCode = transactionCode;
    }

    public Date getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }

    public Date getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(Date dateUpdate) {
        this.dateUpdate = dateUpdate;
    }
}

