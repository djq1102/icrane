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
	private int accessoryType;
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

	public int getAccessoryType() {
		return accessoryType;
	}

	public void setAccessoryType(int accessoryType) {
		this.accessoryType = accessoryType;
	}


	
}
