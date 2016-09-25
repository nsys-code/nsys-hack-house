/* Copyright 2016 Nsys #HackTheHouse - Tomas Hrdlicka <tomas@hrdlicka.co.uk>. All rights reserved.
 */

package org.nsys.iot.hackhouse.core.model;

import org.nsys.daemon.user.DefaultUser;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Nsys #HackTheHouse Sensor
 *
 * @author Tomas Hrdlicka <tomas@hrdlicka.co.uk>
 * @see <a href="http://nsys.org">Nsys</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Sensor {
	private long id;
    private String name;
    private String label;
    private String description;
    private boolean enabled;
    private String adapterPluginKey;
    private String adapterClassName;
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

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getAdapterPluginKey() {
		return adapterPluginKey;
	}

	public void setAdapterPluginKey(String adapterPluginKey) {
		this.adapterPluginKey = adapterPluginKey;
	}

	public String getAdapterClassName() {
		return adapterClassName;
	}

	public void setAdapterClassName(String adapterClassName) {
		this.adapterClassName = adapterClassName;
	}

	public DefaultUser getOwner() {
		return owner;
	}

	public void setOwner(DefaultUser owner) {
		this.owner = owner;
	}

	@Override
	public String toString() {
		return String.format("Sensor [id=%d,name=%s,label=%s,description=%s,enabled=%s," +
				"adapterPluginKey=%s,adapterClassName=%s,owner=%s]",
				getId(), getName(), getLabel(), getDescription(), isEnabled(),
				getAdapterPluginKey(), getAdapterClassName(), getOwner());
	}
}