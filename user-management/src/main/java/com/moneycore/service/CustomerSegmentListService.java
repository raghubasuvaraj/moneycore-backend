package com.moneycore.service;

import java.util.List;

import com.moneycore.bean.CustomerSegmentListInfo;
import com.moneycore.entity.CustomerSegmentList;

public interface CustomerSegmentListService {

	CustomerSegmentList findCustomerSegment(String institutionCode, String customerSegmentCode);

	CustomerSegmentList save(CustomerSegmentList customerSegmentList, CustomerSegmentListInfo customerSegmentListInfo);

	CustomerSegmentList update(CustomerSegmentList customerSegmentList, CustomerSegmentListInfo customerSegmentListInfo);

	List<CustomerSegmentList> fetchCustomerSegmentList(String institutionCode);
	public void delete(String customerSegmentCode);

	CustomerSegmentList findCustomerSegment( String customerSegmentCode);
	public boolean findCustSegDup(String customerSegmentName,String institutionCode );
	public List<CustomerSegmentList> findCustSegDup(String institutionCode,String customerSegmentName,String customerSegmentCode );


}
