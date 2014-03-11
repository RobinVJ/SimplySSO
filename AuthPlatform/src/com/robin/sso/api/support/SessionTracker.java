package com.robin.sso.api.support;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Enum Based Singleton - keeps track of authenticated and active user sessions
 * @author robin
 *
 */
public enum SessionTracker {
	INSTANCE;
	
	public enum DataKeys {
		USER_NAME
	}
	
	private static final Logger logger = Logger.getLogger(SessionTracker.class.getName());

	private final Set<String> sessionsTracked = new HashSet<>();

	private Map<String, Map<SessionTracker.DataKeys, Object>> data = new HashMap<>();

	public void addToTrackList(String sessionId, Map<SessionTracker.DataKeys, Object> userData) {
		logger.log(Level.CONFIG, "Adding entry for sessionId " + sessionId);
		synchronized (data) {
			data.put(sessionId, userData);
		}
		synchronized (sessionsTracked) {
			sessionsTracked.add(sessionId);
		}
	}

	public boolean isTracked(String sessionId) {
		boolean contains = false;
		synchronized (sessionsTracked) {
			contains = sessionsTracked.contains(sessionId);
		}
		return contains;
	}

	public void addToData(String sessionId, SessionTracker.DataKeys key, Object value) {
		Map<SessionTracker.DataKeys, Object> userData = null;
		synchronized (data) {
			userData = data.get(sessionId);
			if (userData == null) {
				userData = new HashMap<>();
				data.put(sessionId, userData);
			}
		}
		synchronized (userData) {
			userData.put(key, value);
		}
	}

	public Map<SessionTracker.DataKeys, Object> getDataForId(String sessionId) {
		Map<SessionTracker.DataKeys, Object> userData = data.get(sessionId);
		return null == userData ? null : new HashMap<SessionTracker.DataKeys, Object>(userData);
	}

	public synchronized void removeFromTrackList(String sessionId) {
		logger.log(Level.CONFIG, "Removing entry for sessionId " + sessionId);
		synchronized (sessionsTracked) {
			sessionsTracked.remove(sessionId);
		}
		synchronized (data) {
			data.remove(sessionId);
		}

	}
}
