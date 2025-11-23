package com.proxy.whatsapp.core.clients.whatmsg.dto;

import com.proxy.whatsapp.core.clients.WhatsAppResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ResponseWhatMsgAcceptedMessage extends WhatsAppResponse {

	private String status;

	private Msg msg;

}
