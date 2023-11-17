package com.moneycore.serviceimpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moneycore.bean.StatusListInfo;
import com.moneycore.entity.StatusList;
import com.moneycore.repository.StatusListRepository;
import com.moneycore.service.StatusListService;

@Service("statusListService")
@Transactional
public class StatusListServiceImpl implements StatusListService {

	@Autowired
	StatusListRepository statusListRepository;



	public boolean findstatusdup(String statusName,String institutionCode ){

		return  statusListRepository.existsByStatusNameAndInstitutionCode(statusName,institutionCode);

	}
	public List<StatusList> findstatusdup(String institutionCode,String statusName,String statusCode ){

		return  statusListRepository.findByStatusName(institutionCode,statusName,statusCode);

	}
	@Override
	public StatusList findStatus(String institutionCode, String statusCode) {
		StatusList statusList = null;
		Optional<StatusList> optional = Optional.empty();
		if(institutionCode != null)
			optional = statusListRepository.find(institutionCode, statusCode);
		else
			optional = statusListRepository.find(statusCode);
		if (optional.isPresent()) {
			statusList = optional.get();
		}
		return statusList;
	}

	@Override
	public StatusList save(StatusList statusList, StatusListInfo statusListInfo) {
		statusList = new StatusList();
		statusList.setInstitutionCode(statusListInfo.getInstitutionCode());
		statusList.setStatusCode(statusListInfo.getStatusCode());
		statusList.setStatusName(statusListInfo.getStatusName());
		statusList.setBlockLogin(statusListInfo.getBlockLogin());
		statusList.setBlockTransact(statusListInfo.getBlockTransact());
		statusList.setAbrvWording(statusListInfo.getAbbreviation());
		statusList.setWording(statusListInfo.getWording());
		statusList.setUserCreate(statusListInfo.getUserCreate());
		statusList.setDateCreate(new Date());
		return statusListRepository.save(statusList);
	}

	@Override
	public StatusList update(StatusList statusList, StatusListInfo statusListInfo) {
		if(statusListInfo.getStatusCode().equalsIgnoreCase(statusList.getStatusCode())) {
			statusList.setStatusName(statusListInfo.getStatusName());
			statusList.setBlockLogin(statusListInfo.getBlockLogin());
			statusList.setBlockTransact(statusListInfo.getBlockTransact());
			statusList.setAbrvWording(statusListInfo.getAbbreviation());
			statusList.setWording(statusListInfo.getWording());
			statusList.setUserModif(statusListInfo.getUserModif());
			statusList.setDateModif(new Date());
			return statusListRepository.save(statusList);
		}
		StatusList statusList2 = new StatusList();
		statusList2.setInstitutionCode(statusList.getInstitutionCode());
		statusList2.setStatusCode(statusListInfo.getStatusCode());
		statusList2.setStatusName(statusListInfo.getStatusName());
		statusList2.setBlockLogin(statusListInfo.getBlockLogin());
		statusList2.setBlockTransact(statusListInfo.getBlockTransact());
		statusList2.setAbrvWording(statusListInfo.getAbbreviation());
		statusList2.setWording(statusListInfo.getWording());
		statusList2.setUserCreate(statusList.getUserCreate());
		statusList2.setDateCreate(statusList.getDateCreate());
		statusList2.setUserModif(statusListInfo.getUserModif());
		statusList2.setDateModif(new Date());
		statusListRepository.delete(statusList);
		return statusListRepository.save(statusList2);
	}

	@Override
	public List<StatusList> fetchStatusList(String institutionCode) {
		return statusListRepository.findList(institutionCode);
	}

	public StatusList findstatuscode(String statusCode) {
		StatusList statusList = null;
		Optional<StatusList>optional = statusListRepository.find(statusCode);
		if (optional.isPresent()) {
			statusList = optional.get();
		}
		return statusList;
	}

	public void delete(String statusCode) {
		statusListRepository.deleteById(statusCode);
	}

}
