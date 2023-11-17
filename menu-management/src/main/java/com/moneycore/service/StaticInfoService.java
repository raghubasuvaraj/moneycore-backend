package com.moneycore.service;

import com.moneycore.entity.StaticInfo;

import java.util.List;
import java.util.Optional;

public interface StaticInfoService {
    public StaticInfo saveOrUpdate(StaticInfo staticInfo);

    public List<StaticInfo> fetch(String institutionCode);

}
