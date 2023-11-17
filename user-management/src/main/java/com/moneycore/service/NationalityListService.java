package com.moneycore.service;

import com.moneycore.entity.NationalityList;

import java.util.List;

public interface NationalityListService {

	NationalityList findNationalityById(int nationalityId);

	NationalityList findNationalityByCode(String nationalityCode);
	List<NationalityList> fetchNationality();

}
