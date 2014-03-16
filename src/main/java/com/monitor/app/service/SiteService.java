package com.monitor.app.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.monitor.app.dao.user.SiteDao;
import com.monitor.app.dataobject.Site;
import com.monitor.app.exception.DAOException;
import com.monitor.app.exception.ManagerException;
import com.monitor.app.query.SiteQuery;
import com.monitor.app.result.ServiceResult;
import com.monitor.app.result.msg.MsgEnum;
import com.monitor.app.utils.MsgUtils;

/**
 * 
 * @author tmac
 *
 */

@Service
public class SiteService {
	
	@Resource
	private SiteDao siteDao;

	public ServiceResult addSite(Site site) throws ManagerException{
		try{
			int row = siteDao.addSite(site);
			if(row != 1){
				return MsgUtils.fillMsg(MsgEnum.SITE_ADD_FAIL);
			}
		}catch(DAOException e){
			throw new ManagerException(e);
		}
		return MsgUtils.fillModule(site);
	}
	
	public ServiceResult queryBySiteId(long siteId) throws ManagerException{
		Site site ;
		try {
			site = siteDao.queryBySiteId(siteId);
			if(site == null){
				return MsgUtils.fillMsg(MsgEnum.SITE_NOT_EXIST);
			}
		} catch (DAOException e) {
			throw new ManagerException(e);
		}
		return MsgUtils.fillModule(site);
	}
	
	public ServiceResult updateSite(Site site) throws ManagerException{
		try {
			int rows = siteDao.updateSite(site);
			if(rows != 1){
				return MsgUtils.fillMsg(MsgEnum.SITE_UPDATE_FAIL);
			}
		} catch (DAOException e){
			throw new ManagerException(e);
		}
		return MsgUtils.fillModule(site);
	}
	
	public ServiceResult totalCount(SiteQuery query) throws ManagerException{
		int rows = 0;
		try {
			rows = siteDao.countAll(query);
		} catch (DAOException e){
			throw new ManagerException(e);
		}
		return MsgUtils.fillModule(rows);
	}
	
	public ServiceResult queryAllSite(SiteQuery query) throws ManagerException{
		List<Site> siteList = new ArrayList<Site>();
		try{
			siteList = siteDao.querySite(query);
		}catch(DAOException e){
			throw new ManagerException(e);
		}
		ServiceResult result = new ServiceResult(true);
		result.setModule(siteList);
		return result;
	}
	
}
