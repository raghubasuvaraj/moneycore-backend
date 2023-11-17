package com.moneycore.service;

import java.util.List;

import com.moneycore.bean.WalletSpaceInfo;
import com.moneycore.entity.Branch;
import com.moneycore.entity.WalletSpace;

public interface WalletSpaceService {

	WalletSpace save(WalletSpaceInfo walletSpaceInfo);

	List<WalletSpace> fetchSpaces(String institutionCode, String walletId);

	WalletSpace findSpaceIfExist(String institutionCode, String walletId, String spaceId);

	void delete(String spaceId);

	WalletSpace updateWalletSpace(WalletSpace walletSpace);

	WalletSpace findSpace(String institutionCode, String walletId, String accountNumber);

	double findTotalBalanceOfWalletSpaces(String walletId);

	WalletSpace findSpaceByWalletId(String walletId);

    List<WalletSpace> findAll();
}
