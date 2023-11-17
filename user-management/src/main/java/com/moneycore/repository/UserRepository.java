package com.moneycore.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.moneycore.entity.User;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Integer> {
	public User findByEmail(String email);
	public User findUserByPhoneNumber(String phoneNumber);
	public User findUserByUserName(String userName);
	public List<User> findUserByEmployeNumber(String employeNumber);

	@Query(value = "select a.* from users a  where a.user_name=:userName   and a.user_id <>:userId",nativeQuery = true)
	List<User> findByUserName(@Param("userName")String userName,@Param("userId")Integer userId);

	boolean existsByUserName(String userName);

	Optional<User> findByUserName(String username);

	@Query(value = "select u.* from users u where u.user_create IS NOT NULL order by u.user_id desc",nativeQuery = true)
	List<User> findallUser();
}