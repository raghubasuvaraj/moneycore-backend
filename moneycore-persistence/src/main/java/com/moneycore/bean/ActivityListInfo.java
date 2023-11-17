package com.moneycore.bean;

import java.util.ArrayList;
import java.util.List;

import com.moneycore.entity.ActivityList;

public class ActivityListInfo {

	private String institutionCode;
	private String activityCode;
	private String activityName;
	private String abbreviation;
	private String wording;
	private String userCreate;
	private String userModif;

	public String getInstitutionCode() {
		return institutionCode;
	}

	public void setInstitutionCode(String institutionCode) {
		this.institutionCode = institutionCode;
	}

	public String getActivityCode() {
		return activityCode;
	}

	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public String getWording() {
		return wording;
	}

	public void setWording(String wording) {
		this.wording = wording;
	}

	public String getUserCreate() {
		return userCreate;
	}

	public void setUserCreate(String userCreate) {
		this.userCreate = userCreate;
	}

	public String getUserModif() {
		return userModif;
	}

	public void setUserModif(String userModif) {
		this.userModif = userModif;
	}

	public static List<ActivityListInfo> createResponse(List<ActivityList> activityList) {
		List<ActivityListInfo> activityListInfos = new ArrayList<ActivityListInfo>();
		for (ActivityList list : activityList) {
			ActivityListInfo activityListInfo = new ActivityListInfo();
			activityListInfo.setInstitutionCode(list.getInstitutionCode());
			activityListInfo.setActivityCode(list.getActivityCode());
			activityListInfo.setActivityName(list.getActivityName());
			activityListInfo.setAbbreviation(list.getAbrvWording());
			activityListInfo.setWording(list.getWording());
			activityListInfo.setUserCreate(list.getUserCreate());
			activityListInfo.setUserModif(list.getUserModif());
			activityListInfos.add(activityListInfo);
		}
		return activityListInfos;
	}

	public static ActivityListInfo createSingleResponse(ActivityList list) {
		ActivityListInfo activityListInfo = new ActivityListInfo();
		activityListInfo.setInstitutionCode(list.getInstitutionCode());
		activityListInfo.setActivityCode(list.getActivityCode());
		activityListInfo.setActivityName(list.getActivityName());
		activityListInfo.setAbbreviation(list.getAbrvWording());
		activityListInfo.setWording(list.getWording());
		activityListInfo.setUserCreate(list.getUserCreate());
		activityListInfo.setUserModif(list.getUserModif());
		return activityListInfo;
	}

}
