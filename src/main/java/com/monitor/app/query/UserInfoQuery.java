package com.monitor.app.query;

import com.monitor.app.dao.common.Pagination;


public class UserInfoQuery extends Pagination{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5851387703431381830L;
	
	private Long customerId;
	
	private String userPhone;
	
	private String userName;
	
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	
	public Long getCustomerId() {
		return customerId;
	}
	
	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}


}
