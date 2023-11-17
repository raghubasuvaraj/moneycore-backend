package com.moneycore.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moneycore.model.ApiResponse;
import com.moneycore.service.CustomUserDetailService;
import com.moneycore.util.CommonUtil;
import com.moneycore.util.JwtUtil;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
	
	@Autowired
	private CustomUserDetailService userDetailService;

	@Autowired
	private JwtUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		ApiResponse apiResponse = null;
		boolean sendResponse = false;
		try {
			final String autherizationHeader = request.getHeader("Authorization");
			String userName = null;
			String jwt = null;
			if (autherizationHeader != null && autherizationHeader.startsWith("Bearer ")) {
				jwt = autherizationHeader.substring(7);
				jwt = CommonUtil.decrypt(jwt);
				userName = jwtUtil.extractUsername(jwt);
				jwtUtil.handleClientRequestIdentifier(request, jwt);
			}

			if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				UserDetails userDetails = this.userDetailService.loadUserByUsername(userName);
				if (jwtUtil.validateToken(jwt, userDetails)) {
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());
					usernamePasswordAuthenticationToken
							.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				}
			}
		} catch (Exception e) {
			if (e instanceof MalformedJwtException || e instanceof SignatureException) {
				apiResponse = CommonUtil.getFailureResponse(HttpStatus.UNAUTHORIZED, "Invalid Token.", null);
				response.setStatus(HttpStatus.UNAUTHORIZED.value());
				sendResponse = true;
			} else if (e instanceof ExpiredJwtException) {
				String requestURL = request.getRequestURL().toString();
				// allow for Refresh Token creation if following conditions are true.
				if (requestURL.contains("refreshtoken")) {
					allowForRefreshToken((ExpiredJwtException) e, request);
					sendResponse = false;
				} else {
					apiResponse = CommonUtil.getFailureResponse(HttpStatus.UNAUTHORIZED, "Token Expired.", null);
					response.setStatus(HttpStatus.UNAUTHORIZED.value());
					sendResponse = true;
				}
			} else {
				apiResponse = CommonUtil.getFailureResponse(HttpStatus.INTERNAL_SERVER_ERROR,
						"Some thing went wrong. Please try again later.", null);
				response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
				sendResponse = true;
			}
		}
		if (sendResponse) {
			response.getWriter().write(convertObjectToJson(apiResponse));
		} else {
			filterChain.doFilter(request, response);
		}
	}

	private void allowForRefreshToken(ExpiredJwtException ex, HttpServletRequest request) {

		// create a UsernamePasswordAuthenticationToken with null values.
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				null, null, null);
		// After setting the Authentication in the context, we specify
		// that the current user is authenticated. So it passes the
		// Spring Security Configurations successfully.
		SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
		// Set the claims so that in controller we will be using it to create
		// new JWT
		request.setAttribute("claims", ex.getClaims());

	}

	private String convertObjectToJson(Object object) throws JsonProcessingException {
		if (object == null) {
			return null;
		}
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(object);
	}

}
