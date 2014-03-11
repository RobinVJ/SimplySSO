package com.robin.app.web;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.robin.app.util.NetUtil;
import com.robin.app.web.to.User;

/**
 * Helper Class to which calls related to Auth Session are delegated.
 * (Could also be built as a static class)
 * @author robin
 *
 */
public class AuthSessionHelper {

	private ServletContext context;
	private final String PLATFORM_VERIFY_URL;
	private final String SESSION_COOKIE_NAME;

	public AuthSessionHelper(ServletContext context) {
		this.context = context;
		PLATFORM_VERIFY_URL = this.context.getInitParameter("PLATFORM_VERIFY_URL");
		SESSION_COOKIE_NAME = this.context.getInitParameter("SESSION_COOKIE");
	}

	/**
	 * Method returns the data received from the Auth Platform.
	 * 
	 * @param sessionId
	 * @return {@link User}
	 */
	public User getAuthSessionInfo(String sessionId) {
		User user = null;
		final String URL = PLATFORM_VERIFY_URL + "?sessionId=" + sessionId;
		String response = NetUtil.getContentAtURL(URL, NetUtil.ACCEPT_TEXT_HTML);

		String[] tokens = response.split(",");
		boolean recordFound = Boolean.valueOf(tokens[0]);
		if (recordFound) {
			user = new User();
			user.setName(tokens.length > 1 ? tokens[1] : "-");
		}
		return user;
	}

	/**
	 * Method retrieves the Auth sessionId for the HttpServletRequest
	 * 
	 * @param request
	 * @return
	 */
	public String getAuthSessionId(HttpServletRequest request) {
//		Cookie targetCookie = null;
		Cookie[] cookies = request.getCookies();
		if (null != cookies) {
			for (Cookie cookie : request.getCookies()) {
				// change loop later to direct return
				if (SESSION_COOKIE_NAME.equals(cookie.getName())) {
//					targetCookie = cookie;
					return cookie.getValue();
				}
			}
		}
		return null;
//		return targetCookie == null ? null : targetCookie.getValue();
	}
}
