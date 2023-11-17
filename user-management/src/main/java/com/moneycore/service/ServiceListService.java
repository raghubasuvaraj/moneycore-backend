package com.moneycore.service;

import com.moneycore.bean.ServiceListInfo;
import com.moneycore.entity.ServiceList;
import com.moneycore.entity.TransactionCodeList;

import java.util.List;

public interface ServiceListService {

    ServiceList findServiceCode(String institutionCode, int serviceCode);

    ServiceList findServiceName(String institutionCode, String serviceName);

    ServiceList save(ServiceList serviceList, ServiceListInfo serviceListInfo);

    ServiceList update(ServiceList serviceList,
                       ServiceListInfo serviceListInfo);

    List<ServiceList> fetchMccList(String institutionCode);
    public void delete(int serviceCode);
    ServiceList findServiceCode(int serviceCode);

    public boolean findServiceDup(String ServiceName,String institutionCode );
    public List<ServiceList> findServiceDup(String institutionCode,String ServiceName,Integer ServiceCode );

}
