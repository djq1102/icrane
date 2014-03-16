/**
 * 
 */
package com.monitor.app.dao.model;

import java.util.List;

import com.monitor.app.dataobject.PlcModel;
import com.monitor.app.query.PlcModelQuery;

/**
 * @author ibm
 *
 */
public interface ModelDao {

	public int addModel(PlcModel model);
	
	public int updateModel(PlcModel model);
	
	public int delModel(long modelId);
	
	public List<PlcModel> queryPlcModel(PlcModelQuery query);
	
	public long totalCount(PlcModelQuery query);
	
}
