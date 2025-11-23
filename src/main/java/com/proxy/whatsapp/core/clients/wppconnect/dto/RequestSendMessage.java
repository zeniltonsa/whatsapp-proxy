package com.proxy.whatsapp.core.clients.wppconnect.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequestSendMessage {

	private String phone;
	
	@Builder.Default
	private boolean isGroup = false;
	
	@Builder.Default
	private boolean isNewsletter = false;
	
	@Builder.Default
	private boolean isLid = false;
	
	private String message;

}
