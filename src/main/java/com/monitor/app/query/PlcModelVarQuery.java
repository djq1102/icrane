package com.monitor.app.query;

import com.monitor.app.dao.common.Pagination;

public class PlcModelVarQuery extends Pagination {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2567930026451206635L;

	private long varID;

	private String varName;

	private long modelId;

	public long getVarId() {
		return varID;
	}

	public void setVarId(long varlId) {
		this.varID = varlId;
	}

	public String getVarName() {
		return varName;
	}

	public void setVarName(String varName) {
		this.varName = varName;
	}

	public long getModelId() {
		return modelId;
	}

	public void setModelId(long modelId) {
		this.modelId = modelId;
	}

}
