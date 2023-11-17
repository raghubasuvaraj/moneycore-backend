package com.moneycore.service;

import java.util.List;

import com.moneycore.bean.StatusListInfo;
import com.moneycore.entity.StatusList;

public interface StatusListService {

	StatusList findStatus(String institutionCode, String statusCode);

	StatusList save(StatusList statusList, StatusListInfo statusListInfo);

	StatusList update(StatusList statusList, StatusListInfo statusListInfo);

	List<StatusList> fetchStatusList(String institutionCode);

	StatusList findstatuscode(String cityCode);
	public void delete(String cityCode);

	public boolean findstatusdup(String statusName,String institutionCode );
	public List<StatusList> findstatusdup(String institutionCode,String statusName,String statusCode );

}