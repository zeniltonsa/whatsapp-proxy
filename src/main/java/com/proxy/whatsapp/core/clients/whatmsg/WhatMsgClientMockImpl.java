package com.proxy.whatsapp.core.clients.whatmsg;

import org.springframework.stereotype.Service;

import com.proxy.whatsapp.core.clients.WhatsAppResponse;
import com.proxy.whatsapp.core.clients.whatmsg.dto.ExtendedTextMessage;
import com.proxy.whatsapp.core.clients.whatmsg.dto.Key;
import com.proxy.whatsapp.core.clients.whatmsg.dto.Message;
import com.proxy.whatsapp.core.clients.whatmsg.dto.Msg;
import com.proxy.whatsapp.core.clients.whatmsg.dto.RequestMessage;
import com.proxy.whatsapp.core.clients.whatmsg.dto.ResponseWhatMsgAcceptedMessage;
import com.proxy.whatsapp.exceptions.WhatsAppException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("whatmsg")
public class WhatMsgClientMockImpl implements WhatMsgClient {

	private int attempt = 0;

	public WhatMsgClientMockImpl() {
		log.info("[WhatMsg]: Starting mock service");
	}

	@Override
	public WhatsAppResponse sendMessage(String toPhoneNumber, String message) {
		return this.enviarMensagens(toPhoneNumber, toPhoneNumber, message);
	}

	public ResponseWhatMsgAcceptedMessage enviarMensagens(RequestMessage request) {

		log.info("Request: {}.", attempt);

		if (attempt < 3)
			attempt++;

		if (attempt <= 2)
			throw new WhatsAppException("Error sending mocked message.", null);

		return new ResponseWhatMsgAcceptedMessage("success",
				new Msg(new Key("558282129639@s.whatsapp.net", true, "3EB016FD6EB1E6557FD076"),
						new Message(new ExtendedTextMessage(request.getBody())), "1751580780", "PENDING"));
	}

	public ResponseWhatMsgAcceptedMessage enviarMensagens(String toPhoneNumber, String fromPhoneNumber,
			String message) {

		log.info("Request: {}.", attempt);

		if (attempt < 3)
			attempt++;

		if (attempt <= 2)
			throw new WhatsAppException("Error sending mocked message.");

		return new ResponseWhatMsgAcceptedMessage("success",
				new Msg(new Key(toPhoneNumber + "@s.whatsapp.net", true, fromPhoneNumber + "3EB016FD6EB1E6557FD076"),
						new Message(new ExtendedTextMessage(message)), "1751580780", "PENDING"));
	}

}
