/* Copyright 2016 Nsys #HackTheHouse - Tomas Hrdlicka <tomas@hrdlicka.co.uk>. All rights reserved.
 */

package org.nsys.iot.hackhouse.core.repository;

import org.nsys.daemon.repository.AbstractDataRepositoryService;
import org.nsys.daemon.user.UserService;

import org.nsys.iot.hackhouse.core.entity.SensorEntity;
import org.nsys.iot.hackhouse.core.model.Sensor;

/**
 * Nsys #HackTheHouse Sensor Service
 *
 * @author Tomas Hrdlicka <tomas@hrdlicka.co.uk>
 * @see <a href="http://nsys.org">Nsys</a>
 */
public class SensorService extends AbstractDataRepositoryService<Sensor, SensorEntity, Long, SensorRepository> {

	public SensorService() {
		super(new SensorRepository());
	}

	public Sensor getByName(final String sensorName) {
		return entityToModel(getDataRepositoryManager().getByName(sensorName));
	}

	@Override
	public SensorEntity modelToEntity(final Sensor model) {
		return sensorToEntity(model);
	}

	@Override
	public SensorEntity modelToEntity(final Sensor model, final boolean skipId) {
		return sensorToEntity(model, skipId);
	}

	@Override
	public Sensor entityToModel(final SensorEntity entity) {
		return entityToSensor(entity);
	}

	public static SensorEntity sensorToEntity(final Sensor sensor) {
		return sensorToEntity(sensor, false);
	}

	public static SensorEntity sensorToEntity(final Sensor sensor, final boolean skipId) {
		if (sensor == null) {
			return null;
		}

		SensorEntity entity = new SensorEntity();

		if (!skipId) {
			entity.setId(sensor.getId());
		}

		entity.setName(sensor.getName());
		entity.setLabel(sensor.getLabel());
		entity.setDescription(sensor.getDescription());
		entity.setEnabled(sensor.isEnabled());
		entity.setAdapterPluginKey(sensor.getAdapterPluginKey());
		entity.setAdapterClassName(sensor.getAdapterClassName());
		entity.setOwner(UserService.userToEntity(sensor.getOwner()));
		return entity;
	}

	public static Sensor entityToSensor(final SensorEntity entity) {
		if (entity == null) {
			return null;
		}

		Sensor sensor = new Sensor();
		sensor.setId(entity.getId());
		sensor.setName(entity.getName());
		sensor.setLabel(entity.getLabel());
		sensor.setDescription(entity.getDescription());
		sensor.setEnabled(entity.isEnabled());
		sensor.setAdapterPluginKey(entity.getAdapterPluginKey());
		sensor.setAdapterClassName(entity.getAdapterClassName());
		sensor.setOwner(UserService.entityToUser(entity.getOwner()));
		return sensor;
	}
}