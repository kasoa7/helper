package com.helper.domain;

import java.sql.Date;

public class LaunchVO {
	private String title;
	private int title2;
	private Date start;

	public int getTitle2() {
		return title2;
	}

	public void setTitle2(int title2) {
		this.title2 = title2;
	}

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
		return "LaunchVO [title=" + title + ", start=" + start + "]";
	}

}
