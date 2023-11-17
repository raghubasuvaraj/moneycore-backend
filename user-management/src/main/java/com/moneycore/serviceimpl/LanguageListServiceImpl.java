package com.moneycore.serviceimpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moneycore.bean.LanguageListInfo;
import com.moneycore.entity.LanguageList;
import com.moneycore.repository.LanguageListRepository;
import com.moneycore.service.LanguageListService;

@Service("languageListService")
public class LanguageListServiceImpl implements LanguageListService {

	@Autowired
	LanguageListRepository languageListRepository;


	public boolean findLangDup(String languageName,String institutionCode ){

		return  languageListRepository.existsByLanguageNameAndInstitutionCode(languageName,institutionCode);

	}
	public List <LanguageList>findLangDup(String institutionCode,String languageName,String languageCode ){

		return  languageListRepository.findByLang(institutionCode,languageName,languageCode);

	}
	@Override
	public LanguageList findLanguage(String institutionCode, String languageCode) {
		LanguageList languageList = null;
		Optional<LanguageList> optional = Optional.empty();
		if (institutionCode != null) {
		optional = languageListRepository.find(institutionCode, languageCode);
		} else {
			optional = languageListRepository.find(languageCode);
		}
		if (optional.isPresent()) {
			languageList = optional.get();
		}
		return languageList;
	}

	@Override
	public LanguageList save(LanguageList languageList, LanguageListInfo languageListInfo) {
		languageList = new LanguageList();
		languageList.setInstitutionCode(languageListInfo.getInstitutionCode());
		languageList.setLanguageCode(languageListInfo.getLanguageCode());
		languageList.setLanguageName(languageListInfo.getLanguageName());
		languageList.setAbrvWording(languageListInfo.getAbbrevation());
		languageList.setWording(languageListInfo.getWording());
		languageList.setUserCreate(languageListInfo.getUserCreate());
		languageList.setDateCreate(new Date());
		return languageListRepository.save(languageList);
	}

	@Override
	public LanguageList update(LanguageList languageList, LanguageListInfo languageListInfo) {
		languageList.setLanguageName(languageListInfo.getLanguageName());
		languageList.setAbrvWording(languageListInfo.getAbbrevation());
		languageList.setWording(languageListInfo.getWording());
		languageList.setUserModif(languageListInfo.getUserModif());
		languageList.setDateModif(new Date());
		return languageListRepository.save(languageList);
	}

	@Override
	public List<LanguageList> fetchLanguageList(String institutionCode) {
		return languageListRepository.findList(institutionCode);
	}

	public void delete(String languageCode) {
		languageListRepository.deleteById(languageCode);
	}

	public LanguageList findlanguageCode(String languageCode) {
		LanguageList languageList = null;
		Optional<LanguageList>optional = languageListRepository.find(languageCode);
		if (optional.isPresent()) {
			languageList = optional.get();
		}
		return languageList;
	}
}
