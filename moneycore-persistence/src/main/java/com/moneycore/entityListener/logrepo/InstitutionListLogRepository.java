package com.moneycore.entityListener.logrepo;

import com.moneycore.entity.log.InstitutionListLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstitutionListLogRepository extends JpaRepository<InstitutionListLog, Long> {
}
