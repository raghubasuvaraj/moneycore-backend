package com.moneycore.serviceimpl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.moneycore.bean.UserData;
import com.moneycore.entity.Profile;
import com.moneycore.entity.User;
import com.moneycore.repository.UserRepository;
import com.moneycore.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	RestTemplate restTemplate;

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public Optional<User> findByUserID(int id) {
		return userRepository.findById(id);
	}

	@Override
	public void saveUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userRepository.save(user);
	}

	public boolean findUserdup(String userName){

		return  userRepository.existsByUserName(userName);

	}
	public List<User> findUserdup(String userName,Integer userId){

		return  userRepository.findByUserName(userName,userId);

	}


	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public List<User> findAllUsers() {

		return userRepository.findallUser();
	}

	@Override
	public UserData update(User users) {
		User user = userRepository.save(users);
		return UserData.getUser(user);
	}

	public String randomPassword() {
		String ALPHA_NUMERIC_STRING = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%&?{}*";
		StringBuilder builder = new StringBuilder();
		int count = 8;
		while (count-- != 0) {
			int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
			builder.append(ALPHA_NUMERIC_STRING.charAt(character));
		}
		return builder.toString();
	}

	@Override
	public UserData insert(UserData users) {
		User addUser = new User();
		addUser.setUserName(users.getUserName());
		addUser.setStatus("A");
		addUser.setJobTitle(users.getJobTitle());
		addUser.setLanguage(users.getLanguage());
		addUser.setEmail(users.getEmail());
		addUser.setEmployeNumber(users.getEmployeNumber());
		addUser.setPhoneNumberCode(users.getPhoneNumberCode());
		addUser.setPhoneNumber(users.getPhoneNumber());
		addUser.setAccountStartDate(new Timestamp(new Date().getTime()));
		addUser.setActivEmail("1");
		addUser.setInstitutionFk(users.getInstitutionFk());
		addUser.setBankCodeAccessList(users.getBankCodeAccessList());
		Profile profile = new Profile();
		profile.setProfileId(users.getProfileFk());
		addUser.setProfileFk(profile);
		addUser.setCountryFk(users.getCountryFk());
		addUser.setDateCreate(new Timestamp(new Date().getTime()));
		addUser.setUserCreate(users.getUserCreate());
		addUser.setPassword(bCryptPasswordEncoder.encode(users.getPassword()));
		addUser.setAccountStartDate(users.getAccountStartDate());
		addUser.setAccountEndDate(users.getAccountEndDate());
		User e = userRepository.save(addUser);
		return UserData.getUser(e);
	}

	@Override
	public boolean findUserForLogin(String email) {
		List list = em.createNativeQuery(
				"select u.email , r.role_name as role from assigned_roles2permissions ap , users u , roles r where u.profile_fk = ap.profile_fk and r.roleid = ap.assigned_role_fk and u.email=:email")
				.setParameter("email", email).getResultList();
		return list.size() > 0 ? true : false;
	}

	@Override
	public User getUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public void delete(int id) {
		userRepository.deleteById(id);
	}

	@Override
	public User findUserByPhoneNumber(String phoneNumber) {
		return userRepository.findUserByPhoneNumber(phoneNumber);
	}

	@Override
	public User findUserByUserName(String userName) {
		return userRepository.findUserByUserName(userName);
	}

	@Override
	public List<User> findUserByEmployeNumber(String employeeNumber) {
		return userRepository.findUserByEmployeNumber(employeeNumber);
	}

}