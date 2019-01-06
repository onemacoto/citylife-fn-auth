package com.citylife.function.auth.integration;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.EnumUtils;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.GenericFilterBean;

public class IntegrationAuthenticationFilter extends GenericFilterBean  {
	private static final String AUTH_TYPE_PARM_NAME = "auth_type";
	private static final String OAUTH_TOKEN_URL = "/oauth/token";
	private RequestMatcher requestMatcher;
	
	public IntegrationAuthenticationFilter(){
        this.requestMatcher = new OrRequestMatcher(
                new AntPathRequestMatcher(OAUTH_TOKEN_URL, "GET"),
                new AntPathRequestMatcher(OAUTH_TOKEN_URL, "POST")
        );
    }
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        if(requestMatcher.matches(httpRequest)) {
       		IntegrationAuthentication auth = IntergrationAuthenticationBuilder.build(EnumUtils.getEnum(AuthType.class, request.getParameter(AUTH_TYPE_PARM_NAME))).param(httpRequest.getParameterMap()).toAuth();
    		IntegrationAuthenticationContext.set(auth);
        	try {
        		chain.doFilter(request,response);
        	} finally {
        		 IntegrationAuthenticationContext.clear();
        	}
        }else{
        	chain.doFilter(httpRequest,httpResponse);
        }
	}

}
