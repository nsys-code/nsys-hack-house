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
import org.nsys.daemon.user.DefaultUser;
import org.nsys.daemon.user.UserManager;
import org.nsys.portal.controller.PortalAdminController;

import org.nsys.iot.hackhouse.core.model.Device;
import org.nsys.iot.hackhouse.core.model.DeviceSensor;
import org.nsys.iot.hackhouse.core.model.Sensor;
import org.nsys.iot.hackhouse.core.repository.DeviceSensorService;
import org.nsys.iot.hackhouse.core.repository.DeviceService;
import org.nsys.iot.hackhouse.core.repository.SensorService;
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
	private DeviceSensorService deviceSensorService;
	private SensorService sensorService;
	private UserManager userManager;

	private static final String LOCATION_HEADER_ACTIONS = "hackhouse.header.actions.device-mgmt";
	private static final String LOCATION_HEADER_ACTIONS_SENSOR = "hackhouse.header.actions.device-sensor-mgmt";
	private static final String DISPLAY_NAME = "Device Management";
	private static final String DISPLAY_NAME_SENSOR = "Device Sensor Management";
	private static final String DEVICE_ADD_SUCCESS = "nsys.hackhouse.device.mgmt.deviceAddSuccess";
	private static final String DEVICE_ADD_NAME = "nsys.hackhouse.device.mgmt.deviceAddName";
	private static final String SENSOR_ADD_SUCCESS = "nsys.hackhouse.device.mgmt.sensorAddSuccess";
	private static final String SENSOR_ADD_NAME = "nsys.hackhouse.device.mgmt.sensorAddName";

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

	public DeviceSensorService getDeviceSensorService() {
		if (deviceSensorService == null) {
			deviceSensorService = ComponentProvider.getInstance().getComponent(DeviceSensorService.class);
		}

		return deviceSensorService;
	}

	public SensorService getSensorService() {
		if (sensorService == null) {
			sensorService = ComponentProvider.getInstance().getComponent(SensorService.class);
		}

		return sensorService;
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

		Map<String, Object> context = new HashMap<String, Object>();
		context.put("devices", getDeviceService().getAll());

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

		if (device != null) {
			device.setOwner((DefaultUser) getUserManager().getLoggedInUser(request));

			if (device.getPort() < 1 || device.getPort() > MAX_PORT_NUMBER) {
				device.setPort(DEFAULT_PORT);
			}
		}

		request.getSession().setAttribute(DEVICE_ADD_SUCCESS, false);
		request.getSession().setAttribute(DEVICE_ADD_NAME, device.getName());

		if (getDeviceService().add(device) != null) {
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

		getDeviceSensorService().removeByDeviceId(deviceId);
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

	@RequestMapping(value = "/{deviceId}/sensors", method = RequestMethod.GET)
	public ModelAndView showDeviceSensors(
			final @PathVariable("deviceId") long deviceId,
			final HttpServletRequest request,
			final HttpServletResponse response) {

		Map<String, Object> context = new HashMap<String, Object>();
		context.put("deviceSensors", getDeviceSensorService().getByDeviceId(deviceId));
		context.put("deviceId", deviceId);

		if (request.getSession().getAttribute(SENSOR_ADD_SUCCESS) != null &&
			request.getSession().getAttribute(SENSOR_ADD_NAME) != null) {
			boolean sensorAddSuccess = (boolean) request.getSession().getAttribute(SENSOR_ADD_SUCCESS);
			String sensorAddName = (String) request.getSession().getAttribute(SENSOR_ADD_NAME);

			String sensorAddMessage = 
					sensorAddSuccess ? 
							String.format("Sensor '%s' has been added successfully!", sensorAddName) :
							String.format("Unable to add sensor '%s'!", sensorAddName);

			context.put("sensorAddSuccess", sensorAddSuccess);
			context.put("sensorAddMessage", sensorAddMessage);

			request.getSession().removeAttribute(SENSOR_ADD_SUCCESS);
			request.getSession().removeAttribute(SENSOR_ADD_NAME);
		}

		String imageUri = String.format("%s/resources/images/nsys_logo_avatar.png", request.getContextPath());

		setDisplayName(DISPLAY_NAME_SENSOR);
		showHeader(imageUri, LOCATION_HEADER_ACTIONS_SENSOR, null);

		return render("/templates/device-sensor-mgmt.vm", context, request, response);
	}

	@RequestMapping(value = "/{deviceId}/sensors/add", method = RequestMethod.GET)
	@ResponseBody
	public String showAddDeviceSensor(
			final @PathVariable("deviceId") long deviceId,
			final @RequestParam("redir") String redir,
			final HttpServletRequest request,
			final HttpServletResponse response) {

		Map<String, Object> context = new HashMap<String, Object>();
		context.put("sensors", getSensorService().getAll());
		context.put("deviceId", deviceId);
		context.put("redir", redir);

		return renderFragment("/templates/device-sensor-add.vm", context, request, response);
	}

	@RequestMapping(value = "/{deviceId}/sensors/add", method = RequestMethod.POST)
	public ModelAndView processAddDeviceSensor(
			final @PathVariable("deviceId") long deviceId,
			final HttpServletRequest request,
			final HttpServletResponse response) {

		String redir = request.getParameter("redir");
		String sensorName = request.getParameter("sensor");

		Device device = getDeviceService().get(deviceId);
		Sensor sensor = getSensorService().getByName(sensorName);

		if (device != null && sensor != null &&
			(getDeviceSensorService().getByDeviceIdAndSensorId(device.getId(), sensor.getId()) == null)) {

			DeviceSensor deviceSensor = new DeviceSensor();
			deviceSensor.setDevice(device);
			deviceSensor.setSensor(sensor);

			request.getSession().setAttribute(SENSOR_ADD_SUCCESS, false);
			request.getSession().setAttribute(SENSOR_ADD_NAME, sensor.getName());

			if (getDeviceSensorService().add(deviceSensor) != null) {
				request.getSession().setAttribute(SENSOR_ADD_SUCCESS, true);
			}
		}

		return new ModelAndView(String.format("redirect:%s", redir));
	}

	@RequestMapping(value = "/{deviceId}/sensors/{deviceSensorId}/remove", method = RequestMethod.GET)
	public ModelAndView processRemoveDeviceSensor(
			final @PathVariable("deviceId") long deviceId,
			final @PathVariable("deviceSensorId") long deviceSensorId,
			final HttpServletRequest request,
			final HttpServletResponse response) {

		getDeviceSensorService().remove(getDeviceSensorService().get(deviceSensorId));

		return new ModelAndView(String.format("redirect:/device/%d/sensors", deviceId));
	}

	protected List<String> getProtocols() {
		List<String> protocols = new ArrayList<String>();
		protocols.add(HostNode.DEFAULT_PROTOCOL);
		protocols.add(HostNode.DEFAULT_PROTOCOL_SSL);
		return protocols;
	}
}