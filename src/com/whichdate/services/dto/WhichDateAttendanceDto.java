package com.whichdate.services.dto;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.whichdate.services.util.JacksonHelper;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WhichDateAttendanceDto {
	
	private WhichDateEventDto event;
	private String invitee;
	
	public void setEvent(WhichDateEventDto event) {
		this.event = event;
	}
	public WhichDateEventDto getEvent() {
		return event;
	}
	public void setInvitee(String invitee) {
		this.invitee = invitee;
	}
	public String getInvitee() {
		return invitee;
	}
	
	@Override
	public String toString() {
		return JacksonHelper.convertToJSON(this);
	}
}
