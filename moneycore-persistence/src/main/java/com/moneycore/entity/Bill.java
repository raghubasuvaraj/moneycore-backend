package com.moneycore.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "bill")
public class Bill {

    @Id
    @Column(name = "Bill_id")
    private String BillId;

    @ManyToOne
    @JoinColumn(name = "referencecode_id")
    private ReferenceCode referencecodeId;

    @ManyToOne
    @JoinColumn(name = "client_code")
    private Client clientCode;

    @Column(name = "total")
    private double total;

    @Column(name = "pay_amount")
    private double payAmount ;

    @Column(name = "pending_amount")
    private double pendingAmount ;

    @Column(name = "transaction_fee")
    private double transactionFee ;

    @Column(name = "transaction_code")
    private String transactionCode ;

    @Column(name = "entityName")
    private String entityName;

    @Column(name = "service")
    private String service;
    @Column(name = "bill_sequence")
    private Integer billSequence ;

    @Column(name = "date_create")
    private Date dateCreate;

    @Column(name = "date_update")
    private Date dateUpdate;

    public String getBillId() {
        return BillId;
    }

    public void setBillId(String billId) {
        BillId = billId;
    }

    public ReferenceCode getReferencecodeId() {
        return referencecodeId;
    }

    public void setReferencecodeId(ReferenceCode referencecodeId) {
        this.referencecodeId = referencecodeId;
    }

    public Client getClientCode() {
        return clientCode;
    }

    public void setClientCode(Client clientCode) {
        this.clientCode = clientCode;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(double payAmount) {
        this.payAmount = payAmount;
    }

    public double getPendingAmount() {
        return pendingAmount;
    }

    public void setPendingAmount(double pendingAmount) {
        this.pendingAmount = pendingAmount;
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

    public Integer getBillSequence() {
        return billSequence;
    }

    public void setBillSequence(Integer billSequence) {
        this.billSequence = billSequence;
    }
}
