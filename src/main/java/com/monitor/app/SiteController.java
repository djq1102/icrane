package com.monitor.app;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.monitor.app.dataobject.Site;
import com.monitor.app.result.ServiceResult;
import com.monitor.app.service.SiteService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class SiteController {
	
	private static final Logger logger = LoggerFactory.getLogger(SiteController.class);
	
	@Resource
	private SiteService siteService;
	
	@RequestMapping(value = "/site/add")
	public String add(@RequestParam("siteName") String siteName, @RequestParam("siteAddress") String siteAddress, 
			@RequestParam("location") String location, @RequestParam("customerId") long customerId,
			@RequestParam("contactName") String contactName, @RequestParam("contactPhone") String contactPhone,
			@RequestParam("contactEmail") String contactEmail, Model model) throws Exception{
		Site site = new Site();
		site.setSiteName(siteName);
		site.setSiteAddress(siteAddress);
		site.setContactName(contactName);
		site.setCustomerId(customerId);
		site.setContactName(contactName);
		site.setContactEmail(contactEmail);
		site.setContactPhone(contactPhone);
		site.setLocation(location);
		ServiceResult result	= siteService.addSite(site);
		model.addAttribute("result", result);
		ServiceResult serviceResult = siteService.queryAllSite();
		if(serviceResult.isSuccess()){
			List<Site> users  = (List<Site>)serviceResult.getModule();
			model.addAttribute("users", users);
		}else{
			model.addAttribute("result2", serviceResult);
		}
		return "";
	}
	
}
