package com.googleapi.json.internal.calendar;

import java.util.Date;

public class CalendarEventDto {
	
	private Date from;
	private Date to;
	private Long eventId;
	
	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}
	
	public Long getEventId() {
		return eventId;
	}
	
	public Date getFrom() {
		return from;
	}
	public void setFrom(Date from) {
		this.from = from;
	}
	public Date getTo() {
		return to;
	}
	public void setTo(Date to) {
		this.to = to;
	}
	
}
