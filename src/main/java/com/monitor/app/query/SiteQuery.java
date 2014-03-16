package com.monitor.app.query;

import com.monitor.app.dao.common.Pagination;

public class SiteQuery extends Pagination{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6760996317312580959L;
	
	private Long siteId;
	
	private String siteName;
	
	private Long customerId;
	
	private Byte status;

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

}
