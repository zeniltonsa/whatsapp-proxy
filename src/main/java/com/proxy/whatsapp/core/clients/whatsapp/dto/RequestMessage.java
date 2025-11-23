package com.proxy.whatsapp.core.clients.whatsapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class RequestMessage {

	@JsonProperty("messaging_product")
	private String messagingProduct;
	
	private String to;
	
	private String type;
	
	private TemplateCobrancaAmigavel template;

}