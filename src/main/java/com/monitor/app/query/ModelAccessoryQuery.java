/**
 * 
 */
package com.monitor.app.query;

import com.monitor.app.dao.common.Pagination;

/**
 * @author ibm
 *
 */
public class ModelAccessoryQuery extends Pagination {

	private static final long serialVersionUID = 6556912306884548857L;

	private long accessoryId;
	private String accessoryName;

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
