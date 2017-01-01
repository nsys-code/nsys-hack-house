/* Copyright 2016, 2017 Nsys #HackTheHouse - Tomas Hrdlicka <tomas@hrdlicka.co.uk>. All rights reserved.
 */

package org.nsys.iot.hackhouse.portal.webapp;

import org.nsys.portal.AbstractPortalConfig;

/**
 * Nsys #HackTheHouse Portal Configuration class
 *
 * @author Tomas Hrdlicka <tomas@hrdlicka.co.uk>
 * @see <a href="http://nsys.org">Nsys</a>
 */
public class NsysHackHousePortalMainConfig extends AbstractPortalConfig {

	public static final String PORTAL_NAME = "Nsys HackHouse Portal";
	public static final String PORTAL_LOGO_TEXT = "NSYS HH";

	@Override
	public void configure() {
		setPortalName(PORTAL_NAME);
		setPortalLogoText(PORTAL_LOGO_TEXT);
		setPortalCopyright(getCopyrightHtml());
		setPortalVersion(PortalConfig.getVersion());
		setPortalBuildNumber(PortalConfig.getBuildNumber());
	}

	protected String getCopyrightHtml() {
		String html = "Copyright &copy; 2016, 2017 <a href=\"http://tomas.hrdlicka.co.uk\" title=\"Tomas Xboot Hrdlicka\">Tomas Hrdlicka</a>";
		return html;
	}
}