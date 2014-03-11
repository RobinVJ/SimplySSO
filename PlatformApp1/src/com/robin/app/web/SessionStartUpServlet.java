package com.robin.app.web;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.robin.app.util.Constants;
import com.robin.app.web.to.User;

/**
 * Simple HTTPServlet, serves to provide the session setup functionality for the
 * App. This is only called when the user visits an app for the first time in
 * the Session.
 * 
 * @author robin
 * 
 */
@SuppressWarnings("serial")
public class SessionStartUpServlet extends HttpServlet {
	private static final Logger logger = Logger.getLogger(SessionStartUpServlet.class.getName());

	private AuthSessionHelper authSessionHelper;

	/**
	 * Simple Init method
	 */
	@Override
	public void init() throws ServletException {
		authSessionHelper = new AuthSessionHelper(this.getServletContext());
		logger.log(Level.CONFIG, "init call completed for " + this.getServletConfig().getServletName());
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession httpSession = request.getSession(false);
		if (null != httpSession) {
			String sessionId = httpSession.getId();
			logger.log(Level.INFO, "An existing session found for user - but prefering to destroy the same !! - "
					+ sessionId);
			httpSession.invalidate();
		}
		initaliseUserSession(request);

		RequestDispatcher dispatcher = request.getRequestDispatcher(Constants.SESSION_LANDING_VIEW);
		dispatcher.forward(request, response);
	}

	/**
	 * Method is used to setup local session for the application
	 * 
	 * @param request
	 */
	private void initaliseUserSession(HttpServletRequest request) {
		String sessionId = authSessionHelper.getAuthSessionId(request);
		User user = authSessionHelper.getAuthSessionInfo(sessionId);

		HttpSession httpSession = request.getSession();
		logger.log(Level.CONFIG,"adding attribute to session with id " + httpSession.getId());
		httpSession.setAttribute("username", user.getName());
	}
}
