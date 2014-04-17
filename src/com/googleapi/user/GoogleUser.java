package com.googleapi.user;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import org.joda.time.DateTime;

import com.googleapi.domain.GoogleEntity;
import com.googleapi.json.userinfo.GoogleUserInfo;


@Entity
/**
 * 
 * NB: No password stored as this is all managed by Google.
 * 
 * @author dave.hampton
 *
 */
public class GoogleUser extends GoogleEntity {

	private static final long serialVersionUID = 8626318521621005815L;
	private String authCode;
	private String email;
	private Date expires;
	private String authToken;
	private String refreshToken;
	
	@OneToOne(cascade=CascadeType.ALL)
    private String name;
    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFamily_name() {
		return family_name;
	}

	public void setFamily_name(String family_name) {
		this.family_name = family_name;
	}

	public boolean isVerified_email() {
		return verified_email;
	}

	public void setVerified_email(boolean verified_email) {
		this.verified_email = verified_email;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

    private String family_name;
    private boolean verified_email;
    private String link;
    private String picture;
    private String gender;
    
	public void setUserInfo(GoogleUser authedUser) {
		email = authedUser.getEmail();
		name = authedUser.getName() != null? authedUser.getName() : name;
	    family_name = authedUser.getFamily_name();
	    verified_email = authedUser.isVerified_email();
	    link = authedUser.getLink();
	    picture = authedUser.getPicture();
	    gender = authedUser.getGender();
	}


	public void setUserInfo(GoogleUserInfo googleUserInfo) {
		email = googleUserInfo.getEmail();
		name = googleUserInfo.getName();
	    family_name = googleUserInfo.getFamily_name();
	    verified_email = googleUserInfo.isVerified_email();
	    link = googleUserInfo.getLink();
	    picture = googleUserInfo.getPicture();
	    gender = googleUserInfo.getGender();
	}
	
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	
	public String getRefreshToken() {
		return refreshToken;
	}
	
	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}
	public String getAuthToken() {
		return authToken;
	}
	
	public Date getExpires() {
		return expires;
	}
	
	public boolean tokenHasExpired() {
		return expires==null || new DateTime(expires).isBeforeNow();
	}
	
	public void setExpires(Date expires) {
		this.expires = expires;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}
	
	public String getAuthCode() {
		return authCode;
	}
}
