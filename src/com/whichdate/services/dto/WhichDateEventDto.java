package com.whichdate.services.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.whichdate.services.dto.report.WhichDateEventReportDto;
import com.whichdate.services.util.JacksonHelper;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WhichDateEventDto {

	private String title;
	private String description;
	private String location;
	private boolean invitesSent;
	private int quota;
	private String email;
	private WhichDateEmailTemplateDto emailTemplate;
	private long parentId;

	private boolean calendarEventCreated;
	
	private WhichDateEventReportDto eventReportDto = new WhichDateEventReportDto();

	private List<WhichDateInviteeDto> notifiers = new ArrayList<WhichDateInviteeDto>();
	
	public boolean isCalendarEventCreated() {
		return calendarEventCreated;
	}
	
	public void setCalendarEventCreated(boolean calendarEventCreated) {
		this.calendarEventCreated = calendarEventCreated;
	}
	
	public void setNotifiers(List<WhichDateInviteeDto> notifiers) {
		this.notifiers = notifiers;
	}
	
	public List<WhichDateInviteeDto> getNotifiers() {
		return notifiers;
	}
	
	public void setEmailTemplate(WhichDateEmailTemplateDto emailTemplate) {
		this.emailTemplate = emailTemplate;
	}
	
	public WhichDateEmailTemplateDto getEmailTemplate() {
		return emailTemplate;
	}
	
	public WhichDateEventReportDto getEventReportDto() {
		return eventReportDto;
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
	
	private List<WhichDateDateDto> proposedDates = new ArrayList<WhichDateDateDto>();
	private List<WhichDateInviteeDto> invitees = new ArrayList<WhichDateInviteeDto>();
	

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
	public List<WhichDateDateDto> getProposedDates() {
		if (proposedDates != null) {
			Collections.sort(proposedDates, new Comparator<WhichDateDateDto>() {
				public int compare(WhichDateDateDto o1, WhichDateDateDto o2) {
					return o1.getFrom().compareTo(o2.getFrom());
				}
			});
		}
		return proposedDates;
	}
	public void setProposedDates(List<WhichDateDateDto> proposedDates) {
		this.proposedDates = proposedDates;
	}
	public void setInvitees(List<WhichDateInviteeDto> invitees) {
		this.invitees = invitees;
	}
	public List<WhichDateInviteeDto> getInvitees() {
		return invitees;
	}
	
	public void setInvitesSent(boolean invitesSent) {
		this.invitesSent = invitesSent;
	}
	
	public boolean isInvitesSent() {
		return invitesSent;
	}
	
	@Override
	public String toString() {
		return JacksonHelper.convertToJSON(this);
	}
	public void createReport() {
		if (proposedDates != null) {
			eventReportDto.createReport(parentId, calendarEventCreated, proposedDates, invitees);
		}
	}

	public long getParentId() {
		return parentId;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}
	
}
