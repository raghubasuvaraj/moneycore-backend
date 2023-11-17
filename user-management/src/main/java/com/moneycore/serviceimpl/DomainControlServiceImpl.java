package com.moneycore.serviceimpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.moneycore.bean.DomainControlInfo;
import com.moneycore.entity.DomainControl;
import com.moneycore.entity.InstitutionList;
import com.moneycore.entity.WalletType;
import com.moneycore.entity.WalletTypeDomainControl;
import com.moneycore.model.ResponseModel;
import com.moneycore.repository.CommonRepository;
import com.moneycore.repository.DomainControlRepository;
import com.moneycore.service.DomainControlService;
import com.moneycore.util.CommonUtil;

@Service("domainControlService")
@Transactional
public class DomainControlServiceImpl implements DomainControlService {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	CommonRepository commonRepository;

	@Autowired
	DomainControlRepository domainControlRepository;

	@Override
	public DomainControl save(DomainControlInfo domainControlInfo) {
		String controlIndexCode = "10" + commonRepository.generateControlIndex();
		DomainControl domainControl = new DomainControl();
		domainControl.setInstitutionCode(getInstitution(domainControlInfo.getInstitutionCode()));
		domainControl.setControlIndex("CI" + controlIndexCode);
		domainControl.setAbrvWording(domainControlInfo.getAbbreviation());
		domainControl.setWording(domainControlInfo.getDescription());
		domainControl.setCurrencyCode(domainControlInfo.getCurrencyCodeList());
		domainControl.setMccCode(domainControlInfo.getMccCodeList());
		domainControl.setCountryCode(domainControlInfo.getCountryCodeList());
		domainControl.setMerchantId(domainControlInfo.getMerchantsList());
		domainControl.setTransactionCode(domainControlInfo.getTransactionsList());
		domainControl.setDailyOnusAmnt(domainControlInfo.getDailyInternalTransactionLimit());
		domainControl.setDailyOnusNbr(domainControlInfo.getNoOfDailyInternalTransactions());
		domainControl.setDailyNatAmnt(domainControlInfo.getDailyNationalTransactionLimit());
		domainControl.setDailtNatNbr(domainControlInfo.getNoOfDailyNationalTransactions());
		domainControl.setDailyInternatAmnt(domainControlInfo.getDailyInterntionalTransactionLimit());
		domainControl.setDailyInternatNbr(domainControlInfo.getNoOfDailyInternationalTransactions());
		domainControl.setDailyTotAmnt(domainControlInfo.getDailyTotalTransactionLimit());
		domainControl.setDailyTotNbr(domainControlInfo.getTotalNoOfDailyTransactions());
		domainControl.setPerOnusAmnt(domainControlInfo.getInternalTransactionAmountLimit());
		domainControl.setPerOnusNbr(domainControlInfo.getNoOfInternalTransactions());
		domainControl.setPerNatAmnt(domainControlInfo.getNationalTransactionAmountLimit());
		domainControl.setPerNatNbr(domainControlInfo.getNoOfNationalTransactions());
		domainControl.setPerInternatAmnt(domainControlInfo.getInternationalTransactionAmountLimit());
		domainControl.setPerInternatNbr(domainControlInfo.getNoOfInternationalTransactions());
		domainControl.setPerTotAmnt(domainControlInfo.getTotalTransactionAmountLimit());
		domainControl.setPerTotNbr(domainControlInfo.getTotalNoOfTransactions());
		domainControl.setUserCreate(domainControlInfo.getUserCreate());
		domainControl.setDateCreate(new Date());
		return domainControlRepository.save(domainControl);
	}

	@Override
	public List<DomainControl> fetchDomainControl(String institutionCode, String controlIndex) {
		List<DomainControl> controls = new ArrayList<DomainControl>();
		if (institutionCode !=null && controlIndex != null) {
			DomainControl domainControl = domainControlRepository.fetch(institutionCode, controlIndex);
			WalletTypeDomainControl walletTypeDomainControl = getWalletTypeDomainControl(
					domainControl.getControlIndex());
			if (walletTypeDomainControl != null) {
				domainControl.setDefault(walletTypeDomainControl.getIsDefault());
				domainControl.setWalletTypeId(walletTypeDomainControl.getWalletTypeId());
				String walletTypeName = getWalletTypeName(walletTypeDomainControl.getWalletTypeId());
				if(walletTypeName!=null)
					domainControl.setWalletTypeName(walletTypeName);
			}
			controls.add(domainControl);
		} else if (institutionCode !=null){
			controls = domainControlRepository.fetch(institutionCode);
			for (DomainControl domainControl : controls) {
				WalletTypeDomainControl walletTypeDomainControl = getWalletTypeDomainControl(
						domainControl.getControlIndex());
				if (walletTypeDomainControl != null) {
					domainControl.setDefault(walletTypeDomainControl.getIsDefault());
					domainControl.setWalletTypeId(walletTypeDomainControl.getWalletTypeId());
					String walletTypeName = getWalletTypeName(walletTypeDomainControl.getWalletTypeId());
					if(walletTypeName!=null)
						domainControl.setWalletTypeName(walletTypeName);
				}
			}
		}else{
			controls = domainControlRepository.fetch();
			for (DomainControl domainControl : controls) {
				WalletTypeDomainControl walletTypeDomainControl = getWalletTypeDomainControl(
						domainControl.getControlIndex());
				if (walletTypeDomainControl != null) {
					domainControl.setDefault(walletTypeDomainControl.getIsDefault());
					domainControl.setWalletTypeId(walletTypeDomainControl.getWalletTypeId());
					String walletTypeName = getWalletTypeName(walletTypeDomainControl.getWalletTypeId());
					if(walletTypeName!=null)
						domainControl.setWalletTypeName(walletTypeName);
				}
			}
		}
		return controls;
	}

	@Override
	public DomainControl update(DomainControl domainControl, DomainControlInfo domainControlInfo) {
		domainControl.setAbrvWording(domainControlInfo.getAbbreviation());
		domainControl.setWording(domainControlInfo.getDescription());
		domainControl.setCurrencyCode(domainControlInfo.getCurrencyCodeList());
		domainControl.setMccCode(domainControlInfo.getMccCodeList());
		domainControl.setCountryCode(domainControlInfo.getCountryCodeList());
		domainControl.setMerchantId(domainControlInfo.getMerchantsList());
		domainControl.setTransactionCode(domainControlInfo.getTransactionsList());
		domainControl.setDailyOnusAmnt(domainControlInfo.getDailyInternalTransactionLimit());
		domainControl.setDailyOnusNbr(domainControlInfo.getNoOfDailyInternalTransactions());
		domainControl.setDailyNatAmnt(domainControlInfo.getDailyNationalTransactionLimit());
		domainControl.setDailtNatNbr(domainControlInfo.getNoOfDailyNationalTransactions());
		domainControl.setDailyInternatAmnt(domainControlInfo.getDailyInterntionalTransactionLimit());
		domainControl.setDailyInternatNbr(domainControlInfo.getNoOfDailyInternationalTransactions());
		domainControl.setDailyTotAmnt(domainControlInfo.getDailyTotalTransactionLimit());
		domainControl.setDailyTotNbr(domainControlInfo.getTotalNoOfDailyTransactions());
		domainControl.setPerOnusAmnt(domainControlInfo.getInternalTransactionAmountLimit());
		domainControl.setPerOnusNbr(domainControlInfo.getNoOfInternalTransactions());
		domainControl.setPerNatAmnt(domainControlInfo.getNationalTransactionAmountLimit());
		domainControl.setPerNatNbr(domainControlInfo.getNoOfNationalTransactions());
		domainControl.setPerInternatAmnt(domainControlInfo.getInternationalTransactionAmountLimit());
		domainControl.setPerInternatNbr(domainControlInfo.getNoOfInternationalTransactions());
		domainControl.setPerTotAmnt(domainControlInfo.getTotalTransactionAmountLimit());
		domainControl.setPerTotNbr(domainControlInfo.getTotalNoOfTransactions());
		domainControl.setUserModif(domainControlInfo.getUserModif());
		domainControl.setDateModif(new Date());
		return domainControlRepository.save(domainControl);
	}

	@Override
	public DomainControl fetch(String institutionCode, String controlIndex) {
		DomainControl domainControl = null;
		Optional<DomainControl> optional = Optional.empty();
		if(institutionCode != null)
			optional = domainControlRepository.find(institutionCode, controlIndex);
		else
			optional = domainControlRepository.find(controlIndex);
		if (optional.isPresent()) {
			domainControl = optional.get();
		}
		return domainControl;
	}

	@Override
	public DomainControl findDefaultCheck(String institutionCode) {
		return domainControlRepository.checkDefault(institutionCode);
	}

	@Override
	public boolean checkIfClientHasDomainControl(String institutionCode, String clientCode) {
		Optional<DomainControl> optional = domainControlRepository.checkIfClientHasDomainControl(institutionCode,
				clientCode);
		if (optional.isPresent()) {
			return true;
		}
		return false;
	}



	private WalletTypeDomainControl getWalletTypeDomainControl(String controlIndex) {
		ResponseModel responseModel = null;
		WalletTypeDomainControl walletTypeDomainControl = null;
		String url = CommonUtil.getApplicationBaseUrl() + "/api/usermanagement/internal/user/wallet/type/dc/" + controlIndex;
		ResponseEntity<ResponseModel> responseEntity = CommonUtil.getDataFromOtherService(restTemplate, url,
				CommonUtil.getJwtTokenFromRequest(CommonUtil.getReuestObjectFromContext()));
		if (responseEntity.getStatusCode().equals(HttpStatus.ACCEPTED)) {
			responseModel = responseEntity.getBody();
			walletTypeDomainControl = CommonUtil.convertToOriginalObject(responseModel.getResult(),
					WalletTypeDomainControl.class);
		}
		return walletTypeDomainControl;
	}

	private String getWalletTypeName(String walletTypeId) {
		ResponseModel responseModel = null;
		WalletType walletType = null;
		String url = CommonUtil.getApplicationBaseUrl() + "/api/usermanagement/internal/user/wallet/type/"
				+ walletTypeId;
		ResponseEntity<ResponseModel> responseEntity = CommonUtil.getDataFromOtherService(restTemplate, url, null);
		if (responseEntity.getStatusCode().equals(HttpStatus.ACCEPTED)) {
			responseModel = responseEntity.getBody();
			walletType = CommonUtil.convertToOriginalObject(responseModel.getResult(), WalletType.class);
		}
		return walletType.getWalletTypeName();
	}

	private InstitutionList getInstitution(String institutionCode) {
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
	@Override
	public void delete(String controlIndex) {
		domainControlRepository.deleteById(controlIndex);
	}


	public DomainControl findcontrolIndex(String controlIndex) {
		DomainControl domainControl = null;
		Optional<DomainControl> optional = domainControlRepository.findById( controlIndex);
		if (optional.isPresent()) {
			domainControl = optional.get();
		}
		return domainControl;
	}
}
