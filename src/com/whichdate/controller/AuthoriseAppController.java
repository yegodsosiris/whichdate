package com.whichdate.controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.googleapi.oauth.OAuthScopes;
import com.googleapi.service.GoogleAPIService;

@Controller
public class AuthoriseAppController {

	static final String URI = "/authoriseGoogle";
	
	@Autowired
	GoogleAPIService service;

	@RequestMapping(URI)
	public String authoriseApp(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {

		return service.authorise(request, 
				OAuthScopes.Userinfo_Profile,
				OAuthScopes.Userinfo_Email,
				OAuthScopes.Contacts,
				OAuthScopes.Calendar);
				
	}
	
	
	
}