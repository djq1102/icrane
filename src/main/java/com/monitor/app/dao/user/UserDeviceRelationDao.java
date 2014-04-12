package com.monitor.app.dao.user;

import java.util.List;
import java.util.Map;

import com.monitor.app.dataobject.UserDeviceRelation;
import com.monitor.app.exception.DAOException;

public interface UserDeviceRelationDao {
	
	public int batchInsertUserDeviceRelation(List<UserDeviceRelation> userDeviceRelation) throws DAOException;
	
	@SuppressWarnings("rawtypes")
	public int batchDeleteUserDeviceRelationBydeviceId(Map params) throws DAOException;
	
	@SuppressWarnings("rawtypes")
	public int batchDeleteUserDeviceRelationBysiteId(Map params) throws DAOException;

	public int deleteUserDeviceRelationByUserId(long userId) throws DAOException;
	
	public int deleteUserDeviceRelationByDeviceId(long deviceId) throws DAOException;

	public UserDeviceRelation queryUserDeviceRelationByUserIdAndDeviceId(Map<String,Long> params) throws DAOException;
	
	public List<UserDeviceRelation> queryUserDeviceRelationByUserId(long userId) throws DAOException;

}
