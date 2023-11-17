package com.moneycore.entityListener;


import com.moneycore.entity.Roles;
import com.moneycore.entity.log.RolesLog;
import com.moneycore.entityListener.logrepo.RolesLogRepository;
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


public class RolesListener {

    Logger log = Logger.getLogger(RolesListener.class.getName());

    @PrePersist
    public void prePersist(Roles entity) {
        // Persistence logic
        log.info("UserLog ...INSERT...@prePersist");
//        perform(entity, "INSERT");
    }
    @PostPersist
    public void postPersist(Roles entity) {
        // Persistence logic
        log.info("UserLog ...INSERT...@PostPersist");
        perform(entity, "INSERT");
    }

    @PreUpdate
    public void preUpdate(Roles entity) {
        // Updation logic
        log.info("UserLog ...UPDATE");
        perform(entity, "UPDATE");
    }

    @PreRemove
    public void preRemove(Roles entity) {
        // Removal logic
        log.info("UserLog ...DELETE");
        perform(entity, "DELETE");
    }

    @Transactional(MANDATORY)
    void perform(Roles entity, String action) {
        String loginUser = "system";
        Authentication authenticationContext = SecurityContextHolder.getContext().getAuthentication();
        if ( authenticationContext != null && !authenticationContext.getName().equals("anonymousUser")) loginUser = authenticationContext.getName();
        RolesLogRepository logRepository = (RolesLogRepository) BeanUtil.getBean("rolesLogRepository");
        logRepository.save(new RolesLog(entity, action, loginUser));
    }
}
