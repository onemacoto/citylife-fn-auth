package com.citylife.function.auth.integration;

import java.io.Serializable;

public class IntegrationAuthentication implements Serializable {

  private static final long serialVersionUID = 1L;

  private AuthType authType;

  private AuthRequestVO authParameters;

  public IntegrationAuthentication(AuthType authType) {
    this.authType = authType;
  }

  public AuthType getAuthType() {
    return authType;
  }

  public void setAuthType(AuthType authType) {
    this.authType = authType;
  }

  public AuthRequestVO getAuthParameters() {
    return authParameters;
  }

  public void setAuthParameters(AuthRequestVO authParameters) {
    this.authParameters = authParameters;
  }

}
