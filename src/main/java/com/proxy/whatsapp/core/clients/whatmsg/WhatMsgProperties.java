package com.proxy.whatsapp.core.clients.whatmsg;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties("whatsapp.whatmsg")
public class WhatMsgProperties {

	private String apiUrl;

	private String apiKey;

}