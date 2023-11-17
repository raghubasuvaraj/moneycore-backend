package com.moneycore.serviceimpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.moneycore.bean.ClientStatusInfo;
import com.moneycore.component.Translator;
import com.moneycore.entity.Client;
import com.moneycore.entity.GrantPermission;
import com.moneycore.entity.ServiceList;
import com.moneycore.entity.WalletBalance;
import com.moneycore.model.ResponseModel;
import com.moneycore.repository.WalletBalanceRepository;
import com.moneycore.service.WalletBalanceService;
import com.moneycore.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Arrays;
import java.util.List;

@Service("walletBalanceService")
@Slf4j
public class WalletBalanceServiceImpl implements WalletBalanceService {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	WalletBalanceRepository walletBalanceRepository;

	@Override
	public WalletBalance saveWalletBalance(WalletBalance walletBalance) {
		return walletBalanceRepository.save(walletBalance);
	}

	@Override
	public List<WalletBalance> findByWalletId(String walletId) {
		List<WalletBalance> wallet =null;
		try {
			wallet = em.createQuery("FROM WalletBalance WHERE walletId='" + walletId + "'")
				.getResultList();
		}catch(Exception e){
			log.info("WalletBalanceServiceImpl.findWalletByWalletId",e.getLocalizedMessage());
		}
		return wallet;
	}

	@Override
	public WalletBalance findWalletByWalletId(String wallet) {
		WalletBalance walletBalance = null;
		try {
			walletBalance = (WalletBalance) em.createQuery("FROM WalletBalance WHERE walletId='" + wallet + "'", WalletBalance.class)
					.getSingleResult();
		}catch(Exception e){
			log.info("WalletBalanceServiceImpl.findWalletByWalletId",e.getLocalizedMessage());
			}
		return walletBalance;
	}

	@Override
	public WalletBalance find(String walletId) {
		try {
		List<WalletBalance> bal = em.createQuery("FROM WalletBalance WHERE walletId='" + walletId + "'")
				.getResultList();
		if (!bal.isEmpty()) {
			for (WalletBalance wb : bal) {
				return wb;
			}
		}
		}catch(Exception e){
			log.info("WalletBalanceServiceImpl.findWalletByWalletId",e.getLocalizedMessage());
		}
		return null;
	}

	@Override
	@javax.transaction.Transactional
	public void adjustBalance() {
			String clientCode= Translator.toLocale("client.clientcode", null);
		    String balance= Translator.toLocale("client.balance", null);
			em.createNativeQuery("update wallet_balance set balance=:balance where client_code!=:clientCode", WalletBalance.class)
					.setParameter("balance", balance).setParameter("clientCode",clientCode).executeUpdate();
	}

	@Transactional
	@Override
	public void walletBalance() {
		Query walletBalance = em.createNativeQuery("delete from wallet_balance");
		int countResult1 = walletBalance.executeUpdate();
	}
	public void activateClient(int clientCode) throws JsonProcessingException {
		String url = CommonUtil.getApplicationBaseUrl() + "/api/clientmanagement/internal/client/unblock/" + clientCode;

		ClientStatusInfo clientStatusInfo = new ClientStatusInfo();
		clientStatusInfo.setStatusCode("A");
		clientStatusInfo.setStatusCode("Auto Activated");

		String bodyJson = CommonUtil.convertObjectToJson(clientCode);

		ResponseEntity<ResponseModel> responseEntity = CommonUtil.putDataFromOtherService(restTemplate, url,
				CommonUtil.getJwtTokenFromRequest(CommonUtil.getReuestObjectFromContext()), bodyJson);
		if (responseEntity.getStatusCode().equals(HttpStatus.ACCEPTED)) {
			ResponseModel responseModel = responseEntity.getBody();
			CommonUtil.convertToOriginalObject(responseModel.getResult(), null);
		}
	}

	@Override
	public List<ServiceList> getServiceListByInstitutionCode(String institutionCode) {
		String url = CommonUtil.getApplicationBaseUrl() + "/api/usermanagement/internal/user/getServiceList/" + institutionCode;
		ResponseModel responseModel =null;
		List<ServiceList> serviceLists = null;
		ResponseEntity<ResponseModel> responseEntity = CommonUtil.getDataFromOtherService(restTemplate, url,
				CommonUtil.getJwtTokenFromRequest(CommonUtil.getReuestObjectFromContext()));
		if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
			responseModel = responseEntity.getBody();
			ServiceList[] serviceListsArray = CommonUtil.convertToOriginalObject(responseModel.getResult(),
					ServiceList[].class);
			if (serviceListsArray != null) {
				serviceLists = Arrays.asList(serviceListsArray);
			}
		}
		return serviceLists;
	}

}
