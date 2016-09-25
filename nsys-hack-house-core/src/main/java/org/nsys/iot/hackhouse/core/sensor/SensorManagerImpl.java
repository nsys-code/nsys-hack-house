/* Copyright 2016 Nsys #HackTheHouse - Tomas Hrdlicka <tomas@hrdlicka.co.uk>. All rights reserved.
 */

package org.nsys.iot.hackhouse.core.sensor;

import java.util.HashMap;
import java.util.Map;

import org.nsys.core.NeuralBag;
import org.nsys.logging.Log;
import org.nsys.util.StringUtils;
import org.nsys.system.ComponentProvider;
import org.nsys.plugin.PluginBundle;
import org.nsys.daemon.plugin.ManagementAgentPluginManager;

import org.nsys.iot.hackhouse.core.adapter.SensorAdapter;
import org.nsys.iot.hackhouse.core.model.Device;
import org.nsys.iot.hackhouse.core.model.Sensor;
import org.nsys.iot.hackhouse.core.repository.SensorService;

/**
 * Sensor Manager class
 *
 * @author Tomas Hrdlicka <tomas@hrdlicka.co.uk>
 * @see <a href="http://nsys.org">Nsys</a>
 */
public class SensorManagerImpl implements SensorManager {
	private final Log log = Log.getLogger(SensorManagerImpl.class);
	private SensorService sensorService;
	private final Map<String, SensorAdapter> adapters = new HashMap<String, SensorAdapter>();

	protected Log getLog() {
		return log;
	}

	public SensorService getSensorService() {
		if (sensorService == null) {
			sensorService = ComponentProvider.getInstance().getComponent(SensorService.class);
		}

		return sensorService;
	}

	@Override
	public void register(final Sensor sensor) {
		if (sensor == null) {
			getLog().warn("Unable to register undefined sensor!");
			return;
		}

		if (StringUtils.isNullOrEmpty(sensor.getName())) {
			getLog().warn("Unable to register sensor with undefined name!");
			return;
		}

		if (StringUtils.isNullOrEmpty(sensor.getAdapterPluginKey())) {
			getLog().warn("Unable to register sensor with undefined adapter pluginKey!");
			return;
		}

		if (StringUtils.isNullOrEmpty(sensor.getAdapterClassName())) {
			getLog().warn("Unable to register sensor with undefined adapter className!");
			return;
		}

		if (getSensorService().getByName(sensor.getName()) != null) {
			getLog().warnFormat("Sensor '%s' is registered already!", sensor.getName());
			return;
		}

		getLog().debugFormat("Registering sensor adapter %s with id '%s'", sensor.getAdapterClassName(), sensor.getName());
		getSensorService().add(sensor);
	}

	@Override
	public void unregister(final String sensorName) {
		Sensor sensor = getSensorService().getByName(sensorName);

		if (sensor == null) {
			getLog().warnFormat("Unable to unregister sensor '%s'!", sensorName);
			return;
		}

		getLog().debugFormat("Unregistering sensor adapter %s with id '%s'", sensor.getAdapterClassName(), sensor.getName());
		getSensorService().remove(sensor);
	}

	@Override
	public String process(final String sensorName, final Device device, final NeuralBag bag) {
		SensorAdapter adapter = null;

		if (adapters.containsKey(sensorName)) {
			adapter = adapters.get(sensorName);

		} else {
			adapter = loadAdapter(sensorName);

			if (adapter == null) {
				return null;
			}

			adapters.put(sensorName, adapter);
		}

		return adapter.process(device, bag);
	}

	protected SensorAdapter loadAdapter(final String sensorName) {
		Sensor sensor = getSensorService().getByName(sensorName);

		if (sensor == null) {
			return null;
		}

		PluginBundle bundle = ManagementAgentPluginManager.getInstance().getPluginBundle(sensor.getAdapterPluginKey());

		if (bundle == null) {
			return null;
		}

		SensorAdapter adapter = null;

		try {
			Class<?> clazz = bundle.getClassLoader().loadClass(sensor.getAdapterClassName());
			adapter = (SensorAdapter) clazz.newInstance();

		} catch (final ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
			getLog().error(String.format("Unable to load sensor adapter %s!", sensor.getAdapterClassName()), ex);
		}

		return adapter;
	}
}