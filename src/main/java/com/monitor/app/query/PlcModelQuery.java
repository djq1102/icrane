package com.monitor.app.query;

import com.monitor.app.dao.common.Pagination;

public class PlcModelQuery extends Pagination{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2567930026451206635L;
	private long modelId;
	private String modelName;
	
	public long getModelId() {
		return modelId;
	}
	public void setModelId(long modelId) {
		this.modelId = modelId;
	}
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	
	
}
