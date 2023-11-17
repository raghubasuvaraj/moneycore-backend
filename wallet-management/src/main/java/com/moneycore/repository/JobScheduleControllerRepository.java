package com.moneycore.repository;

import com.moneycore.entity.JobScheduleController;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobScheduleControllerRepository extends JpaRepository<JobScheduleController, Long> {

    JobScheduleController findByJobSchedulerCode(String code);
    JobScheduleController findByJobSchedulerName(String name);
    JobScheduleController findByJobSchedulerCodeAndJobSchedulerName(String code, String name);


}
