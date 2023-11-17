package com.moneycore.serviceimpl;

import com.moneycore.bean.ServiceListInfo;
import com.moneycore.bean.TransactionCodeListInfo;
import com.moneycore.entity.ServiceList;
import com.moneycore.entity.TransactionCodeList;
import com.moneycore.repository.ServiceListRepository;
import com.moneycore.repository.TransactionCodeListRepository;
import com.moneycore.service.ServiceListService;
import com.moneycore.service.TransactionCodeListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service("serviceListService")
@Transactional
public class ServiceListServiceImpl implements ServiceListService {

    @Autowired
    ServiceListRepository serviceListRepository;

    @Autowired
    private TransactionCodeListService transactionCodeListService;



    public boolean findServiceDup(String Name,String institutionCode ){

        return  serviceListRepository.existsByServiceNameAndInstitutionCode(Name,institutionCode);

    }

    public List<ServiceList> findServiceDup(String institutionCode,String Name,Integer ServiceCode ){

        return  serviceListRepository.findByServiceName(institutionCode,Name,ServiceCode);

    }
    @Override
    public ServiceList findServiceCode(String institutionCode, int serviceCode) {
        ServiceList serviceList = null;
        Optional<ServiceList> optional = Optional.empty();
        if (institutionCode != null) {
            optional = serviceListRepository.find(institutionCode, serviceCode);
        } else {
            optional = serviceListRepository.find(serviceCode);
        }
        if (optional.isPresent()) {
            serviceList = optional.get();
        }
        return serviceList;
    }
    @Override
    public ServiceList findServiceName(String institutionCode, String serviceName) {
        ServiceList serviceList = null;
        Optional<ServiceList> optional = Optional.empty();
        if (institutionCode != null) {
            optional = serviceListRepository.findService(institutionCode, serviceName);
        } else {
            optional = serviceListRepository.findService(serviceName);
        }
        if (optional.isPresent()) {
            serviceList = optional.get();
        }
        return serviceList;
    }

    @Override
    public ServiceList save(ServiceList serviceList,
                            ServiceListInfo serviceListInfo) {
        serviceList = new ServiceList();
        serviceList.setInstitutionCode(serviceListInfo.getInstitutionCode());
        serviceList.setDescription(serviceListInfo.getDescription());
        serviceList.setServiceName(serviceListInfo.getServiceName());
        serviceList.setStatus(serviceListInfo.getStatus());
        serviceList.setAbrvWording(serviceListInfo.getAbrvWording());
        serviceList.setUserCreate(serviceListInfo.getUserCreate());
        serviceList.setDateCreate(new Date());
        return serviceListRepository.save(serviceList);
    }

    @Override
    public ServiceList update(ServiceList serviceList, ServiceListInfo serviceListInfo) {
        serviceList.setInstitutionCode(serviceListInfo.getInstitutionCode());
        serviceList.setDescription(serviceListInfo.getDescription());
        serviceList.setServiceName(serviceListInfo.getServiceName());
        serviceList.setStatus(serviceListInfo.getStatus());
        serviceList.setAbrvWording(serviceListInfo.getAbrvWording());
//        serviceList.setDateCreate(serviceList.getDateCreate());
//        serviceList.setUserCreate(serviceList.getUserCreate());
        serviceList.setUserModif(serviceListInfo.getUserModif());
        serviceList.setDateModif(new Date());
//        serviceListRepository.delete(serviceList);
        return serviceListRepository.save(serviceList);
    }

    @Override
    public List<ServiceList> fetchMccList(String institutionCode) {
        return serviceListRepository.findList(institutionCode);
    }

    public ServiceList findServiceCode( int serviceCode) {
        ServiceList serviceList = null;
        Optional<ServiceList> optional = serviceListRepository.find(serviceCode);

        if (optional.isPresent()) {
            serviceList = optional.get();
        }
        return serviceList;
    }

    public void delete(int serviceCode) {
        serviceListRepository.deleteById(serviceCode);
    }
}
