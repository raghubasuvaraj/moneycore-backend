package com.moneycore.serviceimpl;

import com.moneycore.bean.BeneficiaryInfo;
import com.moneycore.entity.*;
import com.moneycore.repository.BeneficiaryRepository;
import com.moneycore.service.BeneficiaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service("beneficiaryService")
public class BeneficiaryServiceImpl implements BeneficiaryService {

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	BeneficiaryRepository beneficiaryRepository;

	@PersistenceContext
	private EntityManager em;


	@Override
	public List<BeneficiaryInfo> findByWalletId(String walletId) {
		List<BeneficiaryInfo> beneficiaryList = new ArrayList<>();
		List<Beneficiary> beneficiary = new ArrayList<>();
		Query query = em.createNativeQuery("Select * FROM beneficiary WHERE wallet_id='" + walletId + "' and is_deleted=false",Beneficiary.class);
		beneficiary = query.getResultList();
		for(Beneficiary details : beneficiary){
			BeneficiaryInfo beneficiaryInfo = new BeneficiaryInfo(details.getBeneficiaryId(),details.getBeneficiaryWalletId(), details.getInstitutionCode().getInstitutionCode(), details.getBeneficiaryName(), details.getPhoneNumber());
			beneficiaryList.add(beneficiaryInfo);
		}
		return beneficiaryList;
	}

	@Override
	public Beneficiary addBeneficiary(Beneficiary beneficiary) {
		return beneficiaryRepository.save(beneficiary);
	}

	@Override
	public Optional<Beneficiary> findById(int beneficiaryId) {
		Optional<Beneficiary> beneficiary = beneficiaryRepository.findById(beneficiaryId);
		return beneficiary;
	}

	@Override
	public Long checkBeneficiary(String walletId, String beneficiaryId) {
		String filterQuery = "";
		filterQuery = " bl.wallet_id LIKE '%" + walletId+ "%'  AND bl.beneficiary_wallet_id LIKE '%" + beneficiaryId+ "%' and is_deleted=false";
		Query countQuery = em.createNativeQuery("select count(0) FROM beneficiary bl WHERE " +filterQuery);
		BigInteger count = (BigInteger)countQuery.getSingleResult();
		Long countResult = count.longValue();
		return countResult;
	}

	@Override
	public void delete(int beneficiaryId) {
		beneficiaryRepository.deleteById(beneficiaryId);
	}
}
