package com.proxy.whatsapp.core.clients.whatsapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Contact {

	private String input;

	@JsonProperty("wa_id")
	private String waId;

}
