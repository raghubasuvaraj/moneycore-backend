package com.moneycore.service;

import java.util.List;

import com.moneycore.entity.Profile;

public interface ProfileService {

	public Profile findByName(String name);

	public List<Profile> findUserByName(String name);

	public Profile insert(Profile profile);

	public int insertProfile(Profile profile);

	public List<Profile> findAllProfiles(String institutionCode);

	public Profile find(int id);

	public Profile update(Profile profile);

	public String getAlphaNumericString(int n);

	public void delete(int id);
	public List<Profile> checkduplication(String profileName,String institutionCode);
	public List<Profile> checkupdatedup(String profileName,String institutionCode,Integer profileId);


}
