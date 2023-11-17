package com.moneycore.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "menu_history")
public class MenuHistory implements Serializable {
	    private static final long serialVersionUID = 1L;
	    @Id
	    @Column(name = "menu_code")
	    private String menuCode;
	    @Column(name = "menu_id")
	    private String menuId;
	    @Column(name = "name")
	    private String name;
	    @Column(name = "workspace")
	    private String workSpace;
	    @Column(name = "module")
	    private String module;
	    @Column(name = "wording")
	    private String wording;
	    @Column(name = "type")
	    private String type;
	    @Column(name = "menu_level")
	    private int menuLevel;
	    @Column(name = "menu_order")
	    private int menuOrder;
	    @Column(name = "url")
	    private String url;
	    @Column(name = "key_words")
	    private String keyWords;
	    @Column(name = "params")
	    private String params;
	    @Column(name = "user_create")
	    private String userCreate;
	    @Column(name = "date_create")
	    @Temporal(TemporalType.DATE)
	    private Date dateCreate;
	    @Column(name = "user_modif")
	    private String userModif;
	    @Column(name = "date_modif")
	    @Temporal(TemporalType.DATE)
	    private Date dateModif;

	    @Column(name = "menu_parent_fk")
	    private String menuParentFk;
	     @Column(name = "screen_reffered_fk")
	    private String screenReferedFk;

		public String getMenuId() {
			return menuId;
		}
		public void setMenuId(String menuId) {
			this.menuId = menuId;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getWorkspace() {
			return workSpace;
		}
		public void setWorkspace(String workspace) {
			this.workSpace = workspace;
		}
		public String getModule() {
			return module;
		}
		public void setModule(String module) {
			this.module = module;
		}
		public String getWording() {
			return wording;
		}
		public void setWording(String wording) {
			this.wording = wording;
		}
		public String getMenuCode() {
			return menuCode;
		}
		public void setMenuCode(String menuCode) {
			this.menuCode = menuCode;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public int getMenuLevel() {
			return menuLevel;
		}
		public void setMenuLevel(int menuLevel) {
			this.menuLevel = menuLevel;
		}
		public int getMenuOrder() {
			return menuOrder;
		}
		public void setMenuOrder(int menuOrder) {
			this.menuOrder = menuOrder;
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public String getKeyWords() {
			return keyWords;
		}
		public void setKeyWords(String keyWords) {
			this.keyWords = keyWords;
		}
		public String getParams() {
			return params;
		}
		public void setParams(String params) {
			this.params = params;
		}
		public String getUserCreate() {
			return userCreate;
		}
		public void setUserCreate(String userCreate) {
			this.userCreate = userCreate;
		}
		public Date getDateCreate() {
			return dateCreate;
		}
		public void setDateCreate(Date dateCreate) {
			this.dateCreate = dateCreate;
		}
		public String getUserModif() {
			return userModif;
		}
		public void setUserModif(String userModif) {
			this.userModif = userModif;
		}
		public Date getDateModif() {
			return dateModif;
		}
		public void setDateModif(Date dateModif) {
			this.dateModif = dateModif;
		}

	public String getWorkSpace() {
		return workSpace;
	}

	public void setWorkSpace(String workSpace) {
		this.workSpace = workSpace;
	}


	public String getMenuParentFk() {
		return menuParentFk;
	}

	public void setMenuParentFk(String menuParentFk) {
		this.menuParentFk = menuParentFk;
	}

	public String getScreenReferedFk() {
		return screenReferedFk;
	}

	public void setScreenReferedFk(String screenReferedFk) {
		this.screenReferedFk = screenReferedFk;
	}

	@Override
		public String toString() {
			return "Menu [menuCode=" + menuCode + ", menuId=" + menuId + ", name=" + name + ", workSpace=" + workSpace
					+ ", module=" + module + ", wording=" + wording + ", type=" + type + ", menuLevel=" + menuLevel
					+ ", menuOrder=" + menuOrder + ", url=" + url + ", keyWords=" + keyWords + ", params=" + params
					+ ", userCreate=" + userCreate + ", dateCreate=" + dateCreate + ", userModif=" + userModif
					+ ", dateModif=" + dateModif + ", menuParentFk=" + menuParentFk + ", screenReferedFk="
					+ screenReferedFk + "]";
		}
		
}
