package com.moneycore.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.moneycore.bean.UserData;
import com.moneycore.entity.User;

public interface UserService {

	public User findUserByEmail(String email);
	public Optional<User> findByUserID(int id);
	public void saveUser(User user);
	public UserData update(User users);
	public List<User> getAllUsers();
	public UserData insert(@Valid UserData newUser);
	public List<User> findAllUsers();
	public String randomPassword();
	public boolean findUserForLogin(String email);
	public User getUserByEmail(String email);
	public void delete(int id);
	public boolean findUserdup(String userName);
	public List<User> findUserdup(String userName,Integer userId);

	public User findUserByPhoneNumber(String phoneNumber);
	public User findUserByUserName(String userName);
	public List<User> findUserByEmployeNumber(String employeeNumber);

}