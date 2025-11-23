package com.proxy.whatsapp.core.clients.evolutionapi.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Instance {
	private String instanceName;
	private String state;

}