package com.monitor.app.dao.user;

import java.util.List;

import com.monitor.app.dataobject.UserDeviceRelation;

public interface UserDeviceRelationDao {
	
	public int batchInsertUserDeviceRelation(List<UserDeviceRelation> userDeviceRelation);
	
	public int batchDeleteUserDeviceRelationBydeviceId(List<String> deviceIds);
	
	public List<UserDeviceRelation> queryUserDeviceRelationByUserId(long userId);

}
