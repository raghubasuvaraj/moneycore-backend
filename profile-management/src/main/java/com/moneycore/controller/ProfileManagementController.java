package com.moneycore.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.moneycore.component.Translator;
import com.moneycore.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.moneycore.model.ResponseModel;
import com.moneycore.service.ProfileService;
import com.moneycore.util.CommonUtil;

@RestController
@RequestMapping("/api/profilemanagement")
public class ProfileManagementController {

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	private ProfileService profileService;

	@RequestMapping(value = { "/profile/{profileId}" }, method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<ResponseModel> getProfileById(HttpServletRequest request,
			@PathVariable("profileId") int profileId) {
		Profile profile = profileService.find(profileId);
		ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("profile.view", null), profile);
		return ResponseEntity.accepted().body(responseModel);
	}

	@RequestMapping(value = { "/profiles" }, method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<ResponseModel> profileList(HttpServletRequest request,
			@RequestParam(required = false) String institutionCode) {
		List<Profile> profiles = profileService.findAllProfiles(institutionCode);
		for (Profile p : profiles) {
			p.setRoles(getRoleIdsByProfileId(p.getProfileId(), request));
			p.setInstitutionFk(p.getInstitute().getInstitutionCode());
		}
		ResponseModel responseModel = new ResponseModel(true, "", profiles);
		return ResponseEntity.accepted().body(responseModel);
	}

	@RequestMapping(value = {
			"/profile/register" }, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> registerProfile(HttpServletRequest request, @Valid @RequestBody Profile newProfile) {
		Date date = new Date();
		List<Profile> profile = profileService.findUserByName(newProfile.getName());
		List<Profile> checkDuplication=profileService.checkduplication(newProfile.getName(),newProfile.getInstitute().getInstitutionCode());

		if (checkDuplication.size() > 0) {
			ResponseModel responseModel = new ResponseModel(false, "profile name is already Exists", null);
			return ResponseEntity.accepted().body(responseModel);
		}
		if (profile.size() > 0) {
			ResponseModel responseModel = new ResponseModel(false, "Profile has already registered with this name",
					null);
			return ResponseEntity.status(200).body(responseModel);
		}
		String institute_fk = newProfile.getInstitutionFk();
		InstitutionList insList = getInstitutionByCode(institute_fk, request);
		if (insList != null) {
			newProfile.setInstitute(insList);
		}
		newProfile.setProfileCode(profileService.getAlphaNumericString(8));
		Profile profiles = profileService.insert(newProfile);
		if (!newProfile.getRoles().isEmpty()) {
			for (int r : newProfile.getRoles()) {
				Permissions permission = new Permissions();
				permission.setRoleId(r);
				Profile assignRole = profileService.findByName(newProfile.getName());
				permission.setProfileId(assignRole.getProfileId());
				permission.setCreateUser(newProfile.getUserCreate());
				permission.setDateCreate(date);
				permission.setSansitiveOperationRecord("1");
				permission.setDeleted(false);
				savePermissions(request, permission);
			}
		}
		ResponseModel responseModel = new ResponseModel(true, "", profiles);
		return ResponseEntity.accepted().body(responseModel);
	}

	@RequestMapping(value = { "/profile/edit/{id}" }, method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> editProfile(@PathVariable int id) {
		Profile profile = profileService.find(id);
		if (profile == null) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("profile.nodata", null), null);
			return ResponseEntity.status(200).body(responseModel);
		}
		List<Roles> permissions = getRolesByProfileId(profile.getProfileId());
		profile.setPermissions(permissions);
		ResponseModel responseModel = new ResponseModel(true, "", profile);
		return ResponseEntity.accepted().body(responseModel);
	}

	@RequestMapping(value = { "/profile/{id}" }, method = RequestMethod.PUT)
	public @ResponseBody ResponseEntity<?> updateRole(@PathVariable int id, @Valid @RequestBody Profile post,
			HttpServletRequest request) {
		Profile profile = profileService.find(id);
		if (profile == null) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("profile.noupdate", null), null);
			return ResponseEntity.status(200).body(responseModel);
		}
		List<Profile> profileDup=profileService.checkupdatedup(post.getName(),post.getInstitutionFk(),id);

		if ( profileDup .size() >0) {
			ResponseModel responseModel = new ResponseModel(false, "profile name is already exists", null);
			return ResponseEntity.accepted().body(responseModel);
		}
		List<Permissions> permissions = getPermissionsByProfileId(profile.getProfileId(), request);
		if (!permissions.isEmpty()) {
			deletePermissionsByProfileId(profile.getProfileId(), request);
		}


		profile.setAdmin(post.getAdmin());
		profile.setName(post.getName());
		profile.setStatus(post.getStatus());
		profile.setWording(post.getWording());
		profile.setBankDataAccess(post.getBankDataAccess());
		profile.setUserModif(post.getUserModif());
		profile.setDateModif(new Date());
		profile.setSensitiveOperationRecord(post.getSensitiveOperationRecord());
		Profile profileResponse = profileService.update(profile);

		if (!post.getRoles().isEmpty()) {
			for (int r : post.getRoles()) {
				Permissions permission = new Permissions();
				permission.setRoleId(r);
				permission.setProfileId(profileResponse.getProfileId());
				permission.setDateModif(new Date());
				permission.setUserModif(post.getUserModif());
				permission.setDeleted(false);
				savePermissions(request, permission);
			}
		}
		List<Roles> permissionUpdate = getRolesByProfileId(profileResponse.getProfileId());
		profileResponse.setPermissions(permissionUpdate);
		profileResponse.setRoles(getRoleIdsByProfileId(profile.getProfileId(), request));
		profileResponse.setInstitutionFk(profileResponse.getInstitute().getInstitutionCode());
		String institute_fk = profileResponse.getInstitutionFk();
		InstitutionList insList = getInstitutionByCode(institute_fk, request);
		if (insList != null) {
			profileResponse.setInstitute(insList);
		}
		profileResponse.setInstitutionFk(institute_fk);
		ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("profile.profileupdate", null), profileResponse);
		return ResponseEntity.accepted().body(responseModel);
	}

	private List<Roles> getRolesByProfileId(int profileId) {
		ResponseModel responseModel = null;
		List<Roles> roles = null;
		String url = CommonUtil.getApplicationBaseUrl() + "/api/rolemanagement/internal/role/" + profileId;
		ResponseEntity<ResponseModel> responseEntity = CommonUtil.getDataFromOtherService(restTemplate, url, null);
		if (responseEntity.getStatusCode().equals(HttpStatus.ACCEPTED)) {
			responseModel = responseEntity.getBody();
			Roles[] rolesArray = CommonUtil.convertToOriginalObject(responseModel.getResult(), Roles[].class);
			if (rolesArray != null) {
				roles = Arrays.asList(rolesArray);
			}
		}
		return roles;
	}

	@SuppressWarnings("unchecked")
	private List<Integer> getRoleIdsByProfileId(int profileId, HttpServletRequest request) {
		ResponseModel responseModel = null;
		List<Integer> roles = null;
		String url = CommonUtil.getApplicationBaseUrl() + "/api/rolemanagement/role_ids/" + profileId;
		ResponseEntity<ResponseModel> responseEntity = CommonUtil.getDataFromOtherService(restTemplate, url,
				CommonUtil.getJwtTokenFromRequest(request));
		if (responseEntity.getStatusCode().equals(HttpStatus.ACCEPTED)) {
			responseModel = responseEntity.getBody();
			roles = CommonUtil.convertToOriginalObject(responseModel.getResult(), List.class);
		}
		return roles;
	}

	private List<Permissions> getPermissionsByProfileId(int profileId, HttpServletRequest request) {
		ResponseModel responseModel = null;
		List<Permissions> permissions = null;
		String url = CommonUtil.getApplicationBaseUrl() + "/api/rolemanagement/permission/" + profileId;
		ResponseEntity<ResponseModel> responseEntity = CommonUtil.getDataFromOtherService(restTemplate, url,
				CommonUtil.getJwtTokenFromRequest(request));
		if (responseEntity.getStatusCode().equals(HttpStatus.ACCEPTED)) {
			responseModel = responseEntity.getBody();
			Permissions[] permissionsArray = CommonUtil.convertToOriginalObject(responseModel.getResult(),
					Permissions[].class);
			if (permissionsArray != null) {
				permissions = Arrays.asList(permissionsArray);
			}
		}
		return permissions;
	}

	private void deletePermissionsByProfileId(int profileId, HttpServletRequest request) {
		String url = CommonUtil.getApplicationBaseUrl() + "/api/rolemanagement/permission/" + profileId;
		ResponseEntity<ResponseModel> responseEntity = CommonUtil.deleteDataFromOtherService(restTemplate, url,
				CommonUtil.getJwtTokenFromRequest(request));
		if (responseEntity.getStatusCode().equals(HttpStatus.ACCEPTED)) {
		}
	}

	private void savePermissions(HttpServletRequest request, Permissions permissions) {
		String url = CommonUtil.getApplicationBaseUrl() + "/api/rolemanagement/permission";
		HttpEntity<Permissions> entity = new HttpEntity<>(permissions,
				CommonUtil.getHeaders(CommonUtil.getJwtTokenFromRequest(request)));
		ResponseEntity<ResponseModel> responseEntity = restTemplate.postForEntity(url, entity, ResponseModel.class);
		if (responseEntity.getStatusCode().equals(HttpStatus.ACCEPTED)) {
		}
	}

	private InstitutionList getInstitutionByCode(String institutionCode, HttpServletRequest request) {
		ResponseModel responseModel = null;
		InstitutionList institutionList = null;
		String url = CommonUtil.getApplicationBaseUrl() + "/api/institutionmanagement/institution/" + institutionCode;
		ResponseEntity<ResponseModel> responseEntity = CommonUtil.getDataFromOtherService(restTemplate, url,
				CommonUtil.getJwtTokenFromRequest(request));
		if (responseEntity.getStatusCode().equals(HttpStatus.ACCEPTED)) {
			responseModel = responseEntity.getBody();
			institutionList = CommonUtil.convertToOriginalObject(responseModel.getResult(), InstitutionList.class);
		}
		return institutionList;
	}

	private List<User> getUsers(HttpServletRequest request) {
		ResponseModel responseModel = null;
		List<User> users = null;
		String url = CommonUtil.getApplicationBaseUrl() + "/api/usermanagement/users";
		ResponseEntity<ResponseModel> responseEntity = CommonUtil.getDataFromOtherService(restTemplate, url,
				CommonUtil.getJwtTokenFromRequest(request));
		if (responseEntity.getStatusCode().equals(HttpStatus.ACCEPTED)) {
			responseModel = responseEntity.getBody();
			User[] userArray = CommonUtil.convertToOriginalObject(responseModel.getResult(),
					User[].class);
			if (userArray != null) {
				users = Arrays.asList(userArray);
			}
		}
		return users;
	}

	@RequestMapping(value = {
			"/profile/delete/{id}" }, method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> deleteProfile(@PathVariable("id") int id,HttpServletRequest request) {
		Profile profile = profileService.find(id);
		if (profile == null) {
			ResponseModel responseModel = new ResponseModel(false, "No profile found with this id", null);
			return ResponseEntity.status(200).body(responseModel);
		}

		List<User> usersCheck = getUsers(request);
		long getUserSize = 0;
		if(usersCheck.size() > 0) {
			getUserSize = usersCheck.stream().filter(userget -> userget.getProfileFk()!=null && id==userget.getProfileFk().getProfileId()).count();
		}
		if(getUserSize > 0){
			ResponseModel responseModel = new ResponseModel(false, "Without Deleting the  User You Can't Delete Profile", null);
			return ResponseEntity.status(200).body(responseModel);
		}

		List<Permissions> permissions = getPermissionsByProfileId(profile.getProfileId(), request);
		if (!permissions.isEmpty()) {
			deletePermissionsByProfileId(profile.getProfileId(), request);
		}
		    profileService.delete(id);
			ResponseModel responseModel = new ResponseModel(true, "Profile deleted successfully", null);
			return ResponseEntity.accepted().body(responseModel);
	}


}
