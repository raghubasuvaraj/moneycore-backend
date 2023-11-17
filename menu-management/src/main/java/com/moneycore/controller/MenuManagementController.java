package com.moneycore.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.moneycore.component.Translator;
import com.moneycore.entity.GrantPermission;
import com.moneycore.entity.NotoficationService;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.moneycore.entity.Menu;
import com.moneycore.entity.Screen;
import com.moneycore.model.ResponseModel;
import com.moneycore.service.MenuService;
import com.moneycore.service.ScreenService;

@RestController
@RequestMapping("/api/menumanagement")
public class MenuManagementController {

	@Autowired
	private MenuService menuService;

	@Autowired
	private ScreenService screenService;

	@RequestMapping(value = {
			"/menu/register" }, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> registerMenu(@Valid @RequestBody Menu newmenu) {

	Menu menuList = new Menu();
	try {
		if(!NumberUtils.isParsable(newmenu.getMenuId())){
			ResponseModel responseModel = new ResponseModel(false, "invalid menu id . only allow numeric value ", null);
			return ResponseEntity.accepted().body(responseModel);
		}

		Menu menu = menuService.findByMenuCode(newmenu.getMenuCode());
		Boolean checkExistsByName=menuService.existsByName(newmenu.getName());

		Boolean checkExistsByMenuCode = menuService.existsByMenuCode(newmenu.getMenuCode());
		Boolean checkExistsByMenuId = menuService.existsByMenuId(newmenu.getMenuId());
		Boolean checkExistsByUrl = menuService.existsByUrl(newmenu.getUrl());

		if (checkExistsByMenuId.equals(true) ) {
			ResponseModel responseModel = new ResponseModel(false, "menu id is already Exists", null);
			return ResponseEntity.accepted().body(responseModel);
		}
		if (checkExistsByMenuCode.equals(true) ) {
			ResponseModel responseModel = new ResponseModel(false, "menu code is already Exists", null);
			return ResponseEntity.accepted().body(responseModel);
		}

		if (checkExistsByName.equals(true) ) {
			ResponseModel responseModel = new ResponseModel(false, "menu name is already Exists", null);
			return ResponseEntity.accepted().body(responseModel);
		}
		if (checkExistsByUrl.equals(true) ) {
			ResponseModel responseModel = new ResponseModel(false, "menu url is already Exists", null);
			return ResponseEntity.accepted().body(responseModel);
		}

		if (menu != null) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("menu.code", null), null);
			return ResponseEntity.accepted().body(responseModel);
		}
		if (newmenu.getScreenReferedFk() != null) {
			String sc = newmenu.getScreenReferedFk().getScreenCode();
			List<Screen> screen = screenService.findByScreenBYCode(sc);
			if (screen.isEmpty()) {
				ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("menu.codenomatch", null),
						null);
				return ResponseEntity.status(200).body(responseModel);
			} else {
				newmenu.setScreenReferedFk(screen.get(0));
			}
		}
		if (newmenu.getMenuParentFk() != null) {
			String mc = newmenu.getMenuParentFk().getMenuCode();
			Menu addMenuparent = menuService.findByMenuCode(mc);
			if (addMenuparent == null) {
				ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("menu.codenomatchsys", null),
						null);
				return ResponseEntity.status(200).body(responseModel);
			} else {
				newmenu.setMenuParentFk(addMenuparent);
			}
		}

			menuList = menuService.insert(newmenu);

		}catch(Exception e){
			ResponseModel responseModel = new ResponseModel(false, "trying to process incorrect data", null);
			return ResponseEntity.ok().body(responseModel);
		}
		ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("menu.menucode", null), menuList);
		return ResponseEntity.accepted().body(responseModel);
	}

	@RequestMapping(value = { "/menus" }, method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> menuList(HttpServletRequest request) {
		List<Menu> menu = menuService.findAll();
		ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("menu.menucode", null), menu);
		return ResponseEntity.accepted().body(responseModel);
	}

	@RequestMapping(value = { "/menu/{menucode}" }, method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> getMenyByCode(HttpServletRequest request,
			@PathVariable("menucode") String menuCode) {
		Menu menu = menuService.findByMenuCode(menuCode);
		ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("menu.menucode", null), menu);
		return ResponseEntity.accepted().body(responseModel);
	}

	@RequestMapping(value = {
			"/menu/delete/{menuCode}" }, method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> deleteMenu(@PathVariable("menuCode") String menuCode) {
		Menu menu = menuService.findByMenuCode(menuCode);
	try{
		if (menu == null) {
			ResponseModel responseModel = new ResponseModel(false, "No Menu found with this menu code", null);
			return ResponseEntity.status(200).body(responseModel);
		}

		List<GrantPermission> permissionCheck = menuService.findByMenu(menuCode);
		long getPermiSize = 0;
		if (permissionCheck.size() > 0) {
			getPermiSize = permissionCheck.stream().filter(permi -> permi.getMenuFk() != null && menuCode.equalsIgnoreCase(permi.getMenuFk().getMenuCode())).count();
		}
		if (getPermiSize > 0) {
			ResponseModel responseModel = new ResponseModel(false, "Without Deleting the  Menu Permission You Can't Delete Menu", null);
			return ResponseEntity.status(200).body(responseModel);
		}

		List<Menu> listMenu = menuService.findAll();
		long getMenuSize = 0;

		if (listMenu.size() > 0) {
			getMenuSize = listMenu.stream().filter(menupk -> menupk.getMenuParentFk() != null && menuCode.equalsIgnoreCase(menupk.getMenuParentFk().getMenuCode())).count();
		}
		if (getMenuSize > 0) {
			ResponseModel responseModel = new ResponseModel(false, "Without Deleting the Child Menu You Can't Delete Main Menu", null);
			return ResponseEntity.status(200).body(responseModel);
		} else {
			menuService.delete(menuCode);
			ResponseModel responseModel = new ResponseModel(true, "Menu deleted successfully", null);
			return ResponseEntity.accepted().body(responseModel);
		}
	}catch(Exception e){
			ResponseModel responseModel = new ResponseModel(false, "error occurred in delete", null);
			return ResponseEntity.ok().body(responseModel);
		}
	}
}
