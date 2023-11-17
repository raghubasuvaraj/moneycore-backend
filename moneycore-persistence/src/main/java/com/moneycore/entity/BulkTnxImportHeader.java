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
@Table(name = "bulk_tnx_import_header")
public class BulkTnxImportHeader {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String clientUploadId;
    private String institutionCode;
    private Date uploadDateTime;
    private String uploadStatus;
    private Integer importStatus; // 0=InActive,  1=Active, 2=Processed, 3=Failed, 5=In-Process
    private String errorMessage;

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
