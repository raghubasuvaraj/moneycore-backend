package com.moneycore.tasksch;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.moneycore.entity.BulkTnxImportHeader;
import com.moneycore.entity.BulkTnxImportLines;
import com.moneycore.entity.JobScheduleController;
import com.moneycore.repository.BulkTnxImportHeaderRepository;
import com.moneycore.repository.BulkTnxImportLinesRepository;
import com.moneycore.repository.JobScheduleControllerRepository;
import com.moneycore.service.WalletTransaction121Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.Predicate;
import java.util.*;

@Configuration
@EnableScheduling
public class BulkTnxImportScheduler {


    private final static Logger logger = LoggerFactory.getLogger(BulkTnxImportScheduler.class);
    private Map<String, String> uniqueHdrProcessMap;

    @PersistenceContext
    private EntityManager em;

    private final BulkTnxImportHeaderRepository tnxImportHeaderRepository;
    private final BulkTnxImportLinesRepository tnxImportLinesRepository;
    private final WalletTransaction121Service walletTransaction121Service;
    private final JobScheduleControllerRepository jobScheduleControllerRepository;

    @Autowired
    public BulkTnxImportScheduler(BulkTnxImportHeaderRepository tnxImportHeaderRepository, BulkTnxImportLinesRepository tnxImportLinesRepository, WalletTransaction121Service walletTransaction121Service, JobScheduleControllerRepository jobScheduleControllerRepository) {
        this.tnxImportHeaderRepository = tnxImportHeaderRepository;
        this.tnxImportLinesRepository = tnxImportLinesRepository;
        this.walletTransaction121Service = walletTransaction121Service;
        this.jobScheduleControllerRepository = jobScheduleControllerRepository;
    }

    @Transactional(readOnly = true)
    public List<BulkTnxImportLines> getActiveImportLineList(){
        // query
        String sortField = "id";
        Sort sort =  Sort.by(sortField).ascending();
        Pageable pageable = PageRequest.of(0, 2000, sort);

        Page<BulkTnxImportLines> tempList =  this.tnxImportLinesRepository.findAll((Specification<BulkTnxImportLines>) (root, cq, cb) -> {

            Predicate p = cb.conjunction();
            p = cb.and(p, cb.equal(root.get("importStatus"), 1)); // 0=InActive,  1=Active, 2=Processed, 3=Failed, 5=In-Process
            return p;

        }, pageable);

        List<BulkTnxImportLines> lineList = new ArrayList<>();
        for (BulkTnxImportLines line : tempList){
            lineList.add(line);
        }
        return lineList;

    }

    public void updateHeaderStatus(String institutionCode, String clientUploadId){

        String filterQuery = "";
        if (institutionCode != null) {
            filterQuery = filterQuery + " AND w.institutionCode = '" + institutionCode + "'";
        }
        if (institutionCode != null) {
            filterQuery = filterQuery + " AND w.clientUploadId = '" + clientUploadId + "'";
        }
        Query countQuery = em.createQuery("select count(w) FROM BulkTnxImportLines w WHERE (1=1) " + filterQuery);
        long countAllResult = (Long)countQuery.getSingleResult();

        filterQuery = filterQuery + " AND w.importStatus = '2'";
        countQuery = em.createQuery("select count(w) FROM BulkTnxImportLines w WHERE (1=1) " + filterQuery);
        long countAllStatusResult = (Long)countQuery.getSingleResult();

        if(countAllResult == countAllStatusResult){
            BulkTnxImportHeader tnxImportHeader = tnxImportHeaderRepository.findByInstitutionCodeAndClientUploadId(institutionCode, clientUploadId);
            if(tnxImportHeader != null){
                tnxImportHeader.setImportStatus(2);
                tnxImportHeader.setErrorMessage("");
                tnxImportHeader.setUploadStatus("Completed");
                tnxImportHeaderRepository.saveAndFlush(tnxImportHeader);
            }
        }

    }

    public void setHeaderStatusInProgress(String institutionCode, String clientUploadId){
        BulkTnxImportHeader tnxImportHeader = tnxImportHeaderRepository.findByInstitutionCodeAndClientUploadId(institutionCode, clientUploadId);
        if(tnxImportHeader != null){
            tnxImportHeader.setImportStatus(5);
            tnxImportHeaderRepository.saveAndFlush(tnxImportHeader);
        }
    }

    public void updateLineStatus(Long importLineId, Integer statusCode){
        BulkTnxImportLines tnxImportLines =  tnxImportLinesRepository.findByPrimaryId(importLineId);
        if (tnxImportLines != null){
            tnxImportLines.setUploadStatus("Success");
            tnxImportLines.setImportStatus(statusCode);
            if(statusCode == 2){
                tnxImportLines.setErrorMessage("");
            }
            tnxImportLinesRepository.saveAndFlush(tnxImportLines);
        }
    }

    // Transactional Annotation
    public void processImportData() throws JsonProcessingException {
        uniqueHdrProcessMap = new HashMap<>();
        List<Map<String, String>> procHdrKeysList = new ArrayList<>();

        List<BulkTnxImportLines> lineList = getActiveImportLineList();
        for (BulkTnxImportLines line : lineList){

            String tnxStatus = walletTransaction121Service.doTransaction(line.getId());

            if(tnxStatus != null && tnxStatus.equals("success")){
                updateLineStatus(line.getId(), 2);
                // unique key
                String uniqueKey = line.getInstitutionCode() + "__KEY__" + line.getClientUploadId();
                if( !uniqueHdrProcessMap.containsKey(uniqueKey) ){
                    uniqueHdrProcessMap.put(uniqueKey, uniqueKey);
                    setHeaderStatusInProgress(line.getInstitutionCode(), line.getClientUploadId());
                    Map<String, String> beanX = new HashMap<>();
                    beanX.put("institutionCode", line.getInstitutionCode());
                    beanX.put("clientUploadId", line.getClientUploadId());
                    procHdrKeysList.add(beanX);
                }
            } else if(tnxStatus == null){
                updateLineStatus(line.getId(), 3);
            }

        }

        // check and update header status
        for (Map<String, String> beanY : procHdrKeysList){
            updateHeaderStatus( beanY.get("institutionCode"), beanY.get("clientUploadId") );
        }

    }


    public JobScheduleController getJobSchControllerInst(){
        return jobScheduleControllerRepository.findByJobSchedulerCodeAndJobSchedulerName("100", "BulkImportSch");
    }

    @Scheduled(fixedDelay = 45000)
    public void scheduleTaskWithFixDelay() throws JsonProcessingException {

        System.out.println("chk@ Calling Task Scheduler... each 2min" + new Date());
        JobScheduleController jobSchInst = getJobSchControllerInst();

        if(jobSchInst != null){

            Integer runningStatus = jobSchInst.getRunningStatus();
            if( runningStatus != null && runningStatus == 1){

                String executionStatus = jobSchInst.getExecutionStatus();
                if( executionStatus != null && executionStatus.equals("Free") ){

                    logger.warn("chk@ Calling Task Scheduler... each 2min ----> Status: Free");
                    // make Busy
                    jobSchInst.setExecutionStatus("Busy");
                    jobScheduleControllerRepository.saveAndFlush(jobSchInst);
                    // run process
                    processImportData();
                    // execution done
                    // make Free
                    JobScheduleController jobSchInst2 = getJobSchControllerInst();
                    jobSchInst2.setExecutionStatus("Free");
                    jobScheduleControllerRepository.saveAndFlush(jobSchInst2);

                } else {
                    logger.warn("chk@ Calling Task Scheduler... each 2min ----> Status: Busy");
                }

            } else {
                logger.warn("chk@ Calling Task Scheduler... each 2min ----> Status: Job Not Running");
            }


        } else {
            logger.warn("chk@ Calling Task Scheduler... each 2min ----> Status: Job Controller not define");

        }

    }



}
