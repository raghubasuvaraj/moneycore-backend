package com.moneycore.entityListener;

import com.moneycore.entity.Menu;
import com.moneycore.entity.log.MenuLog;
import com.moneycore.entityListener.logrepo.MenuLogRepository;
import com.moneycore.utils.BeanUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import javax.persistence.PostPersist;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.transaction.Transactional;
import java.util.logging.Logger;
import static javax.transaction.Transactional.TxType.MANDATORY;


public class MenuListener {

    Logger log = Logger.getLogger(MenuListener.class.getName());

    @PrePersist
    public void prePersist(Menu entity) {
        // Persistence logic
        log.info("UserLog ...INSERT...@prePersist");
//        perform(entity, "INSERT");
    }
    @PostPersist
    public void postPersist(Menu entity) {
        // Persistence logic
        log.info("UserLog ...INSERT...@PostPersist");
        perform(entity, "INSERT");
    }

    @PreUpdate
    public void preUpdate(Menu entity) {
        // Updation logic
        log.info("UserLog ...UPDATE");
        perform(entity, "UPDATE");
    }

    @PreRemove
    public void preRemove(Menu entity) {
        // Removal logic
        log.info("UserLog ...DELETE");
        perform(entity, "DELETE");
    }

    @Transactional(MANDATORY)
    void perform(Menu entity, String action) {
        String loginUser = "system";
        Authentication authenticationContext = SecurityContextHolder.getContext().getAuthentication();
        if ( authenticationContext != null && !authenticationContext.getName().equals("anonymousUser")) loginUser = authenticationContext.getName();
        MenuLogRepository logRepository = (MenuLogRepository) BeanUtil.getBean("menuLogRepository");
        logRepository.save(new MenuLog(entity, action, loginUser));
    }

}
