/* Copyright 2016 Nsys #HackTheHouse - Tomas Hrdlicka <tomas@hrdlicka.co.uk>. All rights reserved.
 */

package org.nsys.iot.hackhouse.core.repository;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;

import org.nsys.daemon.repository.AbstractDataRepositoryManager;

import org.nsys.iot.hackhouse.core.CoreConfig;
import org.nsys.iot.hackhouse.core.entity.SensorEntity;

/**
 * Nsys #HackTheHouse Sensor Repository
 *
 * @author Tomas Hrdlicka <tomas@hrdlicka.co.uk>
 * @see <a href="http://nsys.org">Nsys</a>
 */
public class SensorRepository extends AbstractDataRepositoryManager<SensorEntity, Long> {

	public SensorRepository() {
		super(SensorEntity.class, CoreConfig.getDataRepositoryConfig());
	}

	public SensorEntity getByName(final String sensorName) {
		try {
			String hql = "SELECT s FROM Sensor s WHERE s.name = :name";
			TypedQuery<SensorEntity> query = getEntityManager().createQuery(hql, SensorEntity.class);
			query.setParameter("name", sensorName);

	        return query.getSingleResult();

		} catch (final NoResultException | NonUniqueResultException ex) {
			return null;
		}
	}
}