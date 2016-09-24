/* Copyright 2016 Nsys #HackTheHouse - Tomas Hrdlicka <tomas@hrdlicka.co.uk>. All rights reserved.
 */

package org.nsys.iot.hackhouse.portal.webapp.api;

import javax.jws.WebService;

import org.springframework.stereotype.Service;

import org.nsys.daemon.model.Status;
import org.nsys.daemon.server.AbstractWebService;

/**
 * Nsys #HackTheHouse SOAP (Simple Object Access protocol) Service
 *
 * @author Tomas Hrdlicka <tomas@hrdlicka.co.uk>
 * @see <a href="http://nsys.org">Nsys</a>
 */
@WebService(endpointInterface = "org.nsys.iot.hackhouse.portal.webapp.api.HackHouseSoapService", serviceName = "NsysHackHouseService")
@Service("hackhouseSoapService")
public class HackHouseSoapServiceImpl extends AbstractWebService implements HackHouseSoapService {
    protected HackHouseApiService api;

	public HackHouseApiService getApi() {
		if (api == null) {
			api = HackHouseApiServiceImpl.getInstance();
		}

		return api;
	}

	@Override
	public Status getStatus() throws HackHouseException {
		Status status = null;

		try {
			status = getApi().getStatus(getRequest());

		} catch (final Exception ex) {
			String errorMsg = "Unable to get Nsys #HackTheHouse status!";
			getLog().error(String.format("%s Error: %s", errorMsg, ex.getMessage()), ex);
			throw new HackHouseException(errorMsg, ex.getMessage());
		}

		return status;
	}
}