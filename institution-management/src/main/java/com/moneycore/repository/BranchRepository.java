package com.moneycore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.moneycore.entity.Branch;

import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Repository("BranchRepository")
public interface BranchRepository extends JpaRepository<Branch, String> {
    boolean existsByBranchName(String Name);

    @Query(value = "SELECT * FROM branch_list WHERE branch_name = :branchName and institution_code =:institutionCode and branch_code <>:branchCode ", nativeQuery = true)
    List<Branch> findByName(@Param("branchName")String branchName,@Param("institutionCode")String institutionCode,@Param("branchCode")String branchCode);


    @Query(value = "select u.* from branch_list u where u.user_create != '0' order by u.branch_code DESC", nativeQuery = true)
    List<Branch> findAllBranch();

}
