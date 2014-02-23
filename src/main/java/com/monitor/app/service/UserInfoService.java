/**
 * 
 */
package com.monitor.app.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.monitor.app.dao.userinfo.UserInfoQueryDao;
import com.monitor.app.dataobject.UserInfo;
import com.monitor.app.result.ServiceResult;
import com.monitor.app.result.msg.MsgEnum;
import com.monitor.app.utils.MsgUtils;

/**
 * @author Administrator
 *
 */
@Service
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
	
}
