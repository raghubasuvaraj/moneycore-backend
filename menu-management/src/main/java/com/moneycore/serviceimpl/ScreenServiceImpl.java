package com.moneycore.serviceimpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.moneycore.entity.GrantPermission;
import com.moneycore.entity.ScreenHistory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moneycore.entity.Screen;
import com.moneycore.repository.ScreenRepository;
import com.moneycore.service.ScreenService;
import org.springframework.transaction.annotation.Transactional;

@Service("screenService")
@Slf4j
public class ScreenServiceImpl implements ScreenService {

    @Autowired
    private ScreenRepository screenRepository;

    @PersistenceContext
    private EntityManager em;

    @Override
    public Screen insert(Screen screen) {
        Screen s = new Screen();
        try {
            Date date = new Date();
            screen.setDateCreate(date);
            s = screenRepository.save(screen);
        } catch (Exception e) {
            log.info("ScreenServiceImpl.insert", e.getLocalizedMessage());
        }
        return s;
    }

    @Override
    public List<Screen> findByScreenCode(String screenCode) {
        List<Screen> screenList = null;
        try {
            screenList = em
                    .createNativeQuery("select i.* from screen i where screen_code=:screenCode", Screen.class)
                    .setParameter("screenCode", screenCode).getResultList();
        } catch (Exception e) {
            log.info("ScreenServiceImpl.findByScreenCode", e.getLocalizedMessage());
        }
        return screenList;
    }

    @Override
    public Optional<Screen> find(String code) {
        Optional<Screen> screen = Optional.of(new Screen());
        try {
            screen = screenRepository.findById(code);
        } catch (Exception e) {
            log.info("ScreenServiceImpl.find", e.getLocalizedMessage());
        }
        return screen;
    }

    @Override
    public List<Screen> findAll() {
        List<Screen> list = null;
        try {
            list = screenRepository.findAllScreens();
        } catch (Exception e) {
            log.info("ScreenServiceImpl.findAll", e.getLocalizedMessage());
        }
        return list;
    }

    @Override
    public Screen update(Screen screen) {
        Screen response = new Screen();
        try {
            response = screenRepository.save(screen);
        } catch (Exception e) {
            log.info("ScreenServiceImpl.update", e.getLocalizedMessage());
        }
        return response;
    }

    @Override
    public List<Screen> findByScreenBYCode(String screenCode) {
        List<Screen> screenList = null;
        try {
            screenList = screenRepository.findByScreenBYCode(screenCode);
        } catch (Exception e) {
            log.info("ScreenServiceImpl.findByScreenBYCode", e.getLocalizedMessage());
        }
        return screenList;
    }

    @Override
    @Transactional
    public void delete(String screenCode) {
        try {
        /*    em.createNativeQuery("insert into screen_history select i.* from screen i where screen_code=:screenCode", ScreenHistory.class)
                    .setParameter("screenCode", screenCode).executeUpdate();*/
            screenRepository.deleteById(screenCode);
        } catch (Exception e) {
            log.info("ScreenServiceImpl.delete", e.getLocalizedMessage());
        }
    }

    @Override
    public List<GrantPermission> findByScreen(String screenCode) {
        List<GrantPermission> grant = null;
        try {
            grant = em
                    .createNativeQuery("select * from grant_permission where screen_fk='" + screenCode + "' order by screen_fk",
                            GrantPermission.class)
                    .getResultList();
        } catch (Exception e) {
            log.info("ScreenServiceImpl.findByScreen", e.getLocalizedMessage());
        }
        return grant;
    }

    public boolean existsByName(String Name) {
        boolean existsByName = false;
        try {
            existsByName = screenRepository.existsByName(Name);
        } catch (Exception e) {
            log.info("ScreenServiceImpl.existsByName", e.getLocalizedMessage());
        }
        return existsByName;
    }

    @Override
    public boolean existsByScreenId(String screenId) {
        boolean existsByScreenId = false;
        try {
            existsByScreenId = screenRepository.existsByScreenId(screenId);
        } catch (Exception e) {
            log.info("ScreenServiceImpl.existsByScreenId", e.getLocalizedMessage());
        }
        return existsByScreenId;
    }

    @Override
    public boolean existsByScreenCode(String screenCode) {
        boolean existsByScreenCode = false;
        try {
            existsByScreenCode = screenRepository.existsByScreenCode(screenCode);
        } catch (Exception e) {
            log.info("ScreenServiceImpl.existsByScreenCode", e.getLocalizedMessage());
        }
        return existsByScreenCode;
    }

    @Override
    public boolean existsByUrl(String url) {
        boolean existsByUrl = false;
        try {
            existsByUrl = screenRepository.existsByUrl(url);
        } catch (Exception e) {
            log.info("ScreenServiceImpl.existsByUrl", e.getLocalizedMessage());
        }
        return existsByUrl;
    }


}
