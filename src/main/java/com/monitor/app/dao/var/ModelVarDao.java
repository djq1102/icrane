/**
 * 
 */
package com.monitor.app.dao.var;

import java.util.List;

import com.monitor.app.dataobject.ModelVar;

/**
 * @author ibm
 *
 */
public interface ModelVarDao {

	public int addModelVar(ModelVar modelVar);
	
	public int updateModelVar(ModelVar modelVar);
	
	public int delModelVar(long varId);
	
	public List<ModelVar> queryVarsByModelId(long modelId);
	
}
