package com.moneycore.entityListener.logrepo;

import com.moneycore.entity.log.MenuLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuLogRepository extends JpaRepository<MenuLog, Long> {
}
