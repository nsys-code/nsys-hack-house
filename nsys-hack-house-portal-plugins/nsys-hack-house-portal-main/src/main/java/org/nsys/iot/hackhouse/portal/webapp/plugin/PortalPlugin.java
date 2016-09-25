/* Copyright 2016 Nsys #HackTheHouse - Tomas Hrdlicka <tomas@hrdlicka.co.uk>. All rights reserved.
 */

package org.nsys.iot.hackhouse.portal.webapp.plugin;

import org.nsys.event.Event;
import org.nsys.plugin.PluginContext;
import org.nsys.daemon.event.SystemStartedEvent;
import org.nsys.daemon.host.DatabaseManager;
import org.nsys.daemon.plugin.AbstractManagementAgentPlugin;
import org.nsys.daemon.repository.DataRepositoryConfig;
import org.nsys.daemon.utils.NsysDaemonUtils;
import org.nsys.portal.event.PortalStartedEvent;
import org.nsys.system.ServiceProvider;

import org.nsys.iot.hackhouse.core.CoreConfig;
import org.nsys.iot.hackhouse.core.repository.DeviceService;
import org.nsys.iot.hackhouse.core.repository.SensorService;
import org.nsys.iot.hackhouse.core.sensor.SensorManager;
import org.nsys.iot.hackhouse.core.sensor.SensorManagerImpl;
import org.nsys.iot.hackhouse.portal.webapp.PortalConfig;

/**
 * Nsys #HackTheHouse Portal Main Plugin
 *
 * @author Tomas Hrdlicka <tomas@hrdlicka.co.uk>
 * @see <a href="http://nsys.org">Nsys</a>
 */
public class PortalPlugin extends AbstractManagementAgentPlugin {

	@Override
	public void load(PluginContext context) {
		getLog().debugFormat("Starting plugin %s", getName());
		PortalConfig.loadConfig();

		DatabaseManager dbManager = ServiceProvider.getInstance().getServiceHost(DatabaseManager.class);
		DataRepositoryConfig config = dbManager.newRepositoryConfig(CoreConfig.REPOSITORY_NAME, getClass().getClassLoader());
		dbManager.addRepositoryConfig(CoreConfig.REPOSITORY_NAME, config);

		addComponents();
	}

	@Override
	public void unload(PluginContext context) {
		getLog().debugFormat("Stopped plugin %s", getName());

		DatabaseManager dbManager = ServiceProvider.getInstance().getServiceHost(DatabaseManager.class);
		dbManager.removeRepositoryConfig(CoreConfig.REPOSITORY_NAME);
	}

	@Override
	public void handleEvent(PluginContext context, Event event) {
		if (event != null) {
			if (event instanceof PortalStartedEvent) {
				getLog().info("Nsys Portal has been started successfully!");
			}

			else if (event instanceof SystemStartedEvent) {
				getLog().info("The system is up and running!");
			}
		}
	}

	protected void addComponents() {
		DeviceService deviceService = new DeviceService();
		SensorService sensorService = new SensorService();
		SensorManager sensorManager = new SensorManagerImpl();

		NsysDaemonUtils.addGlobalComponent(DeviceService.class, deviceService);
		NsysDaemonUtils.addGlobalComponent(SensorService.class, sensorService);
		NsysDaemonUtils.addGlobalComponent(SensorManager.class, sensorManager);
	}
}