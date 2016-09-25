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
 * Nsys #HackTheHouse Sensor Entity
 *
 * @author Tomas Hrdlicka <tomas@hrdlicka.co.uk>
 * @see <a href="http://nsys.org">Nsys</a>
 */
@Entity(name = "Sensor")
@Table(name = "NSYS_HACKHOUSE_SENSOR")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class SensorEntity extends BaseEntity {

    @Column(name = "NAME")
    private String name;

    @Column(name = "LABEL")
    private String label;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "ENABLED")
    private boolean enabled;

    @Column(name = "ADAPTER_PLUGIN_KEY")
    private String adapterPluginKey;

    @Column(name = "ADAPTER_CLASS_NAME")
    private String adapterClassName;

    @OneToOne
    @JoinColumn(name="USER_ID")
    private UserEntity owner;

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

	public UserEntity getOwner() {
		return owner;
	}

	public void setOwner(UserEntity owner) {
		this.owner = owner;
	}
}