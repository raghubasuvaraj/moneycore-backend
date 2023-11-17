package com.moneycore.serviceimpl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moneycore.entity.Component;
import com.moneycore.repository.ComponentRepository;
import com.moneycore.service.ComponentService;

@Service("ComponentService")
public class ComponentServiceImpl implements ComponentService {

	@Autowired
	private ComponentRepository componentRepository;

	@Autowired
	private EntityManager em;

	@Override
	public Component insert(Component component) {
		Date date = new Date();
//		Component addComponent=new Component();
//		addComponent.setComponentCode(component.getComponentCode());
//		addComponent.setName(component.getName());
//		addComponent.setScreenCode(component.getScreenCode());
//		addComponent.setType(component.getType());
//		addComponent.setParentComponent(component.getParentComponent());
//		addComponent.setUserCreate("1000");
		component.setDateCreate(date);
//		addComponent.setUserModif("1000");
//		addComponent.setDateModif(date);
		Component c = componentRepository.save(component);
		return c;
	}

	@Override
	public List<Component> findByComponentCode(String componentCode) {
		List<Component> componentList = em
				.createNativeQuery("select i.* from component i where component_code=:componentCode", Component.class)
				.setParameter("componentCode", componentCode).getResultList();
		return componentList;
	}

//	@Override
//	public Optional<Component> find(String code) {
//		return componentRepository.findById(code);
//	}

	@Override
	public List<Component> findAll() {
		Query query = em.createNamedQuery("Component.query_find_all_components", Component.class);
		List<Component> list = query.getResultList();
		return list;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Component> findIt(String email) {
		List<Component> comp = em.createNativeQuery(
				"select * from moneycore_test.component where component_code in"
						+ "(select component_fk from moneycore_test.grant_permission where role_fk in"
						+ "(select roleid from moneycore_test.roles where roleid in"
						+ "(select assigned_role_fk from moneycore_test.assigned_roles2permissions where profile_fk in"
						+ "(SELECT profile_id from moneycore_test.profile where profile_id in"
						+ "(SELECT profile_fk from moneycore_test.users where email='" + email + "' )))))",
				Component.class).getResultList();

		return comp;
	}
//
//	@Override
//	public Component update(Component component) {
//		Component response = componentRepository.save(component);
//		return response;
//	}
//	
//	@Override
//	public List<Component> findByComponentBYCode(String componentCode) {
//		List<Component> componentList = em.createNativeQuery("select i.* from component i where component_code=:componentCode",Component.class).setParameter("componentCode", componentCode).getResultList();
//		return componentList;
//	}

}
