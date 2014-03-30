package com.monitor.app.dao.user;

import java.util.List;
import java.util.Map;

import com.monitor.app.dataobject.Device;
import com.monitor.app.dataobject.DeviceAccessoryBindDO;
import com.monitor.app.exception.DAOException;
import com.monitor.app.query.DeviceQuery;

public interface DeviceDao {
	
	  public int addDevice(Device device) throws DAOException;
	  
	  public Device queryByDeviceId(long deviceId)throws DAOException;
	  
	  @SuppressWarnings("rawtypes")
	  public List<Device> queryByDeviceIds(Map params) throws DAOException;
	  
	  public List<Device> queryDevice(DeviceQuery deviceQuery) throws DAOException;
	  
	  public List<Device> queryDevicesBySiteId(long siteId) throws DAOException;
	  
	  public int updateDevice(Device device)throws DAOException;
	  
	  public int countAll(DeviceQuery deviceQuery) throws DAOException;
	  
	  public int deleteDevice(long deviceId) throws DAOException;
	  
	  public List<DeviceAccessoryBindDO> queryDeviceBindDataByDeviceId(long deviceId) throws DAOException;
	  
	  public void batchDeleteAccBindBydeviceId(long deviceId)  throws DAOException;
	  
	  public void batchInsertAccBindRelation(List<DeviceAccessoryBindDO> binds)  throws DAOException;
	  
}
