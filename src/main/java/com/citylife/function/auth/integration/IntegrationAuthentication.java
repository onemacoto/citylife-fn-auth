package com.citylife.function.auth.integration;

import java.io.Serializable;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;

public class IntegrationAuthentication implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private AuthType authType;
    
	private Map<String, String[]> authParameters;
	

	public IntegrationAuthentication(AuthType authType) {
	   this.authType = authType;
	}
	
	public AuthType getAuthType() {
		return authType;
	}

	public void setAuthType(AuthType authType) {
		this.authType = authType;
	}

	public Map<String, ?> getAuthParameters() {
		return authParameters;
	}

	public void setAuthParameters(Map<String, String[]> authParameters) {
		this.authParameters = authParameters;
	}

	public String getParameter(String key) {
        String[] values = this.authParameters.get(key);
        if(!ArrayUtils.isEmpty(values)) {
            return values[0];
        }
        return null;
	} 

}
