package com.whichdate.services.repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.googleapi.user.GoogleUser;

@Repository
public class WhichDateUserRepositoryImpl extends BaseDaoImpl implements WhichDateUserRepository{
	

	public void saveUser(GoogleUser user) {
		save(user);
	}

	public GoogleUser getUser(String email) {
		String queryString = String.format("SELECT e from GoogleUser e WHERE e.email='%s'", email);
		Query query;
		GoogleUser user = null;
		EntityManager entityManager = getEntityManager();
		try {
			query = entityManager.createQuery(queryString);
			user = (GoogleUser) query.getSingleResult();
			
		} catch (NoResultException e) {
			return null;
		} finally {
			entityManager.close();
		}
		return user;
	}

	public void updateUser(GoogleUser googleUser) {
		update(googleUser);
	}

}
