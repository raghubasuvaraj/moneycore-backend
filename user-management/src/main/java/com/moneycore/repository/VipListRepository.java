package com.moneycore.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.moneycore.entity.VipList;

@Repository
public interface VipListRepository extends JpaRepository<VipList, String> {

	@Query(value = "SELECT * FROM vip_list where institution_code = :institutionCode AND vip_code = :vipCode   order by date_create desc", nativeQuery = true)
	Optional<VipList> find(@Param("institutionCode") String institutionCode, @Param("vipCode") String vipCode);

	@Query(value = "SELECT * FROM vip_list where institution_code = :institutionCode   order by date_create desc", nativeQuery = true)
	List<VipList> fetchList(@Param("institutionCode") String institutionCode);
	boolean existsByVipNameAndInstitutionCode(String vipName,String institutionCode);

	@Query(value = "SELECT * FROM vip_list where institution_code = :institutionCode AND vip_name =:vipName AND vip_code <> :vipCode  order by date_create desc", nativeQuery = true)
	List<VipList> findByVipName(@Param("institutionCode") String institutionCode,@Param("vipName") String vipName, @Param("vipCode") String vipCode);

}
