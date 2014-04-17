package com.whichdate.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.logging.Logger;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.googleapi.json.internal.calendar.CalendarEventDto;
import com.whichdate.services.dto.WhichDateAttendanceDto;
import com.whichdate.services.dto.WhichDateEventDto;
import com.whichdate.services.exception.JsonError;
import com.whichdate.services.service.WhichDateEventService;

@Controller
@RequestMapping("/rest")
public class RestController extends BaseController {

	private static final Logger log = Logger.getLogger(RestController.class.getName());
	
	@ExceptionHandler (Exception.class)
    @ResponseStatus (HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView handleAllExceptions(Exception ex) {
		ex.printStackTrace();
		//System.out.println(ex.getMessage());
        return new JsonError(ex.getMessage()).asModelAndView();
    }
	
	@Autowired
	private WhichDateEventService whichDateEventService;
	
	@RequestMapping(value="/events", method = RequestMethod.PUT)
	public @ResponseBody WhichDateEventDto updateEvent(@RequestBody WhichDateEventDto eventDto, ModelMap model) throws UnsupportedEncodingException, MessagingException, IOException, Exception  {
		return whichDateEventService.updateEvent(eventDto, getCurrentUser(model));
	}
	
	@RequestMapping(value="/events", method = RequestMethod.GET)
	public @ResponseBody List<WhichDateEventDto> getEvents(ModelMap model)  {
		return whichDateEventService.getEvents(getCurrentUser(model));
	}

	@RequestMapping(value="/events", method = RequestMethod.POST)
	public @ResponseBody WhichDateEventDto createEvent(@RequestBody WhichDateEventDto eventDto, ModelMap model) throws Exception  {
		return whichDateEventService.createEvent(eventDto, getCurrentUser(model));
	}

	@RequestMapping(value="/cron", method=RequestMethod.GET)
	public @ResponseBody Integer forCron() {
		return 1;
	}

	@RequestMapping(value="/event/attendance", method = RequestMethod.POST)
	public @ResponseBody WhichDateAttendanceDto updateAttendance(@RequestBody WhichDateAttendanceDto whichDateAttendanceDto, ModelMap model) throws Exception  {
		return whichDateEventService.updateAttendance(whichDateAttendanceDto, isLocalHost(model));
	}

	@RequestMapping(value="/events/{id}", method = RequestMethod.GET)
	public @ResponseBody WhichDateEventDto getEvent(@PathVariable Long id) throws NumberFormatException, EntityNotFoundException  {
		WhichDateEventDto event = whichDateEventService.getEvent(id);
		return event;
	}

	@RequestMapping(value="/invites/{id}", method = RequestMethod.POST)
	public @ResponseBody Boolean sendInvites(@PathVariable Long id, ModelMap model) throws IOException, Exception  {
		whichDateEventService.sendInvites(id, getCurrentUser(model), isLocalHost(model));
		return Boolean.TRUE;
	}

	@RequestMapping(value="/inviteupdate", method = RequestMethod.POST)
	public @ResponseBody Boolean sendInvites(@RequestBody WhichDateEventDto eventDto, ModelMap model) throws IOException, Exception  {
		whichDateEventService.sendInviteUpdate(eventDto, getCurrentUser(model), isLocalHost(model));
		return Boolean.TRUE;
	}

	@RequestMapping(value="/calendar/create", method = RequestMethod.POST)
	public @ResponseBody Boolean createCalendarEvent(@RequestBody CalendarEventDto calendarEventDto, ModelMap model) throws IOException, Exception  {
		return whichDateEventService.createCalendarEvent(calendarEventDto, getCurrentUser(model), isLocalHost(model));
	}

	@RequestMapping(value="/reminders/{id}", method = RequestMethod.POST)
	public @ResponseBody Boolean sendReminders(@PathVariable Long id, ModelMap model) throws IOException, Exception  {
		whichDateEventService.sendReminders(id, getCurrentUser(model), isLocalHost(model));
		return Boolean.TRUE;
	}

	@RequestMapping(value="/events/{id}", method = RequestMethod.DELETE)
	public @ResponseBody Boolean deleteEvent(@PathVariable Long id) throws NumberFormatException, EntityNotFoundException  {
		whichDateEventService.deleteEvent(id);
		return Boolean.TRUE;
	}
}
