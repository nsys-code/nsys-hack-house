/* Copyright 2016 Nsys #HackTheHouse - Tomas Hrdlicka <tomas@hrdlicka.co.uk>. All rights reserved.
 */

package org.nsys.iot.hackhouse.portal.webapp.api;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

/**
 * Nsys #HackTheHouse RESTful Service interface
 *
 * @author Tomas Hrdlicka <tomas@hrdlicka.co.uk>
 * @see <a href="http://nsys.org">Nsys</a>
 */
public interface HackHouseRestService {

	Response getStatus(final HttpServletRequest request);

}