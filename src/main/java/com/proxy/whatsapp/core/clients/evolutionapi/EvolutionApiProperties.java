package com.proxy.whatsapp.core.clients.evolutionapi;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties("whatsapp.evolution-api")
public class EvolutionApiProperties {

	private String apiUrl;
	private String instance;
	private String apiKey;

}