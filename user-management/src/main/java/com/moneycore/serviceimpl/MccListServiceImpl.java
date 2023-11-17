package com.moneycore.serviceimpl;

import com.moneycore.bean.MccListInfo;
import com.moneycore.entity.MccList;
import com.moneycore.repository.MccListRepository;
import com.moneycore.service.MccListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service("mccListService")
@Transactional
public class MccListServiceImpl implements MccListService {

	@Autowired
	MccListRepository mccListRepository;
	public boolean findMccDup(String mccName,String institutionCode ){

		return  mccListRepository.existsByMccNameAndInstitutionCode(mccName,institutionCode);

	}

	public List<MccList> findMccDup(String institutionCode,String mccName,String mccCode ){

		return  mccListRepository.findByMccName(institutionCode,mccName,mccCode);

	}

	@Override
	public MccList findMcc(String institutionCode, String mccCode) {
		MccList mccList = null;
		Optional<MccList> optional = Optional.empty();
		if (institutionCode != null) {
			optional = mccListRepository.find(institutionCode, mccCode);
		} else {
			optional = mccListRepository.find(mccCode);
		}
		if (optional.isPresent()) {
			mccList = optional.get();
		}
		return mccList;
	}

	@Override
	public MccList save(MccList mccList, MccListInfo mccListInfo) {
		mccList = new MccList();
		mccList.setInstitutionCode(mccListInfo.getInstitutionCode());
		mccList.setMccCode(mccListInfo.getMccCode());
		mccList.setMccName(mccListInfo.getMccName());
		mccList.setAbrvWording(mccListInfo.getAbbreviation());
		mccList.setWording(mccListInfo.getWording());
		mccList.setUserCreate(mccListInfo.getUserCreate());
		mccList.setDateCreate(new Date());
		return mccListRepository.save(mccList);
	}

	@Override
	public MccList update(MccList mccList, MccListInfo mccListInfo) {
		mccList.setMccName(mccListInfo.getMccName());
		mccList.setAbrvWording(mccListInfo.getAbbreviation());
		mccList.setWording(mccListInfo.getWording());
		mccList.setUserModif(mccListInfo.getUserModif());
		mccList.setDateModif(new Date());
		return mccListRepository.save(mccList);
	}

	@Override
	public List<MccList> fetchMccList(String institutionCode) {
		return mccListRepository.findList(institutionCode);
	}

	public MccList findMcc( String mccCode) {
		MccList mccList = null;
		Optional<MccList> optional = mccListRepository.find(mccCode);

		if (optional.isPresent()) {
			mccList = optional.get();
		}
		return mccList;
	}

	public void delete(String mccCode) {
		mccListRepository.deleteById(mccCode);
	}
}
