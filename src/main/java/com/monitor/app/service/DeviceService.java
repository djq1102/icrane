package com.monitor.app.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.monitor.app.dao.user.DeviceDao;
import com.monitor.app.dataobject.Device;
import com.monitor.app.exception.DAOException;
import com.monitor.app.exception.ManagerException;
import com.monitor.app.query.DeviceQuery;
import com.monitor.app.result.ServiceResult;
import com.monitor.app.result.msg.MsgEnum;
import com.monitor.app.utils.MsgUtils;

/**
 * 
 * @author tmac
 *
 */

@Service
public class DeviceService {
	
	@Resource
	private DeviceDao deviceDao;
	
	public ServiceResult addDevice(Device device) throws ManagerException{
		try{
			int row = deviceDao.addDevice(device);
			if(row != 1){
				return MsgUtils.fillMsg(MsgEnum.DEVICE_ADD_FAIL);
			}
		}catch(DAOException e){
			throw new ManagerException(e);
		}
		return MsgUtils.fillModule(device);
	}
	
	public ServiceResult queryByDeviceId(long deviceId) throws ManagerException{
		Device device ;
		try {
			device = deviceDao.queryByDeviceId(deviceId);
			if(device == null){
				return MsgUtils.fillMsg(MsgEnum.DEVICE_NOT_EXIST);
			}
		} catch (DAOException e) {
			throw new ManagerException(e);
		}
		return MsgUtils.fillModule(device);
	}
	
	public ServiceResult queryBySiteId(long siteId) throws ManagerException{
		List<Device> devices = new ArrayList<Device>() ;
		try {
			devices = deviceDao.queryDevicesBySiteId(siteId);
		} catch (DAOException e) {
			throw new ManagerException(e);
		}
		return MsgUtils.fillModule(devices);
	}
	
	public ServiceResult deleteDevice(long deviceId) throws ManagerException{
		try{
			int row = deviceDao.deleteDevice(deviceId);
			if(row != 1){
				return MsgUtils.fillMsg(MsgEnum.DEVICE_ADD_FAIL);
			}
		}catch(DAOException e){
			throw new ManagerException(e);
		}
		ServiceResult result = new ServiceResult(true);
		return result;
	}
	
	public ServiceResult updateDevice(Device device) throws ManagerException{
		try {
			int rows = deviceDao.updateDevice(device);
			if (rows != 1){
				return MsgUtils.fillMsg(MsgEnum.DEVICE_UPDATE_FAIL);
			}
		} catch (DAOException e){
			throw new ManagerException(e);
		}
		return MsgUtils.fillModule(device);
	}
	
	public ServiceResult queryAllDevice(DeviceQuery query) throws ManagerException{
		List<Device> list = new ArrayList<Device>();
		try{
			list = deviceDao.queryDevice(query);
		}catch(DAOException e){
			throw new ManagerException(e);
		}
		ServiceResult result = new ServiceResult(true);
		result.setModule(list);
		return result;
	}

	public ServiceResult totalCount(DeviceQuery query) throws ManagerException{
		int num = 0;
		try{
			num = deviceDao.countAll(query);
		}catch(DAOException e){
			throw new ManagerException(e);
		}
		ServiceResult result = new ServiceResult(true);
		result.setModule(num);
		return result;
	}

}
