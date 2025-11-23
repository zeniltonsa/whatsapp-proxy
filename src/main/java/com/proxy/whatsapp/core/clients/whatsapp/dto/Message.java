package com.proxy.whatsapp.core.clients.whatsapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Message {

	private String id;

	@JsonProperty("message_status")
	private String messageStatus;

}
