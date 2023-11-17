package com.moneycore.service;

import java.util.List;

import com.moneycore.bean.WalletTypeInfo;
import com.moneycore.entity.WalletType;

public interface WalletTypeService {

	WalletType findWalletType(String institutionCode, String walletTypeId);

	WalletType save(WalletTypeInfo walletTypeInfo);

	WalletType update(WalletType walletType, WalletTypeInfo walletTypeInfo);

	List<WalletType> fetchWalletType(String institutionCode);

	WalletType findDefaultCheck(String institutionCode);

	WalletType findWalletType(String walletTypeId);

	public void delete(String walletTypeId);
	public boolean findWalletTypeDup(String walletType,String institutionCode );
	public List<WalletType> findWalletTypeDup(String institutionCode,String walletName,String walletTypeId );

}
