package com.whichdate.services.dto.report;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.whichdate.services.dto.WhichDateInviteeDto;
import com.whichdate.services.util.InviteStatuses;
import com.whichdate.services.util.JacksonHelper;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WhichDateInviteeReportDto {
	

	public static final int NOT_REPLIED = 0;
	
	String fullname;
	int availability = InviteStatuses.UNKNOWN;
	int importance;
	int result;
	
	public WhichDateInviteeReportDto(WhichDateInviteeDto inviteeDto) {
		this.fullname=inviteeDto.getFullname();
		this.importance = inviteeDto.getImportance();
		this.result = NOT_REPLIED;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public int getAvailability() {
		return availability;
	}
	public void setAvailability(int availability) {
		this.availability = availability;
	}
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	
	public int getImportance() {
		return importance;
	}
	
	@Override
	public String toString() {
		return JacksonHelper.convertToJSON(this);
	}
	
	
}
