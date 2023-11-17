package com.moneycore.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "component")
@NamedQuery(query = "select u from Component u where user_create != '0'", name = "Component.query_find_all_components")
public class Component implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "component_code")
	private String componentCode;
	@Column(name = "component_id")
	private String componentId;
	@Column(name = "component_name")
	private String name;
	@Column(name = "component_type")
	private String type;
	@ManyToOne
	@JoinColumn(name = "screen_code_fk")
	private Screen screenCode;
	@OneToOne
	@JoinColumn(name = "parent_component_fk")
	private Component parentComponent;
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

	public String getComponentCode() {
		return componentCode;
	}

	public void setComponentCode(String componentCode) {
		this.componentCode = componentCode;
	}

	public String getComponentId() {
		return componentId;
	}

	public void setComponentId(String componentId) {
		this.componentId = componentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Screen getScreenCode() {
		return screenCode;
	}

	public void setScreenCode(Screen screenCode) {
		this.screenCode = screenCode;
	}

	public Component getParentComponent() {
		return parentComponent;
	}

	public void setParentComponent(Component parentComponent) {
		this.parentComponent = parentComponent;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
