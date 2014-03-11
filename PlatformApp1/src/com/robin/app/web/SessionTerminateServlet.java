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

/**
 * Simple HTTPServlet, serves to provide the session terminate functionality for
 * the event of a timeout. This is only called when the user visits an app for
 * the first time in the Session.
 * 
 * @author robin
 * 
 */
@SuppressWarnings("serial")
public class SessionTerminateServlet extends HttpServlet {
	private static final Logger logger = Logger.getLogger(SessionTerminateServlet.class.getName());

	/**
	 * Simple Init method
	 */
	@Override
	public void init() throws ServletException {
		logger.log(Level.CONFIG, "init call completed for " + this.getServletConfig().getServletName());
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession httpSession = request.getSession(false);
		if (null != httpSession) {
			String sessionId = httpSession.getId();
			logger.log(Level.INFO, "Getting rid of existing Http Session - id  " + sessionId);
			httpSession.invalidate();
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher(Constants.APP_LOGOUT_VIEW);
		
		dispatcher.forward(request, response);
	}

}
