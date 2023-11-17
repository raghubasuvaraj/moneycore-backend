package com.moneycore.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.moneycore.component.Translator;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.moneycore.bean.AccessManagementModel;
import com.moneycore.bean.RoleAndPermissionResponse;
import com.moneycore.entity.GrantPermission;
import com.moneycore.entity.InstitutionList;
import com.moneycore.entity.Menu;
import com.moneycore.entity.Permissions;
import com.moneycore.entity.Roles;
import com.moneycore.entity.Screen;
import com.moneycore.model.ResponseModel;
import com.moneycore.service.GrantPermissionService;
import com.moneycore.service.PermissionsService;
import com.moneycore.service.RolesService;
import com.moneycore.util.CommonUtil;

@RestController
@RequestMapping("/api/rolemanagement")
public class RoleManagementController {

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	private RolesService roleService;

	@Autowired
	private PermissionsService permissionsService;

	@Autowired
	GrantPermissionService grantPermissionService;

	@RequestMapping(value = { "/internal/role/{profileId}" }, method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<ResponseModel> getUserRolesByProfileId(HttpServletRequest request,
			@PathVariable("profileId") int profileId) {
	List<Roles> roles=null;
	try{
		roles = roleService.getRolesForProfile(profileId);
	} catch (Exception e) {
		ResponseModel responseModel = new ResponseModel(false, "error occurred loading role data ", roles);
		return ResponseEntity.accepted().body(responseModel);
	}
		ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("role.view", null), roles);
		return ResponseEntity.accepted().body(responseModel);
	}

	@RequestMapping(value = { "/role_ids/{profileId}" }, method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<ResponseModel> getRoleIdsByProfileId(HttpServletRequest request,
			@PathVariable("profileId") int profileId) {
		List<Integer> roles = permissionsService.findRole(profileId);
		ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("role.view", null), roles);
		return ResponseEntity.accepted().body(responseModel);
	}

	@RequestMapping(value = { "/role/{name}" }, method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<ResponseModel> getRoleByName(HttpServletRequest request,
			@PathVariable("name") String name) {
		Roles role = roleService.getRoleByName(name);
		ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("role.view", null), role);
		return ResponseEntity.accepted().body(responseModel);
	}

	@RequestMapping(value = { "/roles" }, method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> roleList(HttpServletRequest request) {
		List<RoleAndPermissionResponse> responses = new ArrayList<RoleAndPermissionResponse>();
		try {
			List<Roles> role = roleService.findAll();
			for (Roles roles : role) {
				List<GrantPermission> grantPermissions = grantPermissionService.findGrantByRole(roles.getRoleid());
				RoleAndPermissionResponse roleAndPermissionResponse = RoleAndPermissionResponse.generateResponse(roles,
						grantPermissions);
				responses.add(roleAndPermissionResponse);
			}
		}catch(Exception e){
			ResponseModel responseModel = new ResponseModel(true, "failed to load roles data", null);
			return ResponseEntity.accepted().body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("role.permission", null), responses);
		return ResponseEntity.accepted().body(responseModel);
	}

	@RequestMapping(value = {
			"/role/register" }, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> registerRole(HttpServletRequest request, @Valid @RequestBody Roles newRole) {
		List<Roles> role = roleService.findByRoleCode(newRole.getRoleCode());
		List<Roles> checkDuplication=roleService.checkRoleNameduplication(newRole.getRoleName(),newRole.getInstitute().getInstitutionCode());
		if (checkDuplication.size() > 0) {
			ResponseModel responseModel = new ResponseModel(false, "role name is already exists in "+newRole.getInstitute().getInstitutionName()+" institute", null);
			return ResponseEntity.ok().body(responseModel);
		}
		if (role.size() > 0) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("role.eixist", null), null);
			return ResponseEntity.status(200).body(responseModel);
		}
		String institute_fk = newRole.getInstitutionCode();
		InstitutionList insList = getInstitutionByCode(institute_fk, request);
		if (insList != null) {
			newRole.setInstitute(insList);
		}
		Roles addRole = roleService.insertRoles(newRole);
		ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("role.view", null), addRole);
		return ResponseEntity.accepted().body(responseModel);
	}

	@RequestMapping(value = { "/role/edit/{id}" }, method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> editRole(@PathVariable int id) {
		Optional<Roles> editedRole = roleService.find(id);
		if (editedRole == null) {
			ResponseModel responseModel = new ResponseModel(false, "No record found.", null);
			return ResponseEntity.status(200).body(responseModel);
		}
		Roles role = editedRole.get();
		ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("role.view", null), role);
		return ResponseEntity.accepted().body(responseModel);
	}

	@RequestMapping(value = { "/role/{id}" }, method = RequestMethod.PUT)
	public @ResponseBody ResponseEntity<?> updateRole(@PathVariable int id, @Valid @RequestBody Roles post) {
		Optional<Roles> editedRole = roleService.find(id);
		if (editedRole == null) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("role.noupdate", null), null);
			return ResponseEntity.status(200).body(responseModel);
		}
        List<Roles> checkDuplication=roleService.checkUpdateDuplication(post.getRoleName(),post.getInstitutionCode(),id);

        if (checkDuplication.size() > 0) {
            ResponseModel responseModel = new ResponseModel(false, "role Name is already exists", null);
            return ResponseEntity.accepted().body(responseModel);
        }

		Roles roles = editedRole.get();
		roles.setRoleName(post.getRoleName());
		roles.setStatus(post.getStatus());
		roles.setBankDataAccess(post.getBankDataAccess());
		roles.setModifyby(post.getModifyby());
		roles.setWording(post.getWording());
		roles.setModifydate(new Timestamp(new Date().getTime()));
		roles.setAdmin(post.getAdmin());

		Roles rolesResponse = roleService.update(roles);
		ResponseModel responseModel = new ResponseModel(true, "", rolesResponse);
		return ResponseEntity.accepted().body(responseModel);
	}

	@RequestMapping(value = {
			"/role/permission" }, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> registerRoleAndPermission(HttpServletRequest request,
			@RequestBody AccessManagementModel accessManagementModel) {
      try {
		  if (accessManagementModel.getRoleCode() != null) {
			  if (roleService.findRole(accessManagementModel.getRoleCode()) != null) {
				  ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("role.eixist", null), null);
				  return ResponseEntity.status(200).body(responseModel);
			  }
		  }
		  List<Roles> checkDuplication = roleService.checkRoleNameduplication(accessManagementModel.getRoleName(), accessManagementModel.getInstitutionCode());
		  if (checkDuplication.size() > 0) {
			  ResponseModel responseModel = new ResponseModel(false, "role name already exists in " + accessManagementModel.getInstitutionCode() + " institute", null);
			  return ResponseEntity.ok().body(responseModel);
		  }
		  InstitutionList insList = getInstitutionByCode(accessManagementModel.getInstitutionCode(), request);
		  if (insList == null) {
			  ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("role.instit", null), null);
			  return ResponseEntity.status(200).body(responseModel);
		  }
		  Roles role = new Roles();
		  role.setRoleName(accessManagementModel.getRoleName());
		  role.setInstitute(insList);
		  role.setAdmin(accessManagementModel.getAdmin());
		  role.setStatus(Character.toString(accessManagementModel.getStatus()));
		  role.setWording(accessManagementModel.getWording());
		  role.setUserCreate(accessManagementModel.getUserCreate());
		  Roles r = roleService.insertRoles(role);
		  for (int i = 0; i < accessManagementModel.getPermissions().size(); i++) {
			  for (int j = 0; j < accessManagementModel.getPermissions().get(i).getType().size(); j++) {
				  GrantPermission grantPermission = new GrantPermission();
				  Screen screen = new Screen();
				  screen.setScreenCode(accessManagementModel.getPermissions().get(i).getScreenFk());
				  Menu menu = new Menu();
				  menu.setMenuCode(accessManagementModel.getPermissions().get(i).getMenuFk());
				  Roles roles = new Roles();
				  roles.setRoleid(r.getRoleid());
				  grantPermission.setScreenFk(screen);
				  grantPermission.setMenuFk(menu);
				  grantPermission.setRoleFk(roles);
				  grantPermission.setUserCreate(accessManagementModel.getUserCreate());
				  grantPermission.setDateCreate(new Date());
				  grantPermission.setType(accessManagementModel.getPermissions().get(i).getType().get(j));
				  grantPermissionService.addGP(grantPermission);
			  }
		  }
	  }catch(Exception e){
		  ResponseModel responseModel = new ResponseModel(true, "incorrect data", null);
		  return ResponseEntity.status(200).body(responseModel);
	  }
		ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("role.created", null), null);
		return ResponseEntity.status(200).body(responseModel);
	}

	@RequestMapping(value = {
			"/role/permission/{id}" }, method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateRoleAndPermission(@PathVariable int id,
			@RequestBody AccessManagementModel accessManagementModel) {
		Optional<Roles> editedRole = roleService.find(id);
		if (!editedRole.isPresent()) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("role.wrongid", null), null);
			return ResponseEntity.status(200).body(responseModel);
		}
		List<Roles> checkDuplication=roleService.checkUpdateDuplication(accessManagementModel.getRoleName(),accessManagementModel.getInstitutionCode(),id);

		if (checkDuplication.size() > 0) {
			ResponseModel responseModel = new ResponseModel(false, "role name is already exists", null);
			return ResponseEntity.accepted().body(responseModel);
		}
		Roles roles = editedRole.get();
		roles.setRoleName(accessManagementModel.getRoleName());
		roles.setAdmin(accessManagementModel.getAdmin());
		roles.setStatus(Character.toString(accessManagementModel.getStatus()));
		roles.setWording(accessManagementModel.getWording());
		roles.setModifyby(accessManagementModel.getUserModif());
		roles.setModifydate(new Timestamp(new Date().getTime()));
		Roles r = roleService.update(roles);
		List<GrantPermission> grantPermissions = grantPermissionService.findByRole(r.getRoleid());
		if (!grantPermissions.isEmpty()) {
			grantPermissionService.deleteByRole(r.getRoleid());
		}
		for (int i = 0; i < accessManagementModel.getPermissions().size(); i++) {
			for (int j = 0; j < accessManagementModel.getPermissions().get(i).getType().size(); j++) {
				GrantPermission grantPermission = new GrantPermission();
				Screen screen = new Screen();
				screen.setScreenCode(accessManagementModel.getPermissions().get(i).getScreenFk());
				Menu menu = new Menu();
				menu.setMenuCode(accessManagementModel.getPermissions().get(i).getMenuFk());
				Roles role = new Roles();
				role.setRoleid(r.getRoleid());
				grantPermission.setScreenFk(screen);
				grantPermission.setMenuFk(menu);
				grantPermission.setRoleFk(role);
				grantPermission.setUserCreate(r.getUserCreate());
				grantPermission.setDateCreate(r.getDateCreate());
				grantPermission.setUserModif(accessManagementModel.getUserModif());
				grantPermission.setDateModif(new Date());
				grantPermission.setType(accessManagementModel.getPermissions().get(i).getType().get(j));
				grantPermissionService.update(grantPermission);
			}
		}
		ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("role.permissionupdate", null), null);
		return ResponseEntity.status(200).body(responseModel);
	}

	@RequestMapping(value = { "/permission/{profileId}" }, method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<ResponseModel> getPermissionsByProfileId(HttpServletRequest request,
			@PathVariable("profileId") int profileId) {
		List<Permissions> permissions = permissionsService.getPermissionsByProfile(profileId);
		ResponseModel responseModel = new ResponseModel(true, "", permissions);
		return ResponseEntity.accepted().body(responseModel);
	}

	@RequestMapping(value = { "/permission/{profileId}" }, method = RequestMethod.DELETE)
	public @ResponseBody ResponseEntity<ResponseModel> deletePermissionsByProfileId(HttpServletRequest request,
			@PathVariable("profileId") int profileId) {
		permissionsService.deleteByProfile(profileId);
		ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("role.permissiondelete", null), null);
		return ResponseEntity.accepted().body(responseModel);
	}
	
	@RequestMapping(value = { "/permission" }, method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<ResponseModel> savePermissions(HttpServletRequest request,
			@Valid @RequestBody Permissions permissions) {
		permissionsService.savePermissions(permissions);
		ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("role.permissionsave", null), null);
		return ResponseEntity.accepted().body(responseModel);
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
			"/role/delete/{roleid}" }, method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> deleteRoles(@PathVariable("roleid") int roleid) {
		Optional<Roles> roles = roleService.find(roleid);
		if (roles == null) {
			ResponseModel responseModel = new ResponseModel(false, "No Role found with this role code", null);
			return ResponseEntity.status(200).body(responseModel);
		}

		List<GrantPermission> permissionCheck = grantPermissionService.findByRole(roleid);
		long getRoleiSize = 0;
		if(permissionCheck.size() > 0) {
			getRoleiSize = permissionCheck.stream().filter(roleget -> roleget.getMenuFk()!=null && roleid==roleget.getRoleFk().getRoleid()).count();
		}
		if(getRoleiSize > 0){
			ResponseModel responseModel = new ResponseModel(false, "Without Deleting the  Grant Role Permission You Can't Delete Roles", null);
			return ResponseEntity.status(200).body(responseModel);
		}

		if(permissionsService.ifRolePresentCheck(roleid)){
			ResponseModel responseModel = new ResponseModel(false, "Without Deleting the Profile Permission You Can't Delete Roles", null);
			return ResponseEntity.status(200).body(responseModel);
		}else{
			roleService.delete(roleid);
			ResponseModel responseModel = new ResponseModel(true, "Role deleted successfully", null);
			return ResponseEntity.accepted().body(responseModel);
		}
	}
}
