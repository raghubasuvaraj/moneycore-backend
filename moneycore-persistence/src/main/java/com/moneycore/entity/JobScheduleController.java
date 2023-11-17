package com.moneycore.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import java.util.Date;

@Setter
@Getter
@Entity
@Table(name = "job_schedule_controller")
public class JobScheduleController {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String jobSchedulerCode;
    private String jobSchedulerName;
    private Integer runningStatus;      // 0 - Stop, 1=Run
    private String executionStatus;     // 0 - free, 1 - InProgress code execution // Free // Busy

    // log fields
    @Column(name = "creation_datetime")
    @CreationTimestamp
    private Date creationDateTime;
    @Column(name = "last_update_datetime")
    @UpdateTimestamp
    private Date lastUpdateDateTime;

}
