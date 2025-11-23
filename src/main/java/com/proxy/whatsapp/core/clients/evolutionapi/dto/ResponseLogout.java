package com.proxy.whatsapp.core.clients.evolutionapi.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ResponseLogout {

	private String status;
	private boolean error;
	private Response response;

	@Builder
	@Data
	public static class Response {

		private String message;
	}

}
