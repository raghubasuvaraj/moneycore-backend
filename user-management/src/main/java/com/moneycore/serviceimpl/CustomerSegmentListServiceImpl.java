package com.moneycore.serviceimpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moneycore.bean.CustomerSegmentListInfo;
import com.moneycore.entity.CustomerSegmentList;
import com.moneycore.repository.CustomerSegmentListRepository;
import com.moneycore.service.CustomerSegmentListService;

@Service("customerSegmentListService")
@Transactional
public class CustomerSegmentListServiceImpl implements CustomerSegmentListService {

	@Autowired
	CustomerSegmentListRepository customerSegmentListRepository;

	public boolean findCustSegDup(String customerSegmentName,String institutionCode ){

		return  customerSegmentListRepository.existsByCustomerSegmentNameAndInstitutionCode(customerSegmentName,institutionCode);

	}
	public List<CustomerSegmentList> findCustSegDup(String institutionCode,String customerSegmentName,String customerSegmentCode ){

		return  customerSegmentListRepository.FindByCustSegName(institutionCode,customerSegmentName,customerSegmentCode);

	}
	@Override
	public CustomerSegmentList findCustomerSegment(String institutionCode, String customerSegmentCode) {
		CustomerSegmentList customerSegmentList = null;
		Optional<CustomerSegmentList> optional = Optional.empty();
		if(institutionCode != null)
			optional = customerSegmentListRepository.find(institutionCode, customerSegmentCode);
		else
			optional = customerSegmentListRepository.find(customerSegmentCode);
		if (optional.isPresent()) {
			customerSegmentList = optional.get();
		}
		return customerSegmentList;
	}

	@Override
	public CustomerSegmentList save(CustomerSegmentList customerSegmentList, CustomerSegmentListInfo customerSegmentListInfo) {
		customerSegmentList = new CustomerSegmentList();
		customerSegmentList.setInstitutionCode(customerSegmentListInfo.getInstitutionCode());
		customerSegmentList.setCustomerSegmentCode(customerSegmentListInfo.getCustomerSegmentCode());
		customerSegmentList.setCustomerSegmentName(customerSegmentListInfo.getCustomerSegmentName());
		customerSegmentList.setAbrvWording(customerSegmentListInfo.getAbbreviation());
		customerSegmentList.setWording(customerSegmentListInfo.getWording());
		customerSegmentList.setUserCreate(customerSegmentListInfo.getUserCreate());
		customerSegmentList.setDateCreate(new Date());
		return customerSegmentListRepository.save(customerSegmentList);
	}

	@Override
	public CustomerSegmentList update(CustomerSegmentList customerSegmentList, CustomerSegmentListInfo customerSegmentListInfo) {
		CustomerSegmentList customerSegmentList2 = new CustomerSegmentList();
		customerSegmentList2.setInstitutionCode(customerSegmentList.getInstitutionCode());
		customerSegmentList2.setCustomerSegmentCode(customerSegmentListInfo.getCustomerSegmentCode());
		customerSegmentList2.setCustomerSegmentName(customerSegmentListInfo.getCustomerSegmentName());
		customerSegmentList2.setAbrvWording(customerSegmentListInfo.getAbbreviation());
		customerSegmentList2.setWording(customerSegmentListInfo.getWording());
		customerSegmentList2.setUserCreate(customerSegmentList.getUserCreate());
		customerSegmentList2.setDateCreate(customerSegmentList.getDateCreate());
		customerSegmentList2.setUserModif(customerSegmentListInfo.getUserModif());
		customerSegmentList2.setDateModif(new Date());
		customerSegmentListRepository.delete(customerSegmentList);
		return customerSegmentListRepository.save(customerSegmentList2);
	}

	@Override
	public List<CustomerSegmentList> fetchCustomerSegmentList(String institutionCode) {
		return customerSegmentListRepository.fetchList(institutionCode);
	}

	public CustomerSegmentList findCustomerSegment( String customerSegmentCode) {
		CustomerSegmentList customerSegmentList = null;

		Optional<CustomerSegmentList>optional = customerSegmentListRepository.find(customerSegmentCode);
		if (optional.isPresent()) {
			customerSegmentList = optional.get();
		}
		return customerSegmentList;
	}

	public void delete(String customerSegmentCode) {
		customerSegmentListRepository.deleteById(customerSegmentCode);
	}

}
