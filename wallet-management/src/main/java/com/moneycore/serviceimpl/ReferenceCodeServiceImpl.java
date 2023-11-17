package com.moneycore.serviceimpl;

import com.moneycore.entity.ReferenceCode;
import com.moneycore.model.ReferenceCodeDetials;
import com.moneycore.repository.ReferenceCodeRepository;
import com.moneycore.service.ReferenceCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("referenceCodeService")
public class ReferenceCodeServiceImpl implements ReferenceCodeService {
    @Autowired
    ReferenceCodeRepository referenceCodeRepository;
    public ReferenceCode save(ReferenceCode referenceCode) {
        return referenceCodeRepository.save(referenceCode);
    }

    public ReferenceCode findByReferencecodeId(String refrenceCode){
        return referenceCodeRepository.findByReferencecodeId(refrenceCode);
    };

}

