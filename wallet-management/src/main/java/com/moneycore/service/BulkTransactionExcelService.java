package com.moneycore.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface BulkTransactionExcelService {
    Map<String, String> writeFileDataInUploadTable(MultipartFile file);
}
