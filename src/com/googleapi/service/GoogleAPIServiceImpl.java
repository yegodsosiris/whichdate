package com.googleapi.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.googleapi.json.calendar.CalendarList;
import com.googleapi.json.contacts.ContactList;
import com.googleapi.json.contacts.Entry;
import com.googleapi.json.contacts.Gd$email;
import com.googleapi.json.internal.calendar.GoogleCalendarDto;
import com.googleapi.json.userinfo.GoogleUserInfo;
import com.googleapi.oauth.OAuthScopes;
import com.googleapi.oauth.OAuthToken;
import com.googleapi.user.GoogleContact;
import com.googleapi.user.GoogleUser;
import com.googleapi.util.AppConfig;
import com.googleapi.util.URLConnectorHelper;
import com.whichdate.services.util.JacksonHelper;

@Service
public class GoogleAPIServiceImpl implements GoogleAPIService {

	private static final String AUTH_TOKEN_TEMPLATE = "code=%s&client_id=%s&client_secret=%s&redirect_uri=%s&grant_type=authorization_code";
	private static final String REFRESH_TOKEN_TEMPLATE = "refresh_token=%s&client_id=%s&client_secret=%s&grant_type=refresh_token";
	private static final String AUTH_QUERY = "%s?scope=%s&%s=%s&redirect_uri=%s&response_type=code&client_id=%s&approval_prompt=force&access_type=offline";
	private static final String MAX_RESULTS = "max-results=5000";
	private static final String ALT_JSON = "alt=json";
	private static final String AUTH_CODE_KEY = "code";
	private static final String ERROR = "error";
	private static final String UTF8 = "UTF8";
	private static final String CONTENT_LENGTH = "Content-Length";
	private static final String POST = "POST";
	private static final String APPLICATION_JSON = "application/json";
	private static final String CONTENT_TYPE = "Content-Type";
	private static final String BEARER = "Bearer ";
	private static final String AUTHORIZATION = "Authorization";

	@Autowired
	AppConfig appConfig;

	@Autowired
	GoogleUserService googleUserService;
	


	private String constructCalendarUri(GoogleUser googleUser)
			throws UnsupportedEncodingException {
		return String.format("%s/%s/events?sendNotifications=true&key=%s", 
				appConfig.getCalenderUrl(), 
				URLConnectorHelper.encode(googleUser.getEmail()),
				appConfig.getApiKey());
	}
	
	@Override
	public int createGoogleCalendarEvent(GoogleUser googleUser, GoogleCalendarDto googleCalendarDto) throws MalformedURLException, IOException {

		String json = JacksonHelper.convertToJSON(googleCalendarDto); 
		HttpURLConnection con = (HttpURLConnection) new URL(constructCalendarUri(googleUser)).openConnection();
		con.setRequestMethod(POST);
		con.setRequestProperty(AUTHORIZATION, BEARER + googleUser.getAuthToken());
		con.addRequestProperty(CONTENT_TYPE, APPLICATION_JSON);
		con.setDoOutput(true);

		con.setRequestProperty(CONTENT_LENGTH, Integer.toString(json.length()));
		con.getOutputStream().write(json.getBytes(UTF8));

		int responseCode = con.getResponseCode();
		
		con.disconnect();
		
		return  responseCode;

	}

	@Override
	public GoogleUser getGoogleUserPostAuthorisation(HttpServletRequest request)
			throws IOException, Exception {

		String error = (String) request.getAttribute(ERROR);
		if (!StringUtils.isBlank(error)) {
			throw new IllegalStateException(error);
		}
		String authCode = (String) request.getParameter(AUTH_CODE_KEY);

		GoogleUser googleUser = new GoogleUser();
		googleUser.setAuthCode(authCode);

		return getGoogleUser(googleUser);
	}

	public GoogleUser getGoogleUser(GoogleUser googleUser) throws IOException,
			Exception {
		GoogleUserInfo googleUserInfo = JacksonHelper.getObjectFromJson(
				getFeed(googleUser, appConfig.getUserInfoService()),
				GoogleUserInfo.class);
		googleUser.setUserInfo(googleUserInfo);
		return googleUser;
	}

	public List<GoogleContact> getContacts(GoogleUser googleUser)
			throws Exception {

		if (!StringUtils.isEmpty(googleUser.getAuthCode())) {

			String feed = getFeed(googleUser, appConfig.getContactsUrl(),
					ALT_JSON, MAX_RESULTS);

			ContactList contactList = JacksonHelper.getObjectFromJson(feed,
					ContactList.class);
			List<Entry> entries = contactList.getFeed().getEntry();

			List<GoogleContact> contacts = new ArrayList<GoogleContact>();
			List<GoogleContact> dodgyContacts = new ArrayList<GoogleContact>();
			List<String> goodContacts = new ArrayList<String>();

			for (Entry entry : entries) {
				String name = entry.getTitle().get$t();
				List<Gd$email> emails = entry.getGd$email();
				for (Gd$email email : emails) {
					GoogleContact c = new GoogleContact();
					c.setFullname(name);
					c.setEmail(email.getAddress());
					if (StringUtils.isBlank(name)) {
						dodgyContacts.add(c);
					} else {
						contacts.add(c);
						goodContacts.add(StringUtils.lowerCase(email
								.getAddress()));
					}
				}
			}
			for (GoogleContact googleContact : dodgyContacts) {
				if (!goodContacts.contains(StringUtils.lowerCase(googleContact
						.getEmail()))) {
					googleContact.setFullname(googleContact.getEmail());
					contacts.add(googleContact);
				}
			}
			return contacts;
		}
		return new ArrayList<GoogleContact>();
	}

	@Override
	public String authorise(HttpServletRequest request,
			OAuthScopes... oAuthScopes) throws UnsupportedEncodingException {

		String state = URLConnectorHelper.isHost(request,
				appConfig.getDevHost()) ? appConfig.localEnv
				: appConfig.liveEnv;

		List<String> scopes = new ArrayList<String>();

		for (OAuthScopes oAuthScope : oAuthScopes) {
			scopes.add(oAuthScope.getScope());
		}

		String scopesString = String
				.format("%s", StringUtils.join(scopes, " "));
		String url = String.format(AUTH_QUERY, appConfig.getoAuthEndpoint(),
				URLConnectorHelper.encode(scopesString), appConfig.stateKey,
				URLConnectorHelper.encode(state),
				URLConnectorHelper.encode(appConfig.getCallBackURL()),
				appConfig.getClientId());

		return "redirect:" + url;
	}

	@Override
	public CalendarList getCalendarList(GoogleUser googleUser) throws Exception {
		String feed = getFeed(googleUser, appConfig.getCalendarListUrl(),
				ALT_JSON, MAX_RESULTS);
		return JacksonHelper.getObjectFromJson(feed, CalendarList.class);
	}

	private String getFeed(GoogleUser googleUser, String serviceUrl,
			String... additionalParams) throws Exception {
		OAuthToken oAuthToken = getOAuthToken(googleUser);
		oAuthToken.setAccess_token(oAuthToken.getAccess_token());
		if (oAuthToken.hasEror()) {
			return JacksonHelper.convertToJSON(oAuthToken);
		}
		List<String> params = new ArrayList<String>();
		if (additionalParams != null) {
			params.addAll(Arrays.asList(additionalParams));
		}
		params.add(String.format("access_token=%s",
				oAuthToken.getAccess_token()));
		String URL = String.format("%s?%s", serviceUrl,
				StringUtils.join(params, "&"));
		String feed = URLConnectorHelper.executeGet(URL);
		return feed;
	}

	private OAuthToken getOAuthToken(GoogleUser googleUser)
			throws JsonParseException, JsonMappingException, IOException {

		boolean requiresUpdate = googleUser.getAuthToken() == null
				|| googleUser.tokenHasExpired();
		if (requiresUpdate) {
			String params;
			if (googleUser.getAuthToken() == null) {
				params = String.format(AUTH_TOKEN_TEMPLATE,
						URLConnectorHelper.encode(googleUser.getAuthCode()),
						URLConnectorHelper.encode(appConfig.getClientId()),
						URLConnectorHelper.encode(appConfig.getClientSecret()),
						URLConnectorHelper.encode(appConfig.getCallBackURL()));
			} else {
				params = String
						.format(REFRESH_TOKEN_TEMPLATE, URLConnectorHelper
								.encode(googleUser.getRefreshToken()),
								URLConnectorHelper.encode(appConfig
										.getClientId()), URLConnectorHelper
										.encode(appConfig.getClientSecret()),
								URLConnectorHelper.encode(appConfig
										.getCallBackURL()));
			}

			String json = URLConnectorHelper.excutePost(
					appConfig.getTokenUrl(), params);

			OAuthToken token = JacksonHelper.getObjectFromJson(json,
					OAuthToken.class);
			if (token.hasEror()) {
				throw new IllegalAccessError(token.getError());
			}
			DateTime dt = DateTime.now().plusSeconds(
					Integer.valueOf(token.getExpires_in()));
			googleUser.setExpires(dt.toDate());
			if (googleUser.getAuthToken() == null) {
				googleUser.setRefreshToken(token.getRefresh_token());
			}
			if (requiresUpdate) {
				googleUser.setAuthToken(token.getAccess_token());

				if (googleUser.getEmail() != null) {
					googleUserService.updateUser(googleUser);
				}
			}
			return token;
		} else {
			OAuthToken oat = new OAuthToken();
			oat.setAccess_token(googleUser.getAuthToken());
			return oat;
		}
	}

}
