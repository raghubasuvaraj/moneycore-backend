package com.moneycore.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.moneycore.component.Translator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.moneycore.entity.Component;
import com.moneycore.entity.Screen;
import com.moneycore.entity.User;
import com.moneycore.model.ResponseModel;
import com.moneycore.service.ComponentService;
import com.moneycore.service.ScreenService;
import com.moneycore.util.CommonUtil;

@RestController
@RequestMapping("/api/menumanagement")
public class ComponentManagementController {

	@Autowired
	private ComponentService componentService;

	@Autowired
	private ScreenService screenService;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@RequestMapping(value = {
			"/component/register" }, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> registerComponent(@Valid @RequestBody Component newcomponent) {
		List<Component> component = componentService.findByComponentCode(newcomponent.getComponentCode());
		if (!component.isEmpty()) {
			ResponseModel responseModel = new ResponseModel(false,
					Translator.toLocale("component.alreadyreg", null), null);
			return ResponseEntity.accepted().body(responseModel);
		}
		String sc = newcomponent.getScreenCode().getScreenCode();
		List<Screen> screen = screenService.findByScreenBYCode(sc);
		if (screen.isEmpty()) {
			ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("component.syscode", null), null);
			return ResponseEntity.status(200).body(responseModel);
		} else {
			newcomponent.setScreenCode(newcomponent.getScreenCode());
		}
		Component componentList = componentService.insert(newcomponent);
		ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("component.code", null), componentList);
		return ResponseEntity.accepted().body(responseModel);
	}

	@RequestMapping(value = { "/component" }, method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> componentList(@Valid @RequestBody User user) {
		User userRecord = getUserByEmail(user.getEmail());
		if (userRecord != null) {
			boolean isPasswordMatch = bCryptPasswordEncoder.matches(user.getPassword(), userRecord.getPassword());
			if (isPasswordMatch) {
				List<Component> comp = componentService.findIt(user.getEmail());
				ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("component.code", null), comp);
				return ResponseEntity.accepted().body(responseModel);
			}
		}
		ResponseModel responseModel = new ResponseModel(false, Translator.toLocale("component.wrong", null), null);
		return ResponseEntity.accepted().body(responseModel);
	}

	@RequestMapping(value = { "/components" }, method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> componentList(HttpServletRequest request) {
		List<Component> comp = componentService.findAll();
		ResponseModel responseModel = new ResponseModel(true, Translator.toLocale("component.code", null), comp);
		return ResponseEntity.accepted().body(responseModel);
	}

	private User getUserByEmail(String email) {
		ResponseModel responseModel = null;
		User user = null;
		String url = CommonUtil.getApplicationBaseUrl() + "/api/usermanagement/internal/user/" + email;
		ResponseEntity<ResponseModel> responseEntity = CommonUtil.getDataFromOtherService(restTemplate, url, null);
		if (responseEntity.getStatusCode().equals(HttpStatus.ACCEPTED)) {
			responseModel = responseEntity.getBody();
			user = CommonUtil.convertToOriginalObject(responseModel.getResult(), User.class);
		}
		return user;
	}

}
