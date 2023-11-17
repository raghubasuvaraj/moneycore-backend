package com.moneycore.serviceimpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.moneycore.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moneycore.repository.MenuRepository;
import com.moneycore.service.MenuService;
import org.springframework.transaction.annotation.Transactional;

@Service("MenuService")
@Slf4j
public class MenuServiceImpl implements MenuService {

	@Autowired
	private MenuRepository menuRepository;

	@PersistenceContext
	private EntityManager em;

	@Override
	public Menu insert(Menu menu) {
		Menu m = new Menu();
		try {
			Date date = new Date();
			menu.setDateCreate(date);
			m = menuRepository.save(menu);
		}catch (Exception e) {
				log.info("MenuServiceImpl.insert", e.getLocalizedMessage());
			}
		return m;
	}

	@Override
	public Optional<Menu> find(String code) {
		Optional<Menu> menu = Optional.of(new Menu());
		try {
			menu=menuRepository.findById(code);
		}catch (Exception e) {
			log.info("MenuServiceImpl.find", e.getLocalizedMessage());
		}
		return menu;
	}

	@Override
	public List<Menu> findAll() {
		List<Menu> listMenus=null;
	try{
		listMenus = menuRepository.findAllMenus();
	}catch (Exception e) {
		log.info("MenuServiceImpl.findAll", e.getLocalizedMessage());
	}
		return listMenus;
	}

	@Override
	public Menu update(Menu menu) {
		Menu response = new Menu();
		try{
			response = menuRepository.save(menu);
		}catch (Exception e) {
			log.info("MenuServiceImpl.update", e.getLocalizedMessage());
		}
		return response;
	}

	@Override
	public Menu findByMenuCode(String menuCode) {
		Menu menu = null;
		try{
		Optional<Menu> optional = menuRepository.findById(menuCode);
		if (optional.isPresent()) {
			menu = optional.get();
		}
		}catch (Exception e) {
			log.info("MenuServiceImpl.findByMenuCode", e.getLocalizedMessage());
		}
		return menu;
	}

	@Override
	@Transactional
	public void delete(String menuCode) {
	try{
/*		em.createNativeQuery("insert into menu_history select i.* from menu i where menu_code=:menuCode", MenuHistory.class)
				.setParameter("menuCode", menuCode).executeUpdate();*/
		menuRepository.deleteById(menuCode);
	}catch (Exception e) {
		log.info("MenuServiceImpl.delete", e.getLocalizedMessage());
	}
	}



	@Override
	public List<GrantPermission> findByMenu(String menuCode) {
		List<GrantPermission> grant =null;
	try{
		grant = em
				.createNativeQuery("select * from grant_permission where menu_fk='" + menuCode + "' order by menu_fk",
						GrantPermission.class)
				.getResultList();
	}catch (Exception e) {
		log.info("MenuServiceImpl.findByMenu", e.getLocalizedMessage());
	}
		return grant;
	}

	@Override
	public boolean existsByName(String Name) {
		boolean existsByName = false;
		try {
			existsByName = menuRepository.existsByName(Name);
		} catch (Exception e) {
			log.info("MenuServiceImpl.existsByName", e.getLocalizedMessage());
		}
		return existsByName;
	}

	@Override
	public boolean existsByMenuCode(String menuCode) {
		boolean existsByMenuCode = false;
		try {
			existsByMenuCode = menuRepository.existsByMenuCode(menuCode);
		} catch (Exception e) {
			log.info("MenuServiceImpl.existsByMenuCode", e.getLocalizedMessage());
		}
		return existsByMenuCode;
	}

	@Override
	public boolean existsByMenuId(String menuId) {
		boolean existsByMenuId = false;
		try {
			existsByMenuId = menuRepository.existsByMenuId(menuId);
		} catch (Exception e) {
			log.info("MenuServiceImpl.existsByMenuId", e.getLocalizedMessage());
		}
		return existsByMenuId;
	}

	@Override
	public boolean existsByUrl(String Url) {
		boolean existsByUrl = false;
		try {
			existsByUrl = menuRepository.existsByUrl(Url);
		} catch (Exception e) {
			log.info("MenuServiceImpl.existsByUrl", e.getLocalizedMessage());
		}
		return existsByUrl;
	}

}
