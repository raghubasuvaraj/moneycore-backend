package com.moneycore.entityListener;

import com.moneycore.entity.InstitutionList;
import com.moneycore.entity.log.InstitutionListLog;
import com.moneycore.entityListener.logrepo.InstitutionListLogRepository;
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


public class InstitutionListListener {

    Logger log = Logger.getLogger(InstitutionListListener.class.getName());

    @PrePersist
    public void prePersist(InstitutionList entity) {
        // Persistence logic
        log.info("UserLog ...INSERT...@prePersist");
        perform(entity, "INSERT");
    }
    @PostPersist
    public void postPersist(InstitutionList entity) {
        // Persistence logic
        log.info("UserLog ...INSERT...@PostPersist");
//        perform(entity, "INSERT");
    }

    @PreUpdate
    public void preUpdate(InstitutionList entity) {
        // Updation logic
        log.info("UserLog ...UPDATE");
        perform(entity, "UPDATE");
    }

    @PreRemove
    public void preRemove(InstitutionList entity) {
        // Removal logic
        log.info("UserLog ...DELETE");
        perform(entity, "DELETE");
    }

    @Transactional(MANDATORY)
    void perform(InstitutionList entity, String action) {
        String loginUser = "system";
        Authentication authenticationContext = SecurityContextHolder.getContext().getAuthentication();
        if ( authenticationContext != null && !authenticationContext.getName().equals("anonymousUser")) loginUser = authenticationContext.getName();
        InstitutionListLogRepository logRepository = (InstitutionListLogRepository) BeanUtil.getBean("institutionListLogRepository");
        logRepository.save(new InstitutionListLog(entity, action, loginUser));
    }


}
