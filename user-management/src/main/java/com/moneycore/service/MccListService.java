package com.moneycore.service;

import java.util.List;

import com.moneycore.bean.MccListInfo;
import com.moneycore.entity.MccList;

public interface MccListService {

	MccList findMcc(String institutionCode, String mccCode);

	MccList save(MccList mccList, MccListInfo mccListInfo);

	MccList update(MccList mccList, MccListInfo mccListInfo);

	List<MccList> fetchMccList(String institutionCode);
	public void delete(String regionCode);

	MccList findMcc(String mccCode);
	public boolean findMccDup(String mccName,String institutionCode );

	public List<MccList> findMccDup(String institutionCode,String mccName,String mccCode );

}
