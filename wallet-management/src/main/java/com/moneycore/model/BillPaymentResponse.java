package com.moneycore.model;
import lombok.*;
@Setter
@Getter
public class BillPaymentResponse {
    private String refrenceCode;
    private double paidAmount;
    private double balanceAmount;
    private double totalAmount;

}
