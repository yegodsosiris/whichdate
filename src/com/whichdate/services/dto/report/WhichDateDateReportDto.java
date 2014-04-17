package com.whichdate.services.dto.report;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.whichdate.services.dto.WhichDateAttendeeDto;
import com.whichdate.services.dto.WhichDateDateDto;
import com.whichdate.services.dto.WhichDateInviteeDto;
import com.whichdate.services.util.InviteStatuses;
import com.whichdate.services.util.JacksonHelper;
@JsonIgnoreProperties(ignoreUnknown = true)
public class WhichDateDateReportDto {

	private String PENDING = "Pending";
	private String POSSIBLE = "Possible";
	private String GOOD = "Good";
	private String NOT_POSSIBLE = "Not Possible";

	private Date from;
	private Date to;
	private String fromTime;
	private String toTime;
	
	int score;
	String status;
	int max;
	boolean bestMatch;
	
	public void setBestMatch() {
		this.bestMatch = true;
	}
	
	public boolean possible() {
		return ! NOT_POSSIBLE.equals(status);
	}
	
	public boolean isBestMatch() {
		return bestMatch;
	}
	
	public int getMax() {
		return max;
	}
	
	List<WhichDateInviteeReportDto> inviteeReportDtos = new ArrayList<WhichDateInviteeReportDto>();
	Map<String, WhichDateInviteeReportDto> map = new HashMap<String, WhichDateInviteeReportDto>();
	private WhichDateDateDto dateDto;
	private boolean allDay;
	
	public WhichDateDateReportDto(WhichDateDateDto dateDto, List<WhichDateInviteeDto> inviteeDtos) {
		this.dateDto = dateDto;
		
		if (inviteeDtos != null) {
			for (WhichDateInviteeDto inviteeDto : inviteeDtos) {
				max+=(inviteeDto.getImportance()*2);
				WhichDateInviteeReportDto e = new WhichDateInviteeReportDto(inviteeDto);
				map.put(inviteeDto.getEmail(), e);
				inviteeReportDtos.add(e);
			}
		}
		List<String> emails = new ArrayList<String>();
		emails.addAll(map.keySet());
		List<WhichDateAttendeeDto> attendees = dateDto.getAttendees();
		if (attendees!=null) {
			for (WhichDateAttendeeDto attendee : attendees) {
				emails.remove(attendee.getEmail());
			}
		}
		status = emails.isEmpty() ? GOOD : PENDING;
		report();
	}
	
	
	public Date getTo() {
		return to;
	}
	
	public String getToTime() {
		return toTime;
	}
	
	public Date getFrom() {
		return from;
	}
	
	public String getFromTime() {
		return fromTime;
	}
	

	
	public List<WhichDateInviteeReportDto> getInviteeReportDtos() {
		return inviteeReportDtos;
	}
	
	public String getStatus() {
		return status;
	}
	
	public boolean isAllDay() {
		return allDay;
	}

	public void report() {
		this.from = dateDto.getFrom();
		this.to = dateDto.getTo();
		this.fromTime = dateDto.getFromTime();
		this.toTime = dateDto.getToTime();
		this.allDay = dateDto.isAllDay();
		List<WhichDateAttendeeDto> attendees = dateDto.getAttendees();
		if (attendees != null) {
			for (WhichDateAttendeeDto responce : attendees) {
				WhichDateInviteeReportDto invite = map.get(responce.getEmail());
				if (invite != null) { // incase their invite was revoked
					int availability = responce.getAvailability();
					invite.setAvailability(availability);
					boolean notAvailabile = availability == InviteStatuses.RESPONCE_NO;
					if (invite.getImportance() == InviteStatuses.INVITE_REQUIRED) {
						if (notAvailabile) {
							status = NOT_POSSIBLE;
							invite.setResult(InviteStatuses.INVITEE_RESULT_BAD);
							
						}
						else if (maybe(responce)) {
							setOk(invite);
						}
						else {
							setGood(invite);
						}
					}
					else if (invite.getImportance() == InviteStatuses.INVITE_DESIRED) {
						if(notAvailabile) {
							invite.setResult(InviteStatuses.INVITEE_RESULT_BAD);
						}
						else if (maybe(responce)) {
							setOk(invite);
						}
						else {
							setGood(invite);
						}
					}
					else {
						if(notAvailabile) {
							setOk(invite);
						}
						else if (maybe(responce)) {
							setOk(invite);
						}
						else {
							setGood(invite);
						}
					}
				}
			}
		}
	}

	protected void setGood(WhichDateInviteeReportDto invite) {
		invite.setResult(InviteStatuses.INVITEE_RESULT_GOOD);
		score+=(invite.getImportance()*2);
	}

	protected void setOk(WhichDateInviteeReportDto invite) {
		invite.setResult(InviteStatuses.INVITEE_RESULT_OK);
		if(!NOT_POSSIBLE.equals(status)) {
			status=POSSIBLE;
		}
		score+=invite.getImportance();
	}

	protected boolean maybe(WhichDateAttendeeDto responce) {
		return responce.getAvailability() == InviteStatuses.RESPONCE_MAYBE;
	}
	
	@Override
	public String toString() {
		return JacksonHelper.convertToJSON(this);
	}
	
	public int getScore() {
		return score;
	}
}
