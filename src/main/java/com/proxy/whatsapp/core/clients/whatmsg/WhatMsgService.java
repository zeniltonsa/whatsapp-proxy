package com.proxy.whatsapp.core.clients.whatmsg;

import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import com.proxy.whatsapp.core.clients.whatmsg.dto.ResponseWhatMsgAcceptedMessage;

@HttpExchange
public interface WhatMsgService {

	@PostExchange(
			url = "/msg", 
			accept = MediaType.APPLICATION_JSON_VALUE, 
			contentType = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	ResponseWhatMsgAcceptedMessage sendMessage(@RequestBody MultiValueMap<String, String> formData);

}
