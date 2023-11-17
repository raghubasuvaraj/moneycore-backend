package com.moneycore.serviceimpl;

import com.moneycore.entity.Bill;
import com.moneycore.model.BillPaymentResponse;
import com.moneycore.repository.BillRepository;
import com.moneycore.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("BillService")
public class BillServiceImpl implements BillService {
    @Autowired
    BillRepository billRepository;

    public Bill save(Bill bill) {
        return billRepository.save(bill);
    }

    public List<Bill> findByReferencecodeId(String refrenceCode){
        return billRepository.findByReferencecodeId(refrenceCode);
    };
    public List<BillPaymentResponse> PaymentBillListResponse (String refrenceCode){
        List<BillPaymentResponse> billPaymentResponses=new ArrayList<>();
        List<Bill>payedBills=billRepository.findByReferencecodeId(refrenceCode);

        for (Bill value: payedBills) {
            BillPaymentResponse billresponse=new BillPaymentResponse();
            billresponse.setRefrenceCode(refrenceCode);
            billresponse.setTotalAmount(value.getTotal());
            billresponse.setPaidAmount(value.getPayAmount());
            billresponse.setBalanceAmount(value.getPendingAmount());
            billPaymentResponses.add(billresponse);
        }
        return billPaymentResponses;

    };

}
