package com.moneycore.service;

import java.util.List;

import com.moneycore.bean.RegionListInfo;
import com.moneycore.entity.RegionList;

public interface RegionListService {

	RegionList findRegion(String institutionCode, String regionCode);

	RegionList save(RegionList regionList, RegionListInfo regionListInfo);

	RegionList update(RegionList regionList, RegionListInfo regionListInfo);

	List<RegionList> fetchRegionList(String institutionCode);
	public void delete(String regionCode);

	RegionList findRegionCode(String regionCode);

	public List<RegionList> findregiondup(String regionName,String institutionCode);
	public List<RegionList> findregiondup(String regionName,String institutionCode,String regionId);

}
