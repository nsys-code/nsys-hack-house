/* Copyright 2016 Nsys #HackTheHouse - Tomas Hrdlicka <tomas@hrdlicka.co.uk>. All rights reserved.
 */

package org.nsys.iot.hackhouse.portal.webapp.api;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.ws.WebFault;

import org.nsys.daemon.model.ErrorData;

/**
 * Nsys #HackTheHouse Exception
 *
 * @author Tomas Hrdlicka <tomas@hrdlicka.co.uk>
 * @see <a href="http://nsys.org">Nsys</a>
 */
@SuppressWarnings("serial")
@WebFault(name = "NsysHackHouseException")
@XmlAccessorType(XmlAccessType.FIELD)
public class HackHouseException extends Exception {
	private final String reason;
	private final ErrorData errorData;

	public HackHouseException() {
		this(null, null, null);
	}

	public HackHouseException(final String message) {
		this(message, null, null);
	}

    public HackHouseException (final String message, final String reason) {
    	this(message, reason, null);
    }

    public HackHouseException (final String message, final ErrorData errorData) {
    	this(message, null, errorData);
    }

    public HackHouseException (final String message, final String reason, final ErrorData errorData) {
        super(message);
        this.reason = reason;
        this.errorData = errorData;
    }

	public String getReason() {
		return reason;
	}

	public ErrorData getErrorData() {
		return errorData;
	}
}