package com.helper.controller;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.Events;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.helper.util.CalendarEvents;
import com.helper.util.UserUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public final class GoogleAuthHelper {
	// GOOGLE CONSOLE API - CLEINT ID, SECRET
	private static final String CLIENT_ID = "364445372740-dfi7mmpkhcudg453o9u5bqmaeeen3tdd.apps.googleusercontent.com";
	private static final String CLIENT_SECRET = "DcwkE2RJdDMARJW4-43eL0CT";
	private static final String CALLBACK_URI = "http://localhost:8088/email/test";

	// GOOGLE API ACCESS SCOPE
	// 첫 로그인 시에 동의화면에서 승인하게 된다.
	private static final Iterable<String> SCOPE = Arrays.asList("https://www.googleapis.com/auth/plus.login",
			"https://www.googleapis.com/auth/userinfo.email", CalendarScopes.CALENDAR_READONLY,
			"https://www.googleapis.com/auth/calendar");
	private static final String USER_INFO_URL = "https://www.googleapis.com/oauth2/v2/userinfo";

	/*
	 * JacksonFacotry : CLIENT 정보들을 GOOGLE API에 맞게 파싱 
	 * HTTP_TRANSPORT : GOOGLE API 통신 모듈
	 */
	private static final JsonFactory JSON_FACTORY = new JacksonFactory();
	private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
	private static final String APPLICATION_NAME = "HELPER";

	private final GoogleAuthorizationCodeFlow flow;
	private String stateToken = null;
	private String jsonIdentity = null;

	/*
	 * CLEINT ID, SECRET, JSON_FACTORY, HTTP_TRANSPORT, SCOPE를 이용해서 FLOW를 빌드
	 */
	public GoogleAuthHelper() {
		flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY, CLIENT_ID, CLIENT_SECRET,
				(Collection<String>) SCOPE).build();

		generateStateToken();
	}

	/*
	 * CLEINT ID,SECRET,CALLBACK_URI를 이용해서 LOGIN URL BUILD
	 */

	public String buildLoginUrl() {
		final GoogleAuthorizationCodeRequestUrl url = flow.newAuthorizationUrl();
		return url.setRedirectUri(CALLBACK_URI).setState(stateToken).build();
	}

	/*
	 * State token Generate
	 */
	private void generateStateToken() {
		SecureRandom sr1 = new SecureRandom();

		stateToken = "google;" + sr1.nextInt();
	}

	public String getStateToken() {
		return stateToken;
	}

	public static com.google.api.services.calendar.Calendar getCalendarService(Credential credential)
			throws IOException {
		return new com.google.api.services.calendar.Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
				.setApplicationName(APPLICATION_NAME).build();
	}

	/*
	 * Token을 발급하는 과정을 거쳐 사용자 정보와 코드를 얻을 수 있다
	 */
	public String getUserInfoJson(final String authCode) throws Exception {
		// response : 내부에서 생성한 state token과 code를 이용하여 GOOGLE에서 발급하는 TOKEN GET
		final GoogleTokenResponse response = flow.newTokenRequest(authCode).setRedirectUri(CALLBACK_URI).execute();
		final Credential credential = flow.createAndStoreCredential(response, null);
		final HttpRequestFactory requestFactory = HTTP_TRANSPORT.createRequestFactory(credential);
		
		// Make an authenticated request
		final GenericUrl url = new GenericUrl(USER_INFO_URL);
		final HttpRequest request = requestFactory.buildGetRequest(url);
		request.getHeaders().setContentType("application/json");
		final String jsonIdentity = request.execute().parseAsString();

		UserUtils.getInstance().setCredential(credential);
		setJsonIdentity(jsonIdentity);
		createCalendarEvents();

		return jsonIdentity;
	}

	public void setJsonIdentity(String jsonIdentity) throws Exception {
		this.jsonIdentity = jsonIdentity;
		convertEmail();
	}

	public String getJsonIdentity() throws Exception {
		return jsonIdentity;
	}

	// jsonIdentity에 들어있는 계정 이메일을 저장
	public void convertEmail() throws Exception {
		String kvPairs[] = jsonIdentity.split(",");

		for (int i = 0; i < kvPairs.length; i++) {
			String kvPair[] = kvPairs[i].split(":");

			if (kvPair[0].equals("\n \"email\"")) {
				UserUtils.getInstance().setEmailAddress(kvPair[1].replaceAll(" ", ""));
			}
		}
	}

	// Event를 FullCalendar의 형식으로 파싱하는 Method
	public String remakeEventsToCalendar(Events events) throws Exception {
		JSONObject obj = new JSONObject();
		JSONArray array = new JSONArray();

		List<Event> items = events.getItems();
		if (items.size() == 0) {
			System.out.println("----------This Email has No events-------------");
		} else {
			for (Event event : items) {
				String s = event.getStart().values().toString();
				s = s.replace("[", "");
				s = s.replace("]", "");
				String e = event.getEnd().values().toString();
				e = e.replace("[", "");
				e = e.replace("]", "");

				obj.put("title", event.getSummary());
				obj.put("start", s);
				obj.put("end", e);
				array.add(obj);
			}
		}
		return array.toString();
	}

	// Calendar에 event를 띄워주는 Method
	public void createCalendarEvents() throws Exception {
		com.google.api.services.calendar.Calendar service = getCalendarService(UserUtils.getInstance().getCredential());

		Events events = service.events().list("primary").setMaxResults(1000).setOrderBy("startTime")
				.setSingleEvents(true).execute();

		CalendarEvents.getInstance().setCalendarEvents(events);
		CalendarEvents.getInstance().setRemakeCalendarEvents(remakeEventsToCalendar(events));

	}

	// Calendar Event Insert Method
	public Boolean insertEventToCalendar(String[] eventStringArray) throws Exception {
		com.google.api.services.calendar.Calendar service = getCalendarService(UserUtils.getInstance().getCredential());

		Event event = new Event().setSummary(eventStringArray[0]).setLocation(eventStringArray[3])
				.setDescription(eventStringArray[4]);

		DateTime startDateTime = new DateTime(eventStringArray[1]);
		EventDateTime start = new EventDateTime().setDateTime(startDateTime);
		event.setStart(start);

		DateTime endDateTime = new DateTime(eventStringArray[2]);
		EventDateTime end = new EventDateTime().setDateTime(endDateTime);
		event.setEnd(end);

		String calendarId = "primary";
		event = service.events().insert(calendarId, event).execute();

		createCalendarEvents();

		return true;
	}

	// Calendar Event Update Method
	public Boolean updateEventToCalendar(String[] str) throws Exception {
		com.google.api.services.calendar.Calendar service = getCalendarService(UserUtils.getInstance().getCredential());
		String calendarId = "primary";
		Event event = service.events().get(calendarId, str[5]).execute();

		event.setDescription(str[4]);
		DateTime startDateTime = new DateTime(str[1]);
		EventDateTime start = new EventDateTime().setDateTime(startDateTime);
		event.setStart(start);

		DateTime endDateTime = new DateTime(str[2]);
		EventDateTime end = new EventDateTime().setDateTime(endDateTime);
		event.setEnd(end);

		event.setLocation(str[3]);
		event.setSummary(str[0]);

		service.events().update("primary", event.getId(), event).execute();
		createCalendarEvents();

		return true;
	}

	// Calendar Event Delete Method
	public Boolean deleteEventToCalendar(String id) throws Exception {
		com.google.api.services.calendar.Calendar service = getCalendarService(UserUtils.getInstance().getCredential());
		String calendarId = "primary";
		service.events().delete(calendarId, id).execute();

		createCalendarEvents();

		return true;
	}
}