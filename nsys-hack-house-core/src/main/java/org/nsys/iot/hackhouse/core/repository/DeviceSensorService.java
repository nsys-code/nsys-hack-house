/* Copyright 2016 Nsys #HackTheHouse - Tomas Hrdlicka <tomas@hrdlicka.co.uk>. All rights reserved.
 */

package org.nsys.iot.hackhouse.core.repository;

import java.util.ArrayList;
import java.util.List;

import org.nsys.daemon.repository.AbstractDataRepositoryService;
import org.nsys.iot.hackhouse.core.entity.DeviceSensorEntity;
import org.nsys.iot.hackhouse.core.model.DeviceSensor;

/**
 * Nsys #HackTheHouse Device Sensor Service
 *
 * @author Tomas Hrdlicka <tomas@hrdlicka.co.uk>
 * @see <a href="http://nsys.org">Nsys</a>
 */
public class DeviceSensorService extends 
	AbstractDataRepositoryService<DeviceSensor, DeviceSensorEntity, Long, DeviceSensorRepository> {

	public DeviceSensorService() {
		super(new DeviceSensorRepository());
	}

	public List<DeviceSensor> getByDeviceId(final long deviceId) {
		List<DeviceSensor> deviceSensors = new ArrayList<DeviceSensor>();
		List<DeviceSensorEntity> entities = getDataRepositoryManager().getByDeviceId(deviceId);

		for (DeviceSensorEntity entity : entities) {
			deviceSensors.add(entityToModel(entity));
		}

		return deviceSensors;
	}

	public DeviceSensor getByDeviceIdAndSensorId(final long deviceId, final long sensorId) {
		return entityToModel(getDataRepositoryManager().getByDeviceIdAndSensorId(deviceId, sensorId));
	}

	public void removeByDeviceId(final long deviceId) {
		try {
			beginTransaction();
			getDataRepositoryManager().removeByDeviceId(deviceId);
			commitTransaction();

		} catch (final Exception ex) {
			rollbackTransaction();
		}
	}

	@Override
	public DeviceSensorEntity modelToEntity(final DeviceSensor model) {
		return deviceSensorToEntity(model);
	}

	@Override
	public DeviceSensorEntity modelToEntity(final DeviceSensor model, final boolean skipId) {
		return deviceSensorToEntity(model, skipId);
	}

	@Override
	public DeviceSensor entityToModel(final DeviceSensorEntity entity) {
		return entityToDeviceSensor(entity);
	}

	public static DeviceSensorEntity deviceSensorToEntity(final DeviceSensor deviceSensor) {
		return deviceSensorToEntity(deviceSensor, false);
	}

	public static DeviceSensorEntity deviceSensorToEntity(final DeviceSensor deviceSensor, final boolean skipId) {
		if (deviceSensor == null) {
			return null;
		}

		DeviceSensorEntity entity = new DeviceSensorEntity();

		if (!skipId) {
			entity.setId(deviceSensor.getId());
		}

		entity.setDevice(DeviceService.deviceToEntity(deviceSensor.getDevice()));
		entity.setSensor(SensorService.sensorToEntity(deviceSensor.getSensor()));
		return entity;
	}

	public static DeviceSensor entityToDeviceSensor(final DeviceSensorEntity entity) {
		if (entity == null) {
			return null;
		}

		DeviceSensor deviceSensor = new DeviceSensor();
		deviceSensor.setId(entity.getId());
		deviceSensor.setDevice(DeviceService.entityToDevice(entity.getDevice()));
		deviceSensor.setSensor(SensorService.entityToSensor(entity.getSensor()));
		return deviceSensor;
	}
}