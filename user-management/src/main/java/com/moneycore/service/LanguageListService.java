package com.moneycore.service;

import java.util.List;

import com.moneycore.bean.LanguageListInfo;
import com.moneycore.entity.LanguageList;

public interface LanguageListService {

	LanguageList findLanguage(String institutionCode, String languageCode);

	LanguageList save(LanguageList languageList, LanguageListInfo languageListInfo);

	LanguageList update(LanguageList languageList, LanguageListInfo languageListInfo);

	List<LanguageList> fetchLanguageList(String institutionCode);

	LanguageList findlanguageCode(String languageCode);
	public void delete(String languageCode);
	public boolean findLangDup(String languageName,String institutionCode );
	public List <LanguageList>findLangDup(String institutionCode,String languageName,String languageCode );

}
