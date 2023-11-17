package com.moneycore.entityListener;

import com.moneycore.entity.Screen;
import com.moneycore.entity.log.ScreenLog;
import com.moneycore.entityListener.logrepo.ScreenLogRepository;
import com.moneycore.utils.BeanUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.transaction.Transactional;
import java.util.logging.Logger;
import static javax.transaction.Transactional.TxType.MANDATORY;


public class ScreenListener {

    Logger log = Logger.getLogger(ScreenListener.class.getName());

    @PrePersist
    public void prePersist(Screen entity) {
        // Persistence logic
        log.info("UserLog ...INSERT");
        perform(entity, "INSERT");
    }

    @PreUpdate
    public void preUpdate(Screen entity) {
        // Updation logic
        log.info("UserLog ...UPDATE");
        perform(entity, "UPDATE");
    }

    @PreRemove
    public void preRemove(Screen entity) {
        // Removal logic
        log.info("UserLog ...DELETE");
        perform(entity, "DELETE");
    }

    @Transactional(MANDATORY)
    void perform(Screen entity, String action) {
        String loginUser = "system";
        Authentication authenticationContext = SecurityContextHolder.getContext().getAuthentication();
        if ( authenticationContext != null && !authenticationContext.getName().equals("anonymousUser")) loginUser = authenticationContext.getName();
        ScreenLogRepository logRepository = (ScreenLogRepository) BeanUtil.getBean("screenLogRepository");
        logRepository.save(new ScreenLog(entity, action, loginUser));
    }

}
