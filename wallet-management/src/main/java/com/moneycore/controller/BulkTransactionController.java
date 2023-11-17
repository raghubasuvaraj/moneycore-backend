package com.moneycore.controller;

import com.moneycore.entity.BulkTnxImportHeader;
import com.moneycore.entity.BulkTnxImportLines;
import com.moneycore.model.PaginatedResponse;
import com.moneycore.model.ResponseModel;
import com.moneycore.service.BulkTnxImportLinesExcelExporter;
import com.moneycore.service.BulkTnxImportService;
import com.moneycore.service.BulkTransactionExcelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.ByteArrayInputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bulk-transaction")
public class BulkTransactionController {

    public final static Logger logger = LoggerFactory.getLogger(BulkTransactionController.class);

    private final BulkTransactionExcelService bulkTransactionExcelService;
    private final BulkTnxImportLinesExcelExporter tnxImportLinesExcelExporter;
    private final BulkTnxImportService tnxImportService;

    public BulkTransactionController(BulkTransactionExcelService bulkTransactionExcelService, BulkTnxImportLinesExcelExporter tnxImportLinesExcelExporter, BulkTnxImportService tnxImportService){
        this.bulkTransactionExcelService = bulkTransactionExcelService;
        this.tnxImportLinesExcelExporter = tnxImportLinesExcelExporter;
        this.tnxImportService = tnxImportService;
    }

    @RequestMapping(value = {"/upload-excel"}, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> uploadExcelFile(@RequestParam("file") MultipartFile file){

        try {
            Map<String, String> returnObj = bulkTransactionExcelService.writeFileDataInUploadTable(file);
            Map<String, Object> responseJSON = new LinkedHashMap<>();
            responseJSON.put("institutionCode", returnObj.get("institutionCode"));
            responseJSON.put("clientUploadId", returnObj.get("clientUploadId"));
            responseJSON.put("progressTraceLink1", "/api/header-progress-list");
            responseJSON.put("progressTraceLink2", "/api/lines-progress-list");
            responseJSON.put("excelDownloadLink", "/api/bulk-transaction/download-excel/"+returnObj.get("institutionCode")+"/"+returnObj.get("clientUploadId"));
            ResponseModel responseModel = new ResponseModel(true, "Successfully process upload file", responseJSON);
            return new ResponseEntity<>(responseModel, HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            logger.error("ERROR:", e);
            ResponseModel responseModel = new ResponseModel(false, "Fail to process upload file \n\n" + e.getMessage() , null);
            return ResponseEntity.status(HttpStatus.OK).body(responseModel);
        }

    }

    @GetMapping("/download-excel/{institutionCode}/{clientUploadId}")
    public ResponseEntity<?> downloadExcelFile(@PathVariable("institutionCode") String institutionCode, @PathVariable("clientUploadId") String clientUploadId, @RequestParam(name = "importStatus", required = false) String importStatus){

        List<BulkTnxImportLines> linesList = tnxImportLinesExcelExporter.getImportLineList(institutionCode, clientUploadId, importStatus);
        ByteArrayInputStream stream = tnxImportLinesExcelExporter.bulkTnxImportLinesToExcelFile(linesList);
        InputStreamResource file = new InputStreamResource(stream);

        String downloadFilename = "Bulk_Transaction_Template.xlsx";
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+downloadFilename)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(file);

    }

    // track header progress
    @GetMapping("/header-progress-list")
    public ResponseEntity<?> bulkTnxHeaderStatusList(@RequestParam(name = "institutionCode") String institutionCode,
                                                     @RequestParam(name = "clientUploadId", required = false) String clientUploadId,
                                                     @RequestParam(name = "importStatus", required = false) String importStatus,
                                                     @RequestParam(defaultValue = "1") int pageNo,
                                                     @RequestParam(defaultValue = "10") int pageSize){
        Page<BulkTnxImportHeader> pages = tnxImportService.getImportHeaderList(pageNo, pageSize, institutionCode, clientUploadId, importStatus);
        PaginatedResponse response = new PaginatedResponse(true, 200, "Bulk Transaction Header Status List", pages.getContent(), pages.getNumber()+1, pages.getSize(), pages.getTotalPages(), pages.getTotalElements());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // track line progress
    @GetMapping("/lines-progress-list")
    public ResponseEntity<?> bulkTnxLineStatusList(@RequestParam(name = "institutionCode") String institutionCode,
                                                   @RequestParam(name = "clientUploadId", required = false) String clientUploadId,
                                                   @RequestParam(name = "importStatus", required = false) String importStatus,
                                                   @RequestParam(defaultValue = "1") int pageNo,
                                                   @RequestParam(defaultValue = "10") int pageSize){
        Page<BulkTnxImportLines> pages = tnxImportService.getImportLineList(pageNo, pageSize, institutionCode, clientUploadId, importStatus);
        PaginatedResponse response = new PaginatedResponse(true, 200, "Bulk Transaction Line Status List", pages.getContent(), pages.getNumber()+1, pages.getSize(), pages.getTotalPages(), pages.getTotalElements());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


}
