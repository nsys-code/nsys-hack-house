/* Copyright 2016 Nsys #HackTheHouse - Tomas Hrdlicka <tomas@hrdlicka.co.uk>. All rights reserved.
 */

package org.nsys.iot.hackhouse.portal.webapp.utils;

import java.util.Map;

import org.nsys.iot.hackhouse.core.model.Device;
import org.nsys.util.StringUtils;

/**
 * Controller Validation Utils
 *
 * @author Tomas Hrdlicka <tomas@hrdlicka.co.uk>
 * @see <a href="http://nsys.org">Nsys</a>
 */
public class ValidatorUtils {

	public static boolean validateString(
			final String fieldName,
			final String fieldValue,
			final String fieldLabel,
			final Map<String, String> errors) {

		boolean isError = false;

		if (StringUtils.isNullOrEmpty(fieldValue)) {
			isError = true;
			errors.put(fieldName, String.format("%s cannot be empty!", fieldLabel));
		}

		else if (StringUtils.containsWhitespace(fieldValue)) {
			isError = true;
			errors.put(fieldName, "Enter numbers and alphabets only!");
		}

		return isError;
	}

	public static boolean validateDevice(
			final String fieldName,
			final String fieldValue,
			final Device device,
			final Map<String, String> errors) {

		boolean isError = false;

		if (device != null) {
			isError = true;
			errors.put(fieldName, String.format("Device %s '%s' exists already!", fieldName, fieldValue));
		}

		return isError;
	}
}