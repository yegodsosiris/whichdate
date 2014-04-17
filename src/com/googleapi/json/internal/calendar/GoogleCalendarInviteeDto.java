package com.googleapi.json.internal.calendar;

public class GoogleCalendarInviteeDto {

	
	
	private String email;
	private String displayName;
	
	public GoogleCalendarInviteeDto(String email, String fullname) {
		super();
		this.email = email;
		this.displayName = fullname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getDisplayName() {
		return displayName;
	}
	
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

}
