/* Copyright 2016 Nsys #HackTheHouse - Tomas Hrdlicka <tomas@hrdlicka.co.uk>. All rights reserved.
 */

package org.nsys.iot.hackhouse.portal.webapp.api;

import javax.jws.WebMethod;
import javax.jws.WebService;

import org.nsys.daemon.model.Status;

/**
 * Nsys #HackTheHouse SOAP (Simple Object Access protocol) Service Interface
 *
 * @author Tomas Hrdlicka <tomas@hrdlicka.co.uk>
 * @see <a href="http://nsys.org">Nsys</a>
 */
@WebService
public interface HackHouseSoapService {

	@WebMethod
	Status getStatus() throws HackHouseException;

}