package com.moneycore.entityListener.logrepo;

import com.moneycore.entity.log.BranchLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BranchLogRepository extends JpaRepository<BranchLog, Long> {
}
