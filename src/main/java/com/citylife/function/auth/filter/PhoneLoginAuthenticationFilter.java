package com.citylife.function.auth.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.citylife.function.auth.token.PhoneAuthenticationToken;

public class PhoneLoginAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	private static final String SPRING_SECURITY_RESTFUL_PHONE_KEY = "phone";
	private static final String SPRING_SECURITY_RESTFUL_VERIFY_CODE_KEY = "verifyCode";

	private static final String SPRING_SECURITY_RESTFUL_LOGIN_URL = "/phoneLogin";

	private boolean postOnly = true;

	public PhoneLoginAuthenticationFilter() {
		super(new AntPathRequestMatcher(SPRING_SECURITY_RESTFUL_LOGIN_URL, "POST"));
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		if (postOnly && !request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
		}

		AbstractAuthenticationToken authRequest;
		String principal;
		String credentials;

		// 手机验证码登陆
		principal = obtainParameter(request, SPRING_SECURITY_RESTFUL_PHONE_KEY);
		credentials = obtainParameter(request, SPRING_SECURITY_RESTFUL_VERIFY_CODE_KEY);

		principal = principal.trim();
		authRequest = new PhoneAuthenticationToken(principal, credentials);

		// Allow subclasses to set the "details" property
		setDetails(request, authRequest);
		return this.getAuthenticationManager().authenticate(authRequest);
	}

	private void setDetails(HttpServletRequest request, AbstractAuthenticationToken authRequest) {
		authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
	}

	private String obtainParameter(HttpServletRequest request, String parameter) {
		String result = request.getParameter(parameter);
		return result == null ? "" : result;
	}

}
