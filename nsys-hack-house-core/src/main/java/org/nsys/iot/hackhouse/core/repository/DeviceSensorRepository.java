/* Copyright 2016 Nsys #HackTheHouse - Tomas Hrdlicka <tomas@hrdlicka.co.uk>. All rights reserved.
 */

package org.nsys.iot.hackhouse.core.repository;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.nsys.daemon.repository.AbstractDataRepositoryManager;

import org.nsys.iot.hackhouse.core.CoreConfig;
import org.nsys.iot.hackhouse.core.entity.DeviceSensorEntity;

/**
 * Nsys #HackTheHouse Device Sensor Repository
 *
 * @author Tomas Hrdlicka <tomas@hrdlicka.co.uk>
 * @see <a href="http://nsys.org">Nsys</a>
 */
public class DeviceSensorRepository extends AbstractDataRepositoryManager<DeviceSensorEntity, Long> {

	public DeviceSensorRepository() {
		super(DeviceSensorEntity.class, CoreConfig.getDataRepositoryConfig());
	}

	public List<DeviceSensorEntity> getByDeviceId(final long deviceId) {
		String hql = "SELECT ds FROM DeviceSensor ds WHERE ds.device.id = :deviceId";
		TypedQuery<DeviceSensorEntity> query = getEntityManager().createQuery(hql, DeviceSensorEntity.class);
		query.setParameter("deviceId", deviceId);
        return query.getResultList();
	}

	public DeviceSensorEntity getByDeviceIdAndSensorId(final long deviceId, final long sensorId) {
		try {
			String hql = "SELECT ds FROM DeviceSensor ds WHERE ds.device.id = :deviceId AND ds.sensor.id = :sensorId";
			TypedQuery<DeviceSensorEntity> query = getEntityManager().createQuery(hql, DeviceSensorEntity.class);
			query.setParameter("deviceId", deviceId);
			query.setParameter("sensorId", sensorId);

	        return query.getSingleResult();

		} catch (final NoResultException | NonUniqueResultException ex) {
			return null;
		}
	}

	public void removeByDeviceId(final long deviceId) {
		try {
			if (!isBatchMode()) getEntityManager().getTransaction().begin();

			String hql = "DELETE FROM DeviceSensor ds WHERE ds.device.id = :deviceId";
			Query query = getEntityManager().createQuery(hql);
			query.setParameter("deviceId", deviceId);
			query.executeUpdate();

			if (!isBatchMode()) getEntityManager().getTransaction().commit();

		} catch (final Exception ex) {
			getLog().error("Unable to execute query!", ex);

			if (!isBatchMode()) {
				getEntityManager().getTransaction().rollback();

			} else {
				throw ex;
			}
		}
	}
}