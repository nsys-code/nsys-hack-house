/* Copyright 2016 Nsys #HackTheHouse - Tomas Hrdlicka <tomas@hrdlicka.co.uk>. All rights reserved.
 */

package org.nsys.iot.hackhouse.adapter.virtualsensors;

import java.util.Properties;

import org.nsys.util.ConfigurationManager;

/**
 * Nsys #HackTheHouse Virtual Sensors Adapter Configuration
 *
 * @author Tomas Hrdlicka <tomas@hrdlicka.co.uk>
 * @see <a href="http://nsys.org">Nsys</a>
 */
public class AdapterConfig {

	public static final String CONFIG_NAME = "nsys-hack-house-virtual-sensors.cfg";
	public static final String CONFIG_NAME_INTERNAL = "nsys-hack-house-virtual-sensors.cfg.internal";
	public static final String VERSION = "nsys.hackhouse.adapter.virtualsensors.version";

	public static void loadConfig() {
		ConfigurationManager config = ConfigurationManager.getInstance();

		Properties props = config.loadConfig(String.format("/%s", CONFIG_NAME), AdapterConfig.class);

		if (props != null) {
			config.merge(props);
		}

		props = config.loadConfig(String.format("/%s", CONFIG_NAME_INTERNAL), AdapterConfig.class);

		if (props != null) {
			config.merge(props, true);
		}
	}

	public static String getVersion() {
		String version = ConfigurationManager.getInstance().getProperty(VERSION);
		return ConfigurationManager.getVersion(version);
	}

	public static String getBuildNumber() {
		String version = ConfigurationManager.getInstance().getProperty(VERSION);
		return ConfigurationManager.getBuildNumber(version);
	}
}