package com.monitor.app.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.monitor.app.dao.user.UserSiteRelationDao;
import com.monitor.app.dataobject.UserSiteRelation;
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
public class UserSiteRelationService {
	
	@Resource
	private UserSiteRelationDao userSiteRelationDao;
	
	public ServiceResult addUserSiteRelation(List<UserSiteRelation> userSiteRelations) throws ManagerException{
		try{
			int row = userSiteRelationDao.batchInsertUserSiteRelation(userSiteRelations);
			if(row == 0){
				return MsgUtils.fillMsg(MsgEnum.USER_SITE_RELATION_ADD_FAIL);
			}
		}catch(DAOException e){
			throw new ManagerException(e);
		}
		ServiceResult result = new ServiceResult(true);
		return result;
	}

	public ServiceResult queryUserSiteRelationByUserId(long userId) throws ManagerException{
		List<UserSiteRelation> userSiteRelations = new ArrayList<UserSiteRelation>();
		try {
			userSiteRelations = userSiteRelationDao.queryUserSiteRelationByUserId(userId);
		} catch (DAOException e) {
			throw new ManagerException(e);
		}
		return MsgUtils.fillModule(userSiteRelations);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ServiceResult deleteUserSiteRelationBysiteIds(long userId,List<String> siteIds) throws ManagerException{
		Map params = new HashMap();
		params.put("userId", userId);
		params.put("siteIds", siteIds);
		try{
			int row = userSiteRelationDao.batchDeleteUserSiteRelationBySiteId(params);
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
