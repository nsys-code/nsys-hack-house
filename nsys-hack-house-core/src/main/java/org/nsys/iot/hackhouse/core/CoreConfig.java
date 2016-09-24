/* Copyright 2016 Nsys #HackTheHouse - Tomas Hrdlicka <tomas@hrdlicka.co.uk>. All rights reserved.
 */

package org.nsys.iot.hackhouse.core;

import java.util.Properties;

import org.nsys.util.ConfigurationManager;
import org.nsys.system.ServiceProvider;
import org.nsys.daemon.host.DatabaseManager;
import org.nsys.daemon.repository.DataRepositoryConfig;

/**
 * Nsys #HackTheHouse Core Configuration
 *
 * @author Tomas Hrdlicka <tomas@hrdlicka.co.uk>
 * @see <a href="http://nsys.org">Nsys</a>
 */
public class CoreConfig {

	public static final String CONFIG_NAME = "nsys-hack-house-core.cfg";
	public static final String CONFIG_NAME_INTERNAL = "nsys-hack-house-core.cfg.internal";
	public static final String VERSION = "nsys.hackhouse.core.version";

	public static final String REPOSITORY_NAME = "nsys-hack-house";

	public static void loadConfig() {
		ConfigurationManager config = ConfigurationManager.getInstance();

		Properties props = config.loadConfig(String.format("/%s", CONFIG_NAME), CoreConfig.class);

		if (props != null) {
			config.merge(props);
		}

		props = config.loadConfig(String.format("/%s", CONFIG_NAME_INTERNAL), CoreConfig.class);

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

	public static DataRepositoryConfig getDataRepositoryConfig() {
		DatabaseManager dbManager = ServiceProvider.getInstance().getServiceHost(DatabaseManager.class);
		return dbManager.getRepositoryConfig(REPOSITORY_NAME);
	}
}