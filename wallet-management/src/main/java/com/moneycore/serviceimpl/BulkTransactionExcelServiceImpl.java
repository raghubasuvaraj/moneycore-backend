package com.moneycore.serviceimpl;

import com.moneycore.entity.BulkTnxImportHeader;
import com.moneycore.entity.BulkTnxImportLines;
import com.moneycore.repository.BulkTnxImportHeaderRepository;
import com.moneycore.repository.BulkTnxImportLinesRepository;
import com.moneycore.service.BulkTransactionExcelService;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.*;

@Service
public class BulkTransactionExcelServiceImpl implements BulkTransactionExcelService {


    private final BulkTnxImportLinesRepository tnxImportLinesRepository;
    private final BulkTnxImportHeaderRepository tnxImportHeaderRepository;

    private static final Map<String, Integer> excelColumnMapConfig = new HashMap<String, Integer>(){{
        put("clientUploadId", 0);
        put("institutionCode", 1);
        put("phoneNumber", 2);
        put("amount", 3);
        put("currency", 4);
        put("transactionCode", 5);
        put("reason", 6);
        put("balance", 7);
        put("uploadDataTime", 8);
        put("uploadStatus", 9);
        put("importStatus", 10);
        put("importId", 11);
        put("errorMessage", 12);
    }};

    @Autowired
    public BulkTransactionExcelServiceImpl(BulkTnxImportLinesRepository tnxImportLinesRepository, BulkTnxImportHeaderRepository tnxImportHeaderRepository) {
        this.tnxImportLinesRepository = tnxImportLinesRepository;
        this.tnxImportHeaderRepository = tnxImportHeaderRepository;
    }

    private boolean checkUniqueClientUploadId(ArrayList<BulkTnxImportLines> bulkTnxLines){

        String clientUploadId = "";
        int count = 0;
        for (BulkTnxImportLines tnxImportLines : bulkTnxLines){
            count++;
            String clientUploadId_ = tnxImportLines.getClientUploadId();
            if (count > 1){
                if(!clientUploadId_.equals(clientUploadId)){
                    return false;
                }
            }
            clientUploadId = clientUploadId_;
        }
        return true;

    }

    private String getTransactionCode(XSSFRow row){
        CellType cellType = row.getCell( excelColumnMapConfig.get("transactionCode") ).getCellType();
        switch(cellType) {
            case NUMERIC:
                double transactionCode_ =  row.getCell( excelColumnMapConfig.get("transactionCode") ).getNumericCellValue();
                return String.valueOf((int)transactionCode_);
            case STRING:
                return row.getCell( excelColumnMapConfig.get("transactionCode") ).getStringCellValue();
            default:
                throw new RuntimeException("Unexpected cell type (" + cellType + ")");
        }
    }

    private Long getImportId(XSSFRow row){
        int lastCellNum = row.getLastCellNum();
        if( (excelColumnMapConfig.get("importId")+1) > lastCellNum ) return null;
        CellType cellType = row.getCell( excelColumnMapConfig.get("importId") ).getCellType();
        switch(cellType) {
            case NUMERIC:
                double importId =  row.getCell( excelColumnMapConfig.get("importId") ).getNumericCellValue();
                return (long)importId;
            case STRING:
                String _importId = row.getCell( excelColumnMapConfig.get("importId") ).getStringCellValue();
                if(_importId == null) return null;
                if(_importId.trim().equals("")) return null;
                return Long.parseLong(_importId);
            default:
                throw new RuntimeException("Unexpected cell type (" + cellType + ")");
        }
    }

    private String getStringCellValue(XSSFRow row, int cellIndex){
        int lastCellNum = row.getLastCellNum();
        if(lastCellNum != (cellIndex+1) ) return null;
        CellType cellType = row.getCell( cellIndex ).getCellType();
        switch(cellType) {
            case NUMERIC:
                double cellValueD =  row.getCell( cellIndex ).getNumericCellValue();
                return String.valueOf((int)cellValueD);
            case STRING:
                String cellValueS = row.getCell( cellIndex ).getStringCellValue();
                if(cellValueS == null) return null;
                return cellValueS.trim();
            default:
                throw new RuntimeException("Unexpected cell type (" + cellType + ")");
        }
    }
    private Integer getIntegerCellValue(XSSFRow row, int cellIndex){
        String cellValueS = getStringCellValue(row, cellIndex);
        if(cellValueS == null) return null;
        return Integer.parseInt(cellValueS);
    }


    private BulkTnxImportLines excelToLineMapper(XSSFRow row){

        String clientUploadId = row.getCell( excelColumnMapConfig.get("clientUploadId") ).getStringCellValue();
        String institutionCode = row.getCell( excelColumnMapConfig.get("institutionCode") ).getStringCellValue();
        String phoneNumber = row.getCell( excelColumnMapConfig.get("phoneNumber") ).getStringCellValue();
        Double amount = row.getCell( excelColumnMapConfig.get("amount") ).getNumericCellValue();
        String currency = row.getCell( excelColumnMapConfig.get("currency") ).getStringCellValue();
        Integer importStatus = getIntegerCellValue(row, excelColumnMapConfig.get("importStatus"));
        if(importStatus == null) importStatus = 1;

        String transactionCode = getTransactionCode(row);
        Long importId = getImportId(row);

        BulkTnxImportLines line;
        if(importId != null){
            Optional<BulkTnxImportLines> optionalLine = tnxImportLinesRepository.findById(importId);
            if(optionalLine.isPresent()){
                line = optionalLine.get();
                if(line.getImportStatus() == 2) return null; // no update action // 0=InActive,  1=Active, 2=Processed, 3=Failed, 5=In-Process
            } else {
                line = new BulkTnxImportLines();
                line.setUploadDateTime(new Date());
            }
        } else {
            line = new BulkTnxImportLines();
            line.setUploadDateTime(new Date());
        }

        line.setClientUploadId(clientUploadId);
        line.setInstitutionCode(institutionCode);
        line.setPhoneNumber(phoneNumber);
        line.setAmount(amount);
        line.setCurrency(currency);
        line.setTransactionCode(transactionCode);
        line.setUploadStatus("Success");
        line.setImportStatus(importStatus);
        return line;

    }


    public BulkTnxImportHeader writeDataInHeaderTable(BulkTnxImportLines importLines){

        String clientUploadId = importLines.getClientUploadId();
        String institutionCode = importLines.getInstitutionCode();

        BulkTnxImportHeader tnxImportHeader = tnxImportHeaderRepository.findByInstitutionCodeAndClientUploadId(institutionCode, clientUploadId);
        if(tnxImportHeader == null){
            tnxImportHeader = new BulkTnxImportHeader();
        }
        tnxImportHeader.setClientUploadId(clientUploadId);
        tnxImportHeader.setInstitutionCode(institutionCode);
        tnxImportHeader.setUploadDateTime(new Date());
        tnxImportHeader.setUploadStatus("success");
        tnxImportHeader.setImportStatus(1);
        return tnxImportHeaderRepository.save(tnxImportHeader);
    }


    @Override
    public Map<String, String> writeFileDataInUploadTable(MultipartFile file) {

        Map<String, String> returnObj = new HashMap<>();
        ArrayList<BulkTnxImportLines> bulkTnxLines = new ArrayList<>();

        try {

            XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
            XSSFSheet worksheet = workbook.getSheetAt(0);

            for ( int index = 0; index < worksheet.getPhysicalNumberOfRows(); index++ ) {
                if (index > 0) {
                    System.out.println("index: " + index);
                    XSSFRow row = worksheet.getRow(index);
                    BulkTnxImportLines line = excelToLineMapper(row);
                    if (line == null) continue;                         // 0=InActive,  1=Active, 2=Processed, 3=Failed, 5=In-Process
                    bulkTnxLines.add(line);
                }
            }
            // validation
            boolean uniqueValue = checkUniqueClientUploadId(bulkTnxLines);
            if(!uniqueValue){
                throw new RuntimeException("Duplicate client upload ID, please ensure unique client upload ID");
            }
            // save in lines table
            this.tnxImportLinesRepository.saveAll(bulkTnxLines);
            BulkTnxImportHeader importHeader = this.writeDataInHeaderTable(bulkTnxLines.get(0));
            returnObj.put("clientUploadId", importHeader.getClientUploadId());
            returnObj.put("institutionCode", importHeader.getInstitutionCode());

        } catch (IOException e) {
            throw new RuntimeException("Fail to process excel data: " + e.getMessage());
        }

        return returnObj;

    }


}
