package com.monitor.app.dao.user;

import java.util.List;
import java.util.Map;

import com.monitor.app.dataobject.UserSiteRelation;
import com.monitor.app.exception.DAOException;


/**
 * 
 * @author tmac
 *
 */
public interface UserSiteRelationDao {
	
	public int batchInsertUserSiteRelation(List<UserSiteRelation> userSiteRelation) throws DAOException;
	
	@SuppressWarnings("rawtypes")
	public int batchDeleteUserSiteRelationBySiteId(Map map) throws DAOException;
	
	public List<UserSiteRelation> queryUserSiteRelationByUserId(long userId) throws DAOException;

}
