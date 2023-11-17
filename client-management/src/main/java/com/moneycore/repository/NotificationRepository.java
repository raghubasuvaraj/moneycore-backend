package com.moneycore.repository;

import com.moneycore.bean.RegistrationDataInfo;
import com.moneycore.entity.Address;
import com.moneycore.entity.NotoficationService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface NotificationRepository extends JpaRepository<NotoficationService, Integer> {
    @Query(value = "SELECT * FROM notification_list where status = 'Success'", nativeQuery = true)
    public List<NotoficationService> findstatus();

    @Query(value = "SELECT * FROM notification_list where client_code = :clientCode", nativeQuery = true)
    public List<NotoficationService> findclientcode(@Param("clientCode") int clientCode);
}
