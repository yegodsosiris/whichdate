package com.whichdate.services.mail;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.googleapi.user.GoogleUser;
import com.googleapi.util.URLConnectorHelper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import com.whichdate.services.domain.aggregated.WhichDateEvent;
import com.whichdate.services.domain.aggregated.WhichDateInvitee;
import com.whichdate.services.util.InviteStatuses;

@Service
public class MailSenderImpl implements MailSender {
	
	private static final Logger log = Logger.getLogger(MailSenderImpl.class.getName());

	@Value("#{config[attendUrl]}")
	private String url;
	
	@Value("#{config[mailgunApiKey]}")
	private String mailgunApiKey;
	
	@Value("#{config[mailgunUrl]}")
	private String mailgunUrl;
	
	@Override
	public void sendMail(WhichDateEvent event, GoogleUser googleUser, boolean localhost) throws UnsupportedEncodingException  {
		
        sendMail(event, googleUser, event.getInvitees(), null, null, localhost);
	}
	
	@Override
	public void sendReminderMail(WhichDateEvent event, GoogleUser googleUser, boolean localhost) throws UnsupportedEncodingException  {
		
		List<WhichDateInvitee> sendto = event.getNonResponders();
		if (!sendto.isEmpty()) {
			sendMail(event, googleUser, sendto, null, null, localhost);
		}
	}
	
	@Override
	public ClientResponse SendSimpleMessage(String toEmail, String toName, String fromEmail, String fromName, String body, String subject, boolean localhost) {
	       
		if (localhost) toEmail=InviteStatuses.DAVE_EMAIL;
		
		Client client = Client.create();
       client.addFilter(new HTTPBasicAuthFilter("api",mailgunApiKey));
       WebResource webResource =
               client.resource(mailgunUrl);
       MultivaluedMapImpl formData = new MultivaluedMapImpl();
       formData.add("from", String.format("%s <%s>", fromName, fromEmail));
       formData.add("to", toEmail);
       formData.add("subject", subject);
       formData.add("html", body);
       log.warning(String.format("message sent to %s (%s) from %s (%s) %s", toName, toEmail, fromName, fromEmail, subject));
       return webResource.type(MediaType.APPLICATION_FORM_URLENCODED).
               post(ClientResponse.class, formData);
	}
	

	private void sendMail(WhichDateEvent event, GoogleUser googleUser, List<WhichDateInvitee> invitees, String optSubject, String optBody, boolean localhost) throws UnsupportedEncodingException {
		
    	String msgBody = getMessageBody(event);
    	String subject = String.format("You have been invited to %s", event.getTitle());
    	if (!StringUtils.isEmpty(optBody)) {
    		subject = String.format("Update to Event %s", event.getTitle());
    	}
        for (WhichDateInvitee invitee : invitees) {
        	String email = invitee.getEmail();
			String body = StringUtils.replace(msgBody, "@link@", String.format(url, event.getParentId(), URLConnectorHelper.encode(URLConnectorHelper.encryptString(email))));            
			if(!StringUtils.isEmpty(optBody)) {
				body = String.format("<h2>Update to event %s</h2>%s<h3>Original invite: See link below to register you availability</h3><hr><p>%s", event.getTitle(), optBody, body);
			}
			SendSimpleMessage(email, invitee.getFullname(), googleUser.getEmail(), googleUser.getName(), body, subject, localhost);
		}
	}

	protected String getMessageBody(WhichDateEvent event) {
		StringBuilder sb = new StringBuilder();
    	Scanner sc = new Scanner(MailSenderImpl.class.getClassLoader().getResourceAsStream("email.template.htm"));
		while (sc.hasNext()) {
			String next = sc.nextLine();
			next = StringUtils.replace(next, "@title@", event.getTitle());
			next = StringUtils.replace(next, "@description@", event.getDescription());
			sb.append(next);
		}
		
		String msgBody = sb.toString();
		return msgBody;
	}

	@Override
	public void sendMail(WhichDateEvent event, String subject, String body, GoogleUser from, List<WhichDateInvitee> to, boolean localhost) throws UnsupportedEncodingException {
		if (!to.isEmpty()) {
			sendMail(event, from, to, subject, body, localhost);
		}
	}
}
