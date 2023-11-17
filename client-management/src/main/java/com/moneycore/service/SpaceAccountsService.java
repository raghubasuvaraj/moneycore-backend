package com.moneycore.service;

import java.util.List;

import com.moneycore.bean.SpaceInfo;
import com.moneycore.entity.SpaceAccounts;

public interface SpaceAccountsService {

	SpaceAccounts findSpaceAccount(String walletId, String iban);

	SpaceAccounts save(SpaceAccounts spaceAccounts, SpaceInfo spaceInfo);

	List<SpaceAccounts> fetchSpaceAccounts(String walletId);

	SpaceAccounts findSpaceAccounts(int id);

	void deleteSpace(int id);

}
