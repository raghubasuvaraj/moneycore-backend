package com.moneycore.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.moneycore.component.Translator;
import com.moneycore.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.moneycore.bean.Menues;
import com.moneycore.bean.UserData;
import com.moneycore.bean.UserResponse;
import com.moneycore.email.EmailBuilder;
import com.moneycore.email.Mail;
import com.moneycore.model.ResponseModel;
import com.moneycore.service.Mailer;
import com.moneycore.service.UserService;
import com.moneycore.util.CommonUtil;

@RestController
@RequestMapping("/api/usermanagement")
public class UserManagementController {

	@Value("${mail.from}")
	private String testEmail;

	@Autowired
	private Mailer emailService;

	@Autowired
	private UserService userService;

	@Autowired
	RestTemplate restTemplate;

	private static final Logger logger = LoggerFactory.getLogger(UserManagementController.class);

	@RequestMapping(value = { "/internal/user/{email}" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<ResponseModel> getUserByEmail(HttpServletRequest request,
			@PathVariable("email") String email) {

		User user = userService.getUserByEmail(email);

		ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("user.view", null), user);
		return ResponseEntity.accepted().body(responseModel);
	}

	@RequestMapping(value = { "/users" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> userlist(HttpServletRequest request) {
		List<User> users = userService.findAllUsers();
		logger.info("USERS LIST",users);
		ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("user.view", null), users);
		return ResponseEntity.accepted().body(responseModel);
	}

	@RequestMapping(value = {
			"/user/register" }, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> registerUser(@Valid @RequestBody UserData newUser) throws MessagingException {
		User existUser = userService.findUserByEmail(newUser.getEmail());
		Boolean userdup=userService.findUserdup(newUser.getUserName());

		if (userdup.equals(true) ) {
			ResponseModel responseModel = new ResponseModel(false, "user name is already exist", null);
			return ResponseEntity.accepted().body(responseModel);
		}

		if (existUser != null) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("user.reg", null), null);
			return ResponseEntity.accepted().body(responseModel);
		}
		User existPhone = userService.findUserByPhoneNumber(newUser.getPhoneNumber());
		if (existPhone != null) {
			ResponseModel responseModel = new ResponseModel(false, "User already registered with this Phone Number", null);
			return ResponseEntity.accepted().body(responseModel);
		}
		User existUserName = userService.findUserByUserName(newUser.getUserName());
		if (existUserName != null) {
			ResponseModel responseModel = new ResponseModel(false, "User already registered with this User Name", null);
			return ResponseEntity.accepted().body(responseModel);
		}
		List<User> existEmpno = userService.findUserByEmployeNumber(newUser.getEmployeNumber());
		if (existEmpno.size() > 0) {
			ResponseModel responseModel = new ResponseModel(false, "User already registered with this Employee Number", null);
			return ResponseEntity.accepted().body(responseModel);
		}
		Profile profile = getProfileById(newUser.getProfileFk());
		if (profile == null) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("user.profileid", null), null);
			return ResponseEntity.accepted().body(responseModel);
		}
		String password = userService.randomPassword();
		System.out.println("password after random generate::" + password);
		newUser.setPassword(password);
		UserData u = userService.insert(newUser);
		String to = u.getEmail();
		Mail mail = new EmailBuilder().From(testEmail).To(to).Template("confirmation.html")
				.AddContext("subject", "Money Core").AddContext("password", password)
				.AddContext("username", u.getUserName()).AddContext("email", u.getEmail())
				.Subject("Money Core Registration").createMail();
		emailService.sendMail(mail, true);
		sendmail(password,u.getUserName(), u.getEmail());
		ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("user.userdata", null), u);
		return ResponseEntity.accepted().body(responseModel);
	}

	@RequestMapping(value = { "/user/edit/{id}" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> editUsers(@PathVariable int id) {
		Optional<User> editedUser = userService.findByUserID(id);
		if (editedUser == null) {
			ResponseModel responseModel = new ResponseModel(false, "No record found.", null);
			return ResponseEntity.status(200).body(responseModel);
		}
		User users = editedUser.get();
		ResponseModel responseModel = new ResponseModel(true, "User", users);
		return ResponseEntity.accepted().body(responseModel);
	}

	@RequestMapping(value = { "/user/{id}" }, method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateUsers(@PathVariable int id, @Valid @RequestBody UserData userData) {
		Optional<User> editedUser = userService.findByUserID(id);
		if (editedUser == null) {
			ResponseModel responseModel = new ResponseModel(false, "No record found.", null);
			return ResponseEntity.status(200).body(responseModel);
		}
		List<User> Userdup=userService.findUserdup(userData.getUserName(), id);

		if (Userdup.size() >0 ) {
			ResponseModel responseModel = new ResponseModel(false, "user name is already exists", null);
			return ResponseEntity.accepted().body(responseModel);
		}

		User users = editedUser.get();
		users.setUserName(userData.getUserName());
		users.setJobTitle(userData.getJobTitle());
		users.setPhoneNumber(userData.getPhoneNumber());
		users.setLanguage(userData.getLanguage());
		users.setBankCodeAccessList(userData.getBankCodeAccessList());
		users.setDateModif(new Timestamp(new Date().getTime()));
		users.setUserModif(userData.getUserModif());
		users.setAccountEndDate(userData.getAccountEndDate());
		Profile profile = null;
		if (userData.getProfileFk() > 0) {
			profile = getProfileById(userData.getProfileFk());
			if (profile == null) {
				ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("user.profileid", null), null);
				return ResponseEntity.accepted().body(responseModel);
			}
		}
		if (profile == null) {
			users.setProfileFk(null);
		} else {
			users.setProfileFk(profile);
		}
		UserData userDataResponse = userService.update(users);
		ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("user.userdata", null), userDataResponse);
		return ResponseEntity.accepted().body(responseModel);
	}
	public void sendmail(String password,String username,String email) throws MessagingException {
		Mail mail1 = new EmailBuilder().From(testEmail).To(Translator.toLocale("client.test", null)).Template("confirmation.html")
				.AddContext("subject", "Money Core").AddContext("password", password)
				.AddContext("username", username).AddContext("email", email)
				.Subject("Money Core Registration").createMail();
		emailService.sendMail(mail1, true);
	}
	@RequestMapping(value = { "/user/menus/detail" }, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> getUserMenusDetail(HttpServletRequest request) {
		System.out.println("getUserMenusDetail");
		User user = userService.findUserByEmail(CommonUtil.getCurrentUserName());
		if (user != null) {
			System.out.println("User found");
			UserResponse userResponse = new UserResponse();
			userResponse.setUserId(user.getUserId());
			userResponse.setUserName(user.getUserName());
			userResponse.setEmail(user.getEmail());
			if (user.getInstitutionFk() != null) {
				userResponse.setInstitutionCode(user.getInstitutionFk());
				InstitutionList insList = getInstitutionByCode(user.getInstitutionFk(), request);
				if (insList != null) {
					userResponse.setInstitutionName(insList.getInstitutionName());
				}
			}
			int userProfile = user.getProfileFk().getProfileId();
			List<Roles> roleList = getRolesByProfileId(userProfile);
			List<GrantPermission> l = null;
			List<Menues> menues = new ArrayList<Menues>();
			List<Menues> menuesResp = new ArrayList<Menues>();
			for (Roles r : roleList) {
				List<Menues> subScreenList = new ArrayList<Menues>();

				l = getGrantPermissionsByRoleId(r.getRoleid());
				if (l != null && !l.isEmpty()) {
					for (GrantPermission gp : l) {
						Menu menu = getMenuByCode(gp.getMenuFk().getMenuCode());
						Menues screens = new Menues();
						screens.setMenuCode(menu.getMenuCode());
						screens.setMenuName(menu.getName());
						screens.setMenuType(menu.getType());
						screens.setMenuDisplayName(menu.getWording());
						screens.setScreenCode(menu.getScreenReferedFk());
						screens.setURL(menu.getUrl());
						if (!(menu.getMenuParentFk() == null)) {
							screens.setParentMenu(menu.getMenuParentFk().getMenuCode());
						}
						screens.setGrants(gp.getType());
						subScreenList.add(screens);
					}
				}

				for(Menues manuValidate : subScreenList){
					String parentMenu = CommonUtil.masknull(manuValidate.getParentMenu());
					if(parentMenu.isEmpty()){
						ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("user.parent.menu"+manuValidate.getMenuName(), null), null);
						return ResponseEntity.accepted().body(responseModel);
					}
				}

				Collections.sort(subScreenList, new Comparator<Menues>() {
					@Override
					public int compare(Menues o1, Menues o2) {
						return o1.getParentMenu().compareTo(o2.getParentMenu());

					}
				});

				String ParentScreen = "";
				for (Menues menu : subScreenList) {
					Menu parentMenu = getMenuByCode(menu.getParentMenu());
					if (!ParentScreen.equals(parentMenu.getMenuCode())) {
						ParentScreen = parentMenu.getMenuCode();
						List<Menues> subm = new ArrayList<Menues>();
						Menues mm = new Menues();
						mm.setMenuCode(parentMenu.getMenuCode());
						mm.setMenuName(parentMenu.getName());
						mm.setMenuType(parentMenu.getType());
						mm.setMenuDisplayName(parentMenu.getWording());
						mm.setScreenCode(parentMenu.getScreenReferedFk());
						mm.setURL(parentMenu.getUrl());
						if (!(parentMenu.getMenuParentFk() == null)) {
							mm.setParentMenu(parentMenu.getMenuParentFk().getMenuCode());
						}
						// String oldMenuCode = "";
						for (Menues menu2 : subScreenList) {
							if (menu2.getParentMenu().equals(ParentScreen)) {
								// if(!oldMenuCode.equals(menu2.getMenuCode())){
								// oldMenuCode = menu2.getMenuCode();
								subm.add(menu2);
								// }
							}
						}
						mm.setSubMenu(subm);
						menues.add(mm);
						if (parentMenu.getMenuParentFk() == null) {
							menuesResp.add(mm);
							userResponse.setMenues(menuesResp);
							menues = new ArrayList<Menues>();
						} else {
							Menu parent2 = getMenuByCode(menu.getParentMenu());
							while (!(parent2.getMenuParentFk() == null)) {
								parent2 = getMenuByCode(parent2.getMenuParentFk().getMenuCode());
								Menues rootMenu = new Menues();
								rootMenu.setMenuCode(parent2.getMenuCode());
								rootMenu.setMenuName(parent2.getName());
								rootMenu.setMenuType(parent2.getType());
								rootMenu.setMenuDisplayName(parent2.getWording());
								rootMenu.setScreenCode(parent2.getScreenReferedFk());
								rootMenu.setURL(parent2.getUrl());
								if (!(parent2.getMenuParentFk() == null)) {
									rootMenu.setParentMenu(parent2.getMenuParentFk().getMenuCode() + "");
								}
								rootMenu.setSubMenu(menues);
								menues = new ArrayList<Menues>();
								menues.add(rootMenu);
							}
							menuesResp.add(menues.get(0));
							userResponse.setMenues(menuesResp);
						}
					}

				}
			}
			ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("user.response", null), userResponse);
			return ResponseEntity.accepted().body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("user.norole", null), null);
		return ResponseEntity.accepted().body(responseModel);
	}

	private List<GrantPermission> getGrantPermissionsByRoleId(int roleId) {
		ResponseModel responseModel = null;
		List<GrantPermission> grantPermissions = null;
		String url = CommonUtil.getApplicationBaseUrl() + "/api/rolemanagement/grantpermission/" + roleId;
		ResponseEntity<ResponseModel> responseEntity = CommonUtil.getDataFromOtherService(restTemplate, url,
				CommonUtil.getJwtTokenFromRequest(CommonUtil.getReuestObjectFromContext()));
		if (responseEntity.getStatusCode().equals(HttpStatus.ACCEPTED)) {
			responseModel = responseEntity.getBody();
			GrantPermission[] grantPermissionsArray = CommonUtil.convertToOriginalObject(responseModel.getResult(),
					GrantPermission[].class);
			if (grantPermissionsArray != null) {
				grantPermissions = Arrays.asList(grantPermissionsArray);
			}
		}
		return grantPermissions;
	}

	private Menu getMenuByCode(String menuCode) {
		ResponseModel responseModel = null;
		Menu menu = null;
		String url = CommonUtil.getApplicationBaseUrl() + "/api/menumanagement/menu/" + menuCode;
		ResponseEntity<ResponseModel> responseEntity = CommonUtil.getDataFromOtherService(restTemplate, url,
				CommonUtil.getJwtTokenFromRequest(CommonUtil.getReuestObjectFromContext()));
		if (responseEntity.getStatusCode().equals(HttpStatus.ACCEPTED)) {
			responseModel = responseEntity.getBody();
			menu = CommonUtil.convertToOriginalObject(responseModel.getResult(), Menu.class);
		}
		return menu;
	}

	private Profile getProfileById(int profileId) {
		ResponseModel responseModel = null;
		Profile profile = null;
		String url = CommonUtil.getApplicationBaseUrl() + "/api/profilemanagement/profile/" + profileId;
		ResponseEntity<ResponseModel> responseEntity = CommonUtil.getDataFromOtherService(restTemplate, url,
				CommonUtil.getJwtTokenFromRequest(CommonUtil.getReuestObjectFromContext()));
		if (responseEntity.getStatusCode().equals(HttpStatus.ACCEPTED)) {
			responseModel = responseEntity.getBody();
			profile = CommonUtil.convertToOriginalObject(responseModel.getResult(), Profile.class);
		}
		return profile;
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

	@RequestMapping(value = {
			"/user/delete/{id}" }, method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> deleteProfile(@PathVariable("id") int id,HttpServletRequest request) {
		Optional<User> editedUser = userService.findByUserID(id);
		if (editedUser == null) {
			ResponseModel responseModel = new ResponseModel(false, "No record found.", null);
			return ResponseEntity.status(200).body(responseModel);
		}
		userService.delete(id);
		ResponseModel responseModel = new ResponseModel(true, "User deleted successfully", null);
		return ResponseEntity.accepted().body(responseModel);
	}

}
