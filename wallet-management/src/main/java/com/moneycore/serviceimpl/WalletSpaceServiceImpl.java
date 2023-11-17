package com.moneycore.serviceimpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import com.moneycore.entity.Branch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.moneycore.bean.WalletSpaceInfo;
import com.moneycore.entity.InstitutionList;
import com.moneycore.entity.Wallet;
import com.moneycore.entity.WalletSpace;
import com.moneycore.model.ResponseModel;
import com.moneycore.repository.CommonRepository;
import com.moneycore.repository.WalletSpaceRepository;
import com.moneycore.service.WalletSpaceService;
import com.moneycore.util.CommonUtil;

@Service("walletSpaceService")
@Transactional
public class WalletSpaceServiceImpl implements WalletSpaceService {

	@Autowired
	WalletSpaceRepository walletSpaceRepository;

	@Autowired
	CommonRepository commonRepository;

	@Autowired
	private RestTemplate restTemplate;

	@PersistenceContext
	private EntityManager em;
	@Override
	public WalletSpace save(WalletSpaceInfo walletSpaceInfo) {
		Optional<WalletSpace> optional = walletSpaceRepository.fetchAccountNumberLength();
		String original = null;
		if (optional.isPresent()) {
			original = optional.get().getAccountNumber().replaceAll("[^0-9]", "");
		}
		int code = commonRepository.generateWalletSpaceId();
		String walletSpaceId = "WS" + code;
		if (original == null) {
			original = "0000000000000";
		}
		String incremented = String.format("%0" + original.length() + "d", code);
		WalletSpace walletSpace = new WalletSpace();
		walletSpace.setInstitutionCode(getInstitutionByCode(walletSpaceInfo.getInstitutionCode()));
		walletSpace.setWalletId(getWallet(walletSpaceInfo.getWalletId()));
		walletSpace.setSpaceId(walletSpaceId);
		walletSpace.setAccountNumber("WS" + incremented);
		walletSpace.setSpaceName(walletSpaceInfo.getSpaceName());
		walletSpace.setDescription(walletSpaceInfo.getDescription());
		walletSpace.setTargetDate(new Date());
		walletSpace.setAmount(walletSpaceInfo.getAmount());
		return walletSpaceRepository.save(walletSpace);
	}

	@Override
	public List<WalletSpace> fetchSpaces(String institutionCode, String walletId) {
		return walletSpaceRepository.fetchList(institutionCode, walletId);
	}

	@Override
	public WalletSpace findSpaceIfExist(String institutionCode, String walletId, String spaceId) {
		WalletSpace walletSpace = null;
		Optional<WalletSpace> optional = walletSpaceRepository.fetch(institutionCode, walletId, spaceId);
		if (optional.isPresent()) {
			walletSpace = optional.get();
		}
		return walletSpace;
	}

	@Override
	public void delete(String spaceId) {
		walletSpaceRepository.deleteById(spaceId);
	}

	@Override
	public WalletSpace updateWalletSpace(WalletSpace walletSpace) {
		return walletSpaceRepository.save(walletSpace);	
	}

	@Override
	public WalletSpace findSpace(String institutionCode, String walletId, String accountNumber) {
		WalletSpace walletSpace = null;
		Optional<WalletSpace> optional = walletSpaceRepository.fetchByAccountNumber(institutionCode, walletId, accountNumber);
		if (optional.isPresent()) {
			walletSpace = optional.get();
		}
		return walletSpace;
	}

	@Override
	public double findTotalBalanceOfWalletSpaces(String walletId) {
		return walletSpaceRepository.fetchTotalBalance(walletId);
	}

	@Override
	public WalletSpace findSpaceByWalletId(String walletId) {
		WalletSpace walletSpace = null;
		Optional<WalletSpace> optional = walletSpaceRepository.fetchByWalletId(walletId);
		if (optional.isPresent()) {
			walletSpace = optional.get();
		}
		return walletSpace;
	}

	private InstitutionList getInstitutionByCode(String institutionCode) {
		ResponseModel responseModel = null;
		InstitutionList institutionList = null;
		String url = CommonUtil.getApplicationBaseUrl() + "/api/institutionmanagement/internal/institution/"
				+ institutionCode;
		ResponseEntity<ResponseModel> responseEntity = CommonUtil.getDataFromOtherService(restTemplate, url, null);
		if (responseEntity.getStatusCode().equals(HttpStatus.ACCEPTED)) {
			responseModel = responseEntity.getBody();
			institutionList = CommonUtil.convertToOriginalObject(responseModel.getResult(), InstitutionList.class);
		}
		return institutionList;
	}

	private Wallet getWallet(String walletId) {
		ResponseModel responseModel = null;
		Wallet wallet = null;
		String url = CommonUtil.getApplicationBaseUrl() + "/api/walletmanagement/internal/client/wallet/" + walletId;
		ResponseEntity<ResponseModel> responseEntity = CommonUtil.getDataFromOtherService(restTemplate, url, null);
		if (responseEntity.getStatusCode().equals(HttpStatus.ACCEPTED)) {
			responseModel = responseEntity.getBody();
			wallet = CommonUtil.convertToOriginalObject(responseModel.getResult(), Wallet.class);
		}
		return wallet;
	}

	@Override
	public List<WalletSpace> findAll() {
		Query query = em.createNamedQuery("WalletSpace.query_find_all_walletspace", WalletSpace.class);
		List<WalletSpace> list = query.getResultList();
		return list;
	}

}
