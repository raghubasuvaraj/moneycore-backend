package com.moneycore.entityListener;

import com.moneycore.entity.Branch;
import com.moneycore.entity.log.BranchLog;
import com.moneycore.entityListener.logrepo.BranchLogRepository;
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


public class BranchListener {

    Logger log = Logger.getLogger(BranchListener.class.getName());

    @PrePersist
    public void prePersist(Branch entity) {
        // Persistence logic
        log.info("UserLog ...INSERT...@prePersist");
        perform(entity, "INSERT");
    }
    @PostPersist
    public void postPersist(Branch entity) {
        // Persistence logic
        log.info("UserLog ...INSERT...@PostPersist");
//        perform(entity, "INSERT");
    }

    @PreUpdate
    public void preUpdate(Branch entity) {
        // Updation logic
        log.info("UserLog ...UPDATE");
        perform(entity, "UPDATE");
    }

    @PreRemove
    public void preRemove(Branch entity) {
        // Removal logic
        log.info("UserLog ...DELETE");
        perform(entity, "DELETE");
    }

    @Transactional(MANDATORY)
    void perform(Branch entity, String action) {
        String loginUser = "system";
        Authentication authenticationContext = SecurityContextHolder.getContext().getAuthentication();
        if ( authenticationContext != null && !authenticationContext.getName().equals("anonymousUser")) loginUser = authenticationContext.getName();
        BranchLogRepository logRepository = (BranchLogRepository) BeanUtil.getBean("branchLogRepository");
        logRepository.save(new BranchLog(entity, action, loginUser));
    }


}
