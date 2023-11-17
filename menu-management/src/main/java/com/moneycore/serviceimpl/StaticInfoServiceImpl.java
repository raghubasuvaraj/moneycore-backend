package com.moneycore.serviceimpl;

import com.moneycore.entity.StaticInfo;
import com.moneycore.repository.StaticInfoRepository;
import com.moneycore.service.StaticInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Service("staticInfoService")
public class StaticInfoServiceImpl implements StaticInfoService {

    @Autowired
    private StaticInfoRepository staticInfoRepository;

    @PersistenceContext
    private EntityManager em;


    @Override
    public StaticInfo saveOrUpdate(StaticInfo staticInfo) {
        return staticInfoRepository.save(staticInfo);
    }

    @Override
    public List<StaticInfo> fetch(String institutionCode) {
        return staticInfoRepository.findByInstitutionCode(institutionCode);
    }

}
