/* Copyright 2016 Nsys #HackTheHouse - Tomas Hrdlicka <tomas@hrdlicka.co.uk>. All rights reserved.
 */

package org.nsys.iot.hackhouse.portal.webapp.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import org.nsys.system.ComponentProvider;
import org.nsys.portal.controller.PortalAdminController;

import org.nsys.iot.hackhouse.core.repository.SensorService;

/**
 * Sensor Adapter Controller
 *
 * @author Tomas Hrdlicka <tomas@hrdlicka.co.uk>
 * @see <a href="http://nsys.org">Nsys</a>
 */
@Controller
@RequestMapping("/sensor")
public class SensorAdapterController extends PortalAdminController {
	private SensorService sensorService;

	private static final String LOCATION_HEADER_ACTIONS = "hackhouse.header.actions.sensor-adapter";
	private static final String DISPLAY_NAME = "Sensor Adapters";

	public SensorService getSensorService() {
		if (sensorService == null) {
			sensorService = ComponentProvider.getInstance().getComponent(SensorService.class);
		}

		return sensorService;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView showSensors(
			final HttpServletRequest request,
			final HttpServletResponse response) {

		return new ModelAndView("redirect:/sensor/adapters");
	}

	@RequestMapping(value = "/adapters", method = RequestMethod.GET)
	public ModelAndView showSensorAdapters(
			final HttpServletRequest request,
			final HttpServletResponse response) {

		Map<String, Object> context = new HashMap<String, Object>();
		context.put("sensors", getSensorService().getAll());

		String imageUri = String.format("%s/resources/images/nsys_logo_avatar.png", request.getContextPath());

		setDisplayName(DISPLAY_NAME);
		showHeader(imageUri, LOCATION_HEADER_ACTIONS, null);

		return render("/templates/sensor-adapter.vm", context, request, response);
	}
}