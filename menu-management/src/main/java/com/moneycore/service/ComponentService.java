package com.moneycore.service;

import java.util.List;

import javax.validation.Valid;

import com.moneycore.entity.Component;

public interface ComponentService {

	List<Component> findByComponentCode(String componentCode);

	Component insert(@Valid Component newcomponent);

	List<Component> findAll();

	List<Component> findIt(String email);

}
