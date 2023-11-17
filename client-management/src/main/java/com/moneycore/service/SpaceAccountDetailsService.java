package com.moneycore.service;

import java.util.List;
import java.util.Map;

import com.moneycore.bean.SpaceInfo;
import com.moneycore.entity.SpaceAccountDetails;
import com.moneycore.entity.SpaceAccountFields;
import com.moneycore.entity.SpaceAccounts;

public interface SpaceAccountDetailsService {

	SpaceAccountDetails save(SpaceInfo spaceInfo, SpaceAccounts spaceAccounts, SpaceAccountFields spaceAccountFields, String value);

	List<SpaceAccountDetails> fetchSpaceAccountDetails(String walletId);

	SpaceAccountDetails deleteSpaceDetails(int spaceAccountFk);

	List<Map<String, String>> fetchSpaceAccountDetailsByFk(String spaceId);

}
