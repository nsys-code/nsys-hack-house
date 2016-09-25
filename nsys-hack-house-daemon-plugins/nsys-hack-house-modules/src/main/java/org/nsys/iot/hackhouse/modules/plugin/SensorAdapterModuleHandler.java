/* Copyright 2016 Nsys #HackTheHouse - Tomas Hrdlicka <tomas@hrdlicka.co.uk>. All rights reserved.
 */

package org.nsys.iot.hackhouse.modules.plugin;

import org.nsys.plugin.AbstractModuleEntityHandler;
import org.nsys.plugin.Module;
import org.nsys.plugin.ModuleEntity;
import org.nsys.plugin.PluginHandler;

/**
 * Nsys #HackTheHouse Sensor Adapter Module Handler
 *
 * @author Tomas Hrdlicka <tomas@hrdlicka.co.uk>
 * @see <a href="http://nsys.org">Nsys</a>
 */
public class SensorAdapterModuleHandler extends AbstractModuleEntityHandler<SensorAdapterModule> {
	public static final String ENABLED = "enabled";
	public static final String OVERRIDE = "override";

	@Override
	public String getModuleName() {
		return SensorAdapterModule.TYPE;
	}

	@Override
	public Module<?> createModuleInstance() {
		return new SensorAdapterModule();
	}

	@Override
	public void handleEntity(Module<?> module, ModuleEntity entity, PluginHandler pluginHandler) {
		if (!(module instanceof SensorAdapterModule)) {
			return;
		}

		SensorAdapterModule m = (SensorAdapterModule) module;
		m.setEnabled(Boolean.parseBoolean(entity.getAttribute(ENABLED)));
		m.setOverride(Boolean.parseBoolean(entity.getAttribute(OVERRIDE)));
		m.setPluginKey(pluginHandler.getPluginKey());
	}
}