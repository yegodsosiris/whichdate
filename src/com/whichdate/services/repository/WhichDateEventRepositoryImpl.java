package com.whichdate.services.repository;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.googleapi.user.GoogleUser;
import com.whichdate.services.domain.EventJsonWraper;
import com.whichdate.services.domain.aggregated.WhichDateEvent;

@Repository
public class WhichDateEventRepositoryImpl extends BaseDaoImpl implements WhichDateEventRepository {

	@Override
	public List<WhichDateEvent> getEvents(GoogleUser googleUser) {
		String queryString = String.format("SELECT e FROM EventJsonWraper e where e.email='%s'", googleUser.getEmail());
		Query query;
		List<EventJsonWraper> resultList;
		List<WhichDateEvent> returnedResults = new ArrayList<WhichDateEvent>();
		EntityManager entityManager = getEntityManager();
		try {
			query = entityManager.createQuery(queryString);
			resultList = query.getResultList();
			for (EventJsonWraper event : resultList) {
				returnedResults.add(event.getEvent());
			}
		} finally {
			entityManager.close();
		}
		return returnedResults;
	}

	@Override
	public WhichDateEvent updateEvent(WhichDateEvent event) {
		EventJsonWraper jObj = null;
		try {
			jObj = getEventPersistanceObj(event.getParentId());
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
		}
		jObj.setEvent(event);
		update(jObj);
		return event;
	}

	@Override
	public WhichDateEvent createEvent(WhichDateEvent event) {
		EventJsonWraper jObj = new EventJsonWraper();
		jObj.setEvent(event);
		jObj.setEmail(event.getEmail());
		save(jObj);
		event.setParentId(jObj.getId());
		event.setParentKey(event.getParentKey());
		return event;
	}

	private EventJsonWraper getEventPersistanceObj(long id) throws EntityNotFoundException {
		EventJsonWraper jObj = find(EventJsonWraper.class, id);
		return jObj;
	}

	@Override
	public WhichDateEvent getEvent(Long id) throws EntityNotFoundException {
		EventJsonWraper jObj = find(EventJsonWraper.class, id);
		return jObj.getEvent();
	}

	@Override
	public void deleteEvent(Long id) throws EntityNotFoundException {
		delete(EventJsonWraper.class, id);
	}


}
