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
@Table(name = "bulk_tnx_import_lines")
public class BulkTnxImportLines {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    String clientUploadId;
    String institutionCode;
    String phoneNumber;
    Double amount;
    String currency;
    String transactionCode;
    String reason;
    Double accBalance;      // during the time of operation

    Date uploadDateTime;
    String uploadStatus;
    String errorMessage;
    Integer importStatus;   // 0=InActive,  1=Active, 2=Processed, 3=Failed, 5=In-Process

    // log fields
    private String creationUser;
    @Column(name = "creation_datetime")
    @CreationTimestamp
    private Date creationDateTime;
    private String lastUpdateUser;
    @Column(name = "last_update_datetime")
    @UpdateTimestamp
    private Date lastUpdateDateTime;

}
