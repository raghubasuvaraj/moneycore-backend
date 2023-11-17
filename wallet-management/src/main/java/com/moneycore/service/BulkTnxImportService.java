package com.moneycore.service;

import com.moneycore.entity.BulkTnxImportHeader;
import com.moneycore.entity.BulkTnxImportLines;
import org.springframework.data.domain.Page;

public interface BulkTnxImportService {

    Page<BulkTnxImportHeader> getImportHeaderList(int pageNo, int pageSize, String institutionCode, String clientUploadId, String importStatus);
    Page<BulkTnxImportLines> getImportLineList(int pageNo, int pageSize, String institutionCode, String clientUploadId, String importStatus);

}
