package com.moneycore.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moneycore.entity.SpaceAccountFields;
import com.moneycore.repository.SpaceAccountFieldsRepository;
import com.moneycore.service.SpaceAccountFieldsService;

@Service("spaceAccountFieldsService")
@Transactional
public class SpaceAccountFieldsServiceImpl implements SpaceAccountFieldsService {

	@Autowired
	SpaceAccountFieldsRepository spaceAccountFieldsRepository;
	
	@Override
	public List<SpaceAccountFields> fetchSpaceAccountFields(String institutionCode) {
		return spaceAccountFieldsRepository.findList(institutionCode);
	}

	@Override
	public SpaceAccountFields find(int id) {
		SpaceAccountFields spaceAccountFields = null;
		Optional<SpaceAccountFields> optional = spaceAccountFieldsRepository.findById(id);
		if (optional.isPresent()) {
			spaceAccountFields = optional.get();
		}
		return spaceAccountFields;
	}

}
