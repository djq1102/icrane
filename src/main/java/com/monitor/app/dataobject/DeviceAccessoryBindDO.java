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
	private long userId;
	
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

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}


}
