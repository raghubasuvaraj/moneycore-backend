package com.moneycore.repository;

import com.moneycore.entity.ServiceList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("serviceListRepository")
public interface ServiceListRepository extends JpaRepository<ServiceList, Integer> {

	@Query(value = "SELECT * FROM service_list WHERE institution_code = :institutionCode AND service_code = :serviceCode  order by date_create desc", nativeQuery = true)
	Optional<ServiceList> find(@Param("institutionCode") String institutionCode,
							   @Param("serviceCode") int serviceCode);

	@Query(value = "SELECT * FROM service_list WHERE service_code = :serviceCode ", nativeQuery = true)
	Optional<ServiceList> find(@Param("serviceCode") int serviceCode);

	@Query(value = "SELECT * FROM service_list WHERE institution_code = :institutionCode AND service_name = :serviceName  order by date_create desc", nativeQuery = true)
	Optional<ServiceList> findService(@Param("institutionCode") String institutionCode,
							   @Param("serviceName") String serviceName);

	@Query(value = "SELECT * FROM service_list WHERE service_name= :serviceName  order by date_create desc", nativeQuery = true)
	Optional<ServiceList> findService(@Param("serviceName") String serviceName);

	@Query(value = "SELECT * FROM service_list WHERE institution_code = :institutionCode  order by date_create desc", nativeQuery = true)
	List<ServiceList> findList(@Param("institutionCode") String institutionCode);
	boolean existsByServiceNameAndInstitutionCode(String Name,String institutionCode);

	@Query(value = "SELECT * FROM service_list WHERE institution_code = :institutionCode AND service_name=:serviceName AND service_code <> :serviceCode   order by date_create desc", nativeQuery = true)
	List<ServiceList> findByServiceName(@Param("institutionCode") String institutionCode,@Param("serviceName") String serviceName,
							   @Param("serviceCode") int serviceCode);

}
