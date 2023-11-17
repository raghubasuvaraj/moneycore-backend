package com.moneycore.entityListener.logrepo;

import com.moneycore.entity.log.ProfileLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileLogRepository extends JpaRepository<ProfileLog, Long> {
}
