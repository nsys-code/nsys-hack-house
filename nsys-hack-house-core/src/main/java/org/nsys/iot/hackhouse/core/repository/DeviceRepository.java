/* Copyright 2016 Nsys #HackTheHouse - Tomas Hrdlicka <tomas@hrdlicka.co.uk>. All rights reserved.
 */

package org.nsys.iot.hackhouse.core.repository;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;

import org.nsys.daemon.repository.AbstractDataRepositoryManager;

import org.nsys.iot.hackhouse.core.CoreConfig;
import org.nsys.iot.hackhouse.core.entity.DeviceEntity;

/**
 * Nsys #HackTheHouse Device Repository
 *
 * @author Tomas Hrdlicka <tomas@hrdlicka.co.uk>
 * @see <a href="http://nsys.org">Nsys</a>
 */
public class DeviceRepository extends AbstractDataRepositoryManager<DeviceEntity, Long> {

	public DeviceRepository() {
		super(DeviceEntity.class, CoreConfig.getDataRepositoryConfig());
	}

	public DeviceEntity getByName(final String deviceName) {
		try {
			String hql = "SELECT d FROM Device d WHERE d.name = :name";
			TypedQuery<DeviceEntity> query = getEntityManager().createQuery(hql, DeviceEntity.class);
			query.setParameter("name", deviceName);

	        return query.getSingleResult();

		} catch (final NoResultException | NonUniqueResultException ex) {
			return null;
		}
	}
}