package com.whichdate.services.repository;

import java.util.List;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.googleapi.user.GoogleUser;
import com.whichdate.services.domain.aggregated.WhichDateEvent;


public interface WhichDateEventRepository {

	public List<WhichDateEvent> getEvents(GoogleUser googleUser);

	public WhichDateEvent updateEvent(WhichDateEvent event);

	public WhichDateEvent createEvent(WhichDateEvent event);

	public WhichDateEvent getEvent(Long id) throws EntityNotFoundException;

	public void deleteEvent(Long id) throws EntityNotFoundException;

}
