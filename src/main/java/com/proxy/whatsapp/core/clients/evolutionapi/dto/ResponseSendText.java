package com.proxy.whatsapp.core.clients.evolutionapi.dto;

import com.proxy.whatsapp.core.clients.WhatsAppResponse;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class ResponseSendText extends WhatsAppResponse {

	private Key key;
	private String pushName;
	private String status;
	private Message message;
	private String messageType;
	private Long messageTimestamp;
	private String instanceId;

	@Builder
	@Data
	public static class Key {

		private String remoteJid;
		private boolean fromMe;
		private String id;
	}

	@Builder
	@Data
	public static class Message {

		private String conversation;
	}

}
