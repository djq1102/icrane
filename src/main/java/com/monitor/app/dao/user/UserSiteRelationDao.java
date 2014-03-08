package com.monitor.app.dao.user;

import java.util.List;

import com.monitor.app.dataobject.UserSiteRelation;


/**
 * 
 * @author tmac
 *
 */
public interface UserSiteRelationDao {
	
	public int batchInsertUserSiteRelation(List<UserSiteRelation> userSiteRelation);
	
	public int batchDeleteUserSiteRelationBySiteId(List<String> siteIds);
	
	public List<UserSiteRelation> queryUserSiteRelationByUserId(long userId);

}
