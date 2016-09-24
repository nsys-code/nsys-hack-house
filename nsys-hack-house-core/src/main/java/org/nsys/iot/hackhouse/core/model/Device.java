/* Copyright 2016 Nsys #HackTheHouse - Tomas Hrdlicka <tomas@hrdlicka.co.uk>. All rights reserved.
 */

package org.nsys.iot.hackhouse.core.model;

import org.nsys.daemon.user.DefaultUser;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Nsys #HackTheHouse Device
 *
 * @author Tomas Hrdlicka <tomas@hrdlicka.co.uk>
 * @see <a href="http://nsys.org">Nsys</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Device {
	private long id;
    private String name;
    private String description;
    private String protocol;
    private String host;
    private int port;
    private String apiKey;
    private boolean enabled;
    private String timezone;
    private boolean connected;
    private String lastHeard;
    private int statusInterval;
	private double latitude;
	private double longitude;
	private String serial;
	private String manufacturer;
	private String product;
	private String category;
	private DefaultUser owner;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public boolean isConnected() {
		return connected;
	}

	public void setConnected(boolean connected) {
		this.connected = connected;
	}

	public String getLastHeard() {
		return lastHeard;
	}

	public void setLastHeard(String lastHeard) {
		this.lastHeard = lastHeard;
	}

	public int getStatusInterval() {
		return statusInterval;
	}

	public void setStatusInterval(int statusInterval) {
		this.statusInterval = statusInterval;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public DefaultUser getOwner() {
		return owner;
	}

	public void setOwner(DefaultUser owner) {
		this.owner = owner;
	}
	
	@Override
	public String toString() {
		return String.format("Device [id=%d,name=%s,description=%s,protocol=%s," +
				"host=%s,port=%d,apiKey=%s,enabled=%s,timezone=%s,connected=%s," +
				"lastHeard=%s,statusInterval=%d,latitude=%.2f,logitude=%.2f," + 
				"serial=%s,manufacturer=%s,product=%s,category=%s,owner=%s]",
				getId(), getName(), getDescription(), getProtocol(), getHost(), 
				getPort(), getApiKey(), isEnabled(), getTimezone(), isConnected(),
				getLastHeard(), getStatusInterval(), getLatitude(), getLongitude(),
				getSerial(), getManufacturer(), getProduct(), getCategory(), getOwner());
	}
}