package com.proxy.whatsapp.api.v1;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.proxy.whatsapp.core.clients.WhatsAppResponse;
import com.proxy.whatsapp.domain.service.MessageService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(name = "WhatsApp Proxy", description = "WhatsApp Proxy")
@AllArgsConstructor
@RestController
@RequestMapping("/v1")
public class ProxyController {

	private MessageService service;

	@Operation(summary = "Text message", description = "Send a text message")
	@ResponseStatus(code = HttpStatus.OK)
	@PostMapping("/send")
	public WhatsAppResponse testMessage(@RequestParam String clientName, @RequestParam String number,
			@RequestBody String message) {
		return service.testMessage(clientName, number, message);
	}

}
