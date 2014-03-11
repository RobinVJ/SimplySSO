package com.robin.sso.api;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.robin.sso.api.support.SessionTracker;

/**
 * Servlet used to perform logout operations.
 * 
 * @author robin
 * 
 */
@SuppressWarnings("serial")
public class LogoutServlet extends HttpServlet {
	private static final Logger logger = Logger.getLogger(LogoutServlet.class.getName());


	@Override
	public void init() throws ServletException {
		logger.log(Level.CONFIG, "init call completed for " + this.getServletConfig().getServletName());
	}

	//This will be a call from the UI
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession httpSession = request.getSession(false);
		// Removed all tracking information
		if (null != httpSession) {
			String sessionId = httpSession.getId();
			logger.log(Level.INFO, "Removing the sessionId from the list - " + sessionId);
			SessionTracker.INSTANCE.removeFromTrackList(sessionId);
			logger.log(Level.INFO, "Getting rid of existing Http Session - id  " + sessionId);
			httpSession.invalidate();
			response.setStatus(HttpServletResponse.SC_OK); 
		} 
	}

}
