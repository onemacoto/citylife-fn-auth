package com.citylife.function.auth.provider;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import com.citylife.function.auth.integration.AuthType;
import com.citylife.function.auth.integration.IntegrationAuthenticationContext;

public class PhoneAuthenticationProvider extends DaoAuthenticationProvider {

  @Override
  protected void additionalAuthenticationChecks(UserDetails userDetails, 
      UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

  }

  @Override
  public boolean supports(Class<?> authentication) {
    return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication)) && IntegrationAuthenticationContext.get().getAuthType().equals(AuthType.PHONE);
  }
}
