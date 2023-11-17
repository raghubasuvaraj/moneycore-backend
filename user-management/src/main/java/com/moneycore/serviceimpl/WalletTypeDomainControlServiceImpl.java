package com.moneycore.serviceimpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moneycore.entity.WalletTypeDomainControl;
import com.moneycore.repository.WalletTypeDomainControlRepository;
import com.moneycore.service.WalletTypeDomainControlService;

@Service
@Transactional
public class WalletTypeDomainControlServiceImpl implements WalletTypeDomainControlService {

	@Autowired
	WalletTypeDomainControlRepository repository;

	@Override
	public WalletTypeDomainControl findControlIndex(String controlIndex) {
		WalletTypeDomainControl walletTypeDomainControl = null;
		Optional<WalletTypeDomainControl> optional = repository.find(controlIndex);
		if (optional.isPresent()) {
			walletTypeDomainControl = optional.get();
		}
		return walletTypeDomainControl;
	}

	@Override
	public WalletTypeDomainControl findControlIndexByWalletType(String walletTypeId) {
		WalletTypeDomainControl walletTypeDomainControl = null;
		Optional<WalletTypeDomainControl> optional = repository.findByWalletType(walletTypeId);
		if (optional.isPresent()) {
			walletTypeDomainControl = optional.get();
		}
		return walletTypeDomainControl;
	}

}
