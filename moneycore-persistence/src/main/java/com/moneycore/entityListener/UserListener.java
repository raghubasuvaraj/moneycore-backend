package com.moneycore.entityListener;

import com.moneycore.entity.User;
import com.moneycore.entity.log.UserLog;
import com.moneycore.entityListener.logrepo.UserLogRepository;
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


public class UserListener {

    Logger log = Logger.getLogger(UserListener.class.getName());

    @PrePersist
    public void prePersist(User entity) {
        // Persistence logic
        log.info("UserLog ...INSERT...@prePersist");
//        perform(entity, "INSERT");
    }
    @PostPersist
    public void postPersist(User entity) {
        // Persistence logic
        log.info("UserLog ...INSERT...@PostPersist");
        perform(entity, "INSERT");
    }

    @PreUpdate
    public void preUpdate(User entity) {
        // Updation logic
        log.info("UserLog ...UPDATE");
        perform(entity, "UPDATE");
    }

    @PreRemove
    public void preRemove(User entity) {
        // Removal logic
        log.info("UserLog ...DELETE");
        perform(entity, "DELETE");
    }

    @Transactional(MANDATORY)
    void perform(User entity, String action) {
        String loginUser = "system";
        Authentication authenticationContext = SecurityContextHolder.getContext().getAuthentication();
        if ( authenticationContext != null && !authenticationContext.getName().equals("anonymousUser")) loginUser = authenticationContext.getName();
        UserLogRepository logRepository = (UserLogRepository) BeanUtil.getBean("userLogRepository");
        logRepository.save(new UserLog(entity, action, loginUser));
    }
}
