package com.moneycore.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moneycore.bean.SpaceInfo;
import com.moneycore.entity.SpaceAccounts;
import com.moneycore.repository.SpaceAccountsRepository;
import com.moneycore.service.SpaceAccountsService;

@Service("spaceAccountsService")
@Transactional
public class SpaceAccountsServiceImpl implements SpaceAccountsService {

	@Autowired
	SpaceAccountsRepository spaceAccountsRepository;
	
	@Override
	public SpaceAccounts findSpaceAccount(String walletId, String iban) {
		SpaceAccounts spaceAccounts = null;
		Optional<SpaceAccounts> optional = spaceAccountsRepository.find(walletId, iban);
		if (optional.isPresent()) {
			spaceAccounts = optional.get();
		}
		return spaceAccounts;
	}

	@Override
	public SpaceAccounts save(SpaceAccounts spaceAccounts, SpaceInfo spaceInfo) {
		spaceAccounts = new SpaceAccounts();
		spaceAccounts.setWalletId(spaceInfo.getWalletId());
		spaceAccounts.setIBAN(spaceInfo.getIban());
		spaceAccounts.setSpaceName(spaceInfo.getSpaceName());
		return spaceAccountsRepository.save(spaceAccounts);
	}

	@Override
	public List<SpaceAccounts> fetchSpaceAccounts(String walletId) {
		return spaceAccountsRepository.findList(walletId);
	}

	@Override
	public SpaceAccounts findSpaceAccounts(int id) {
		SpaceAccounts spaceAccounts = null;
		Optional<SpaceAccounts> optional = spaceAccountsRepository.findById(id);
		if (optional.isPresent()) {
			spaceAccounts = optional.get();
		}
		return spaceAccounts;
	}

	@Override
	public void deleteSpace(int id) {
		spaceAccountsRepository.deleteById(id);
	}
}
