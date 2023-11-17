package com.moneycore.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.moneycore.entity.FileDB;


@Repository
public interface FileDBRepository extends JpaRepository<FileDB, String> {

	@Query(value = "SELECT * FROM files WHERE client_id = :clientCode AND id_type = :idType", nativeQuery = true)
	Optional<FileDB> findDocument(@Param("clientCode") int clientCode, @Param("idType") String idType);

	@Query(value = "SELECT * FROM files WHERE client_id = :clientCode", nativeQuery = true)
	List<FileDB> findByClientCode(@Param("clientCode") int clientCode);

	@Query(value = "DELETE FROM files WHERE client_id = :clientCode AND id_type = :idType", nativeQuery = true)
	public void delete(int clientCode, String idType);

	boolean existsByIdNumber(String idNumber);

}
