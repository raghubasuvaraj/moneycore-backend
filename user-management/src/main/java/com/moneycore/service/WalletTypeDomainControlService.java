package com.moneycore.service;

import com.moneycore.entity.WalletTypeDomainControl;

public interface WalletTypeDomainControlService {

	WalletTypeDomainControl findControlIndex(String controlIndex);

	WalletTypeDomainControl findControlIndexByWalletType(String walletTypeId);

}
