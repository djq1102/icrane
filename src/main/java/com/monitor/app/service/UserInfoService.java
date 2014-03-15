/**
 * 
 */
package com.monitor.app.service;

import java.util.List;

import javax.annotation.Resource;

import com.monitor.app.dao.userinfo.UserInfoQueryDao;
import com.monitor.app.dataobject.UserInfo;
import com.monitor.app.exception.DAOException;
import com.monitor.app.query.UserInfoQuery;
import com.monitor.app.result.ServiceResult;
import com.monitor.app.result.msg.MsgEnum;
import com.monitor.app.utils.MsgUtils;

/**
 * @author Administrator
 *
 */
public class UserInfoService {

	@Resource
	private UserInfoQueryDao userInfoQueryDao;
	
	public ServiceResult queryUser(String nick){
		UserInfo userInfo = userInfoQueryDao.queryUserByNick(nick);
		if(userInfo==null){
			return MsgUtils.fillMsg(MsgEnum.USER_NOT_EXIST);
		}
		
		return MsgUtils.fillModule(userInfo);
	}
	
	public ServiceResult userInfoAdd(UserInfo userInfo){
		int row = userInfoQueryDao.addUserInfo(userInfo);
		if(row != 1){
			return MsgUtils.fillMsg(MsgEnum.USER_ADD_FAIL);
		}
		return MsgUtils.fillModule(userInfo);
	}
	
	public ServiceResult userInfoEdit(UserInfo userInfo) {
		int row = userInfoQueryDao.updateUserInfo(userInfo);
		if(row != 1){
			return MsgUtils.fillMsg(MsgEnum.USER_UPDATE_FAIL);
		}
		return MsgUtils.fillModule(userInfo);
	}
	
	public ServiceResult userInfoDelte(long userId) {
		int row = userInfoQueryDao.deleteUserInfo(userId);
		if(row != 1){
			return MsgUtils.fillMsg(MsgEnum.USER_UPDATE_FAIL);
		}
		ServiceResult result = new ServiceResult(true);
		return result;
	}
	
   public ServiceResult queryUserInfoByuserId(long userId){
		UserInfo userInfo = userInfoQueryDao.queryUserInfoByUserId(userId);
		if(userInfo==null){
			return MsgUtils.fillMsg(MsgEnum.USER_NOT_EXIST);
		}
		return MsgUtils.fillModule(userInfo);
	}
	
	public ServiceResult queryUserInfoByCustomerId(long customerId){
		UserInfoQuery userInfoQuery = new UserInfoQuery();
		userInfoQuery.setCustomerId(customerId);
		List<UserInfo> userInfoList =  userInfoQueryDao.queryUserInfo(userInfoQuery);
		if(userInfoList == null){
			return MsgUtils.fillMsg(MsgEnum.FAIL_USER_QUERY);
		}
		return MsgUtils.fillModule(userInfoList);
	} 
		
	public ServiceResult queryAllUserInfo(UserInfoQuery UserInfoQuery){
		List<UserInfo> userInfoList =  userInfoQueryDao.queryUserInfo(UserInfoQuery);
		if(userInfoList == null){
			return MsgUtils.fillMsg(MsgEnum.FAIL_USER_QUERY);
		}
		return MsgUtils.fillModule(userInfoList);
	}
	
	public ServiceResult totalCount(UserInfoQuery userInfoQuery){
		try {
			int totalNum = userInfoQueryDao.countAllUserInfo(userInfoQuery);
			return MsgUtils.fillModule(totalNum);
		} catch (DAOException e) {
			return MsgUtils.fillMsg(MsgEnum.FAIL_USER_QUERY);
		}
	}
	
	public ServiceResult queryUsersByPage(int page,int pagesize){
		UserInfoQuery userInfoQuery = new UserInfoQuery();
		userInfoQuery.setCurrentPage(page);
		userInfoQuery.setPageSize(pagesize);
		List<UserInfo> userInfoList =  userInfoQueryDao.queryUserInfo(userInfoQuery);
		if(userInfoList == null){
			return MsgUtils.fillMsg(MsgEnum.FAIL_USER_QUERY);
		}
		return MsgUtils.fillModule(userInfoList);
	}
	
}
