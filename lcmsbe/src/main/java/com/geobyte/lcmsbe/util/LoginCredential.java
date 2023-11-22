package com.geobyte.lcmsbe.util;

public class LoginCredential {
	private String email;
	private String password;
	
	public LoginCredential() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LoginCredential(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "LoginCredential [email=" + email + ", password=" + password + "]";
	}
}
