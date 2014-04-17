package com.whichdate.services.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.googleapi.domain.GoogleEntity;

public abstract class BaseDaoImpl {
	

	protected static final String SIMPLE_SELECT = "SELECT e FROM %s e";

	protected EntityManager getEntityManager() {
		return Persistence.createEntityManagerFactory("events-trans")
				.createEntityManager();
	}

	protected <T extends GoogleEntity> T save(T t) {
		EntityManager entityManager = getEntityManager();
		Key key = t.getKey();
		if (key != null) {
			throw new IllegalStateException("Attempting to save a known entity");
		}
		try {
			entityManager.persist(t);
		} finally {
			entityManager.close();
		}
		return t;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void delete(Class clazz, long id) throws EntityNotFoundException {
		EntityManager entityManager = getEntityManager();
		try {
			Object obj = entityManager.find(clazz, new Long(id));
			entityManager.remove(obj);
		} finally {
			entityManager.close();
		}
	}

	protected void refresh(Class<? extends GoogleEntity> t) {
		EntityManager entityManager = getEntityManager();
		try {
			entityManager.refresh(t);
		} finally {
			entityManager.close();
		}
	}

	protected <T extends GoogleEntity> T update(T t) {
		EntityManager entityManager = getEntityManager();
		try {
			entityManager.merge(t);
		} finally {
			entityManager.close();
		}
		return t;
	}


	protected <T extends GoogleEntity> T find(Class<T> t, long id)
			throws EntityNotFoundException {
		EntityManager entityManager = getEntityManager();
		T tt;
		try {
			tt = entityManager.find(t, new Long(id));
		} finally {
			entityManager.close();
		}
		return tt;
	}

	@SuppressWarnings("unchecked")
	protected <T> List<T> list(Class<? extends GoogleEntity> t) {

		String queryString = String.format(SIMPLE_SELECT, t.getSimpleName());
		Query query;
		List<T> resultList;
		EntityManager entityManager = getEntityManager();
		try {
			query = entityManager.createQuery(queryString);
			resultList = query.getResultList();
		} finally {
			entityManager.close();
		}
		return resultList;
	}

}
