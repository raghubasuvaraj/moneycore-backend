package com.moneycore.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.moneycore.bean.ClientStatusInfo;
import com.moneycore.component.Translator;
import com.moneycore.constant.Constant;
import com.moneycore.entity.Client;
import com.moneycore.entity.Roles;
import com.moneycore.entity.User;
import com.moneycore.model.CustomUser;
import com.moneycore.model.ResponseModel;
import com.moneycore.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		CustomUser customUser = null;
		if (isClientRequest()) {
			Client client = getClientByEmail(username);
			if (client != null) {
				if (client.getStatusCode() != null && (client.getStatusCode().getStatusCode().equals("I")
						|| client.getStatusCode().getStatusCode().equals("A")
						|| client.getStatusCode().getStatusCode().equals("M"))) {
					System.out.println("Client data found");
					customUser = new CustomUser(username, client.getPassword(), new ArrayList<>());
				}else if(client.getStatusCode().getStatusCode().equals("S") && client.getStatusToDate() != null)
				{
					try {
						Date date=new SimpleDateFormat("yyyy-MM-dd").parse(client.getStatusToDate());
						if(date.after(new Date())){
							activateClientAndWallet(client.getClientCode());
							customUser = new CustomUser(username, client.getPassword(), new ArrayList<>());
						}else {
							throw new UsernameNotFoundException("Client is "+client.getStatusCode().getStatusName());
						}
					} catch (ParseException | JsonProcessingException e) {
						e.printStackTrace();
					}

				}
				else if (client.getStatusCode() != null &&
						(client.getStatusCode().getStatusCode().equals("S")
								|| client.getStatusCode().getStatusCode().equals("C")
								|| client.getStatusCode().getStatusCode().equals("B"))) {
					throw new UsernameNotFoundException("Client is "+client.getStatusCode().getStatusName());
				}
			}
		} else {
			User user = getUserByEmail(username);
			if (user != null && (user.getStatus().equals("A") ||user.getStatus().equals("I") || user.getStatus().equals("M"))) {
				System.out.println("User data found");
				customUser = new CustomUser(username, user.getPassword(), getGrantAuthority(getUserRoles(user)));
			} else if (user.getStatus() != null &&
					(user.getStatus().equals("S")
							|| user.getStatus().equals("C")
							|| user.getStatus().equals("B"))) {
				throw new UsernameNotFoundException("User is "+user.getStatus());
			}
		}

		if (customUser != null) {
			return new org.springframework.security.core.userdetails.User(customUser.getUserName(),
					customUser.getPassword(), new ArrayList<>());
		} else {
			throw new UsernameNotFoundException(Translator.toLocale("username.not.found", new Object[]{username}));
		}
	}

	private boolean isClientRequest() {
		boolean isFromClient = false;
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
				.getRequest();
		String uri = request.getRequestURI();
		Boolean isClientRequest = (Boolean) request.getAttribute(Constant.IS_CLIENT_REQUEST);
		if ("/client/authenticate".equals(uri) || (isClientRequest != null && isClientRequest)) {
			isFromClient = true;
		}
		request.setAttribute(Constant.IS_CLIENT_REQUEST, isFromClient);
		return isFromClient;
	}

	private Collection<SimpleGrantedAuthority> getGrantAuthority(List<Roles> roles) {
		Collection<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<SimpleGrantedAuthority>();
		if (roles != null && !roles.isEmpty()) {
			for (Roles role : roles) {
				grantedAuthorities.add(new SimpleGrantedAuthority(role.getRoleName()));
			}
		}
		return grantedAuthorities;
	}

	private Client getClientByEmail(String email) {
		ResponseModel responseModel = null;
		Client client = null;
		String url = CommonUtil.getApplicationBaseUrl() + "/api/clientmanagement/internal/client/" + email;
		System.out.println("Client Url := " + url);
		ResponseEntity<ResponseModel> responseEntity = CommonUtil.getDataFromOtherService(restTemplate, url, null);
		if (responseEntity.getStatusCode().equals(HttpStatus.ACCEPTED)) {
			responseModel = responseEntity.getBody();
			client = CommonUtil.convertToOriginalObject(responseModel.getResult(), Client.class);
		}
		return client;
	}

	private User getUserByEmail(String email) {
		ResponseModel responseModel = null;
		User user = null;
		String url = CommonUtil.getApplicationBaseUrl() + "/api/usermanagement/internal/user/" + email;
		System.out.println("User Url := " + url);
		ResponseEntity<ResponseModel> responseEntity = CommonUtil.getDataFromOtherService(restTemplate, url, null);
		if (responseEntity.getStatusCode().equals(HttpStatus.ACCEPTED)) {
			responseModel = responseEntity.getBody();
			user = CommonUtil.convertToOriginalObject(responseModel.getResult(), User.class);
		}
		return user;
	}

	private List<Roles> getUserRoles(User user) {
		ResponseModel responseModel = null;
		List<Roles> roles = null;
		String url = CommonUtil.getApplicationBaseUrl() + "/api/rolemanagement/internal/role/" + user.getProfileFk().getProfileId();
		System.out.println("ROle Url := "+url);
		ResponseEntity<ResponseModel> responseEntity = CommonUtil.getDataFromOtherService(restTemplate, url, null);
		if (responseEntity.getStatusCode().equals(HttpStatus.ACCEPTED)) {
			responseModel = responseEntity.getBody();
			Roles[] rolesArray = CommonUtil.convertToOriginalObject(responseModel.getResult(), Roles[].class);
			if (rolesArray != null) {
				roles = Arrays.asList(rolesArray);
			}
		}
		return roles;
	}

	public void activateClientAndWallet(int clientCode) throws JsonProcessingException {
		String url = CommonUtil.getApplicationBaseUrl() + "/api/walletmanagement/internal/wallet/unblock/" + clientCode;

		ClientStatusInfo clientStatusInfo = new ClientStatusInfo();
		clientStatusInfo.setStatusCode("A");
		clientStatusInfo.setStatusCode("Auto Activated");

		String bodyJson = CommonUtil.convertObjectToJson(clientCode);

		ResponseEntity<ResponseModel> responseEntity = CommonUtil.putDataFromOtherService(restTemplate, url,
				CommonUtil.getJwtTokenFromRequest(CommonUtil.getReuestObjectFromContext()), bodyJson);
		if (responseEntity.getStatusCode().equals(HttpStatus.ACCEPTED)) {
			ResponseModel responseModel = responseEntity.getBody();
			CommonUtil.convertToOriginalObject(responseModel.getResult(), null);
		}
	}
}
