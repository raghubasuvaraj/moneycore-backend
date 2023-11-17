package com.moneycore.controller;

import com.moneycore.component.Translator;
import com.moneycore.model.AuthenticationRequest;
import com.moneycore.model.AuthenticationResponse;
import com.moneycore.model.ResponseModel;
import com.moneycore.service.CustomUserDetailService;
import com.moneycore.util.CommonUtil;
import com.moneycore.util.JwtUtil;
import io.jsonwebtoken.impl.DefaultClaims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

@RestController
public class JwtAuthenticationController {
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationController.class);
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private CustomUserDetailService userDetailService;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private MessageSource messageSource;

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest,
			HttpServletRequest request) throws Exception {
		final UserDetails userDetails = userDetailService.loadUserByUsername(authenticationRequest.getUserName());
		try{
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				authenticationRequest.getUserName(), authenticationRequest.getPassword()));
		} catch (BadCredentialsException e) {
			throw new BadCredentialsException(e.getMessage());
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

		String jwt = jwtUtil.generateToken(userDetails, request);
		jwt = CommonUtil.encrypt(jwt);
		ResponseModel responseModel = getUserMenusDetail(jwt);
		return ResponseEntity
				.ok(new AuthenticationResponse(jwt, (responseModel != null ? responseModel.getResult() : null)));
	}

	@RequestMapping(value = "/client/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createClientAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest,
			HttpServletRequest request) throws Exception {
		final UserDetails userDetails = userDetailService.loadUserByUsername(authenticationRequest.getUserName());

		try{
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getUserName(), authenticationRequest.getPassword()));
		} catch (BadCredentialsException e) {
			throw new BadCredentialsException(e.getMessage());
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

		String jwt = jwtUtil.generateToken(userDetails, request);
		jwt = CommonUtil.encrypt(jwt);
		ResponseModel responseModel = getClientWalletId(jwt);
		responseModel.setMessage(Translator.toLocale("login.success", null));
		return ResponseEntity.ok(responseModel);
	}

	@RequestMapping(value = "/refreshtoken", method = RequestMethod.GET)
	public ResponseEntity<?> refreshtoken(HttpServletRequest request) throws Exception {
		// From the HttpRequest get the claims
		String token = "";
		DefaultClaims claims = (DefaultClaims) request.getAttribute("claims");
		if (claims != null) {
			Map<String, Object> expectedMap = getMapFromIoJsonwebtokenClaims(claims);
			token = jwtUtil.generateRefreshToken(expectedMap, expectedMap.get("sub").toString());
		} else {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			String existingToken = getJwtToken(request);
			token = jwtUtil.getRefreshToken(existingToken, userDetails);
		}
		token = CommonUtil.encrypt(token);
		logger.info("token......");
		return ResponseEntity.ok(new AuthenticationResponse(token, null));
	}

	private String getJwtToken(HttpServletRequest request) {
		String autherizationHeader = request.getHeader("Authorization");
		String jwt = autherizationHeader.substring(7);
		return CommonUtil.decrypt(jwt);
	}

	private Map<String, Object> getMapFromIoJsonwebtokenClaims(DefaultClaims claims) {
		Map<String, Object> expectedMap = new HashMap<String, Object>();
		for (Entry<String, Object> entry : claims.entrySet()) {
			expectedMap.put(entry.getKey(), entry.getValue());
		}
		return expectedMap;
	}

	private ResponseModel getClientWalletId(String jwtToken) {
		ResponseModel responseModel = null;
		String token = "Bearer " + jwtToken;
		String url = CommonUtil.getApplicationBaseUrl() + "/api/walletmanagement/client/walletid";
		ResponseEntity<ResponseModel> responseEntity = CommonUtil.getDataFromOtherService(restTemplate, url, token);
		if (responseEntity.getStatusCode().equals(HttpStatus.ACCEPTED)) {
			responseModel = responseEntity.getBody();
		}
		return responseModel;
	}

	private ResponseModel getUserMenusDetail(String jwtToken) {
		ResponseModel responseModel = null;
		String token = "Bearer " + jwtToken;
		String url = CommonUtil.getApplicationBaseUrl() + "/api/usermanagement/user/menus/detail";
		System.out.println("Menu url := "+ url);
		ResponseEntity<ResponseModel> responseEntity = CommonUtil.getDataFromOtherService(restTemplate, url, token);
		if (responseEntity.getStatusCode().equals(HttpStatus.ACCEPTED)) {
			responseModel = responseEntity.getBody();
		}
		return responseModel;
	}
}
