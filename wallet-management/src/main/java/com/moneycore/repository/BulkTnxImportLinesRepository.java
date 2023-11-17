package com.moneycore.repository;

import com.moneycore.entity.BulkTnxImportLines;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BulkTnxImportLinesRepository extends JpaRepository<BulkTnxImportLines, Long>, JpaSpecificationExecutor<BulkTnxImportLines> {

    @Query("SELECT e FROM BulkTnxImportLines e WHERE e.id = :pkId")
    BulkTnxImportLines findByPrimaryId(@Param("pkId") Long id);

}
