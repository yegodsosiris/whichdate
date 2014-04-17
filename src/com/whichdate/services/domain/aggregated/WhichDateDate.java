package com.whichdate.services.domain.aggregated;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WhichDateDate {

	private Date from;
	private Date to;
	private String fromTime;
	private String toTime;
	boolean allDay;
	
	private List<WhichDateAttendee> attendees = new ArrayList<WhichDateAttendee>();

	public Integer getFromHour() {
		return fromTime==null?0:Integer.valueOf(fromTime.substring(0, 2));
	}
	public Integer getToHour() {
		return toTime==null?0:Integer.valueOf(toTime.substring(0, 2));
	}

	public Integer getFromMinutes() {
		return fromTime==null?0:Integer.valueOf(fromTime.substring(3, 5));
	}
	public Integer getToMinutes() {
		return toTime==null?0:Integer.valueOf(toTime.substring(3, 5));
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
	public List<WhichDateAttendee> getAttendees() {
		return attendees;
	}
	public void setAttendees(List<WhichDateAttendee> attendees) {
		this.attendees = attendees;
	}
	
	public String getUID() {
		return String.format("%s%s%s%s", from, fromTime, to, toTime);
	}
	
	public WhichDateAttendee getAttendee(WhichDateAttendee attendee) {
		if (attendees==null) {
			return null;
		}
		for (WhichDateAttendee a : attendees) {
			if (a.getEmail().equals(attendee.getEmail())) {
				return a;
			}
		}
		return null;
	}
	public void replaceAttendee(WhichDateAttendee attendee) {
		for (WhichDateAttendee a : attendees) {
			if (a.getEmail().equals(attendee.getEmail())) {
				attendees.remove(a);
				break;
			}
		}
		attendees.add(attendee);
	}
}
