/* Copyright 2016 Nsys #HackTheHouse - Tomas Hrdlicka <tomas@hrdlicka.co.uk>. All rights reserved.
 */

package org.nsys.iot.hackhouse.core.adapter;

import org.nsys.core.NeuralBag;
import org.nsys.iot.hackhouse.core.model.Device;

/**
 * Sensor Adapter interface
 *
 * @author Tomas Hrdlicka <tomas@hrdlicka.co.uk>
 * @see <a href="http://nsys.org">Nsys</a>
 */
public interface SensorAdapter {
	
	String process(final Device device, final NeuralBag bag);

}