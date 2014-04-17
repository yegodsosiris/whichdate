package com.whichdate.controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.googleapi.service.GoogleAPIService;
import com.googleapi.user.GoogleContact;
import com.googleapi.user.GoogleUser;
import com.googleapi.util.AppConfig;
import com.googleapi.util.URLConnectorHelper;
import com.whichdate.services.service.WhichDateUserService;
import com.whichdate.services.util.JacksonHelper;

public abstract class BaseController {

	@Autowired
	WhichDateUserService userService;
	
	@Autowired
	GoogleAPIService googleAPIService;
	
	@Autowired
	AppConfig appConfig;
	
	public final static String COOKIE_KEY = "WHICH_DATE_ORGANISER";
	
	@ModelAttribute("contextpath")
	public String setContextPath(HttpServletRequest request) throws Exception {
		String contextPath = request.getContextPath();
		return "/".equals(contextPath) ? "" : contextPath;
	}

	
	@ModelAttribute("isLocalHost")
	public boolean isLocalHost(HttpServletRequest request) throws Exception {
		return URLConnectorHelper.isHost(request, appConfig.getDevHost());
	}

	@ModelAttribute("user")
	public GoogleUser getUser(@CookieValue(value=COOKIE_KEY,defaultValue="") String cookie) {
		if (!StringUtils.isBlank(cookie)) {
			return userService.getUser(URLConnectorHelper.decryptString(cookie));
		}
		return null;
	}
	
	protected void addContactsToModel(ModelMap model) throws Exception {
		
		GoogleUser googleUser = getCurrentUser(model);
		if (googleUser != null) {
			List<GoogleContact> contacts = googleAPIService.getContacts(googleUser);
			Collections.sort(contacts, new Comparator<GoogleContact>() {

				public int compare(GoogleContact o1, GoogleContact o2) {
					return o1.getLabel().compareTo(o2.getLabel());
				}
			});
			model.addAttribute("contacts", JacksonHelper.convertToJSON(contacts));
			model.addAttribute("user", googleUser);
		}
	}


	protected GoogleUser getCurrentUser(ModelMap model) {
		return (GoogleUser) model.get("user");
	}


	protected boolean isLocalHost(ModelMap model) {
		return (Boolean) model.get("isLocalHost");
	}


}