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

@SuppressWarnings("serial")
public class RefreshServlet extends HttpServlet {
	private static final Logger logger = Logger.getLogger(RefreshServlet.class.getName());

	@Override
	public void init() throws ServletException {
		logger.log(Level.CONFIG, "init call completed for " + this.getServletConfig().getServletName());
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession httpSession = req.getSession(false);
		if (null == httpSession || !SessionTracker.INSTANCE.isTracked(httpSession.getId())) {
			//no session present - this request is invalid
			resp.sendError(401,"No Session Exists ! ");
		} else {
			logger.log(Level.INFO, "Session referesh request received for " + httpSession.getId());
			resp.setStatus(HttpServletResponse.SC_OK); 
		}
	}

}