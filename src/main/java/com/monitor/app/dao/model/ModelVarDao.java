/**
 * 
 */
package com.monitor.app.dao.model;

import java.util.List;

import com.monitor.app.dataobject.ModelVar;
import com.monitor.app.query.PlcModelVarQuery;

/**
 * @author ibm
 *
 */
public interface ModelVarDao {

	public int addPlcModelVar(ModelVar modelVar);
	
	public int updatePlcModelVar(ModelVar modelVar);
	
	public int delPlcModelVar(long varId);
	
	public List<ModelVar> queryPlcModelVar(PlcModelVarQuery query);
	
	public int totalCount(PlcModelVarQuery query);
}
