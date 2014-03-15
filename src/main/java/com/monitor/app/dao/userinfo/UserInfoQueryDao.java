/**
 * 
 */
package com.monitor.app.dao.userinfo;

import java.util.List;

import com.monitor.app.dataobject.UserInfo;
import com.monitor.app.exception.DAOException;
import com.monitor.app.query.UserInfoQuery;

/**
 * @author Administrator
 *
 */
public interface UserInfoQueryDao {

	public UserInfo queryUserByNick(String nick);
	
	public UserInfo queryUserInfoByUserId(long userId);
	
	public int addUserInfo(UserInfo userInfo);
	
    public int updateUserInfo(UserInfo userInfo);
    
    public int deleteUserInfo(long userId);
    
	public List<UserInfo> queryUserInfo(UserInfoQuery userInfoQuery);
	
	public int countAllUserInfo(UserInfoQuery userInfoQuery) throws DAOException;
	
}
