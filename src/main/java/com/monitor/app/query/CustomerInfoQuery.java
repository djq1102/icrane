package com.monitor.app.query;

import java.io.Serializable;

public class CustomerInfoQuery implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9216500833062470021L;
	
	private Long customerId;
	
	private String customerName;
	
	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	
}
