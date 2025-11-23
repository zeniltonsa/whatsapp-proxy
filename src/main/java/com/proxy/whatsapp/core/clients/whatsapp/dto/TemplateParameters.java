package com.proxy.whatsapp.core.clients.whatsapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TemplateParameters {

	@JsonProperty("parameter_name")
	private String parameterName;

	private String type;

	private String text;

}