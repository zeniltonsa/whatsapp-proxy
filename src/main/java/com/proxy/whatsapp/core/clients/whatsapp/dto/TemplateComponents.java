package com.proxy.whatsapp.core.clients.whatsapp.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TemplateComponents {

	private String type;
	
	private List<TemplateParameters> parameters;

}