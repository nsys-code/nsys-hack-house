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

import org.nsys.portal.controller.PortalAdminController;

/**
 * Device Management Controller
 *
 * @author Tomas Hrdlicka <tomas@hrdlicka.co.uk>
 * @see <a href="http://nsys.org">Nsys</a>
 */
@Controller
@RequestMapping("/device")
public class DeviceMgmtController extends PortalAdminController {

	private static String LOCATION_HEADER_ACTIONS = "hackhouse.header.actions";

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView showDevices(
			HttpServletRequest request,
			HttpServletResponse response) {

		return new ModelAndView("redirect:/device/management");
	}

	@RequestMapping(value = "/management", method = RequestMethod.GET)
	public ModelAndView showDeviceManagement(
			HttpServletRequest request,
			HttpServletResponse response) {

		String imageUri = String.format("%s/resources/images/nsys_logo_avatar.png", request.getContextPath());

		Map<String, Object> context = new HashMap<String, Object>();

		//setDisplayName(String.format("@%s's timeline", user));
		showHeader(imageUri, LOCATION_HEADER_ACTIONS, null);

		return render("/templates/device-mgmt.vm", context, request, response);		
	}
}