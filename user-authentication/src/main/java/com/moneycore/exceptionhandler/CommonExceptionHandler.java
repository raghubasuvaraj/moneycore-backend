package com.moneycore.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.moneycore.exception.InvalidJwtToken;
import com.moneycore.exception.UnauthorizedRequestException;
import com.moneycore.exception.UserNotFoundException;
import com.moneycore.util.CommonUtil;

@ControllerAdvice
public class CommonExceptionHandler {

	@ExceptionHandler(value = InvalidJwtToken.class)
	public ResponseEntity<?> exception(InvalidJwtToken exception) {
		return ResponseEntity.ok(CommonUtil.getFailureResponse(HttpStatus.NOT_FOUND, exception.getMessage(), null));
	}

	@ExceptionHandler(value = UserNotFoundException.class)
	public ResponseEntity<?> exception(UserNotFoundException exception) {
		return ResponseEntity.ok(CommonUtil.getFailureResponse(HttpStatus.NOT_FOUND, exception.getMessage(), null));
	}

	@ExceptionHandler(value = UnauthorizedRequestException.class)
	public ResponseEntity<?> exception(UnauthorizedRequestException exception) {
		return ResponseEntity.ok(CommonUtil.getFailureResponse(HttpStatus.UNAUTHORIZED, exception.getMessage(), null));
	}

	@ExceptionHandler(value = BadCredentialsException.class)
	public ResponseEntity<?> exception(BadCredentialsException exception) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(CommonUtil.getFailureResponse(
				HttpStatus.INTERNAL_SERVER_ERROR, "Wrong Password", null));
	}

	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<?> exception(Exception exception) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(CommonUtil.getFailureResponse(
				HttpStatus.INTERNAL_SERVER_ERROR, "Some thing went wrong. Please try again later.", null));
	}

}
