package com.proxy.whatsapp.core.clients.wppconnect;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties("whatsapp.wppconnect")
public class WppconnectProperties {

	private String apiUrl;
	private String session;
	private String secretkey;
	
}