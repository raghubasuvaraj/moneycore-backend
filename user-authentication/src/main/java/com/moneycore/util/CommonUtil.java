package com.moneycore.util;

import java.nio.charset.StandardCharsets;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Collection;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moneycore.constant.Constant;
import com.moneycore.model.ApiResponse;
import com.moneycore.model.ResponseModel;

@Service
public class CommonUtil {

	private static final String SECRET_KEY = "money_core_jwt_secret_key";
	private static final String SALT = "jwt!!!!";
	private static final String CIPHER_KEY = "AES/CBC/PKCS5Padding";
	private static final byte[] IV = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

	private static String BASE_URL;

	@Value("${application.base.url}")
	public void setBaseUrl(String baseUrl) {
		BASE_URL = baseUrl;
	}

	public static ApiResponse getSuccessResponse(String message, Object result) {
		return new ApiResponse(HttpStatus.OK.value(), Constant.OK, message, result);
	}

	public static ApiResponse getFailureResponse(HttpStatus status, String message, Object result) {
		return new ApiResponse(status.value(), Constant.OK, message, result);
	}

	public static String getCurrentUserName() {
		String currentUserName = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			currentUserName = authentication.getName();
		}
		return currentUserName;
	}

	public static String encrypt(String strToEncrypt) {
		String encryptedString = "";
		try {
			IvParameterSpec ivspec = new IvParameterSpec(IV);
			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
			KeySpec spec = new PBEKeySpec(SECRET_KEY.toCharArray(), SALT.getBytes(), 65536, 256);
			SecretKey tmp = factory.generateSecret(spec);
			SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

			Cipher cipher = Cipher.getInstance(CIPHER_KEY);
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);
			encryptedString = Base64.getEncoder()
					.encodeToString(cipher.doFinal(strToEncrypt.getBytes(StandardCharsets.UTF_8)));
		} catch (Exception e) {
			encryptedString = strToEncrypt;
			System.out.println("Error while encrypting: " + e.toString());
		}
		return encryptedString;
	}

	public static String decrypt(String strToDecrypt) {
		String decryptedString = "";
		try {
			IvParameterSpec ivspec = new IvParameterSpec(IV);
			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
			KeySpec spec = new PBEKeySpec(SECRET_KEY.toCharArray(), SALT.getBytes(), 65536, 256);
			SecretKey tmp = factory.generateSecret(spec);
			SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");
			Cipher cipher = Cipher.getInstance(CIPHER_KEY);
			cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);
			decryptedString = new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
		} catch (Exception e) {
			decryptedString = strToDecrypt;
			System.out.println("Error while decrypting: " + e.toString());
		}
		return decryptedString;
	}

	public static ResponseEntity<ResponseModel> getDataFromOtherService(RestTemplate restTemplate, String url,
			String token) {
		return restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<String>(getHeaders(token)),
				ResponseModel.class);
	}

	public static ResponseEntity<ResponseModel> deleteDataFromOtherService(RestTemplate restTemplate, String url,
			String token) {
		return restTemplate.exchange(url, HttpMethod.DELETE, new HttpEntity<String>(getHeaders(token)),
				ResponseModel.class);
	}

	public static ResponseEntity<ResponseModel> putDataFromOtherService(RestTemplate restTemplate, String url,
			String token, String bodyJson) {
		return restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<String>(bodyJson, getHeaders(token)),
				ResponseModel.class);
	}

	public static ResponseEntity<ResponseModel> postDataFromOtherService(RestTemplate restTemplate, String url,
			String token, String bodyJson) {
		return restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<String>(bodyJson, getHeaders(token)),
				ResponseModel.class);
	}

	public static String getApplicationBaseUrl() {
		return BASE_URL;
	}

	public static String getBaseUrlFromRequest() {
		HttpServletRequest request = getReuestObjectFromContext();
		return ServletUriComponentsBuilder.fromRequestUri(request).replacePath(null).build().toUriString();
	}

	public static HttpServletRequest getReuestObjectFromContext() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
				.getRequest();
		return request;
	}

	public static String getJwtTokenFromRequest(HttpServletRequest request) {
		String token = null;
		String bearer = "Bearer ";
		final String autherizationHeader = request.getHeader("Authorization");
		if (autherizationHeader != null && autherizationHeader.startsWith(bearer)) {
			token = bearer + autherizationHeader.substring(7);
		}
		return token;
	}

	public static HttpHeaders getHeaders(String token) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		if (token != null) {
			headers.set("Authorization", token);
		}
		return headers;
	}

	@SuppressWarnings("unchecked")
	public static Collection<SimpleGrantedAuthority> getCurrentUserAuthority() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return (Collection<SimpleGrantedAuthority>) auth.getAuthorities();
	}

	public static <T> T convertToOriginalObject(Object object, Class<T> type) {
		T finalObject = null;
		ObjectMapper objectMapper = new ObjectMapper();
		if (object != null) {
			finalObject = objectMapper.convertValue(object, type);
		}
		return finalObject;
	}

	public static String convertObjectToJson(Object object) throws JsonProcessingException {
		if (object == null) {
			return null;
		}
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(object);
	}

	public static String masknull(String value) {

		if (value == null || value.equalsIgnoreCase("")) {

			value = "";
		}
		return value;
	}

	public static String generateNumericPIN(int len) {
		// Using numeric values
		String numbers = "0123456789";
		// Using random method
		Random randomIndex = new Random();

		char[] otp = new char[len];

		for (int i = 0; i < len; i++) {
			// Use of charAt() method : to get character value
			// Use of nextInt() as it is scanning the value as int
			otp[i] = numbers.charAt(randomIndex.nextInt(numbers.length()));
		}
		return new String(otp);
	}


}
