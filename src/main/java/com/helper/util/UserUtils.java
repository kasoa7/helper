package com.helper.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.api.client.auth.oauth2.Credential;

public class UserUtils {
	private static final Logger LOG = LoggerFactory.getLogger(UserUtils.class);
	private static UserUtils instance = null;
	private String emailAddress = null;
	private Credential credential = null;

	private UserUtils() {}

	public static synchronized UserUtils getInstance() {
		if (instance == null) {
			instance = new UserUtils();
		}
		return instance;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public Credential getCredential() {
		return credential;
	}

	public void setCredential(Credential credential) {
		this.credential = credential;
	}
}
