package com.moneycore.serviceimpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moneycore.bean.ActivityListInfo;
import com.moneycore.entity.ActivityList;
import com.moneycore.repository.ActivityListRepository;
import com.moneycore.service.ActivityListService;

@Service("activityListService")
@Transactional
public class ActivityListServiceImpl implements ActivityListService {

	@Autowired
	ActivityListRepository activityListRepository;

	public boolean findActivityDup(String ActivityName,String institutionCode ){

		return  activityListRepository.existsByActivityNameAndInstitutionCode(ActivityName,institutionCode);

	}


	public List<ActivityList> findActivityDup(String institutionCode, String ActivityName, String ActivityCode ){

		return  activityListRepository.findByActivityName(institutionCode,ActivityName,ActivityCode);

	}

	@Override
	public ActivityList findActivity(String institutionCode, String activityCode) {
		ActivityList activityList = null;
		Optional<ActivityList> optional = Optional.empty();
		if(institutionCode != null)
			optional = activityListRepository.find(institutionCode, activityCode);
		else
			optional = activityListRepository.find(activityCode);
		if (optional.isPresent()) {
			activityList = optional.get();
		}
		return activityList;
	}

	@Override
	public ActivityList save(ActivityList activityList, ActivityListInfo activityListInfo) {
		activityList = new ActivityList();
		activityList.setInstitutionCode(activityListInfo.getInstitutionCode());
		activityList.setActivityCode(activityListInfo.getActivityCode());
		activityList.setActivityName(activityListInfo.getActivityName());
		activityList.setAbrvWording(activityListInfo.getAbbreviation());
		activityList.setWording(activityListInfo.getWording());
		activityList.setUserCreate(activityListInfo.getUserCreate());
		activityList.setDateCreate(new Date());
		return activityListRepository.save(activityList);
	}

	@Override
	public ActivityList update(ActivityList activityList, ActivityListInfo activityListInfo) {
		ActivityList activityList2 = new ActivityList();
		activityList2.setInstitutionCode(activityList.getInstitutionCode());
		activityList2.setActivityCode(activityListInfo.getActivityCode());
		activityList2.setActivityName(activityListInfo.getActivityName());
		activityList2.setAbrvWording(activityListInfo.getAbbreviation());
		activityList2.setWording(activityListInfo.getWording());
		activityList2.setUserCreate(activityList.getUserCreate());
		activityList2.setDateCreate(activityList.getDateCreate());
		activityList2.setUserModif(activityListInfo.getUserModif());
		activityList2.setDateModif(new Date());
		activityListRepository.delete(activityList);
		return activityListRepository.save(activityList2);
	}

	@Override
	public List<ActivityList> fetchActivityList(String institutionCode) {
		return activityListRepository.findList(institutionCode);
	}
	public void delete(String activityCode) {
		activityListRepository.deleteById(activityCode);
	}

	public ActivityList findActivity( String activityCode) {
		ActivityList activityList = null;
		Optional<ActivityList> optional = activityListRepository.find(activityCode);
		if (optional.isPresent()) {
			activityList = optional.get();
		}
		return activityList;
	}
}
