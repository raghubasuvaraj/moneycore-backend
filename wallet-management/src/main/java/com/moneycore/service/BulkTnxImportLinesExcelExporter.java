package com.moneycore.service;

import com.moneycore.entity.BulkTnxImportLines;
import java.io.ByteArrayInputStream;
import java.util.List;

public interface BulkTnxImportLinesExcelExporter {

    List<BulkTnxImportLines> getImportLineList(String institutionCode, String clientUploadId, String importStatus);
    ByteArrayInputStream bulkTnxImportLinesToExcelFile(List<BulkTnxImportLines> lines);

}
