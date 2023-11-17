package com.moneycore.serviceimpl;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moneycore.entity.Profile;
import com.moneycore.repository.ProfileRepository;
import com.moneycore.service.ProfileService;

@Service("profileService")
public class ProfileServiceImpl implements ProfileService {

	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	private ProfileRepository profileRepository;

	public List<Profile> checkduplication(String profileName,String institutionCode){

		return  profileRepository.findByNameandInstitutionName(profileName,institutionCode);

	}
	public List<Profile> checkupdatedup(String profileName,String institutionCode,Integer profileId){

		return  profileRepository.findByProfileName(profileName,institutionCode,profileId);

	}

	public String getAlphaNumericString(int n)// length of string n
	{
		byte[] array = new byte[256];
		new Random().nextBytes(array);
		String randomString = new String(array, Charset.forName("UTF-8"));
		StringBuffer r = new StringBuffer();
		for (int k = 0; k < randomString.length(); k++) {
			char ch = randomString.charAt(k);
			if (((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') || (ch >= '0' && ch <= '9')) && (n > 0)) {
				r.append(ch);
				n--;
			}
		}
		return r.toString();
	}

	@Override
	public Profile insert(Profile profile) {
		return profileRepository.save(profile);
	}

	@Override
	public int insertProfile(Profile profile) {
		Date date = new Date();
		Profile addProfile = new Profile();
		addProfile.setName(profile.getName());
		addProfile.setStatus(profile.getStatus());
		addProfile.setWording(profile.getWording());
		addProfile.setProfileCode(profile.getProfileCode());
		addProfile.setAdmin("0");
		addProfile.setInstitutionFk(profile.getInstitutionFk());
//			addProfile.setEmail(profile.getEmail());
		addProfile.setBankDataAccess(profile.getBankDataAccess());
		addProfile.setPasswordComplexity(profile.getPasswordComplexity());
		addProfile.setSensitiveOperationRecord(profile.getSensitiveOperationRecord());
		addProfile.setUserCreate("1000");
		addProfile.setDateCreate(date);
		addProfile.setUserModif("1000");
		addProfile.setDateModif(date);
		Profile p = profileRepository.save(addProfile);
		return p.getProfileId();
	}

	@Override
	public List<Profile> findAllProfiles(String institutionCode) {
		if(institutionCode != null) {
			return profileRepository.findByIC(institutionCode);
		}
		return profileRepository.findAllProfile();
		// List<Profile> list = entityManager.createNativeQuery("select r.Profile_id ,
		// r.password_complexity , r.admin , r.institution_fk , r.name ,IF(r.status = 1,
		// 'Active' , 'InActive') as status ,r.profile_code , r.email,
		// r.bank_data_access,r.sensitive_operation_record, r.wording , (select
		// user_name from users u where user_id=r.user_create) as user_create
		// ,r.date_create , r.user_modif , r.date_modif from profile r " ,
		// Profile.class).getResultList();
		// List<Profile> list = entityManager.createNativeQuery("select r.*,IF(r.status
		// = 1, 'Active' , 'InActive') as status from profile r " ,
		// Profile.class).getResultList();
		// Query query = entityManager.createQuery("Profile.query_find_all_profiles",
		// Profile.class);
		// List<Profile> list = query.getResultList();
		// return list;
	}

	@Override
	public Profile find(int id) {
		Profile profile = null;
		Optional<Profile> optional = profileRepository.findById(id);
		if (optional.isPresent()) {
			profile = optional.get();
		}
		return profile;
	}

	@Override
	public Profile update(Profile profile) {
		Profile profiles = profileRepository.save(profile);
		return profiles;
	}

	@Override
	public List<Profile> findUserByName(String name) {
		List<Profile> profiles = entityManager
				.createNativeQuery("select p.Profile_id,p.name from profile p where name=:name")
				.setParameter("name", name).getResultList();
		return profiles;
	}

	@Override
	public Profile findByName(String name) {
		return profileRepository.findByName(name);
	}


	@Override
	public void delete(int id) {
		profileRepository.deleteById(id);
	}

}
