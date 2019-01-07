package com.citylife.function.auth.integration;

public class IntergrationAuthenticationBuilder {

  private IntegrationAuthentication auth;

  private IntergrationAuthenticationBuilder(AuthType type) {
    auth = new IntegrationAuthentication(type);
  }

  public static IntergrationAuthenticationBuilder build(AuthType type) {
    return new IntergrationAuthenticationBuilder(type == null ? AuthType.PHONE : type);
  }

  public IntergrationAuthenticationBuilder param(AuthRequestVO params) {
    auth.setAuthParameters(params);
    return this;
  }

  public IntegrationAuthentication toAuth() {
    return auth;
  }

}
