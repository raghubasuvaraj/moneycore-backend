package com.moneycore.aspect;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.moneycore.entity.GrantPermission;
import com.moneycore.exception.UnauthorizedRequestException;
import com.moneycore.model.RequestInfo;
import com.moneycore.model.RequestType;
import com.moneycore.model.ResponseModel;
import com.moneycore.util.CommonUtil;

@Aspect
@Component
public class AuthorizeUserAspect {

	@Autowired
	private RestTemplate restTemplate;

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizeUserAspect.class);

	@Around("@annotation(com.moneycore.annotation.AuthorizeUser)")
	public Object authorize(ProceedingJoinPoint joinPoint) throws Throwable {
		boolean isAuthorized = false;
		RequestInfo requestInfo = getRequestInfo();
		GrantPermission grantPermission = getGrantPermission(requestInfo.getUri());
		if (grantPermission != null) {
			isAuthorized = hasProperRights(requestInfo.getType(), grantPermission);
		}
		if (!isAuthorized) {
			throw new UnauthorizedRequestException("You are not uthorized to perform this operation.");
		}
		return joinPoint.proceed();
	}

	private boolean hasProperRights(String methodType, GrantPermission grantPermission) {
		boolean hasPermission = false;
		String value = RequestType.getEnumValue(methodType);
		if (grantPermission.getType().equals(value)) {
			hasPermission = true;
		}
		return hasPermission;
	}

	private RequestInfo getRequestInfo() {
		RequestInfo requestInfo = new RequestInfo();
		HttpServletRequest request = CommonUtil.getReuestObjectFromContext();
		requestInfo.setUrl(request.getRequestURL().toString());
		requestInfo.setUri(request.getRequestURI());
		requestInfo.setQueryString(request.getQueryString());
		requestInfo.setType(request.getMethod());
		return requestInfo;
	}

	private GrantPermission getGrantPermission(String uri) {
		GrantPermission grantPermission = null;
		String screenUrl = CommonUtil.getApplicationBaseUrl() + "/api/rolemanagement/grantpermission/screen/" + uri.replaceAll("/", "_");
		String menuUrl = CommonUtil.getApplicationBaseUrl() + "/api/rolemanagement/grantpermission/menu/" + uri.replaceAll("/", "_");
		grantPermission = getGrantPermissionByUrl(screenUrl);
		if (grantPermission == null) {
			grantPermission = getGrantPermissionByUrl(menuUrl);
		}
		return grantPermission;
	}

	private GrantPermission getGrantPermissionByUrl(String apiUrl) {
		GrantPermission grantPermission = null;
		ResponseModel responseModel = null;
		ResponseEntity<ResponseModel> responseEntity = CommonUtil.getDataFromOtherService(restTemplate, apiUrl,
				CommonUtil.getJwtTokenFromRequest(CommonUtil.getReuestObjectFromContext()));
		if (responseEntity.getStatusCode().equals(HttpStatus.ACCEPTED)) {
			responseModel = responseEntity.getBody();
			grantPermission = CommonUtil.convertToOriginalObject(responseModel.getResult(), GrantPermission.class);
		}
		return grantPermission;
	}
}
