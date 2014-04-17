package com.whichdate.services.domain.aggregated;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.google.appengine.api.datastore.Key;


@JsonIgnoreProperties(ignoreUnknown = true)
public class WhichDateEvent  {
	
	private String title;
	private String description;
	private String location;
	private int quota;
	private String email;
	private Key parentKey;
	private long parentId;
	private boolean invitesSent;
	
	public void setParentId(long parentId) {
		this.parentId = parentId;
	}
	public void setParentKey(Key parentKey) {
		this.parentKey = parentKey;
	}
	public long getParentId() {
		return parentId;
	}
	public Key getParentKey() {
		return parentKey;
	}

	private List<WhichDateDate> proposedDates = new ArrayList<WhichDateDate>();
	
	private List<WhichDateInvitee> invitees = new ArrayList<WhichDateInvitee>();

	private boolean calendarEventCreated;
	
	public List<WhichDateInvitee> getNonResponders() {
		
		Map<String, WhichDateInvitee> inviteeMap = new HashMap<String, WhichDateInvitee>();
		List<String> emails = new ArrayList<String>();
		
		List<WhichDateInvitee> nonResponders = new ArrayList<WhichDateInvitee>();
		
		// create mapping
		for (WhichDateInvitee invitee : getInvitees()) {
			inviteeMap.put(invitee.getEmail(), invitee);
			emails.add(invitee.getEmail());
		}
		
		// find invitees that haven't yet responded
		for (WhichDateDate date : getProposedDates()) {
			List<String> temp = new ArrayList<String>();
			temp.addAll(emails);
			List<WhichDateAttendee> attendees = date.getAttendees();
			for (WhichDateAttendee attendee : attendees) {
				temp.remove(attendee.getEmail());
			}
			for (String leftoverEmail : temp) {
				WhichDateInvitee e = inviteeMap.get(leftoverEmail);
				if (!nonResponders.contains(e)) {
					nonResponders.add(e);
				}
			}
			
		}
		return nonResponders;
	}

	public void setInvitesSent(boolean invitesSent) {
		this.invitesSent = invitesSent;
	}
	
	public boolean isInvitesSent() {
		return invitesSent;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEmail() {
		return email;
	}
	
	public void setQuota(int quota) {
		this.quota = quota;
	}
	public int getQuota() {
		return quota;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	public List<WhichDateDate> getProposedDates() {
		return proposedDates;
	}
	public void setProposedDates(List<WhichDateDate> proposedDates) {
		
		this.proposedDates = proposedDates;
	}
	public void setInvitees(List<WhichDateInvitee> invitees) {
		this.invitees = invitees;
	}
	public List<WhichDateInvitee> getInvitees() {
		return invitees;
	}

	public void setCalendarEventCreated() {
		this.calendarEventCreated = true;
	}
	
	public boolean isCalendarEventCreated() {
		return calendarEventCreated;
	}
	
}
