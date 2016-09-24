/* Copyright 2016 Nsys #HackTheHouse - Tomas Hrdlicka <tomas@hrdlicka.co.uk>. All rights reserved.
 */

package org.nsys.iot.hackhouse.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.nsys.daemon.entity.BaseEntity;
import org.nsys.daemon.entity.UserEntity;

/**
 * Nsys #HackTheHouse Device Entity
 *
 * @author Tomas Hrdlicka <tomas@hrdlicka.co.uk>
 * @see <a href="http://nsys.org">Nsys</a>
 */
@Entity(name = "Device")
@Table(name = "NSYS_HACKHOUSE_DEVICE")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class DeviceEntity extends BaseEntity {

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "PROTOCOL")
    private String protocol;

    @Column(name = "HOST")
    private String host;

    @Column(name = "PORT")
    private int port;

    @Column(name = "API_KEY")
    private String apiKey;

    @Column(name = "ENABLED")
    private boolean enabled;

    @Column(name = "TIMEZONE")
    private String timezone;

    @Column(name = "CONNECTED")
    private boolean connected;

    @Column(name = "LAST_HEARD")
    private String lastHeard;

    @Column(name = "STATUS_INTERVAL")
    private int statusInterval;

    @Column(name = "LATITUDE")
	private double latitude;

    @Column(name = "LONGITUDE")
	private double longitude;
    
    @Column(name = "SERIAL")
	private String serial;
    
    @Column(name = "MANUFACTURER")
	private String manufacturer;
    
    @Column(name = "PRODUCT")
	private String product;
    
    @Column(name = "CATEGORY")
	private String category;

    @OneToOne
    @JoinColumn(name="USER_ID")
    private UserEntity owner;

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

	public UserEntity getOwner() {
		return owner;
	}

	public void setOwner(UserEntity owner) {
		this.owner = owner;
	}
}