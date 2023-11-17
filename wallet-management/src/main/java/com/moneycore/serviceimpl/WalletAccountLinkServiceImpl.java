package com.moneycore.serviceimpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moneycore.entity.WalletAccountLink;
import com.moneycore.repository.WalletAccountLinkRepository;
import com.moneycore.service.WalletAccountLinkService;

@Service("walletAccountLinkService")
public class WalletAccountLinkServiceImpl implements WalletAccountLinkService {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	WalletAccountLinkRepository walletAccountLinkRepository;

	@Override
	public String getLastAccountNumber() {
		List<WalletAccountLink> list = em
				.createNativeQuery("SELECT * FROM wallet_account_link ORDER BY id DESC LIMIT 1",
						WalletAccountLink.class)
				.getResultList();
		if (!list.isEmpty()) {
			for (WalletAccountLink wal : list) {
				String s = wal.getAccountNumber().replaceAll("[^0-9]", "");
				return s;
			}
		}
		return null;
	}

	@Override
	public WalletAccountLink saveWalletAccount(WalletAccountLink accountLink) {
		return walletAccountLinkRepository.save(accountLink);

	}

	@Override
	public List<WalletAccountLink> findByWalletId(String walletId) {
		List<WalletAccountLink> walletAL = em.createQuery("FROM WalletAccountLink WHERE entityId='" + walletId + "'")
				.getResultList();
		return walletAL;
	}

}
