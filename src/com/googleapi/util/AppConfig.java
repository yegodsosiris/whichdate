package com.googleapi.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppConfig {

	@Value("#{config[clientId]}")
	private String clientId;
	@Value("#{config[callBackURL]}")
	private String callBackURL;
	@Value("#{config[calendarListUrl]}")
	private String calendarListUrl;
	@Value("#{config[calenderUrl]}")
	private String calenderUrl;
	@Value("#{config[devHost]}")
	private String devHost;
	@Value("#{config[oAuthEndpoint]}")
	private String oAuthEndpoint;
	@Value("#{config[siteUrl]}")
	private String siteUrl;
	@Value("#{config[tokenUrl]}")
	private String tokenUrl;
	@Value("#{config[userInfoService]}")
	private String userInfoService;
	@Value("#{config[clientSecret]}")
	private String clientSecret;
	@Value("#{config[apiKey]}")
	private String apiKey;
	@Value("#{config[contactsUrl]}")
	private String contactsUrl;
	
	public final static String localEnv = "localEnv";
	public final static String liveEnv = "liveEnv";
	public final static String stateKey = "state";
	
	public String getClientId() {
		return clientId;
	}
	
	public String getCallBackURL() {
		return String.format("%s/%s", siteUrl, callBackURL);
	}
	
	public String getoAuthEndpoint() {
		return oAuthEndpoint;
	}
	public String getUserInfoService() {
		return userInfoService;
	}
	public String getTokenUrl() {
		return tokenUrl;
	}
	public String getClientSecret() {
		return clientSecret;
	}
	public String getApiKey() {
		return apiKey;
	}
	public String getDevHost() {
		return devHost;
	}
	public String getSiteUrl() {
		return siteUrl;
	}
	public String getCalenderUrl() {
		return calenderUrl;
	}
	public String getCalendarListUrl() {
		return calendarListUrl;
	}
	public String getContactsUrl() {
		return contactsUrl;
	}
}
