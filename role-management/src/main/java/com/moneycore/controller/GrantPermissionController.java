package com.moneycore.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.moneycore.component.Translator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.moneycore.entity.GrantPermission;
import com.moneycore.model.ResponseModel;
import com.moneycore.service.GrantPermissionService;

@RestController
@RequestMapping("/api/rolemanagement")
public class GrantPermissionController {

	@Autowired
	GrantPermissionService grantPermissionService;

	@RequestMapping(value = { "/grantpermission/screen/{uri}" }, method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<ResponseModel> getCurrentUserGrantPermissionByScreenUrl(
			HttpServletRequest request, @PathVariable("uri") String uri) {
		GrantPermission grantPermission = grantPermissionService.findByScreenUrl(uri.replaceAll("_", "/"));
		ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("grand.premission", null), grantPermission);
		return ResponseEntity.accepted().body(responseModel);
	}

	@RequestMapping(value = { "/grantpermission/menu/{uri}" }, method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<ResponseModel> getCurrentUserGrantPermissionByMenuUrl(
			HttpServletRequest request, @PathVariable("uri") String uri) {
		GrantPermission grantPermission = grantPermissionService.findByMenuUrl(uri.replaceAll("_", "/"));
		ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("grand.premission", null), grantPermission);
		return ResponseEntity.accepted().body(responseModel);
	}

	@RequestMapping(value = { "/grantpermission/{roleId}" }, method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<ResponseModel> getGrantPermissionByRoleId(HttpServletRequest request,
			@PathVariable("roleId") int roleId) {
		List<GrantPermission> grantPermissions = grantPermissionService.findGrantByRole(roleId);
		ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("grand.premission", null), grantPermissions);
		return ResponseEntity.accepted().body(responseModel);
	}
}
