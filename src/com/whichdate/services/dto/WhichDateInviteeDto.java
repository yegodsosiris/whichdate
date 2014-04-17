package com.whichdate.services.dto;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.googleapi.util.URLConnectorHelper;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WhichDateInviteeDto {

	private String email;
	private String encEmail;
	private String fullname;
	private int importance;
	
	public String getEmail() {
		return email;
	}
	
	public String getEncEmail() {
		return encEmail;
	}
	
	public void setEmail(String email) {
		this.email = email;
		encEmail = URLConnectorHelper.encryptString(email);
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getFullname() {
		return fullname;
	}
	public int getImportance() {
		return importance;
	}
	public void setImportance(int importance) {
		this.importance = importance;
	}
	
	
}
