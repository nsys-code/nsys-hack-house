/* Copyright 2016 Nsys #HackTheHouse - Tomas Hrdlicka <tomas@hrdlicka.co.uk>. All rights reserved.
 */

package org.nsys.iot.hackhouse.modules.plugin;

import java.util.ArrayList;
import java.util.List;

import org.nsys.event.Event;
import org.nsys.plugin.ModuleEntityHandler;
import org.nsys.plugin.PluginContext;
import org.nsys.system.ComponentProvider;

import org.nsys.daemon.event.PluginModulesLoadedEvent;
import org.nsys.daemon.plugin.AbstractManagementAgentPlugin;

import org.nsys.iot.hackhouse.core.model.Sensor;
import org.nsys.iot.hackhouse.core.sensor.SensorManager;

/**
 * Nsys #HackTheHouse Plugin Modules
 *
 * @author Tomas Hrdlicka <tomas@hrdlicka.co.uk>
 * @see <a href="http://nsys.org">Nsys</a>
 */
public class HackHouseModulesPlugin extends AbstractManagementAgentPlugin {
	private SensorManager sensorManager;

	public SensorManager getSensorManager() {
		if (sensorManager == null) {
			sensorManager = ComponentProvider.getInstance().getComponent(SensorManager.class);
		}

		return sensorManager;
	}

	@Override
	public void load(PluginContext context) {
		getLog().debugFormat("Starting plugin %s", getName());
	}

	@Override
	public void unload(PluginContext context) {
		getLog().debugFormat("Stopped plugin %s", getName());
	}

	@Override
	public void handleEvent(PluginContext context, Event event) {
		if (event != null) {
			if (event instanceof PluginModulesLoadedEvent) {
				PluginModulesLoadedEvent pmlEvent = (PluginModulesLoadedEvent) event;
				List<ModuleEntityHandler> handlers = pmlEvent.getModuleEntityHandler();

				for (ModuleEntityHandler handler : handlers) {
					if (handler instanceof SensorAdapterModuleHandler) {
						processSensorAdapterModules(context, (SensorAdapterModuleHandler) handler);
					}
				}
			}
		}
	}

	@Override
	public List<ModuleEntityHandler> getEntityHandler() {
		List<ModuleEntityHandler> handlers = new ArrayList<ModuleEntityHandler>();
		handlers.add(new SensorAdapterModuleHandler());
		return handlers;
	}

	protected void processSensorAdapterModules(PluginContext context, SensorAdapterModuleHandler handler) {
		if (handler.getModules() == null) {
			return;
		}

		for (SensorAdapterModule module : handler.getModules()) {
			Sensor sensor = new Sensor();
			sensor.setName(module.getKey());
			sensor.setLabel(module.getName());
			sensor.setDescription(module.getDescription());
			sensor.setEnabled(module.isEnabled());
			sensor.setAdapterPluginKey(module.getPluginKey());
			sensor.setAdapterClassName(module.getClassName());
			
			if (module.isOverride()) {
				getSensorManager().unregister(sensor.getName());
			}

			getSensorManager().register(sensor);;
		}
	}
}