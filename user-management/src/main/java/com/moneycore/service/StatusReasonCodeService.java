package com.moneycore.service;

import java.util.List;

import com.moneycore.bean.StatusReasonCodeInfo;
import com.moneycore.entity.StatusReasonCode;

public interface StatusReasonCodeService {

	StatusReasonCode findStatusReason(String institutionCode, String statusCode, String statusReasonCode);

	StatusReasonCode save(StatusReasonCode statusReasonCode, StatusReasonCodeInfo statusReasonCodeInfo);

	StatusReasonCode update(StatusReasonCode statusReasonCode, StatusReasonCodeInfo statusReasonCodeInfo);

	List<StatusReasonCode> fetchStatusReason(String institutionCode, String statusCode);

	StatusReasonCode findStatusReasonByCode(String statusReasonCode);

	StatusReasonCode findStatusReasonCodeByStatusCode(String institutionCode, String statusCode);
	public void delete(String statusReasonCode);
	public boolean findstatusdup(String StatusResName,String institutionCode );
	public List<StatusReasonCode> findstatusdup(String institutionCode,String StatusResName ,String StatusResCode);
}

