package com.moneycore.entityListener.logrepo;

import com.moneycore.entity.log.ScreenLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScreenLogRepository extends JpaRepository<ScreenLog, Long> {
}
