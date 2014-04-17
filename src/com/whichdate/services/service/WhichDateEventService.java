package com.whichdate.services.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.List;

import javax.mail.MessagingException;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.googleapi.json.internal.calendar.CalendarEventDto;
import com.googleapi.user.GoogleUser;
import com.whichdate.services.dto.WhichDateAttendanceDto;
import com.whichdate.services.dto.WhichDateEventDto;

public interface WhichDateEventService {
	
	public WhichDateEventDto updateEvent(WhichDateEventDto eventDto, GoogleUser googleUser) throws UnsupportedEncodingException, MessagingException, IOException, Exception;

	public WhichDateEventDto createEvent(WhichDateEventDto eventDto, GoogleUser currentUser) throws MalformedURLException, IOException, MessagingException, Exception;

	public WhichDateEventDto getEvent(Long id) throws NumberFormatException, EntityNotFoundException ;

	public void deleteEvent(Long id) throws NumberFormatException, EntityNotFoundException ;

	public List<WhichDateEventDto> getEvents(GoogleUser googleUser);

	public WhichDateAttendanceDto updateAttendance(WhichDateAttendanceDto whichDateAttendanceDto, boolean localhost) throws NumberFormatException, EntityNotFoundException ;

	public void sendInvites(Long id, GoogleUser googleUser, boolean localhost) throws IOException, Exception;

	public void sendReminders(Long id, GoogleUser googleUser, boolean localhost) throws IOException, Exception;

	public Boolean createCalendarEvent(CalendarEventDto calendarEventDto, GoogleUser googleUser, boolean localhost) throws IOException, Exception;
	
	public void sendInviteUpdate(WhichDateEventDto event, GoogleUser googleUser, boolean localhost) throws IOException, Exception;
	
}
