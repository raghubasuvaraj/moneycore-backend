package com.moneycore.serviceimpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moneycore.bean.CurrencyListInfo;
import com.moneycore.entity.CurrencyList;
import com.moneycore.repository.CurrencyListRepository;
import com.moneycore.service.CurrencyListService;

@Service("currencyListService")
public class CurrencyListServiceImpl implements CurrencyListService {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	CurrencyListRepository currencyListRepo;

	@Override
	public CurrencyList saveCurrencyList(CurrencyList currencyList) {
		return currencyListRepo.save(currencyList);
	}

	@Override
	public CurrencyList findCurrency(String institutionCode, String currencyCode) {
		CurrencyList currencyList = null;
		Optional<CurrencyList> optional = Optional.empty();
		if (institutionCode != null) {
			optional = currencyListRepo.find(institutionCode, currencyCode);
		} else {
			optional = currencyListRepo.find(currencyCode);
		}
		if (optional.isPresent()) {
			currencyList = optional.get();
		}
		return currencyList;
	}

	@Override
	public CurrencyList save(CurrencyList currencyList, CurrencyListInfo currencyListInfo) {
		currencyList = new CurrencyList();
		currencyList.setInstitutionCode(currencyListInfo.getInstitutionCode());
		currencyList.setCurrencyCode(currencyListInfo.getCurrencyCode());
		currencyList.setAbrvWording(currencyListInfo.getAbrvWording());
		currencyList.setWording(currencyListInfo.getWording());
		currencyList.setPrecisionValue(currencyListInfo.getPrecisionValue());
		currencyList.setUserCreate(currencyListInfo.getUserCreate());
		currencyList.setDateCreate(new Date());
		return currencyListRepo.save(currencyList);
	}

	@Override
	public CurrencyList update(CurrencyList currencyList, CurrencyListInfo currencyListInfo) {
		currencyList.setAbrvWording(currencyListInfo.getAbrvWording());
		currencyList.setWording(currencyListInfo.getWording());
		currencyList.setPrecisionValue(currencyListInfo.getPrecisionValue());
		currencyList.setUserModif(currencyListInfo.getUserModif());
		currencyList.setDateModif(new Date());
		return currencyListRepo.save(currencyList);
	}

	@Override
	public List<CurrencyList> fetchCurrencyList(String institutionCode) {
		return currencyListRepo.findList(institutionCode);
	}
	public void delete(String currencyCode) {
		currencyListRepo.deleteById(currencyCode);
	}

	public CurrencyList findcurrencyCode( String currencyCode) {
		CurrencyList currencyList = null;
		Optional<CurrencyList> optional = currencyListRepo.find(currencyCode);

		if (optional.isPresent()) {
			currencyList = optional.get();
		}
		return currencyList;
	}

}