/**
 * 
 */
package com.monitor.app.dao.model;

import java.util.List;

import com.monitor.app.dataobject.ModelAccessory;
import com.monitor.app.query.ModelAccessoryQuery;

/**
 * @author ibm
 *
 */
public interface AccessoryDao {

	public int addAccessory(ModelAccessory accessory);
	
	public int updateAccessory(ModelAccessory accessory);
	
	public int delAccessory(long accessoryId);
	
	public ModelAccessory queryModelAccessoryById(long accessoryId);
	
	public List<ModelAccessory> queryModelAccessory(ModelAccessoryQuery query);
	
	public int totalCount(ModelAccessoryQuery query);
	
}
