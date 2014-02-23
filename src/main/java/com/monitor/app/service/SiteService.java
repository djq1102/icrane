package com.monitor.app.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.monitor.app.dao.user.SiteDao;
import com.monitor.app.dataobject.Site;
import com.monitor.app.exception.DAOException;
import com.monitor.app.exception.ManagerException;
import com.monitor.app.result.ServiceResult;

@Service
public class SiteService {
	
	@Resource
	private SiteDao siteDao;

	public ServiceResult addSite(Site site) throws ManagerException{
		try{
			siteDao.addSite(site);
		}catch(DAOException e){
			throw new ManagerException(e);
		}
		ServiceResult result = new ServiceResult(true);
		return result;
	}
	
	
	public ServiceResult queryAllSite() throws ManagerException{
		List<Site> siteList = new ArrayList<Site>();
		try{
			siteList = siteDao.queryAllSite();
		}catch(DAOException e){
			throw new ManagerException(e);
		}
		ServiceResult result = new ServiceResult(true);
		result.setModule(siteList);
		return result;
	}
	
}
