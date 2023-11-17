package com.moneycore.serviceimpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.moneycore.service.ServiceListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moneycore.bean.TransactionCodeListInfo;
import com.moneycore.entity.TransactionCodeList;
import com.moneycore.repository.TransactionCodeListRepository;
import com.moneycore.service.TransactionCodeListService;

@Service("transactionCodeListService")
@Transactional
public class TransactionCodeListServiceImpl implements TransactionCodeListService {

	@Autowired
	TransactionCodeListRepository transactionCodeListRepository;
	@Autowired
	ServiceListService serviceListService;

	public boolean findTransDup(String transactionName,String institutionCode ){

		return  transactionCodeListRepository.existsByTransactionNameAndInstitutionCode(transactionName,institutionCode);

	}
	public List<TransactionCodeList> findTransDup(String institutionCode,String transactionName,String transactionCode ){

		return  transactionCodeListRepository.findByTransName(institutionCode,transactionName,transactionCode);

	}
	@Override
	public TransactionCodeList findTransactionCode(String institutionCode, String transactionCode) {
		TransactionCodeList transactionCodeList = null;
		Optional<TransactionCodeList> optional = Optional.empty();
		if (institutionCode != null) {
			optional = transactionCodeListRepository.find(institutionCode, transactionCode);
		} else {
			optional = transactionCodeListRepository.find(transactionCode);
		}
		if (optional.isPresent()) {
			transactionCodeList = optional.get();
		}
		return transactionCodeList;
	}

	@Override
	public List<TransactionCodeList> findTransactionCodeWithServiceCode(String institutionCode, Integer serviceCode) {
		List<TransactionCodeList> transactionCodeList = null;
		Optional<TransactionCodeList> optional = Optional.empty();
		if (institutionCode != null && serviceCode !=null) {
            transactionCodeList = transactionCodeListRepository.findList(institutionCode, serviceCode);
		}
		return transactionCodeList;
	}

	@Override
	public TransactionCodeList save(TransactionCodeList transactionCodeList,
			TransactionCodeListInfo transactionCodeListInfo) {
		transactionCodeList = new TransactionCodeList();
		transactionCodeList.setInstitutionCode(transactionCodeListInfo.getInstitutionCode());
		transactionCodeList.setTransactionCode(transactionCodeListInfo.getTransactionCode());
		transactionCodeList.setTransactionName(transactionCodeListInfo.getTransactionName());
		transactionCodeList.setAbrvWording(transactionCodeListInfo.getAbbreviation());
		transactionCodeList.setWording(transactionCodeListInfo.getWording());
		if(transactionCodeListInfo.getServiceCode() != null){
			transactionCodeList.setServiceList(serviceListService.findServiceCode(transactionCodeListInfo.getInstitutionCode(),transactionCodeListInfo.getServiceCode()));
		}else{
			transactionCodeList.setServiceList(null);
		}
		transactionCodeList.setUserCreate(transactionCodeListInfo.getUserCreate());
		transactionCodeList.setDateCreate(new Date());
		return transactionCodeListRepository.save(transactionCodeList);
	}

	@Override
	public TransactionCodeList update(TransactionCodeList transactionCodeList,
			TransactionCodeListInfo transactionCodeListInfo) {
		TransactionCodeList transactionCodeList2 = new TransactionCodeList();
		transactionCodeList2.setInstitutionCode(transactionCodeList.getInstitutionCode());
		transactionCodeList2.setTransactionCode(transactionCodeListInfo.getTransactionCode());
		transactionCodeList2.setTransactionName(transactionCodeListInfo.getTransactionName());
		transactionCodeList2.setAbrvWording(transactionCodeListInfo.getAbbreviation());
		transactionCodeList2.setWording(transactionCodeListInfo.getWording());
		transactionCodeList2.setServiceList(serviceListService.findServiceCode(transactionCodeListInfo.getInstitutionCode(),transactionCodeListInfo.getServiceCode()));
		transactionCodeList2.setDateCreate(transactionCodeList.getDateCreate());
		transactionCodeList2.setUserCreate(transactionCodeList.getUserCreate());
		transactionCodeList2.setUserModif(transactionCodeListInfo.getUserModif());
		transactionCodeList2.setDateModif(new Date());
		transactionCodeListRepository.delete(transactionCodeList);
		return transactionCodeListRepository.save(transactionCodeList2);
	}

	@Override
	public List<TransactionCodeList> fetchMccList(String institutionCode) {
		return transactionCodeListRepository.findList(institutionCode);
	}

	public TransactionCodeList findTransactionCode( String transactionCode) {
		TransactionCodeList transactionCodeList = null;
		Optional<TransactionCodeList> optional = transactionCodeListRepository.find(transactionCode);

		if (optional.isPresent()) {
			transactionCodeList = optional.get();
		}
		return transactionCodeList;
	}
	public void delete(String transactionCode) {
		transactionCodeListRepository.deleteById(transactionCode);
	}
}
