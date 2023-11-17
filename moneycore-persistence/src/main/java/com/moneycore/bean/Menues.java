package com.moneycore.bean;

import java.util.List;

import com.moneycore.entity.Screen;

public class Menues {
	String MenuCode;
	String MenuName;
	String MenuType;
	String MenuDisplayName;
	Screen screenCode;
	String URL;
	String Grants;
	String ParentMenu;
	List<Menues> SubMenu;

	public String getMenuCode() {
		return MenuCode;
	}

	public void setMenuCode(String menuCode) {
		MenuCode = menuCode;
	}

	public String getMenuName() {
		return MenuName;
	}

	public void setMenuName(String menuName) {
		MenuName = menuName;
	}

	public String getMenuType() {
		return MenuType;
	}

	public void setMenuType(String menuType) {
		MenuType = menuType;
	}

	public String getMenuDisplayName() {
		return MenuDisplayName;
	}

	public void setMenuDisplayName(String menuDisplayName) {
		MenuDisplayName = menuDisplayName;
	}

	public Screen getScreenCode() {
		return screenCode;
	}

	public void setScreenCode(Screen screenCode) {
		this.screenCode = screenCode;
	}

	public String getURL() {
		return URL;
	}

	public void setURL(String uRL) {
		URL = uRL;
	}

	public List<Menues> getSubMenu() {
		return SubMenu;
	}


	public void setSubMenu(List<Menues> subMenu) {
		SubMenu = subMenu;
	}


	public String getGrants(){
		return Grants;
	}

	public void setGrants(String grants){
     	Grants = grants;
	}


	public String getParentMenu(){
		return ParentMenu;
	}

	public void setParentMenu(String parentMenu){
		ParentMenu = parentMenu;
	}
	

}
