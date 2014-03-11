package com.robin.sso.api;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.robin.sso.api.support.SessionTracker;

/***
 * 
 * @author robin
 *
 */
@SuppressWarnings("serial")
public class VerifyServlet extends HttpServlet {
	private static final Logger logger = Logger.getLogger(VerifyServlet.class.getName());

	@Override
	public void init() throws ServletException {
		logger.log(Level.CONFIG, "init call completed for " + this.getServletConfig().getServletName());
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String sessionId = request.getParameter("sessionId");
		logger.log(Level.INFO, "A verify call received for session id - " + sessionId);
		boolean valuePresent = SessionTracker.INSTANCE.isTracked(sessionId);
		response.setContentType("text/html");
		if (valuePresent) {
			Map<SessionTracker.DataKeys, Object> dataMap = SessionTracker.INSTANCE.getDataForId(sessionId);
			// returns comma separated values - highly primitive
			response.getWriter().write("true," + dataMap.get(SessionTracker.DataKeys.USER_NAME));
		} else {
			response.getWriter().write("false");
		}

		response.getWriter().flush();
	}

}
