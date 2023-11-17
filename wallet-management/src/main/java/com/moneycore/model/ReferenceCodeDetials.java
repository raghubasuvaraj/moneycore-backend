package com.moneycore.model;
import lombok.*;

@Setter
@Getter
public class ReferenceCodeDetials {
    private String merchantWalletID;
    private String entityName;
    private String service;
    private double amount;
    private double transactionFee;
    private String transactionCode;
}
