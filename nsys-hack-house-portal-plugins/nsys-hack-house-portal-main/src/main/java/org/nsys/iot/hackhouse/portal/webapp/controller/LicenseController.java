/* Copyright 2016 Nsys #HackTheHouse - Tomas Hrdlicka <tomas@hrdlicka.co.uk>. All rights reserved.
 */

package org.nsys.iot.hackhouse.portal.webapp.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.nsys.logging.Log;
import org.nsys.cloudlet.web.utils.ResourceDownloadHelper;
import org.nsys.iot.hackhouse.portal.webapp.PortalConfig;

/**
 * License Controller
 *
 * @author Tomas Hrdlicka <tomas@hrdlicka.co.uk>
 * @see <a href="http://nsys.org">Nsys</a>
 */
@Controller
@RequestMapping("/license")
public class LicenseController {
	private static final Log log = Log.getLogger(LicenseController.class);

	public static final String LICENSE_FILE_PATH = "/LICENSE-nsys-hack-house.txt";

	@RequestMapping(method = RequestMethod.GET)
	public void showLicense(
			HttpServletRequest request,
			HttpServletResponse response) {

		try {
			ResourceDownloadHelper.fileDownload(LICENSE_FILE_PATH, PortalConfig.getPluginKey(), request, response);

		} catch (final IOException ex) {
			log.warn(String.format("Unable to download resource '%s'!", LICENSE_FILE_PATH), ex);
		}
	}
}