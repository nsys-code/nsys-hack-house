/* Copyright 2016 Nsys #HackTheHouse - Tomas Hrdlicka <tomas@hrdlicka.co.uk>. All rights reserved.
 */

package org.nsys.iot.hackhouse.modules.plugin;

import org.nsys.iot.hackhouse.core.adapter.SensorAdapter;
import org.nsys.plugin.Module;

/**
 * Nsys #HackTheHouse Sensor Adapter Module
 *
 * @author Tomas Hrdlicka <tomas@hrdlicka.co.uk>
 * @see <a href="http://nsys.org">Nsys</a>
 */
public class SensorAdapterModule extends Module<SensorAdapter> {
	public static final String TYPE = "sensor-adapter";

	private boolean enabled;
	private String pluginKey;
	private boolean override;

	public SensorAdapterModule() {
		setType(TYPE);
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getPluginKey() {
		return pluginKey;
	}

	public void setPluginKey(String pluginKey) {
		this.pluginKey = pluginKey;
	}

	public boolean isOverride() {
		return override;
	}

	public void setOverride(boolean override) {
		this.override = override;
	}
}