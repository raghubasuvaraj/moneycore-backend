package com.moneycore.serviceimpl;

import com.moneycore.entity.FaqInfo;
import com.moneycore.entity.StaticInfo;
import com.moneycore.repository.FaqInfoRepository;
import com.moneycore.service.FaqInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Service("faqInfoService")
public class FaqInfoServiceImpl implements FaqInfoService {

    @Autowired
    private FaqInfoRepository faqInfoRepository;

    @PersistenceContext
    private EntityManager em;


    @Override
    public FaqInfo saveOrUpdate(FaqInfo faqInfo) {
        return faqInfoRepository.save(faqInfo);
    }

    @Override
    public List<FaqInfo> fetch(String institutionCode) {
        return faqInfoRepository.findByInstitutionCode(institutionCode);
    }
    @Override
    public List<FaqInfo> fetch(int id, String institutionCode) {
        return faqInfoRepository.findByIdAndInstitutionCode(id, institutionCode);
    }
    @Override
    public void delete(FaqInfo faqInfo) {
        faqInfoRepository.delete(faqInfo);
    }

}
