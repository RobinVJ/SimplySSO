package com.robin.app.web;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@SuppressWarnings("serial")
public class SessionRefreshServlet extends HttpServlet {
	private static final Logger logger = Logger.getLogger(SessionRefreshServlet.class.getName());

	@Override
	public void init() throws ServletException {
		logger.log(Level.CONFIG, "init call completed for " + this.getServletConfig().getServletName());
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession httpSession = req.getSession(false);
		if (null == httpSession) {
			// no session present - this request is invalid
			resp.sendError(401, "No Session Exists ! ");
		} else {
			logger.log(Level.INFO, "Session referesh request received for " + httpSession.getId());
			resp.setStatus(HttpServletResponse.SC_OK);
		}
	}

}