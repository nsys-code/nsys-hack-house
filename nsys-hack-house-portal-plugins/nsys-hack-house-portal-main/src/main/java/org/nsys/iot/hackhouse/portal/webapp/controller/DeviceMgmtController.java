/* Copyright 2016 Nsys #HackTheHouse - Tomas Hrdlicka <tomas@hrdlicka.co.uk>. All rights reserved.
 */

package org.nsys.iot.hackhouse.portal.webapp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import org.nsys.system.ComponentProvider;
import org.nsys.util.JsonUtils;
import org.nsys.util.ModelHelper;
import org.nsys.util.StringUtils;
import org.nsys.daemon.core.HostNode;
import org.nsys.daemon.model.ResponseData;
import org.nsys.daemon.user.UserManager;
import org.nsys.portal.controller.PortalAdminController;

import org.nsys.iot.hackhouse.core.model.Device;
import org.nsys.iot.hackhouse.core.repository.DeviceService;
import org.nsys.iot.hackhouse.portal.webapp.utils.ValidatorUtils;

/**
 * Device Management Controller
 *
 * @author Tomas Hrdlicka <tomas@hrdlicka.co.uk>
 * @see <a href="http://nsys.org">Nsys</a>
 */
@Controller
@RequestMapping("/device")
public class DeviceMgmtController extends PortalAdminController {
	private DeviceService deviceService;
	private UserManager userManager;

	private static final String LOCATION_HEADER_ACTIONS = "hackhouse.header.actions";
	private static final String DISPLAY_NAME = "Device Management";
	private static final String DEVICE_ADD_SUCCESS = "nsys.hackhouse.device.mgmt.deviceAddSuccess";
	private static final String DEVICE_ADD_NAME = "nsys.hackhouse.device.mgmt.deviceAddName";

	private static final int DEFAULT_PORT    = 80;
	private static final int MAX_PORT_NUMBER = 65535;

	private static final String ACTION_ADD    = "add";
	private static final String ACTION_UPDATE = "update";

	public static final String BUTTON_ADD    = "Add";
	public static final String BUTTON_UPDATE = "Update";
	public static final String BUTTON_REMOVE = "Remove";

	public DeviceService getDeviceService() {
		if (deviceService == null) {
			deviceService = ComponentProvider.getInstance().getComponent(DeviceService.class);
		}

		return deviceService;
	}

	public UserManager getUserManager() {
		if (userManager == null) {
			userManager = ComponentProvider.getInstance().getComponent(UserManager.class);
		}

		return userManager;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView showDevices(
			final HttpServletRequest request,
			final HttpServletResponse response) {

		return new ModelAndView("redirect:/device/management");
	}

	@RequestMapping(value = "/management", method = RequestMethod.GET)
	public ModelAndView showManageDevices(
			final HttpServletRequest request,
			final HttpServletResponse response) {

		DeviceService deviceService = ComponentProvider.getInstance().getComponent(DeviceService.class);

		Map<String, Object> context = new HashMap<String, Object>();
		context.put("devices", deviceService.getAll());

		if (request.getSession().getAttribute(DEVICE_ADD_SUCCESS) != null &&
			request.getSession().getAttribute(DEVICE_ADD_NAME) != null) {
			boolean deviceAddSuccess = (boolean) request.getSession().getAttribute(DEVICE_ADD_SUCCESS);
			String deviceAddName = (String) request.getSession().getAttribute(DEVICE_ADD_NAME);

			String deviceAddMessage = 
					deviceAddSuccess ? 
							String.format("Device '%s' has been added successfully!", deviceAddName) :
							String.format("Unable to add device '%s'!", deviceAddName);

			context.put("deviceAddSuccess", deviceAddSuccess);
			context.put("deviceAddMessage", deviceAddMessage);

			request.getSession().removeAttribute(DEVICE_ADD_SUCCESS);
			request.getSession().removeAttribute(DEVICE_ADD_NAME);
		}

		String imageUri = String.format("%s/resources/images/nsys_logo_avatar.png", request.getContextPath());

		setDisplayName(DISPLAY_NAME);
		showHeader(imageUri, LOCATION_HEADER_ACTIONS, null);

		return render("/templates/device-mgmt.vm", context, request, response);		
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	@ResponseBody
	public String showAddDevice(
			final @RequestParam("redir") String redir,
			final HttpServletRequest request,
			final HttpServletResponse response) {
		
		Map<String, Object> context = new HashMap<String, Object>();
		context.put("protocols", getProtocols());
		context.put("action", "add");
		context.put("actionButton", BUTTON_ADD);
		context.put("defaultPort", DEFAULT_PORT);
		context.put("redir", redir);			

		return renderFragment("/templates/device-edit.vm", context, request, response);
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ModelAndView processAddDevice(
			final HttpServletRequest request,
			final HttpServletResponse response) {

		Device device = ModelHelper.create(request, Device.class);
		String redir = request.getParameter("redir");

		if (device.getPort() < 1 || device.getPort() > MAX_PORT_NUMBER) {
			device.setPort(DEFAULT_PORT);
		}

		DeviceService deviceService = ComponentProvider.getInstance().getComponent(DeviceService.class);

		request.getSession().setAttribute(DEVICE_ADD_SUCCESS, false);
		request.getSession().setAttribute(DEVICE_ADD_NAME, device.getName());

		if (deviceService.add(device) != null) {
			request.getSession().setAttribute(DEVICE_ADD_SUCCESS, true);
		}

		return new ModelAndView(String.format("redirect:%s", redir));
	}

	@RequestMapping(value = "/{deviceId}/edit", method = RequestMethod.GET)
	@ResponseBody
	public String showDeviceDetail(
			final @PathVariable("deviceId") long deviceId,
			final @RequestParam("redir") String redir,
			final HttpServletRequest request,
			final HttpServletResponse response) {

		Map<String, Object> context = new HashMap<String, Object>();
		context.put("device", getDeviceService().get(deviceId));
		context.put("protocols", getProtocols());
		context.put("action", ACTION_UPDATE);
		context.put("actionButton", BUTTON_UPDATE);
		context.put("defaultPort", DEFAULT_PORT);
		context.put("redir", redir);

		return renderFragment("/templates/device-edit.vm", context, request, response);
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ModelAndView processUpdateDevice(
			final @RequestParam("redir") String redir,
			final HttpServletRequest request,
			final HttpServletResponse response) {

		Device device = ModelHelper.create(request, Device.class);

		if (device.getPort() < 1 || device.getPort() > MAX_PORT_NUMBER) {
			device.setPort(DEFAULT_PORT);
		}

		getDeviceService().update(device);

		return new ModelAndView(String.format("redirect:%s", redir));
	}

	@RequestMapping(value = "/{deviceId}/remove", method = RequestMethod.GET)
	public ModelAndView processRemoveDevice(
			final @PathVariable("deviceId") long deviceId,
			final HttpServletRequest request,
			final HttpServletResponse response) {

		getDeviceService().remove(getDeviceService().get(deviceId));

		return showDevices(request, response);
	}

	@RequestMapping(value = "/validate", method = RequestMethod.POST)
	@ResponseBody
	public String validateProject(
			@RequestParam("action") String action,
			@RequestParam("name") String name,
			@RequestParam("host") String host,
			@RequestParam("port") String port,
			@RequestParam("apiKey") String apiKey,
			HttpServletRequest request,
			HttpServletResponse response) {

		if (StringUtils.isNullOrEmpty(action)) {
			action = ACTION_ADD;

		} else {
			action = action.toLowerCase();
		}

		boolean isError = false;
		Map<String, String> errors = new HashMap<String, String>();

		if (action.equals(ACTION_ADD)) {
			if (ValidatorUtils.validateString("name", name, "Name", errors) ||
				ValidatorUtils.validateDevice("name", name, getDeviceService().getByName(name), errors)) {
				isError = true;
			}
		}

		if (ValidatorUtils.validateString("host", host, "Host", errors)) {
			isError = true;
		}

		if (port != null && !port.isEmpty()) {
			int portNum = Integer.valueOf(port);

			if (portNum < 1 || portNum > MAX_PORT_NUMBER) {
				isError = true;
				errors.put("port", String.format("Allowed port range is 1 to %d!", MAX_PORT_NUMBER));
			}
		}

		if (ValidatorUtils.validateString("apiKey", apiKey, "API Key", errors)) {
			isError = true;
		}

		ResponseData data = new ResponseData();
		data.setResult(errors);

		if (isError) {
			data.setStatus(ResponseData.FAIL);

		} else {
			data.setStatus(ResponseData.SUCCESS);
		}

		String json = JsonUtils.toJson(data);

		//log.debugFormat("JSON=%s", json);

		return json;
	}

	protected List<String> getProtocols() {
		List<String> protocols = new ArrayList<String>();
		protocols.add(HostNode.DEFAULT_PROTOCOL);
		protocols.add(HostNode.DEFAULT_PROTOCOL_SSL);
		return protocols;
	}
}