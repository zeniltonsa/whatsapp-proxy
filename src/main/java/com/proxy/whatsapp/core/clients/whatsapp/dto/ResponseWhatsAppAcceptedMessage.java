package com.proxy.whatsapp.core.clients.whatsapp.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.proxy.whatsapp.core.clients.WhatsAppResponse;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ResponseWhatsAppAcceptedMessage extends WhatsAppResponse {

	@JsonProperty("messaging_product")
	private String messagingProduct;

	private List<Contact> contacts;

	private List<Message> messages;

}
