package com.monitor.app.dao.user;

import java.util.List;

import com.monitor.app.dataobject.Device;
import com.monitor.app.exception.DAOException;
import com.monitor.app.query.DeviceQuery;

public interface DeviceDao {
	
	  public int addDevice(Device device) throws DAOException;
	  
	  public Device queryByDeviceId(long deviceId)throws DAOException;
	  
	  public List<Device> queryDevice(DeviceQuery deviceQuery) throws DAOException;
	  
	  public int updateDevice(Device device)throws DAOException;
	  
	  public int countAll(DeviceQuery deviceQuery) throws DAOException;
	  

}
