package com.moneycore.entityListener;

import com.moneycore.entity.Profile;
import com.moneycore.entity.log.ProfileLog;
import com.moneycore.entityListener.logrepo.ProfileLogRepository;
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


public class ProfileListener {

    Logger log = Logger.getLogger(ProfileListener.class.getName());

    @PrePersist
    public void prePersist(Profile entity) {
        // Persistence logic
        log.info("UserLog ...INSERT...@prePersist");
//        perform(entity, "INSERT");
    }
    @PostPersist
    public void postPersist(Profile entity) {
        // Persistence logic
        log.info("UserLog ...INSERT...@PostPersist");
        perform(entity, "INSERT");
    }

    @PreUpdate
    public void preUpdate(Profile entity) {
        // Updation logic
        log.info("UserLog ...UPDATE");
        perform(entity, "UPDATE");
    }

    @PreRemove
    public void preRemove(Profile entity) {
        // Removal logic
        log.info("UserLog ...DELETE");
        perform(entity, "DELETE");
    }

    @Transactional(MANDATORY)
    void perform(Profile entity, String action) {
        String loginUser = "system";
        Authentication authenticationContext = SecurityContextHolder.getContext().getAuthentication();
        if ( authenticationContext != null && !authenticationContext.getName().equals("anonymousUser")) loginUser = authenticationContext.getName();
        ProfileLogRepository logRepository = (ProfileLogRepository) BeanUtil.getBean("profileLogRepository");
        logRepository.save(new ProfileLog(entity, action, loginUser));
    }

}
