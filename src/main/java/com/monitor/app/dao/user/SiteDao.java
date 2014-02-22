package com.monitor.app.dao.user;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.monitor.app.dataobject.Site;
import com.monitor.app.exception.DAOException;

public interface SiteDao {
	
	  @Select("insert into cr_site (site_name,site_address,location,customer_id,contact_name,contact_phone,contact_email,status,gmt_create,gmt_modify) "
		  		+ "values (#{site.siteName}, #{site.siteAddress}, #{site.location}, #{site.customerId}, #{site.contactName}, #{site.contactPhone},#{site.contactEmail},#{site.status},now(),now())")
	  void addSite(@Param("Site") Site site) throws DAOException;
	  
	  @Select("SELECT * FROM cr_site")
	  List<Site> queryAllSite()throws DAOException;
	  
	  @Select("SELECT count(*) as total FROM cr_site")
	  int totalCount() throws DAOException;
	  
}
   