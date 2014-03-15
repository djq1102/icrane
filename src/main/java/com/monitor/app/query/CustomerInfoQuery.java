package com.monitor.app.query;

import com.monitor.app.dao.common.Pagination;

public class CustomerInfoQuery extends Pagination{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9216500833062470021L;
	
	private Long customerId;
	
	private String customerName;
	
	private Short customerType;
	
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

	public Short getCustomerType() {
		return customerType;
	}

	public void setCustomerType(Short customerType) {
		this.customerType = customerType;
	}
}
