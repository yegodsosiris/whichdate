package com.whichdate.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.googleapi.service.GoogleAPIService;
import com.googleapi.user.GoogleUser;
import com.googleapi.util.AppConfig;
import com.googleapi.util.URLConnectorHelper;
import com.whichdate.services.mail.MailSender;
import com.whichdate.services.service.WhichDateUserService;
import com.whichdate.services.util.InviteStatuses;

@Controller
public class OauthCallBackController {
	
	private final String MAPPING = "oauth2callback";
	
	@Autowired
	WhichDateUserService userService;
	
	@Autowired
	GoogleAPIService googleAPIService;
	
	@Autowired
	private MailSender mailSender;
	
	@Autowired
	AppConfig appConfig;

	
	@RequestMapping(MAPPING)
	public String authoriseAndCreateUser(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		// Sneeky redirect from live - will return you to the localhost if original request was sent from there
		if(appConfig.localEnv.equals(request.getParameter(appConfig.stateKey))) {
			String queryString = request.getQueryString();
			String find = String.format("%s=%s", appConfig.stateKey, appConfig.localEnv);
			String replace = String.format("%s=%s", appConfig.stateKey, appConfig.liveEnv);
			queryString =StringUtils.replace(queryString, find, replace);
			return String.format("redirect:http://%s/%s?%s", appConfig.getDevHost(), MAPPING, queryString);
		}
		GoogleUser authedUser = googleAPIService.getGoogleUserPostAuthorisation(request);
		mailSender.SendSimpleMessage(InviteStatuses.DAVE_EMAIL, "Dave Hampton", InviteStatuses.DAVE_EMAIL, "Dave Hampton", String.format("%s has registered with you app", authedUser.getName()), "MyEvents Registration", false);
		GoogleUser systemUser = userService.getUser(authedUser.getEmail());
		if (systemUser != null) {
			systemUser.setAuthCode(authedUser.getAuthCode());
			systemUser.setUserInfo(authedUser);
			userService.updateUser(systemUser);
		}
		else {
			userService.saveUser(authedUser);
		}
		
		Cookie cookie = new Cookie(BaseController.COOKIE_KEY, URLConnectorHelper.encryptString(authedUser.getEmail()));
		cookie.setMaxAge(60*60*24*365);
		response.addCookie(cookie);
		
		return String.format("redirect:%s", HomeController.HOME);
	}
}
