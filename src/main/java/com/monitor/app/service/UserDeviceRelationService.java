package com.monitor.app.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.monitor.app.dao.user.UserDeviceRelationDao;
import com.monitor.app.dataobject.UserDeviceRelation;
import com.monitor.app.exception.DAOException;
import com.monitor.app.exception.ManagerException;
import com.monitor.app.result.ServiceResult;
import com.monitor.app.result.msg.MsgEnum;
import com.monitor.app.utils.MsgUtils;

/**
 * 
 * @author tmac
 *
 */

@Service
public class UserDeviceRelationService {
	
	@Resource
	private UserDeviceRelationDao userDeviceRelationDao;
	
	public ServiceResult addUserDeviceRelation(List<UserDeviceRelation> userDeviceRelations) throws ManagerException{
		try{
			int row = userDeviceRelationDao.batchInsertUserDeviceRelation(userDeviceRelations);
			if(row == 0){
				return MsgUtils.fillMsg(MsgEnum.USER_DEVICE_RELATION_ADD_FAIL);
			}
		}catch(DAOException e){
			throw new ManagerException(e);
		}
		return MsgUtils.fillModule(userDeviceRelations);
	}

	public ServiceResult queryUserDeviceRelationByUserId(long userId) throws ManagerException{
		List<UserDeviceRelation> userDeviceRelations = new ArrayList<UserDeviceRelation>();
		try {
			userDeviceRelations = userDeviceRelationDao.queryUserDeviceRelationByUserId(userId);
		} catch (DAOException e) {
			throw new ManagerException(e);
		}
		return MsgUtils.fillModule(userDeviceRelations);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ServiceResult deleteUserDeviceRelationBydeviceIds(long userId,List<String> deviceIds) throws ManagerException{
		Map params = new HashMap();
		params.put("userId", userId);
		params.put("deviceIds", deviceIds);
		try{
			int row = userDeviceRelationDao.batchDeleteUserDeviceRelationBydeviceId(params);
			if(row == 0){
				return MsgUtils.fillMsg(MsgEnum.USER_DEVICE_RELATION_DELETE_FAIL);
			}
		}catch(DAOException e){
			throw new ManagerException(e);
		}
		ServiceResult result = new ServiceResult(true);
		return result;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ServiceResult deleteUserDeviceRelationBysiteIds(long userId,List<String> siteIds) throws ManagerException{
		Map params = new HashMap();
		params.put("userId", userId);
		params.put("siteIds", siteIds);
		try{
			int row = userDeviceRelationDao.batchDeleteUserDeviceRelationBysiteId(params);
			if(row == 0){
				return MsgUtils.fillMsg(MsgEnum.USER_DEVICE_RELATION_DELETE_FAIL);
			}
		}catch(DAOException e){
			throw new ManagerException(e);
		}
		ServiceResult result = new ServiceResult(true);
		return result;
	}
	
	public ServiceResult deleteUserDeviceRelationByUserId(long userId) throws ManagerException{
		try{
			int row = userDeviceRelationDao.deleteUserDeviceRelationByUserId(userId);
			if(row == 0){
				return MsgUtils.fillMsg(MsgEnum.USER_DEVICE_RELATION_DELETE_FAIL);
			}
		}catch(DAOException e){
			throw new ManagerException(e);
		}
		ServiceResult result = new ServiceResult(true);
		return result;
	}
}
