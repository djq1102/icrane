package com.monitor.app.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.monitor.app.dao.user.DeviceDao;
import com.monitor.app.dao.user.UserDeviceRelationDao;
import com.monitor.app.dataobject.Device;
import com.monitor.app.dataobject.DeviceAccessoryBindDO;
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
	
	@Resource
	private UserDeviceRelationDao userDeviceRelationDao;
	
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
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ServiceResult queryByDeviceIds(List<Long> deviceIds) throws ManagerException{
		Map params = new HashMap();
		params.put("deviceIds", deviceIds);
		List<Device> deviceList = new ArrayList<Device>();
		try {
			deviceList = deviceDao.queryByDeviceIds(params);
		} catch (DAOException e) {
			throw new ManagerException(e);
		}
		return MsgUtils.fillModule(deviceList);
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
	
	public ServiceResult queryByCustomerId(long customerId) throws ManagerException{
		List<Device> devices = new ArrayList<Device>() ;
		try {
			devices = deviceDao.queryByCustomerId(customerId);
		} catch (DAOException e) {
			throw new ManagerException(e);
		}
		return MsgUtils.fillModule(devices);
	}

	
	public ServiceResult deleteDevice(long deviceId) throws ManagerException{
		try{
			userDeviceRelationDao.deleteUserDeviceRelationByDeviceId(deviceId);
			deviceDao.batchDeleteAccBindBydeviceId(deviceId);
			deviceDao.deleteDevice(deviceId);
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

	public ServiceResult queryDeviceAccBindData(long deviceId)  throws ManagerException{
		List<DeviceAccessoryBindDO> bindDOList = null;
		try{
			bindDOList = deviceDao.queryDeviceBindDataByDeviceId(deviceId);
		}catch(Exception e){
			throw new ManagerException(e);
		}
		
		return MsgUtils.fillModule(bindDOList);
	}
	
	public ServiceResult batchDeleteAccBind(long deviceId)  throws ManagerException{
		try{
			 deviceDao.batchDeleteAccBindBydeviceId(deviceId);
		}catch(Exception e){
			throw new ManagerException(e);
		}
		
		return MsgUtils.fillModule(null);
	}
	
	public ServiceResult batchAddAccBinds(List<DeviceAccessoryBindDO> binds)  throws ManagerException{
		try{
			 deviceDao.batchInsertAccBindRelation(binds);
		}catch(Exception e){
			throw new ManagerException(e);
		}
		
		return MsgUtils.fillModule(null);
	}
}
