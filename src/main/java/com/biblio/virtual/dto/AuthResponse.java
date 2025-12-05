package com.biblio.virtual.dto;

public class AuthResponse {

	private String token;
	private String tipoToken = "Bearer"; // <-- Angular
	private String username;
	private String role;

	public AuthResponse(String token, String username, String role) {
		this.token = token;
		this.username = username;
		this.role = role;
	}

	// Getters y Setters
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getTipoToken() {
		return tipoToken;
	}

	public void setTipoToken(String tipoToken) {
		this.tipoToken = tipoToken;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}