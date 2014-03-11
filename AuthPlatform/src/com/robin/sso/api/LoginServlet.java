package com.robin.sso.api;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.robin.sso.api.support.Constants;
import com.robin.sso.api.support.SessionTracker;

/**
 * Simple HTTPServlet, serves to provide the login functionality for the Auth
 * Platform.
 * 
 * @author robin
 * 
 */
@SuppressWarnings("serial")
public class LoginServlet extends HttpServlet {
	private static final Logger logger = Logger.getLogger(LoginServlet.class.getName());

	/**
	 * Simple Init method
	 */
	@Override
	public void init() throws ServletException {
		logger.log(Level.CONFIG, "init call completed for " + this.getServletConfig().getServletName());
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		// Remove any existing sessions
		HttpSession httpSession = request.getSession(false);
		if (null != httpSession) {
			String sessionId = httpSession.getId();
			logger.log(Level.INFO, "An existing session found for user - but prefering to destroy the same !! - "
					+ sessionId);
			SessionTracker.INSTANCE.removeFromTrackList(sessionId);
			httpSession.invalidate();
		}

		httpSession = request.getSession(); // new session created
		SessionTracker.INSTANCE.addToTrackList(httpSession.getId(), createUserDataMap(request));

		// Build URLs, set attributes and send to App based login to proceed
		request.setAttribute("appStartupURL", getAppStartupUrl(request));

		// now redirect to the appropriate application
		String userName = request.getParameter("username");
		logger.log(Level.INFO, "forwarding user " + userName + " to original application");
		RequestDispatcher dispatcher = request.getRequestDispatcher(Constants.COMPLETE_LOGIN_VIEW);
		dispatcher.forward(request, response);
	}

	/**
	 * Method is used to build the start-up URL for the called app.
	 * 
	 * @param request
	 * @return {@link String}
	 * @throws UnsupportedEncodingException
	 */
	private String getAppStartupUrl(HttpServletRequest request) throws UnsupportedEncodingException {
		String requestedUrl = request.getParameter(Constants.REQUESTED_URL);
		String encodedRequestedUrl = URLEncoder.encode(requestedUrl, "UTF-8");
		return request.getParameter("LOGIN-URL") + "?url=" + encodedRequestedUrl;
	}

	/**
	 * Method is used to add Session authentication information for an user to
	 * the SessionTracker.
	 * 
	 * @param request
	 * @return {@link Map}
	 */
	private Map<SessionTracker.DataKeys, Object> createUserDataMap(HttpServletRequest request) {
		String userName = request.getParameter("username");
		Map<SessionTracker.DataKeys, Object> userData = new HashMap<>(1);
		userData.put(SessionTracker.DataKeys.USER_NAME, userName == null ? "UNKNOWN" : userName);
		return userData;
	}

}