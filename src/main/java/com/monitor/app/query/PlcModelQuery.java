/**
 * 
 */
package com.monitor.app.query;

import com.monitor.app.dao.common.Pagination;

/**
 * @author ibm
 * 
 */
public class PlcModelQuery extends Pagination {

	private static final long serialVersionUID = 6556912306884548857L;

	private int modelId;
	private String modelName;
	public int getModelId() {
		return modelId;
	}
	public void setModelId(int modelId) {
		this.modelId = modelId;
	}
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
