package com.proxy.whatsapp.core.clients.evolutionapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
@JsonInclude(Include.NON_NULL)
public class ResponseConnectInstance {

	private String code;
	private String base64;
	private String pairingCode;
	private int count;
	
	private Instance instance;

}
