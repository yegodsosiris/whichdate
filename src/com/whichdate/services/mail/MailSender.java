package com.whichdate.services.mail;

import java.io.UnsupportedEncodingException;
import java.util.List;

import com.googleapi.user.GoogleUser;
import com.sun.jersey.api.client.ClientResponse;
import com.whichdate.services.domain.aggregated.WhichDateEvent;
import com.whichdate.services.domain.aggregated.WhichDateInvitee;

public interface MailSender {
	public void sendMail(WhichDateEvent event, GoogleUser googleUser, boolean local) throws UnsupportedEncodingException;
	public void sendReminderMail(WhichDateEvent event, GoogleUser googleUser, boolean local) throws UnsupportedEncodingException;
	public ClientResponse SendSimpleMessage(String toEmail, String toName, String fromEmail, String fromName, String body, String subject, boolean local);
	public void sendMail(WhichDateEvent event, String subject, String body, GoogleUser from, List<WhichDateInvitee> to, boolean localhost) throws UnsupportedEncodingException;
}
