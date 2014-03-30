/**
 * 
 */
package com.monitor.app.dao.user;

import java.util.List;

import com.monitor.app.dataobject.DeviceDoc;
import com.monitor.app.exception.DAOException;
import com.monitor.app.query.DeviceDocQuery;

/**
 * @author ibm
 *
 */
public interface DeviceDocDao {

	public int addDeviceDoc(DeviceDoc doc) throws DAOException;
	
	/**
	 * 逻辑删除。。
	 * 
	 * @param docId
	 * @return
	 * @throws DAOException
	 */
	public int delDeviceDoc(long docId) throws DAOException;
	
	public List<DeviceDoc> queryDeviceDocs(DeviceDocQuery query) throws DAOException;
	
}
