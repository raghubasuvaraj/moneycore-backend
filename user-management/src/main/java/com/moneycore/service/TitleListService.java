package com.moneycore.service;

import java.util.List;

import com.moneycore.bean.TitleListInfo;
import com.moneycore.entity.TitleList;

public interface TitleListService {

	TitleList findTitle(String institutionCode, String titleCode);

	TitleList save(TitleList titleList, TitleListInfo titleListInfo);

	TitleList update(TitleList titleList, TitleListInfo titleListInfo);

	List<TitleList> fetchTitleList(String institutionCode);
	TitleList findTitle( String titleCode);
	public void delete(String titleCode);
	public boolean findTitleDup(String TitleName,String institutionCode );
	public List<TitleList> findTitleDup(String institutionCode,String TitleName, String TitleCode );

}
