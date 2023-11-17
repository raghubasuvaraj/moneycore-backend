package com.moneycore.service;

import java.util.List;
import java.util.Optional;

import com.moneycore.entity.GrantPermission;
import com.moneycore.entity.Menu;

public interface MenuService {

	public Menu insert(Menu menu);

	public Optional<Menu> find(String code);

	public List<Menu> findAll();

	public Menu update(Menu menu);

	public Menu findByMenuCode(String menuCode);

	public void delete(String menuCode);

	public boolean existsByName(String Name);
	public boolean existsByMenuCode(String menuCode);
	public boolean existsByMenuId(String menuId);
	public boolean existsByUrl(String Url);

	public List<GrantPermission> findByMenu(String menuCode);
	}
