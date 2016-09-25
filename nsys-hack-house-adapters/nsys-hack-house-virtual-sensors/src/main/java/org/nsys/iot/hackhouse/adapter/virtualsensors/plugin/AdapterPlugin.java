/* Copyright 2016 Nsys #HackTheHouse - Tomas Hrdlicka <tomas@hrdlicka.co.uk>. All rights reserved.
 */

package org.nsys.iot.hackhouse.adapter.virtualsensors.plugin;

import org.nsys.event.Event;
import org.nsys.plugin.PluginContext;
import org.nsys.daemon.plugin.AbstractManagementAgentPlugin;

/**
 * Nsys #HackTheHouse Virtual Sensors Adapter Plugin
 *
 * @author Tomas Hrdlicka <tomas@hrdlicka.co.uk>
 * @see <a href="http://nsys.org">Nsys</a>
 */
public class AdapterPlugin extends AbstractManagementAgentPlugin {

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
			// Do something...
		}
	}
}