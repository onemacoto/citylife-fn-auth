package com.citylife.function.auth.provider;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import com.citylife.function.auth.integration.AuthType;
import com.citylife.function.auth.integration.IntegrationAuthenticationContext;

public class UserNameDaoAuthenticationProvider extends DaoAuthenticationProvider {

	@Override
	public boolean supports(Class<?> authentication) {
		return (UsernamePasswordAuthenticationToken.class
				.isAssignableFrom(authentication)) && IntegrationAuthenticationContext.get().getAuthType().equals(AuthType.USERNAME);
	}
	
}
