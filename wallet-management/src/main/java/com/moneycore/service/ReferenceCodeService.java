package com.moneycore.service;

import com.moneycore.entity.ReferenceCode;

import java.util.List;

public interface ReferenceCodeService {
    ReferenceCode save(ReferenceCode referenceCode);
    ReferenceCode findByReferencecodeId(String refrenceCode);
}
