package com.proxy.whatsapp.core.clients.whatsapp;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties("whatsapp.cloud-api")
public class WhatsAppCloudApiProperties {

	private String apiUrl;

	private String token;

	private String phoneNumberId;

}