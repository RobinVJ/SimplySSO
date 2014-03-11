package com.robin.app.web;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.robin.app.util.Constants;
import com.robin.app.util.NetUtil;
import com.robin.app.web.to.User;

/**
 * AuthorizationCheckFilter checks if the user has permissions to use a
 * particular page
 * 
 * @author robin
 * 
 */
public class AuthorizationCheckFilter implements Filter {

	private static final Logger logger = Logger.getLogger(AuthorizationCheckFilter.class.getName());

	private FilterConfig filterConfig;
	private AuthSessionHelper authSessionHelper;

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		this.filterConfig = arg0;
		authSessionHelper = new AuthSessionHelper(this.filterConfig.getServletContext());
		logger.log(Level.CONFIG, "Init successfull " + filterConfig.getFilterName());
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		// Get the authentication session ID
		String authSessionId = authSessionHelper.getAuthSessionId(request);

		// Communicate with Master App
		User user = null;
		if (null != authSessionId) {
			user = authSessionHelper.getAuthSessionInfo(authSessionId);
		}

		if (null == user) {
			// non authenticated page - send to Platform for Login
			ServletContext servletContext = filterConfig.getServletContext();
			request.setAttribute("REQUESTED_URL", NetUtil.getCompleteUrl(request));
			request.setAttribute("LOGIN_URL", NetUtil.getAppUrl(request) + Constants.SESSION_SETUP_VIEW);

			// Forward request to AuthPlatform
			ServletContext otherContext = servletContext.getContext("/"
					+ servletContext.getInitParameter("PLATFORM_APP"));
			RequestDispatcher dispatcher = otherContext.getRequestDispatcher(servletContext
					.getInitParameter("PLATFORM_LOGIN_SERVICE"));
			dispatcher.forward(servletRequest, servletResponse);
		} else {
			// Here the code can check if all local instantiation is performed.
			// If not it can handle the flow accordingly

			// If all fine, allow the requested URL to be displayed
			filterChain.doFilter(servletRequest, servletResponse);
		}

	}

	@Override
	public void destroy() {
		// nothing here
	}

}
