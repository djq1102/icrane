package com.monitor.app.query;


import com.monitor.app.dao.common.Pagination;

public class DeviceQuery extends Pagination{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9105391909671244273L;
	
	private Long deviceId;
	
	private Long siteId;
	
	private Long customerId;
	
	private String deviceName;

	public Long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

}
