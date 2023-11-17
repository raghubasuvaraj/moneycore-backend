package com.moneycore.service;

import java.util.List;
import java.util.Optional;

import com.moneycore.entity.GrantPermission;
import com.moneycore.entity.Screen;

public interface ScreenService {
    public Screen insert(Screen screen);

    public List<Screen> findByScreenCode(String screenCode);

    public Optional<Screen> find(String code);

    public List<Screen> findAll();

    public Screen update(Screen screen);

    List<Screen> findByScreenBYCode(String screenCode);

    public boolean existsByName(String Name);

    public boolean existsByScreenId(String screenId);

    public boolean existsByScreenCode(String screenCode);

    public boolean existsByUrl(String url);

    public void delete(String screenCode);

    public List<GrantPermission> findByScreen(String screenCode);
}
