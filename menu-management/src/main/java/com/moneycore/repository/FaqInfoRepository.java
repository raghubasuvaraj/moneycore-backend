package com.moneycore.repository;

import com.moneycore.entity.FaqInfo;
import com.moneycore.entity.StaticInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("faqInfoRepository")
public interface FaqInfoRepository extends JpaRepository<FaqInfo, Integer> {
    List<FaqInfo> findByInstitutionCode(String institutionCode);

    List<FaqInfo> findByIdAndInstitutionCode(int id, String institutionCode);
}
