/* Copyright 2016 Nsys #HackTheHouse - Tomas Hrdlicka <tomas@hrdlicka.co.uk>. All rights reserved.
 */

package org.nsys.iot.hackhouse.portal.webapp.api;

import javax.servlet.http.HttpServletRequest;

import org.nsys.daemon.model.Status;

/**
 * Nsys HackHouse API Service interface
 *
 * @author Tomas Hrdlicka <tomas@hrdlicka.co.uk>
 * @see <a href="http://nsys.org">Nsys</a>
 */
public interface HackHouseApiService {

	Status getStatus(final HttpServletRequest request);

}