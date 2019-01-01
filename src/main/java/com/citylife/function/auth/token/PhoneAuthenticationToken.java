package com.citylife.function.auth.token;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public class PhoneAuthenticationToken extends BaseAuthenticationToken {

	private static final long serialVersionUID = 1L;

	public PhoneAuthenticationToken(Object principal, Object credentials) {
		super(principal, credentials);
	}

	public PhoneAuthenticationToken(Object principal, Object credentials,
			Collection<? extends GrantedAuthority> authorities) {
		super(principal, credentials, authorities);
	}

}
