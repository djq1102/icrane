/**
 * 
 */
package com.monitor.app.dataobject;

/**
 * @author ibm
 * 
 */
public class DeviceAccessoryBindDO {

	private long id;
	private long deviceId;
	private long accessoryId;
	private String accessoryName;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(long deviceId) {
		this.deviceId = deviceId;
	}

	public long getAccessoryId() {
		return accessoryId;
	}

	public void setAccessoryId(long accessoryId) {
		this.accessoryId = accessoryId;
	}

	public String getAccessoryName() {
		return accessoryName;
	}

	public void setAccessoryName(String accessoryName) {
		this.accessoryName = accessoryName;
	}


}
