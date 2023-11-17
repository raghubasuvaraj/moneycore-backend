package com.moneycore.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.moneycore.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Integer> {

//	@Query(value = "SELECT * FROM address where client_code = :clientCode", nativeQuery = true)
//	public List<Address> findByClientCode(@Param("clientCode") int clientCode);

	@Query(value = "SELECT * FROM address where id= :addressId", nativeQuery = true)
	public Optional<Address> findAddress(@Param("addressId") int addressId);

//	@Query(value = "SELECT * FROM address where client_code = :clientCode LIMIT 1", nativeQuery = true)
//	public Optional<Address> findAddress(@Param("clientCode") int clientId);

}
