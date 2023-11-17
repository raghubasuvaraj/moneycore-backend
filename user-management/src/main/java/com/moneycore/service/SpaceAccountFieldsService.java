package com.moneycore.service;

import java.util.List;

import com.moneycore.entity.SpaceAccountFields;

public interface SpaceAccountFieldsService {

	List<SpaceAccountFields> fetchSpaceAccountFields(String institutionCode);

	SpaceAccountFields find(int id);

}
