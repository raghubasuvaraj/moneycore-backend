package com.moneycore.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.moneycore.entity.CustomerSegmentList;

@Repository("customerSegmentListRepository")
public interface CustomerSegmentListRepository extends JpaRepository<CustomerSegmentList, String> {

	@Query(value = "SELECT * FROM customer_segment_list where institution_code = :institutionCode AND customer_segment_code = :customerSegmentCode  order by date_create desc", nativeQuery = true)
	Optional<CustomerSegmentList> find(@Param("institutionCode") String institutionCode,
			@Param("customerSegmentCode") String customerSegmentCode);

	@Query(value = "SELECT * FROM customer_segment_list where institution_code = :institutionCode  order by date_create desc", nativeQuery = true)
	List<CustomerSegmentList> fetchList(@Param("institutionCode") String institutionCode);

	@Query(value = "SELECT * FROM customer_segment_list where customer_segment_code = :customerSegmentCode  order by date_create desc", nativeQuery = true)
	Optional<CustomerSegmentList> find(@Param("customerSegmentCode") String customerSegmentCode);
	boolean existsByCustomerSegmentNameAndInstitutionCode(String customerSegmentName,String institutionCode);

	@Query(value = "SELECT * FROM customer_segment_list where institution_code = :institutionCode AND customer_segment_name=:customerSegmentName AND customer_segment_code <> :customerSegmentCode  order by date_create desc", nativeQuery = true)
	List<CustomerSegmentList> FindByCustSegName(@Param("institutionCode") String institutionCode,@Param("customerSegmentName") String customerSegmentName
									   ,@Param("customerSegmentCode") String customerSegmentCode);
}
