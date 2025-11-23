package com.proxy.whatsapp.core.clients.whatsapp.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TemplateCobrancaAmigavel {

	private String name;
	private TemplateLanguage language;
	private List<TemplateComponents> components;

}