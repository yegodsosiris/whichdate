package com.googleapi.json.internal.calendar;

import java.util.Date;
import java.util.List;

import com.whichdate.services.util.DateHelper;

public class GoogleCalendarDto {

	
	
	public GoogleCalendarDto(String summary, String description, String location, List<GoogleCalendarInviteeDto> attendees) {
		super();
		this.summary = summary;
		this.description = description;
		this.location = location;
		this.start = new GoogleCalendarDateDto();
		this.end = new GoogleCalendarDateDto();
		this.attendees = attendees;
	}
	
	private String summary;
	private String description;
	private String location;
	private GoogleCalendarDateDto start;
	private GoogleCalendarDateDto end;
	private List<GoogleCalendarInviteeDto> attendees;

	public void setStartDateTime(Date d) {
		start.setDateTime(DateHelper.getDateTime(d));
	}
	public void setStartDate(Date d) {
		start.setDate(DateHelper.getDate(d));
	}

	public void setEndDateTime(Date d) {
		end.setDateTime(DateHelper.getDateTime(d));
	}
	public void setEndDate(Date d) {
		end.setDate(DateHelper.getDate(d));
	}
	
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public GoogleCalendarDateDto getStart() {
		return start;
	}
	public void setStart(GoogleCalendarDateDto start) {
		this.start = start;
	}
	public GoogleCalendarDateDto getEnd() {
		return end;
	}
	public void setEnd(GoogleCalendarDateDto end) {
		this.end = end;
	}
	public List<GoogleCalendarInviteeDto> getAttendees() {
		return attendees;
	}
	public void setAttendees(List<GoogleCalendarInviteeDto> attendees) {
		this.attendees = attendees;
	}
	
	
}
