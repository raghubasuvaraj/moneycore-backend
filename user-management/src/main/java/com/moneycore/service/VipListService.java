package com.moneycore.service;

import java.util.List;

import com.moneycore.bean.VipListInfo;
import com.moneycore.entity.VipList;

public interface VipListService {

	VipList findVip(String institutionCode, String vipCode);

	VipList save(VipList vipList, VipListInfo vipListInfo);

	VipList update(VipList vipList, VipListInfo vipListInfo);

	List<VipList> fetchVipList(String institutionCode);

	VipList findVipcode( String vipCode);
	public void delete(String vipCode);
	public void swaggerData();
	public boolean findVipDup(String vipName,String institutionCode );
	public List<VipList> findVipDup(String institutionCode,String vipName,String vipCode );

}
