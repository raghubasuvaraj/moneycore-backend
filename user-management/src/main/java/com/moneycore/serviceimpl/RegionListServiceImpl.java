package com.moneycore.serviceimpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moneycore.bean.RegionListInfo;
import com.moneycore.entity.RegionList;
import com.moneycore.repository.RegionListRepository;
import com.moneycore.service.RegionListService;

@Service("regionListService")
@Transactional
public class RegionListServiceImpl implements RegionListService {

	@Autowired
	RegionListRepository regionListRepository;

	@Override
	public RegionList findRegion(String institutionCode, String regionCode) {
		RegionList regionList = null;
		Optional<RegionList> optional = Optional.empty();
		if(institutionCode != null)
			optional = regionListRepository.find(institutionCode, regionCode);
		else
			optional = regionListRepository.find(regionCode);
		if (optional.isPresent()) {
			regionList = optional.get();
		}
		return regionList;
	}

	@Override
	public RegionList save(RegionList regionList, RegionListInfo regionListInfo) {
		regionList = new RegionList();
		regionList.setInstitutionCode(regionListInfo.getInstitutionCode());
		regionList.setRegionCode(regionListInfo.getRegionCode());
		regionList.setRegionName(regionListInfo.getRegionName());
		regionList.setAbrvWording(regionListInfo.getAbbreviation());
		regionList.setWording(regionListInfo.getWording());
		regionList.setUserCreate(regionListInfo.getUserCreate());
		regionList.setDateCreate(new Date());
		return regionListRepository.save(regionList);
	}

	@Override
	public RegionList update(RegionList regionList, RegionListInfo regionListInfo) {
		RegionList regionList2 = new RegionList();
		regionList2.setInstitutionCode(regionList.getInstitutionCode());
		regionList2.setRegionCode(regionListInfo.getRegionCode());
		regionList2.setRegionName(regionListInfo.getRegionName());
		regionList2.setAbrvWording(regionListInfo.getAbbreviation());
		regionList2.setWording(regionListInfo.getWording());
		regionList2.setUserCreate(regionList.getUserCreate());
		regionList2.setDateCreate(regionList.getDateCreate());
		regionList2.setUserModif(regionListInfo.getUserModif());
		regionList2.setDateModif(new Date());
		regionListRepository.delete(regionList);
		return regionListRepository.save(regionList2);
	}

	@Override
	public List<RegionList> fetchRegionList(String institutionCode) {
		return regionListRepository.fetchList(institutionCode);
	}

	public void delete(String regionCode) {
		regionListRepository.deleteById(regionCode);
	}

	public RegionList findRegionCode( String regionCode) {
		RegionList regionList = null;

		Optional<RegionList> 	optional = regionListRepository.find(regionCode);
		if (optional.isPresent()) {
			regionList = optional.get();
		}
		return regionList;
	}

	public List<RegionList> findregiondup(String regionName,String institutionCode){

		return  regionListRepository.FindByRegionName(regionName,institutionCode);

	}
	public List<RegionList> findregiondup(String regionName,String institutionCode,String regionCode){

		return  regionListRepository.FindByRegionName(regionName,institutionCode,regionCode);

	}
}
