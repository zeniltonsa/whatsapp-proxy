package com.proxy.whatsapp.core.clients.wppconnect;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import com.proxy.whatsapp.core.clients.wppconnect.dto.RequestCreateSession;
import com.proxy.whatsapp.core.clients.wppconnect.dto.RequestSendMessage;
import com.proxy.whatsapp.core.clients.wppconnect.dto.ResponseSendMessage;
import com.proxy.whatsapp.core.clients.wppconnect.dto.ResponseStarSession;
import com.proxy.whatsapp.core.clients.wppconnect.dto.ResponseStatusSession;
import com.proxy.whatsapp.core.clients.wppconnect.dto.WppconnectToken;

@HttpExchange(contentType = MediaType.APPLICATION_JSON_VALUE, accept = MediaType.APPLICATION_JSON_VALUE)
public interface WppconnectService {

	@PostExchange(url = "/{session}/{secretkey}/generate-token")
	WppconnectToken generateToken(@PathVariable String session, @PathVariable String secretkey);

	@PostExchange(url = "/{session}/start-session")
	ResponseStarSession startSession(@PathVariable String session, @RequestBody RequestCreateSession request);

	@PostExchange(url = "/{session}/send-message")
	ResponseSendMessage sendMessage(@PathVariable String session, @RequestBody RequestSendMessage request);

	@GetExchange(url = "/{session}/status-session")
	ResponseStatusSession statusSession(@PathVariable String session);

	@PostExchange(url = "/{session}/logout-session")
	ResponseStatusSession logoutSession(@PathVariable String session);

	@PostExchange(url = "/{session}/close-session")
	ResponseStatusSession closeSession(@PathVariable String session);

}
