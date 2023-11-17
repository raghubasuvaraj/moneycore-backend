package com.moneycore.service;

import java.util.List;

import com.moneycore.bean.ActivityListInfo;
import com.moneycore.entity.ActivityList;

public interface ActivityListService {

	ActivityList findActivity(String institutionCode, String activityCode);

	ActivityList save(ActivityList activityList, ActivityListInfo activityListInfo);

	ActivityList update(ActivityList activityList, ActivityListInfo activityListInfo);

	List<ActivityList> fetchActivityList(String institutionCode);
	ActivityList findActivity(String activityCode);
	public void delete(String regionCode);
	public boolean findActivityDup(String ActivityName,String institutionCode );
	public List<ActivityList> findActivityDup(String institutionCode, String ActivityName, String ActivityCode );

}
