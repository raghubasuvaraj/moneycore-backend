package com.moneycore.service;

import com.moneycore.entity.Bill;
import com.moneycore.model.BillPaymentResponse;

import java.util.List;

public interface BillService {
    Bill save(Bill bill);
    List<Bill> findByReferencecodeId(String refrenceCode);
    List<BillPaymentResponse> PaymentBillListResponse (String refrenceCode);


}
