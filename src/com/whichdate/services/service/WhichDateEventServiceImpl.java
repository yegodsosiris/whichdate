package com.whichdate.services.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import javax.mail.MessagingException;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.googleapi.json.internal.calendar.CalendarEventDto;
import com.googleapi.json.internal.calendar.GoogleCalendarDto;
import com.googleapi.json.internal.calendar.GoogleCalendarInviteeDto;
import com.googleapi.service.GoogleAPIService;
import com.googleapi.user.GoogleUser;
import com.googleapi.util.AppConfig;
import com.whichdate.services.domain.aggregated.WhichDateAttendee;
import com.whichdate.services.domain.aggregated.WhichDateDate;
import com.whichdate.services.domain.aggregated.WhichDateEvent;
import com.whichdate.services.domain.aggregated.WhichDateInvitee;
import com.whichdate.services.dto.WhichDateAttendanceDto;
import com.whichdate.services.dto.WhichDateDateDto;
import com.whichdate.services.dto.WhichDateEventDto;
import com.whichdate.services.dto.WhichDateInviteeDto;
import com.whichdate.services.dtoHelper.WhichDateEventHelper;
import com.whichdate.services.mail.MailSender;
import com.whichdate.services.repository.WhichDateEventRepository;
import com.whichdate.services.util.DateHelper;
import com.whichdate.services.util.InviteStatuses;

@Service
public class WhichDateEventServiceImpl implements WhichDateEventService {

	private static final Logger log = Logger.getLogger(WhichDateEventServiceImpl.class.getName());

	@Autowired
	private WhichDateEventRepository eventRepository;
	@Autowired
	private MailSender mailSender;
	@Autowired
	private WhichDateUserService userService;
	@Autowired
	private GoogleAPIService googleAPIService;
	@Autowired
	AppConfig appConfig;

	private Boolean bookEvent(WhichDateEvent event, CalendarEventDto calendarEventDto,
			GoogleUser googleUser, boolean localhost) throws MalformedURLException, IOException  {

		List<WhichDateDate> proposedDates = event.getProposedDates();
		WhichDateDate confirmedDate = null;
		for (WhichDateDate date : proposedDates) {
			if (date.getFrom().equals(calendarEventDto.getFrom())
					&& date.getTo().equals(calendarEventDto.getTo())) {
				confirmedDate = date;
				continue;
			}
		}
		List<GoogleCalendarInviteeDto> inviteeDtos = new ArrayList<GoogleCalendarInviteeDto>();
		List<WhichDateInvitee> invitees = event.getInvitees();
		for (WhichDateInvitee invitee : invitees) {
			if (localhost
					&& !StringUtils.contains(invitee.getEmail(),
							InviteStatuses.DAVE_NAME)) {
				inviteeDtos.add(new GoogleCalendarInviteeDto(
						InviteStatuses.DAVE_EMAIL, invitee.getFullname()));
			} else {
				inviteeDtos.add(new GoogleCalendarInviteeDto(invitee
						.getEmail(), invitee.getFullname()));
			}
		}

		boolean allDay = confirmedDate.isAllDay();

		GoogleCalendarDto googleCalendarDto = new GoogleCalendarDto(
				event.getTitle(), event.getDescription(), event.getLocation(),
				inviteeDtos);

		if (allDay) {
			googleCalendarDto.setStartDate(confirmedDate.getFrom());
			googleCalendarDto.setEndDate(confirmedDate.getTo());
		} else {
			DateTime fromD = new DateTime(confirmedDate.getFrom()).withTime(
					confirmedDate.getFromHour(),
					confirmedDate.getFromMinutes(), 0, 0);
			DateTime toD = new DateTime(confirmedDate.getTo()).withTime(
					confirmedDate.getToHour(), confirmedDate.getToMinutes(), 0,
					0);
			;
			googleCalendarDto.setStartDateTime(fromD.toDate());
			googleCalendarDto.setEndDateTime(toD.toDate());
		}

		int returnCode = googleAPIService.createGoogleCalendarEvent(googleUser, googleCalendarDto);

		return returnCode == 200;
	}



	private String getErrorDate(WhichDateDateDto d) {
		return String.format("%s [%s] - %s [%s]. Remove this date and create a new",
				DateHelper.getDisplayDate(d.getFrom()),
				d.getFromTime(),
				DateHelper.getDisplayDate(d.getTo()),
				d.getToTime());
	}

	private void validateDates(WhichDateEventDto eventDto) {
		List<String> uids = new ArrayList<String>();
		for (WhichDateDateDto dateDto : eventDto.getProposedDates()) {
			String uid = dateDto.getUID();
			if (uids.contains(uid)) {
				throw new IllegalStateException("Duplicate dates");
			}
			if (!dateDto.isAllDay()) {
				if (dateDto.getFrom().after(dateDto.getTo())) {
					throw new IllegalStateException("Invalid Dates: From should be before to for "+getErrorDate(dateDto));
				}
				if (sameDay(dateDto) && fromTimeIsAfterToTime(dateDto)) {
					throw new IllegalStateException("Invalid times. From time should be before to time for "+getErrorDate(dateDto));
				}
			}
			uids.add(uid);
		}
	}

	protected boolean fromTimeIsAfterToTime(WhichDateDateDto dateDto) {
		return dateDto.getFromHour() >= dateDto.getToHour();
	}

	protected boolean sameDay(WhichDateDateDto dateDto) {
		return dateDto.getFrom().equals(dateDto.getTo());
	}


	@Override
	public WhichDateEventDto updateEvent(WhichDateEventDto eventDto, GoogleUser googleUser) throws UnsupportedEncodingException, MessagingException, IOException, Exception {
		WhichDateEvent event = WhichDateEventHelper.getEvent(eventDto);
		validateDates(eventDto);
		//System.out.println("saving with dates: "+eventDto.getProposedDates().size());
		List<WhichDateDate> proposedDates = event.getProposedDates();
		// Add user as attendee to any new date that has been added
		for (WhichDateDate date : proposedDates) {
			WhichDateAttendee attendee = new WhichDateAttendee();
			attendee.setEmail(googleUser.getEmail());
			attendee.setFullname(googleUser.getName());
			attendee.setAvailability(InviteStatuses.RESPONCE_YES);
			WhichDateAttendee me = date.getAttendee(attendee);
			if (me==null) {
				date.getAttendees().add(attendee);
			}
		}
		WhichDateEvent updatedEvent = eventRepository.updateEvent(event);
		return WhichDateEventHelper.getEventDto(updatedEvent);
	}


	@Override
	public WhichDateEventDto createEvent(WhichDateEventDto eventDto, GoogleUser googleUser) throws Exception {
		eventDto.setEmail(googleUser.getEmail());
		WhichDateEvent event = WhichDateEventHelper.getEvent(eventDto);
		List<WhichDateInvitee> invitees = event.getInvitees();
		boolean selfIncluded = false;
		WhichDateInvitee me = new WhichDateInvitee();
		for (WhichDateInvitee invitee : invitees) {
			if(invitee.getEmail().equals(event.getEmail())) {
				me = invitee;
				selfIncluded = true;
			}
		}
		// Add user automatically as an invitee
		if (!selfIncluded) {
			me.setEmail(googleUser.getEmail());
			me.setFullname(googleUser.getName());
			me.setImportance(InviteStatuses.INVITE_REQUIRED);
			event.getInvitees().add(me);

		}
		List<WhichDateDate> proposedDates = event.getProposedDates();
		// Add user as an attendee automatically
		for (WhichDateDate date : proposedDates) {
			WhichDateAttendee attendee = new WhichDateAttendee();
			attendee.setEmail(me.getEmail());
			attendee.setFullname(me.getFullname());
			attendee.setAvailability(InviteStatuses.RESPONCE_YES);
			date.getAttendees().add(attendee);
		}
		validateDates(eventDto);
		return WhichDateEventHelper.getEventDto(eventRepository.createEvent(event));
	}


	@Override
	public WhichDateEventDto getEvent(Long id) throws NumberFormatException, EntityNotFoundException  {
		WhichDateEvent event = eventRepository.getEvent(id);
		List<WhichDateInvitee> invitees = event.getInvitees();
		List<WhichDateDate> proposedDates = event.getProposedDates();
		for (WhichDateDate date : proposedDates) {
			Set<String> attendeeEmails = new HashSet<String>();
			List<WhichDateAttendee> attendees = date.getAttendees();
			// Get all known attendees
			for (WhichDateAttendee attendee : attendees) {
				attendeeEmails.add(attendee.getEmail());
			}
			// Check all invitees and if they haven't responded then add a fake attendee object
			for(WhichDateInvitee invitee : invitees) {
				if (!attendeeEmails.contains(invitee.getEmail())) {
					WhichDateAttendee att = new WhichDateAttendee();
					att.setAvailability(InviteStatuses.UNKNOWN);
					att.setEmail(invitee.getEmail());
					att.setFullname(invitee.getFullname());
					attendees.add(att);
				}
			}
		}
		return WhichDateEventHelper.getEventDto(event);
	}


	@Override
	public void deleteEvent(Long id) throws NumberFormatException, EntityNotFoundException  {
		log.warning("deleting "+id);
		eventRepository.deleteEvent(id);
	}



	@Override
	public List<WhichDateEventDto> getEvents(GoogleUser googleUser) {

		List<WhichDateEventDto> result = new ArrayList<WhichDateEventDto>();
		if (googleUser != null) {
			List<WhichDateEvent> events = eventRepository.getEvents(googleUser);
			for (WhichDateEvent p : events) {
				WhichDateEventDto eventDto = WhichDateEventHelper.getEventDto(p);
				eventDto.createReport();
				result.add(eventDto);
			}
		}
		return result;
	}

	@Override
	public WhichDateAttendanceDto updateAttendance(WhichDateAttendanceDto whichDateAttendanceDto, boolean localhost) throws NumberFormatException, EntityNotFoundException {

		//log.warning("Updating attendance");
		//log.warning(jTAttendanceDto.toString());
		WhichDateEventDto eventDto = whichDateAttendanceDto.getEvent();

		String me = whichDateAttendanceDto.getInvitee();
		String subject = eventDto.getTitle()+": "+me+" has registered their availability";

		String body = String.format("%s <p><p>click <a href=\"%s\">Here</a> to go to Which Date", subject, appConfig.getSiteUrl());
		GoogleUser eventUser = userService.getUser(eventDto.getEmail());



		String userEmail = me;
		WhichDateInvitee invitee = null;

		// Get the original event and map invitees
		WhichDateEvent originalEvent = eventRepository.getEvent(eventDto.getParentId());

		Map<String, WhichDateDate> dateMap = new HashMap<String, WhichDateDate>();
		List<WhichDateDate> originalDates = originalEvent.getProposedDates();
		for (WhichDateDate date : originalDates) {
			dateMap.put(date.getUID(), date);
		}
		for (WhichDateInvitee i : originalEvent.getInvitees()) {
			if (i.getEmail().equals(userEmail)) {
				invitee = i;
				break;
			}
		}
		if (invitee==null) {
			throw new IllegalStateException(userEmail+" has not been invited to this event");
		}

		WhichDateEvent inComingEvent = WhichDateEventHelper.getEvent(eventDto);
		// Do some stuff here...
		List<WhichDateDate> incomingProposedDates = inComingEvent.getProposedDates();

		body+="<br><b>Availability</b>";
		for (WhichDateDate incomingJtDate : incomingProposedDates) {
			WhichDateDate origDate = dateMap.get(incomingJtDate.getUID());
			body+="<p>"+DateHelper.getDateTime(origDate.getFrom())+" - "+DateHelper.getDateTime(origDate.getTo());
			for (WhichDateAttendee attendee : incomingJtDate.getAttendees()) {
				if(attendee.getEmail().equals(invitee.getEmail())) {
					attendee.setFullname(invitee.getFullname());
					int availability = attendee.getAvailability();
					String av = "Busy";
					if (availability==1)
					{
						av="Maybe";
					} else if (availability==2)
					{
						av= "Free";
					}
					body+="<br>"+av;
					List<WhichDateAttendee> attendees = origDate.getAttendees();
					if (attendees==null) {
						attendees = new ArrayList<WhichDateAttendee>();
						attendees.add(attendee);
						origDate.setAttendees(attendees);
					}
					else {
						WhichDateAttendee a = origDate.getAttendee(attendee);
						if (a==null) {
							origDate.getAttendees().add(attendee);
						}
						else {
							origDate.replaceAttendee(attendee);
						}
					}
				}
			}
		}
		whichDateAttendanceDto.setEvent(WhichDateEventHelper.getEventDto(eventRepository.updateEvent(originalEvent)));
		mailSender.SendSimpleMessage(eventUser.getEmail(), eventUser.getName(), me, me, body, subject, localhost);
		return whichDateAttendanceDto;
	}

	@Override
	public void sendInvites(Long id, GoogleUser googleUser, boolean localhost) throws IOException, Exception {
		WhichDateEvent event = eventRepository.getEvent(id);
		mailSender.sendMail(event, googleUser, localhost);
		event.setInvitesSent(true);
		eventRepository.updateEvent(event);
	}

	@Override
	public void sendInviteUpdate(WhichDateEventDto eventDto, GoogleUser googleUser, boolean localhost) throws IOException, Exception {
		WhichDateEvent event = eventRepository.getEvent(eventDto.getParentId());
		List<WhichDateInviteeDto> notifierDtos = eventDto.getNotifiers();
		List<WhichDateInvitee> notifiers = new ArrayList<WhichDateInvitee>();
		for (WhichDateInviteeDto invitee : notifierDtos) {
			notifiers.add(WhichDateEventHelper.getInvitee(invitee));
		}
		mailSender.sendMail(event, eventDto.getEmailTemplate().getSubject(), eventDto.getEmailTemplate().getBody(), googleUser, notifiers, localhost);
	}


	@Override
	public void sendReminders(Long id, GoogleUser googleUser, boolean localhost) throws IOException, Exception {
		WhichDateEvent event = eventRepository.getEvent(id);
		mailSender.sendReminderMail(event, googleUser, localhost);
	}


	@Override
	public Boolean createCalendarEvent(CalendarEventDto calendarEventDto, GoogleUser googleUser, boolean localhost) throws IOException, Exception {
		WhichDateEvent event = eventRepository.getEvent(calendarEventDto.getEventId());
		Boolean success = bookEvent(event, calendarEventDto, googleUser, localhost);
		if (success) {
			event.setCalendarEventCreated();
			eventRepository.updateEvent(event);
		}
		return success;
	}

}
