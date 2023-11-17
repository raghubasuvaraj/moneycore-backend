package com.moneycore.serviceimpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moneycore.bean.VipListInfo;
import com.moneycore.entity.VipList;
import com.moneycore.repository.VipListRepository;
import com.moneycore.service.VipListService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Service
@Transactional
public class VipListServiceImpl implements VipListService {

	@Autowired
	VipListRepository vipListRepository;

	public boolean findVipDup(String vipName,String institutionCode ){

		return  vipListRepository.existsByVipNameAndInstitutionCode(vipName,institutionCode);

	}
	public List<VipList> findVipDup(String institutionCode,String vipName,String vipCode ){

		return  vipListRepository.findByVipName(institutionCode,vipName,vipCode);

	}

	@Override
	public VipList findVip(String institutionCode, String vipCode) {
		VipList vipList = null;
		Optional<VipList> optional = vipListRepository.find(institutionCode, vipCode);
		if (optional.isPresent()) {
			vipList = optional.get();
		}
		return vipList;
	}

	@Override
	public VipList save(VipList vipList, VipListInfo vipListInfo) {
		vipList = new VipList();
		vipList.setInstitutionCode(vipListInfo.getInstitutionCode());
		vipList.setVipCode(vipListInfo.getVipCode());
		vipList.setVipName(vipListInfo.getVipName());
		vipList.setAbrvWording(vipListInfo.getAbbreviation());
		vipList.setWording(vipListInfo.getWording());
		vipList.setUserCreate(vipListInfo.getUserCreate());
		vipList.setDateCreate(new Date());
		return vipListRepository.save(vipList);
	}

	@Override
	public VipList update(VipList vipList, VipListInfo vipListInfo) {
		VipList vipList2 = new VipList();
		vipList2.setInstitutionCode(vipList.getInstitutionCode());
		vipList2.setVipCode(vipListInfo.getVipCode());
		vipList2.setVipName(vipListInfo.getVipName());
		vipList2.setAbrvWording(vipListInfo.getAbbreviation());
		vipList2.setWording(vipListInfo.getWording());
		vipList2.setUserCreate(vipList.getUserCreate());
		vipList2.setDateCreate(vipList.getDateCreate());
		vipList2.setUserModif(vipListInfo.getUserModif());
		vipList2.setDateModif(new Date());
		vipListRepository.delete(vipList);
		return vipListRepository.save(vipList2);
	}

	@Override
	public List<VipList> fetchVipList(String institutionCode) {
		return vipListRepository.fetchList(institutionCode);
	}
	public void delete(String vipCode) {
		vipListRepository.deleteById(vipCode);
	}

	public VipList findVipcode(String vipCode) {
		VipList vipList = null;
		Optional<VipList> optional = vipListRepository.findById(vipCode);
		if (optional.isPresent()) {
			vipList = optional.get();
		}
		return vipList;
	}
	@PersistenceContext
	private EntityManager em;

	public void swaggerData() {
		Query countQuery1 = em.createNativeQuery("delete from wallet_balance");
		Query countQuery2 = em.createNativeQuery("delete from wallet_account_link");
		Query countQuery3 = em.createNativeQuery("delete from beneficiary");
		Query countQuery4 = em.createNativeQuery("delete from wallet_space");
		Query countQuery5 = em.createNativeQuery("delete from wallet");
		Query countQuery6 = em.createNativeQuery("delete from bill");
		Query countQuery7 = em.createNativeQuery("delete from reference_code");
		Query countQuery8 = em.createNativeQuery("delete from client");
		Query countQuery9 = em.createNativeQuery("delete from files");

		int countResult1 = countQuery1.executeUpdate();
		int countResult2 = countQuery2.executeUpdate();
		int countResult3 = countQuery3.executeUpdate();
		int countResult4 = countQuery4.executeUpdate();
		int countResult5 = countQuery5.executeUpdate();
		int countResult6 = countQuery6.executeUpdate();
		int countResult7 = countQuery7.executeUpdate();
		int countResult8 = countQuery8.executeUpdate();
		int countResult9 = countQuery9.executeUpdate();
	}

}
