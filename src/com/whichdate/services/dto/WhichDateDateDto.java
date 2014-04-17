package com.whichdate.services.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WhichDateDateDto {

	private Date from;
	private Date to;
	private String fromTime;
	private String toTime;
	boolean allDay;

	private List<WhichDateAttendeeDto> attendees = new ArrayList<WhichDateAttendeeDto>();
	
	public Integer getFromHour() {
		return fromTime==null?0:Integer.valueOf(fromTime.substring(0, 2));
	}
	public Integer getToHour() {
		return toTime==null?0:Integer.valueOf(toTime.substring(0, 2));
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
	public void setAllDay(boolean allDay) {
		this.allDay = allDay;
	}
	public boolean isAllDay() {
		return allDay;
	}
	public void setFromTime(String fromTime) {
		this.fromTime = fromTime;
	}
	public String getFromTime() {
		return fromTime;
	}
	public void setToTime(String toTime) {
		this.toTime = toTime;
	}
	public String getToTime() {
		return toTime;
	}
	public List<WhichDateAttendeeDto> getAttendees() {
		return attendees;
	}
	
	public String getUID() {
		return String.format("%s%s%s%s", from, fromTime, to, toTime);
	}
	
	public void setAttendees(List<WhichDateAttendeeDto> attendees) {
		this.attendees = attendees;
	}
}
