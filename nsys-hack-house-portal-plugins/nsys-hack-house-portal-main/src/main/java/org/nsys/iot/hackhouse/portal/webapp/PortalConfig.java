/* Copyright 2016 Nsys #HackTheHouse - Tomas Hrdlicka <tomas@hrdlicka.co.uk>. All rights reserved.
 */

package org.nsys.iot.hackhouse.portal.webapp;

import java.util.Properties;

import org.nsys.util.ConfigurationManager;

/**
 * Nsys #HackTheHouse Configuration
 *
 * @author Tomas Hrdlicka <tomas@hrdlicka.co.uk>
 * @see <a href="http://nsys.org">Nsys</a>
 */
public class PortalConfig {

	public static final String CONFIG_NAME = "nsys-hack-house-portal-main.cfg";
	public static final String CONFIG_NAME_INTERNAL = "nsys-hack-house-portal-main.cfg.internal";
	public static final String VERSION = "nsys.hackhouse.portal.main.version";
	public static final String PLUGIN_KEY = "nsys.hackhouse.portal.main.pluginKey";

	public static void loadConfig() {
		ConfigurationManager config = ConfigurationManager.getInstance();

		Properties props = config.loadConfig(String.format("/%s", CONFIG_NAME), PortalConfig.class);

		if (props != null) {
			config.merge(props);
		}

		props = config.loadConfig(String.format("/%s", CONFIG_NAME_INTERNAL), PortalConfig.class);

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

	public static String getPluginKey() {
		return ConfigurationManager.getInstance().getProperty(PLUGIN_KEY);
	}
}