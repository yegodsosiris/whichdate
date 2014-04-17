package com.whichdate.services.domain.aggregated;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WhichDateAttendee  {
	
	private String email;
	private String fullname;
	private int availability;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public void setAvailability(int availability) {
		this.availability = availability;
	}

	public int getAvailability() {
		return availability;
	}
	
}
