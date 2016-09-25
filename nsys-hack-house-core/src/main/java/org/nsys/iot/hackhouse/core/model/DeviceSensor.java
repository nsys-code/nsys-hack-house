/* Copyright 2016 Nsys #HackTheHouse - Tomas Hrdlicka <tomas@hrdlicka.co.uk>. All rights reserved.
 */

package org.nsys.iot.hackhouse.core.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Nsys #HackTheHouse Device Sensor
 *
 * @author Tomas Hrdlicka <tomas@hrdlicka.co.uk>
 * @see <a href="http://nsys.org">Nsys</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DeviceSensor {
	private long id;
	private Device device;
	private Sensor sensor;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public Sensor getSensor() {
		return sensor;
	}

	public void setSensor(Sensor sensor) {
		this.sensor = sensor;
	}

	@Override
	public String toString() {
		return String.format("DeviceSensor [id=%d,device=%s,sensor=%s]", getId(), getDevice(), getSensor());
	}
}