package com.moneycore.service;

import com.moneycore.entity.FaqInfo;
import com.moneycore.entity.StaticInfo;

import java.util.List;
import java.util.Optional;

public interface FaqInfoService {
    public FaqInfo saveOrUpdate(FaqInfo faqInfo);

    public List<FaqInfo> fetch(String institutionCode);

    public List<FaqInfo> fetch(int faqId, String institutionCode);
    public void delete(FaqInfo faqInfo);

}
