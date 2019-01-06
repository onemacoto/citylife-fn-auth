package com.citylife.function.auth.integration;

import java.util.HashMap;
import java.util.Map;

public class IntergrationAuthenticationBuilder {
	
	private IntegrationAuthentication auth;
	
	private IntergrationAuthenticationBuilder (AuthType type) {
		auth = new IntegrationAuthentication(type);
		auth.setAuthParameters(new HashMap<>(10));
	}

	public static IntergrationAuthenticationBuilder build(AuthType type) {
		return new IntergrationAuthenticationBuilder(type == null ? AuthType.USERNAME : type);
	}
	
	public IntergrationAuthenticationBuilder param(Map<String, String[]> params) {
		auth.setAuthParameters(params);
		return this;
	}
	
	public IntegrationAuthentication toAuth() {
		return auth;
	}
	

}
