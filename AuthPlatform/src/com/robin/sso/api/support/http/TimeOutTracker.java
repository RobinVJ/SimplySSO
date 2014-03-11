package com.robin.sso.api.support.http;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.robin.sso.api.support.SessionTracker;

/**
 * Class implements {@link HttpSessionListener} to keep track of created and
 * destroyed sessions.
 * 
 * @author robin
 * 
 */
public class TimeOutTracker implements HttpSessionListener {
	private static final Logger logger = Logger.getLogger(TimeOutTracker.class.getName());

	@Override
	public void sessionCreated(HttpSessionEvent event) {
		logger.log(Level.INFO, "Session created - " + event.getSession().getId());
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		String sessionId = event.getSession().getId();
		logger.log(Level.INFO, "Destroying the user created session - " + sessionId);
		SessionTracker.INSTANCE.removeFromTrackList(sessionId);
	}

}
