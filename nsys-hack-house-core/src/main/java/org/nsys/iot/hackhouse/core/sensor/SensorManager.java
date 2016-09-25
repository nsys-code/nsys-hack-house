/* Copyright 2016 Nsys #HackTheHouse - Tomas Hrdlicka <tomas@hrdlicka.co.uk>. All rights reserved.
 */

package org.nsys.iot.hackhouse.core.sensor;

import org.nsys.core.NeuralBag;
import org.nsys.iot.hackhouse.core.model.Device;
import org.nsys.iot.hackhouse.core.model.Sensor;

/**
 * Sensor Manager interface
 *
 * @author Tomas Hrdlicka <tomas@hrdlicka.co.uk>
 * @see <a href="http://nsys.org">Nsys</a>
 */
public interface SensorManager {

	void register(final Sensor sensor);
	void unregister(final String sensorName);
	String process(final String sensorName, final Device device, final NeuralBag bag);
	
}