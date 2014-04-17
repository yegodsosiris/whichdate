package com.whichdate.services.dto;



public class WhichDateEventDtoContainer {

	private static final long serialVersionUID = -6078921639643702944L;
	
	private String json;
	private String email;

	
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEmail() {
		return email;
	}
	
	public void setJson(String json) {
		this.json = json;
	}
	
	public String getJson() {
		return json;
	}
	
}
