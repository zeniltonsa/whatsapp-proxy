package com.proxy.whatsapp.core.clients.wppconnect.dto;

import java.util.List;

import com.proxy.whatsapp.core.clients.WhatsAppResponse;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class ResponseSendMessage extends WhatsAppResponse {

	private String status;
	private String session;
	private List<Response> response;

	@Builder
	@Data
	public static class Response {

		private String id;
		private String body;
		private String type;
		private String from;
		private String to;
		private Long timestamp;
		private String content;
	}

}
