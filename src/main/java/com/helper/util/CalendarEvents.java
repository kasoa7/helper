package com.helper.util;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class CalendarEvents {
	private static final Logger LOG = LoggerFactory.getLogger(CalendarEvents.class);
	private static CalendarEvents instance = null;
	private String remakeEvents = null;
	private Events events;
	private Boolean calendar_flag = true;

	private CalendarEvents() {}

	public static synchronized CalendarEvents getInstance() {
		if (instance == null) {
			instance = new CalendarEvents();
		}
		return instance;
	}

	public Events getCalendarEvents() {
		return events;
	}

	public void setCalendarEvents(Events events) {
		this.events = events;
	}

	public String getRemakeCalendarEvents() throws Exception {
		return remakeEvents;
	}

	public void setRemakeCalendarEvents(String remakeEvents) throws Exception {
		this.remakeEvents = remakeEvents;
	}

	public Boolean getCalendar_flag() {
		return calendar_flag;
	}

	public void setCalendar_flag(Boolean calendar_flag) {
		this.calendar_flag = calendar_flag;
	}

}
