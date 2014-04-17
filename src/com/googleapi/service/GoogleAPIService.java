package com.googleapi.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.googleapi.json.calendar.CalendarList;
import com.googleapi.json.internal.calendar.GoogleCalendarDto;
import com.googleapi.oauth.OAuthScopes;
import com.googleapi.user.GoogleContact;
import com.googleapi.user.GoogleUser;

public interface GoogleAPIService {

	public GoogleUser getGoogleUser(GoogleUser googleUser) throws IOException, Exception;
	
	public List<GoogleContact> getContacts(GoogleUser googleUser) throws Exception;
	
	public CalendarList getCalendarList(GoogleUser googleUser) throws Exception;
	
	public String authorise(HttpServletRequest request, OAuthScopes... oAuthScopes) throws UnsupportedEncodingException;
	
	public GoogleUser getGoogleUserPostAuthorisation(HttpServletRequest request) throws IOException, Exception;

	int createGoogleCalendarEvent(GoogleUser googleUser,
			GoogleCalendarDto googleCalendarDto) throws MalformedURLException,
			IOException;
	
}
