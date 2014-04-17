package com.whichdate.services.domain;

import javax.persistence.Entity;

import com.google.appengine.api.datastore.Text;
import com.googleapi.domain.GoogleEntity;
import com.whichdate.services.domain.aggregated.WhichDateEvent;
import com.whichdate.services.util.JacksonHelper;

@Entity
public class EventJsonWraper extends GoogleEntity {
	
	private static final long serialVersionUID = 6216625078640001337L;
	
	private transient WhichDateEvent event;
	private Text json;
	private String email;
	
	public WhichDateEvent getEvent() {
		event = JacksonHelper.getObjectFromJson(json.getValue(), WhichDateEvent.class);
		event.setParentId(getId());
		return event;
	}
	public void setEvent(WhichDateEvent event) {
		this.event = event;
		json = new Text(JacksonHelper.convertToJSON(event));
	}
	
	public Text getJson() {
		return json;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
}
