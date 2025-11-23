package com.proxy.whatsapp.core.clients;

public interface WhatsAppClient {

	WhatsAppResponse sendMessage(String toPhoneNumber, String message);

}
