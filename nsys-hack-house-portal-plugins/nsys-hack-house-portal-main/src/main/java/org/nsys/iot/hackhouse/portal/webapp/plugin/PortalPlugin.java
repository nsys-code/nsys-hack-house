/* Copyright 2016 Nsys #HackTheHouse - Tomas Hrdlicka <tomas@hrdlicka.co.uk>. All rights reserved.
 */

package org.nsys.iot.hackhouse.portal.webapp.plugin;

import org.nsys.event.Event;
import org.nsys.plugin.PluginContext;
import org.nsys.daemon.event.SystemStartedEvent;
import org.nsys.daemon.plugin.AbstractManagementAgentPlugin;
import org.nsys.portal.event.PortalStartedEvent;

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
	}

	@Override
	public void unload(PluginContext context) {
		getLog().debugFormat("Stopped plugin %s", getName());
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
}