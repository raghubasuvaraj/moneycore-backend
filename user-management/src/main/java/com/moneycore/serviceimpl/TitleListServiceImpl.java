package com.moneycore.serviceimpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moneycore.bean.TitleListInfo;
import com.moneycore.entity.TitleList;
import com.moneycore.repository.TitleListRepository;
import com.moneycore.service.TitleListService;

@Service
@Transactional
public class TitleListServiceImpl implements TitleListService {

	@Autowired
	TitleListRepository titleListRepository;


	public boolean findTitleDup(String TitleName,String institutionCode ){

		return  titleListRepository.existsByTitleNameAndInstitutionCode(TitleName,institutionCode);

	}

	public List<TitleList> findTitleDup(String institutionCode,String TitleName, String TitleCode ){

		return  titleListRepository.findByTitleName(institutionCode,TitleName,TitleCode);

	}
	@Override
	public TitleList findTitle(String institutionCode, String titleCode) {
		TitleList titleList = null;
		Optional<TitleList> optional = Optional.empty();
		if(institutionCode != null)
			optional = titleListRepository.find(institutionCode, titleCode);
		else
			optional = titleListRepository.find(titleCode);
		if (optional.isPresent()) {
			titleList = optional.get();
		}
		return titleList;
	}

	@Override
	public TitleList save(TitleList titleList, TitleListInfo titleListInfo) {
		titleList = new TitleList();
		titleList.setInstitutionCode(titleListInfo.getInstitutionCode());
		titleList.setTitleCode(titleListInfo.getTitleCode());
		titleList.setTitleName(titleListInfo.getTitleName());
		titleList.setAbrvWording(titleListInfo.getAbbreviation());
		titleList.setWording(titleListInfo.getWording());
		titleList.setUserCreate(titleListInfo.getUserCreate());
		titleList.setDateCreate(new Date());
		return titleListRepository.save(titleList);
	}

	@Override
	public TitleList update(TitleList titleList, TitleListInfo titleListInfo) {
		titleList.setTitleName(titleListInfo.getTitleName());
		titleList.setAbrvWording(titleListInfo.getAbbreviation());
		titleList.setWording(titleListInfo.getWording());
		titleList.setUserModif(titleListInfo.getUserModif());
		titleList.setDateModif(new Date());
		return titleListRepository.save(titleList);
	}

	@Override
	public List<TitleList> fetchTitleList(String institutionCode) {
		return titleListRepository.fetchList(institutionCode);
	}

	public TitleList findTitle( String titleCode) {
		TitleList titleList = null;
		Optional<TitleList> optional = titleListRepository.find(titleCode);
		if (optional.isPresent()) {
			titleList = optional.get();
		}
		return titleList;
	}

	public void delete(String titleCode) {
		titleListRepository.deleteById(titleCode);
	}

}
