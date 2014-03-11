package com.robin.app.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * Utility methods for Net operations
 * 
 * @author robin
 * 
 */
public class NetUtil {

	private static final Logger logger = Logger.getLogger(NetUtil.class.getName());

	public static final String ACCEPT_TEXT_HTML = "text/html";

	/**
	 * Method will execute a GET call on the specified URL returning the content
	 * as a {@link String}
	 * 
	 * @param getUrl
	 * @param acceptType
	 * @return {@link String}
	 */
	public static String getContentAtURL(String getUrl, String acceptType) {

		String response = null;
		try {
			URL url = new URL(getUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Accept", ACCEPT_TEXT_HTML);
			if (connection.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + connection.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));

			StringBuilder builder = new StringBuilder();
			while ((response = br.readLine()) != null) {
				builder.append(response);
			}
			response = builder.toString();
			logger.log(Level.CONFIG, "Output from Server .... ");
			logger.log(Level.CONFIG, response);

			connection.disconnect();
		} catch (IOException e) {
			throw new RuntimeException("Failed - IO Error", e);
		}
		return response;
	}

	/**
	 * Method used to build absolute URL from a {@link HttpServletRequest}. <br/>
	 * Borrowed from stackoverflow
	 * 
	 * @param request
	 * @return
	 */
	public static String getCompleteUrl(HttpServletRequest request) {

		String scheme = request.getScheme(); // http
		String serverName = request.getServerName(); // hostname.com
		int serverPort = request.getServerPort(); // 80
		String contextPath = request.getContextPath(); // /gmail
		String servletPath = request.getServletPath(); // /servlet/MyServlet
		String pathInfo = request.getPathInfo(); // /a/b;c=123
		String queryString = request.getQueryString(); // d=789

		// Reconstruct original requesting URL
		StringBuilder url = new StringBuilder();
		url.append(scheme).append("://").append(serverName);

		if ((serverPort != 80) && (serverPort != 443)) {
			url.append(":").append(serverPort);
		}

		url.append(contextPath).append(servletPath);

		if (pathInfo != null) {
			url.append(pathInfo);
		}
		if (queryString != null) {
			url.append("?").append(queryString);
		}
		return url.toString();
	}

	/**
	 * Builds the URL upto the Application Name
	 * @param request
	 * @return {@link String}
	 */
	public static String getAppUrl(HttpServletRequest request) {
		String scheme = request.getScheme(); // http
		String serverName = request.getServerName(); // hostname.com
		int serverPort = request.getServerPort(); // 80
		String contextPath = request.getContextPath(); // gmail

		// Reconstruct original requesting URL
		StringBuilder url = new StringBuilder();
		url.append(scheme).append("://").append(serverName);

		if ((serverPort != 80) && (serverPort != 443)) {
			url.append(":").append(serverPort);
		}

		url.append(contextPath);
		return url.toString();
	}

}
