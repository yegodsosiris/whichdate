package com.googleapi.domain;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.google.appengine.api.datastore.Key;

@SuppressWarnings("serial")
@MappedSuperclass
public abstract class GoogleEntity implements Serializable{
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Key key;
	
	public void setKey(Key key) {
		this.key = key;
	}
	
	public boolean isPersistedEntity() {
		return key != null;
	}
	
	public Key getKey() {
		return key;
	}
	private transient long id;
	
	public long getId() {
		return key.getId();
	}
	
}
