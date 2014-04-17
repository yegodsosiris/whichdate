package com.googleapi.user;

import java.io.Serializable;

public class GoogleContact implements Serializable {
	
	private static final long serialVersionUID = 8322584950552731938L;
	private String fullname;
	private String email;
	private String picture;
	
	public void setPicture(String picture) {
		this.picture = picture;
	}
	
	public String getPicture() {
		return picture;
	}
	
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	
	public String getFullname() {
		return fullname;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getLabel() {
		return String.format("%s %s", fullname, email);
	}
	
	public String toString() {
		return String.format("\"%s\" (%s)", fullname, email);
	}
	
	
	
}
