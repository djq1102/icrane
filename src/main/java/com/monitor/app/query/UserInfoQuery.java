package com.monitor.app.query;

import java.io.Serializable;

public class UserInfoQuery implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5851387703431381830L;
	
	private long customerId;

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	
	

}
