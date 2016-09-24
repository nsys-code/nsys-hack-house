/* Copyright 2016 Nsys #HackTheHouse - Tomas Hrdlicka <tomas@hrdlicka.co.uk>. All rights reserved.
 */

package org.nsys.iot.hackhouse.portal.webapp.api;

import javax.servlet.http.HttpServletRequest;

import org.nsys.logging.Log;
import org.nsys.daemon.model.Status;
import org.nsys.daemon.system.SystemModeType;

/**
 * Nsys #HackTheHouse API Service
 *
 * @author Tomas Hrdlicka <tomas@hrdlicka.co.uk>
 * @see <a href="http://nsys.org">Nsys</a>
 */
public class HackHouseApiServiceImpl implements HackHouseApiService {
	private final Log log = Log.getLogger(HackHouseApiServiceImpl.class);
	private static final HackHouseApiService singleton = new HackHouseApiServiceImpl();

	private HackHouseApiServiceImpl() {
	}

	public static HackHouseApiService getInstance() {
		return singleton;
	}

	protected Log getLog() {
		return log;
	}

	@Override
	public Status getStatus(final HttpServletRequest request) {
		Status status = new Status();
		status.setSystemMode(SystemModeType.MAINTENANCE);
		return status;
	}
}