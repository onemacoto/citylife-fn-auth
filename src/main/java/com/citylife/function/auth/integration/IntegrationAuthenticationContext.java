package com.citylife.function.auth.integration;

public class IntegrationAuthenticationContext {
	private static ThreadLocal<IntegrationAuthentication> holder = new InheritableThreadLocal<>();

	public static void set(IntegrationAuthentication integrationAuthentication) {
		holder.set(integrationAuthentication);
	}

	public static IntegrationAuthentication get() {
		return holder.get();
	}

	public static void clear() {
		holder.remove();
	}

}
