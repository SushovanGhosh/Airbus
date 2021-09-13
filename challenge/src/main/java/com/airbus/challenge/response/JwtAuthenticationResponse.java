package com.airbus.challenge.response;


public class JwtAuthenticationResponse {

	private String accessToken;
	private String tokenType = "Bearer";
	private String username;
	private Long userId;
	private int tokenExpiration;
	
	public JwtAuthenticationResponse(String accessToken, String username, Long userId, int tokenExpiration) {
		this.accessToken = accessToken;
		this.username = username;
		this.userId = userId;
		this.tokenExpiration = tokenExpiration;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public int getTokenExpiration() {
		return tokenExpiration;
	}

	public void setTokenExpiration(int tokenExpiration) {
		this.tokenExpiration = tokenExpiration;
	}
		
}
