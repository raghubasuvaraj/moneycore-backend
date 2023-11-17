package com.moneycore.bean;

import java.util.List;

public class GrantPermissionModel {

	private List<String> type;
	private String menuFk;
	private String screenFk;
	private String componentFk;
	private int roleFk;

	public List<String> getType() {
		return type;
	}

	public void setType(List<String> type) {
		this.type = type;
	}

	public String getMenuFk() {
		return menuFk;
	}

	public void setMenuFk(String menuFk) {
		this.menuFk = menuFk;
	}

	public String getScreenFk() {
		return screenFk;
	}

	public void setScreenFk(String screenFk) {
		this.screenFk = screenFk;
	}

	public String getComponentFk() {
		return componentFk;
	}

	public void setComponentFk(String componentFk) {
		this.componentFk = componentFk;
	}

	public int getRoleFk() {
		return roleFk;
	}

	public void setRoleFk(int roleFk) {
		this.roleFk = roleFk;
	}

}
