/* Copyright 2016 Nsys #HackTheHouse - Tomas Hrdlicka <tomas@hrdlicka.co.uk>. All rights reserved.
 */

package org.nsys.iot.hackhouse.core.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.nsys.daemon.entity.BaseEntity;

/**
 * Nsys #HackTheHouse Device Sensor Entity
 *
 * @author Tomas Hrdlicka <tomas@hrdlicka.co.uk>
 * @see <a href="http://nsys.org">Nsys</a>
 */
@Entity(name = "DeviceSensor")
@Table(name = "NSYS_HACKHOUSE_DEVICE_SENSOR")
public class DeviceSensorEntity extends BaseEntity {

    @OneToOne
    @JoinColumn(name="DEVICE_ID")
    private DeviceEntity device;

    @OneToOne
    @JoinColumn(name="SENSOR_ID")
    private SensorEntity sensor;

	public DeviceEntity getDevice() {
		return device;
	}

	public void setDevice(DeviceEntity device) {
		this.device = device;
	}

	public SensorEntity getSensor() {
		return sensor;
	}

	public void setSensor(SensorEntity sensor) {
		this.sensor = sensor;
	}
}