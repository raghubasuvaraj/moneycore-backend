package com.moneycore.serviceimpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moneycore.bean.SpaceInfo;
import com.moneycore.entity.SpaceAccountDetails;
import com.moneycore.entity.SpaceAccountFields;
import com.moneycore.entity.SpaceAccounts;
import com.moneycore.repository.SpaceAccountDetailsRepository;
import com.moneycore.service.SpaceAccountDetailsService;

@Service("spaceAccountDetailsService")
@Transactional
public class SpaceAccountDetailsServiceImpl implements SpaceAccountDetailsService {

	@Autowired
	SpaceAccountDetailsRepository spaceAccountDetailsRepository;
	
	@Override
	public SpaceAccountDetails save(SpaceInfo spaceInfo, SpaceAccounts spaceAccounts, SpaceAccountFields spaceAccountFields, String value) {
		SpaceAccountDetails spaceAccountDetails = new SpaceAccountDetails();
		spaceAccountDetails.setSpaceAccountFk(spaceAccounts);
		spaceAccountDetails.setSpaceAccountFieldsFk(spaceAccountFields);
		spaceAccountDetails.setSpaceAccountFieldValue(value);
		return spaceAccountDetailsRepository.save(spaceAccountDetails);
	}

	@Override
	public List<SpaceAccountDetails> fetchSpaceAccountDetails(String walletId) {
		return spaceAccountDetailsRepository.findList(walletId);
	}

	@Override
	public SpaceAccountDetails deleteSpaceDetails(int spaceAccountFk) {
		return spaceAccountDetailsRepository.deleteBySpaceAccountFk(spaceAccountFk);
	}

	@Override
	public List<Map<String, String>> fetchSpaceAccountDetailsByFk(String spaceId) {
		List<SpaceAccountDetails> listDetails = spaceAccountDetailsRepository.findListByFk(spaceId);
		List<Map<String, String>> listDetailsMap = new ArrayList<Map<String, String>>();
		for (SpaceAccountDetails details : listDetails) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("fieldName", details.getSpaceAccountFieldsFk().getSpaceAccountFieldName());
			map.put("fieldValue", details.getSpaceAccountFieldValue());
			listDetailsMap.add(map);
		}
		return listDetailsMap;
	}

}