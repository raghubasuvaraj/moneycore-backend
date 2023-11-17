package com.moneycore.repository;

import com.moneycore.entity.BulkTnxImportHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BulkTnxImportHeaderRepository extends JpaRepository<BulkTnxImportHeader, Long>, JpaSpecificationExecutor<BulkTnxImportHeader> {

    BulkTnxImportHeader findByInstitutionCodeAndClientUploadId(String institutionCode, String clientUploadId);

}
