package com.moneycore.serviceimpl;

import com.moneycore.entity.BulkTnxImportHeader;
import com.moneycore.entity.BulkTnxImportLines;
import com.moneycore.repository.BulkTnxImportHeaderRepository;
import com.moneycore.repository.BulkTnxImportLinesRepository;
import com.moneycore.service.BulkTnxImportService;
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

@Service
public class BulkTnxImportServiceImpl implements BulkTnxImportService {

    public static Logger logger = LoggerFactory.getLogger(BulkTnxImportServiceImpl.class);

    private final BulkTnxImportLinesRepository tnxImportLinesRepository;
    private final BulkTnxImportHeaderRepository tnxImportHeaderRepository;

    @Autowired
    public BulkTnxImportServiceImpl(BulkTnxImportLinesRepository tnxImportLinesRepository, BulkTnxImportHeaderRepository tnxImportHeaderRepository) {
        this.tnxImportLinesRepository = tnxImportLinesRepository;
        this.tnxImportHeaderRepository = tnxImportHeaderRepository;
    }


    @Override
    public Page<BulkTnxImportHeader> getImportHeaderList(int pageNo, int pageSize, String institutionCode, String clientUploadId, String importStatus){

        logger.info("@institutionCode: {}", institutionCode);
        logger.info("@clientUploadId: {}", clientUploadId);
        logger.info("@importStatus: {}", importStatus);

        // query
        String sortField = "id";
        Sort sort =  Sort.by(sortField).ascending();
        Pageable pageable = PageRequest.of(pageNo-1, pageSize, sort);

        Page<BulkTnxImportHeader> tempList =  this.tnxImportHeaderRepository.findAll((Specification<BulkTnxImportHeader>) (root, cq, cb) -> {
            Predicate p = cb.conjunction();
            p = cb.and(p, cb.equal(root.get("institutionCode"), institutionCode));
            if(clientUploadId != null){
                p = cb.and(p, cb.equal(root.get("clientUploadId"), clientUploadId));
            }
            if(importStatus != null){
                p = cb.and(p, cb.equal(root.get("importStatus"), importStatus)); // 2 = up-coming
            }
            return p;
        }, pageable);

        return tempList;

//        List<BulkTnxImportHeader> headerList = new ArrayList<>();
//        for (BulkTnxImportHeader line : tempList){
//            headerList.add(line);
//        }
//        return headerList;

    }

    @Override
    public Page<BulkTnxImportLines> getImportLineList(int pageNo, int pageSize, String institutionCode, String clientUploadId, String importStatus){

        // query
        String sortField = "id";
        Sort sort =  Sort.by(sortField).ascending();
        Pageable pageable = PageRequest.of(pageNo-1, pageSize, sort);

        Page<BulkTnxImportLines> tempList =  this.tnxImportLinesRepository.findAll((Specification<BulkTnxImportLines>) (root, cq, cb) -> {
            Predicate p = cb.conjunction();
            p = cb.and(p, cb.equal(root.get("institutionCode"), institutionCode));
            if(clientUploadId != null){
                p = cb.and(p, cb.equal(root.get("clientUploadId"), clientUploadId));
            }
            if(importStatus != null){
                p = cb.and(p, cb.equal(root.get("importStatus"), importStatus)); // 2 = up-coming
            }
            return p;
        }, pageable);

        return tempList;

//        List<BulkTnxImportLines> lineList = new ArrayList<>();
//        for (BulkTnxImportLines line : tempList){
//            lineList.add(line);
//        }
//        return lineList;

    }


}
