package com.monitor.app.dao.user;

import java.util.List;

import com.monitor.app.dataobject.Site;
import com.monitor.app.exception.DAOException;
import com.monitor.app.query.SiteQuery;

public interface SiteDao {
	
	 public int addSite(Site site) throws DAOException;
	 
	 public int delteSiteId(long userId,long siteId) throws DAOException;
	  
	 public Site queryBySiteId(long siteId) throws DAOException;
	 
	 public int updateSite(Site site) throws DAOException;
	 
	 public List<Site> querySite(SiteQuery query) throws DAOException;
	 
	 public List<Site> querySiteByCustomerId(long customerId) throws DAOException;
	 
	 public List<Site> queryAllSites() throws DAOException;
	  
	 public int countAll(SiteQuery query) throws DAOException;
	  
}
   