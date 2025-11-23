package com.proxy.whatsapp.core.clients.wppconnect.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WppconnectToken {

	private String status;
	private String session;
	private String token;
	private String full;
	public WppconnectToken(String currentToken) {
		this.token = currentToken;
	}

	public static WppconnectToken bearerToken(String currentToken) {
		return new WppconnectToken(currentToken);
	}

	public String getAuthorization() {
		return "Bearer " + token;
	}
}
