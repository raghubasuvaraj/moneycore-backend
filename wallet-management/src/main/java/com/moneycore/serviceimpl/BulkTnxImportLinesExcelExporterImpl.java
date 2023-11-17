package com.moneycore.serviceimpl;

import com.moneycore.entity.BulkTnxImportLines;
import com.moneycore.repository.BulkTnxImportLinesRepository;
import com.moneycore.service.BulkTnxImportLinesExcelExporter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import javax.persistence.criteria.Predicate;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class BulkTnxImportLinesExcelExporterImpl implements BulkTnxImportLinesExcelExporter {

    public static Logger logger = LoggerFactory.getLogger(BulkTnxImportLinesExcelExporterImpl.class);
    static String SHEET = "Upload Data";


    private final BulkTnxImportLinesRepository tnxImportLinesRepository;

    @Autowired
    public BulkTnxImportLinesExcelExporterImpl(BulkTnxImportLinesRepository tnxImportLinesRepository) {
        this.tnxImportLinesRepository = tnxImportLinesRepository;
    }


    @Override
    public List<BulkTnxImportLines> getImportLineList(String institutionCode, String clientUploadId, String importStatus){

        logger.info("@institutionCode: {}", institutionCode);
        logger.info("@clientUploadId: {}", clientUploadId);
        logger.info("@importStatus: {}", importStatus);

        // query
        String sortField = "id";
        Sort sort =  Sort.by(sortField).ascending();
        Pageable pageable = PageRequest.of(0, 2000, sort);

        Page<BulkTnxImportLines> tempList =  this.tnxImportLinesRepository.findAll((Specification<BulkTnxImportLines>) (root, cq, cb) -> {
            Predicate p = cb.conjunction();
            p = cb.and(p, cb.equal(root.get("institutionCode"), institutionCode));
            p = cb.and(p, cb.equal(root.get("clientUploadId"), clientUploadId));
            if(importStatus != null){
                p = cb.and(p, cb.equal(root.get("importStatus"), importStatus)); // 2 = up-coming
            }
            return p;
        }, pageable);

        List<BulkTnxImportLines> lineList = new ArrayList<>();
        for (BulkTnxImportLines line : tempList){
            lineList.add(line);
        }
        return lineList;

    }

    public void createNoteSheet(Workbook workbook){
        Sheet sheetNote = workbook.createSheet("Notes");
        Row row = sheetNote.createRow(0);
        Cell cell = row.createCell(0);
        cell.setCellValue("ImportStatus");
        cell = row.createCell(1);
        cell.setCellValue("// 0=InActive,  1=Active, 2=Processed, 3=Failed, 5=In-Process");
    }

    @Override
    public ByteArrayInputStream bulkTnxImportLinesToExcelFile(List<BulkTnxImportLines> lines) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");

        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet(SHEET);
            createNoteSheet(workbook);

            Row row = sheet.createRow(0);
            // Row style
            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            Font font = workbook.createFont();
            font.setBold(true);
            headerCellStyle.setFont(font);

            // Creating header
            Cell cell = row.createCell(0);
            cell.setCellValue("Unique ID");
            cell.setCellStyle(headerCellStyle);

            cell = row.createCell(1);
            cell.setCellValue("Institution Code");
            cell.setCellStyle(headerCellStyle);

            cell = row.createCell(2);
            cell.setCellValue("Phone Number");
            cell.setCellStyle(headerCellStyle);

            cell = row.createCell(3);
            cell.setCellValue("Amount");
            cell.setCellStyle(headerCellStyle);

            cell = row.createCell(4);
            cell.setCellValue("Currency");
            cell.setCellStyle(headerCellStyle);

            cell = row.createCell(5);
            cell.setCellValue("Transaction Code");
            cell.setCellStyle(headerCellStyle);

            cell = row.createCell(6);
            cell.setCellValue("Reason/Remarks");
            cell.setCellStyle(headerCellStyle);

            cell = row.createCell(7);
            cell.setCellValue("Balance");
            cell.setCellStyle(headerCellStyle);

            cell = row.createCell(8);
            cell.setCellValue("Upload Time");
            cell.setCellStyle(headerCellStyle);

            cell = row.createCell(9);
            cell.setCellValue("Upload Status");
            cell.setCellStyle(headerCellStyle);

            cell = row.createCell(10);
            cell.setCellValue("Import Status");
            cell.setCellStyle(headerCellStyle);

            cell = row.createCell(11);
            cell.setCellValue("Import ID");
            cell.setCellStyle(headerCellStyle);

            cell = row.createCell(12);
            cell.setCellValue("Import Errors");
            cell.setCellStyle(headerCellStyle);

            // Creating data rows for each line
            for (int i = 0; i < lines.size(); i++) {
                Row dataRow = sheet.createRow(i + 1);
                BulkTnxImportLines line = lines.get(i);

                dataRow.createCell(0).setCellValue( line.getClientUploadId() );
                dataRow.createCell(1).setCellValue( line.getInstitutionCode() );
                dataRow.createCell(2).setCellValue( line.getPhoneNumber() );
                dataRow.createCell(3).setCellValue( line.getAmount() );
                dataRow.createCell(4).setCellValue( line.getCurrency() );
                dataRow.createCell(5).setCellValue( line.getTransactionCode() );
                dataRow.createCell(6).setCellValue( line.getReason() );
                dataRow.createCell(7).setCellValue( (line.getAccBalance() == null) ? 0.00 : line.getAccBalance() );
                String uploadDateTime = (lines.get(i).getUploadDateTime() == null) ? "" : sdf.format(lines.get(i).getUploadDateTime());
                dataRow.createCell(8).setCellValue( uploadDateTime );
                dataRow.createCell(9).setCellValue( line.getUploadStatus() );
                dataRow.createCell(10).setCellValue( (line.getImportStatus() == null) ? "" : line.getImportStatus().toString() );
                dataRow.createCell(11).setCellValue( line.getId() );
                dataRow.createCell(12).setCellValue( line.getErrorMessage() );
            }

            // Making size of column auto resize to fit with data
            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(2);
            sheet.autoSizeColumn(3);
            sheet.autoSizeColumn(4);
            sheet.autoSizeColumn(5);
            sheet.autoSizeColumn(6);
            sheet.autoSizeColumn(7);
            sheet.autoSizeColumn(8);
            sheet.autoSizeColumn(9);
            sheet.autoSizeColumn(10);
            sheet.autoSizeColumn(11);
            sheet.autoSizeColumn(12);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return new ByteArrayInputStream(outputStream.toByteArray());

        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error(ex.getMessage());
            return null;
        }

    }



}