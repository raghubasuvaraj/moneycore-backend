package com.moneycore.repository;

import com.moneycore.entity.StaticInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("staticInfoRepository")
public interface StaticInfoRepository extends JpaRepository<StaticInfo, Integer> {
    List<StaticInfo> findByInstitutionCode(String institutionCode);

    StaticInfo findByIdAndInstitutionCode(int id, String institutionCode);
}
