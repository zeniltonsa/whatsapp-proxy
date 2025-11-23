package com.proxy.whatsapp.core.clients.whatmsg.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Msg {

	private Key key;

	private Message message;

	private String messageTimestamp;

	private String status;

}
