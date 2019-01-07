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
import org.springframework.util.StreamUtils;
import org.springframework.web.filter.GenericFilterBean;

import com.alibaba.fastjson.JSON;

public class IntegrationAuthenticationFilter extends GenericFilterBean {

  private static final String OAUTH_TOKEN_URL = "/oauth/token";

  private RequestMatcher requestMatcher;

  public IntegrationAuthenticationFilter() {
    this.requestMatcher = new OrRequestMatcher(new AntPathRequestMatcher(OAUTH_TOKEN_URL, "GET"), new AntPathRequestMatcher(OAUTH_TOKEN_URL, "POST"));
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    HttpServletRequest httpRequest = (HttpServletRequest) request;
    HttpServletResponse httpResponse = (HttpServletResponse) response;
    if (requestMatcher.matches(httpRequest)) {
      AuthRequestVO params = readWithMessageConverters(httpRequest);
      IntegrationAuthentication auth = IntergrationAuthenticationBuilder.build(EnumUtils.getEnum(AuthType.class, params == null ? null : params.getData().getAuthType())).param(params).toAuth();
      IntegrationAuthenticationContext.set(auth);
      try {
        chain.doFilter(request, response);
      } finally {
        IntegrationAuthenticationContext.clear();
      }
    } else {
      chain.doFilter(httpRequest, httpResponse);
    }
  }

  protected AuthRequestVO readWithMessageConverters(HttpServletRequest httpRequest) throws IOException {
    byte[] requestBody = StreamUtils.copyToByteArray(httpRequest.getInputStream());
    return JSON.parseObject((new String(requestBody, "UTF-8")), AuthRequestVO.class);
  }

}
