/* Copyright 2016 Nsys #HackTheHouse - Tomas Hrdlicka <tomas@hrdlicka.co.uk>. All rights reserved.
 */

package org.nsys.iot.hackhouse.portal.webapp.api;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.nsys.iot.hackhouse.portal.webapp.PortalConfig;

/**
 * Nsys #HackTheHouse API Utils
 *
 * @author Tomas Hrdlicka <tomas@hrdlicka.co.uk>
 * @see <a href="http://nsys.org">Nsys</a>
 */
public class HackHouseApiUtils {

	public static Map<String, String> getHeaders(final HttpServletRequest request) {
		Map<String, String> headers = new HashMap<String, String>();

		if (request != null) {
			headers.put("X-Nsys-HackHouse-API-ServerName", request.getServerName());
		}

		headers.put("X-Nsys-HackHouse-API-Version", PortalConfig.getVersion());
		headers.put("X-Nsys-HackHouse-API-BuildNumber", PortalConfig.getBuildNumber());
		return headers;
	}
}