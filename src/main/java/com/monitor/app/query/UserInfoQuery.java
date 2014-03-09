package com.monitor.app.query;

import com.monitor.app.dao.common.Pagination;


public class UserInfoQuery extends Pagination{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5851387703431381830L;
	
	private Long customerId;
	/**limit开始行号，0代表首行*/
	private Long begin;
	/**limit结尾行号*/
	private Long end;

	public Long getBegin() {
		return begin;
	}

	public void setBegin(Long begin) {
		this.begin = begin;
	}

	public Long getEnd() {
		return end;
	}

	public void setEnd(Long end) {
		this.end = end;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	
	public Long getCustomerId() {
		return customerId;
	}
}
