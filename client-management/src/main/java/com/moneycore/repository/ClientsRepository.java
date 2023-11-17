package com.moneycore.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.moneycore.bean.RegistrationDataInfo;
import com.moneycore.bean.DashboardInfo;

import com.moneycore.entity.Client;

@Repository("clientsRepository")
public interface ClientsRepository extends JpaRepository<Client, Integer> {

	public Client findByEmail(String email);

	@Query(value = "SELECT * FROM client WHERE (pr_phone_1 =:phoneNumber OR pr_phone_2 =:phoneNumber OR pr_phone_3 =:phoneNumber OR pr_phone_4 =:phoneNumber) and is_deleted=false", nativeQuery = true)
	public Optional<Client> findByPhoneNumber(@Param("phoneNumber") String phoneNumber);

	@Query(value = "SELECT * FROM client WHERE institution_code = :instituteCode and mcc_code in (:mccCode) and is_deleted=false", nativeQuery = true)
	public List<Client> getMerchantByMccCode(@Param("instituteCode")String instituteCode, @Param("mccCode")List mccCode);

	@Query(value = "SELECT * FROM dashboard_info_views", nativeQuery = true)
	public Optional<DashboardInfo> findtDashboardInfo();


	@Query(value = "SELECT * FROM registration_info_views where year(SYSDATE()) = year", nativeQuery = true)
	public List<RegistrationDataInfo> findRegDataInfo();

	@Query(value = "SELECT * FROM registration_info_views  where  year(SYSDATE()) != year", nativeQuery = true)
	public List<RegistrationDataInfo> findRegDataInfoPre();

	@Query(value="delete from client where client_code = :clientCode", nativeQuery = true)
	public void hardDelete(@Param("clientCode")int clientCode);
}
