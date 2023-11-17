package com.moneycore.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "grant_permission")
@NamedQuery(query = "select u from GrantPermission u where user_create != '0'", name = "GrantPermission.query_find_all_permissions")
public class GrantPermission implements Serializable {
	private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "grant_permission_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int permissionId;
    @Column(name = "type_grant")
    private String type;
    @OneToOne
    @JoinColumn(name="menu_fk")
    private Menu menuFk;
    @OneToOne
    @JoinColumn(name="screen_fk")
    private Screen screenFk;
    @OneToOne
    @JoinColumn(name="component_fk")
    private Component componentFk;
    @OneToOne
    @JoinColumn(name="role_fk")
    private Roles roleFk;
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
	public int getPermissionId() {
		return permissionId;
	}
	public void setPermissionId(int permissionId) {
		this.permissionId = permissionId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Menu getMenuFk() {
		return menuFk;
	}
	public void setMenuFk(Menu menuFk) {
		this.menuFk = menuFk;
	}
	public Screen getScreenFk() {
		return screenFk;
	}
	public void setScreenFk(Screen screenFk) {
		this.screenFk = screenFk;
	}
	public Component getComponentFk() {
		return componentFk;
	}
	public void setComponentFk(Component componentFk) {
		this.componentFk = componentFk;
	}
	public Roles getRoleFk() {
		return roleFk;
	}
	public void setRoleFk(Roles roleFk) {
		this.roleFk = roleFk;
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
}
