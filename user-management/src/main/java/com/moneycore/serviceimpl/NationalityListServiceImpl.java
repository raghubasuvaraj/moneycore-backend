package com.moneycore.serviceimpl;

import com.moneycore.entity.CountryList;
import com.moneycore.entity.NationalityList;
import com.moneycore.repository.NationalityListRespository;
import com.moneycore.service.NationalityListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Service("nationalityListService")
public class NationalityListServiceImpl implements NationalityListService {

	@Autowired
	NationalityListRespository nationalityListRespository;

	@Autowired
	private RestTemplate restTemplate;

	@PersistenceContext
	private EntityManager em;


	@Override
	public NationalityList findNationalityById(int nationalityCode) {
		NationalityList nationalityList = null;
		Optional<NationalityList> optional = Optional.empty();
		optional = nationalityListRespository.findByNationalityId(nationalityCode);
		if (optional.isPresent()) {
			nationalityList = optional.get();
		}
		return nationalityList;
	}

	@Override
	public NationalityList findNationalityByCode(String nationalityName) {
		NationalityList nationalityList = null;
		Optional<NationalityList> optional = Optional.empty();
		optional = nationalityListRespository.findByNationalityCode(nationalityName);
		if (optional.isPresent()) {
			nationalityList = optional.get();
		}
		return nationalityList;
	}
	@Override
	public List<NationalityList> fetchNationality() {
		return nationalityListRespository.fetchNationality();
	}
}
