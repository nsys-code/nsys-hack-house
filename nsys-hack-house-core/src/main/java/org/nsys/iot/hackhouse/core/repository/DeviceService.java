/* Copyright 2016 Nsys #HackTheHouse - Tomas Hrdlicka <tomas@hrdlicka.co.uk>. All rights reserved.
 */

package org.nsys.iot.hackhouse.core.repository;

import org.nsys.daemon.repository.AbstractDataRepositoryService;
import org.nsys.daemon.user.UserService;

import org.nsys.iot.hackhouse.core.entity.DeviceEntity;
import org.nsys.iot.hackhouse.core.model.Device;

/**
 * Nsys #HackTheHouse Device Service
 *
 * @author Tomas Hrdlicka <tomas@hrdlicka.co.uk>
 * @see <a href="http://nsys.org">Nsys</a>
 */
public class DeviceService extends AbstractDataRepositoryService<Device, DeviceEntity, Long, DeviceRepository> {

	public DeviceService() {
		super(new DeviceRepository());
	}

	public Device getByName(final String deviceName) {
		return entityToModel(getDataRepositoryManager().getByName(deviceName));
	}

	@Override
	public DeviceEntity modelToEntity(final Device model) {
		return deviceToEntity(model);
	}

	@Override
	public DeviceEntity modelToEntity(final Device model, final boolean skipId) {
		return deviceToEntity(model, skipId);
	}

	@Override
	public Device entityToModel(final DeviceEntity entity) {
		return entityToDevice(entity);
	}

	public static DeviceEntity deviceToEntity(final Device device) {
		return deviceToEntity(device, false);
	}

	public static DeviceEntity deviceToEntity(final Device device, final boolean skipId) {
		if (device == null) {
			return null;
		}

		DeviceEntity entity = new DeviceEntity();

		if (!skipId) {
			entity.setId(device.getId());
		}

		entity.setName(device.getName());
		entity.setDescription(device.getDescription());
		entity.setProtocol(device.getProtocol());
		entity.setHost(device.getHost());
		entity.setPort(device.getPort());
		entity.setApiKey(device.getApiKey());
		entity.setEnabled(device.isEnabled());
		entity.setTimezone(device.getTimezone());
		entity.setConnected(device.isConnected());
		entity.setLastHeard(device.getLastHeard());
		entity.setStatusInterval(device.getStatusInterval());
		entity.setLatitude(device.getLatitude());
		entity.setLongitude(device.getLongitude());
		entity.setSerial(device.getSerial());
		entity.setManufacturer(device.getManufacturer());
		entity.setProduct(device.getProduct());
		entity.setCategory(device.getCategory());
		entity.setOwner(UserService.userToEntity(device.getOwner()));
		return entity;
	}

	public static Device entityToDevice(final DeviceEntity entity) {
		if (entity == null) {
			return null;
		}

		Device device = new Device();
		device.setId(entity.getId());
		device.setName(entity.getName());
		device.setDescription(entity.getDescription());
		device.setProtocol(entity.getProtocol());
		device.setHost(entity.getHost());
		device.setPort(entity.getPort());
		device.setApiKey(entity.getApiKey());
		device.setEnabled(entity.isEnabled());
		device.setTimezone(entity.getTimezone());
		device.setConnected(entity.isConnected());
		device.setLastHeard(entity.getLastHeard());
		device.setStatusInterval(entity.getStatusInterval());
		device.setLatitude(entity.getLatitude());
		device.setLongitude(entity.getLongitude());
		device.setSerial(entity.getSerial());
		device.setManufacturer(entity.getManufacturer());
		device.setProduct(entity.getProduct());
		device.setCategory(entity.getCategory());
		device.setOwner(UserService.entityToUser(entity.getOwner()));
		return device;
	}
}