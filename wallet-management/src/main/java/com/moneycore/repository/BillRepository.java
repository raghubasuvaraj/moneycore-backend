package com.moneycore.repository;

import com.moneycore.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BillRepository extends JpaRepository<Bill, String>{

    @Query(value = "select * from bill  where referencecode_id =:refrenceCode", nativeQuery = true)
    List<Bill> findByReferencecodeId (@Param("refrenceCode")  String refrenceCode);

}
