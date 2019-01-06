package com.citylife.function.auth.integration;

public enum AuthType {

	USERNAME("username", 1), PHONE("phone", 2), QQ("qq", 3), WEBCHAT("webchat", 4);
	private String name;
	private int index;

	private AuthType(String name, int index) {
		this.name = name;
		this.index = index;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
}
