package com.helper.domain;

import java.sql.Date;

public class TotalkcalVO {
	private String title;
	private Date start;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	@Override
	public String toString() {
		return "TotalkcalVO [title=" + title + ", start=" + start + "]";
	}
}
