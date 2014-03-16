package com.monitor.app.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.monitor.app.dataobject.Customer;
import com.monitor.app.dataobject.Site;
import com.monitor.app.exception.ManagerException;
import com.monitor.app.query.SiteQuery;
import com.monitor.app.result.ServiceResult;
import com.monitor.app.service.CustomerService;
import com.monitor.app.service.SiteService;
import com.monitor.app.utils.JsonUtil;
import com.monitor.app.utils.MsgUtils;

/**
 * Handles requests for the application home page.
 */
@Controller
public class SiteController {
	
	private static final Logger logger = LoggerFactory.getLogger(SiteController.class);
	
	@Resource
	private SiteService siteService;
	@Resource
	private CustomerService customerService;
	
	@RequestMapping(value = "/site/index",method = {RequestMethod.POST,RequestMethod.GET})
	public String index(Model model,Site site) throws ManagerException {
		logger.warn(">>>action=index");
		model.addAttribute("customers", queryAllCustomers());
		model.addAttribute("siteName", site.getSiteName());
		model.addAttribute("status", site.getStatus());
		model.addAttribute("customerId", site.getCustomerId());
		return "site/site";
	}

	@RequestMapping(value = "/site/siteInput",method = RequestMethod.GET)
	public String siteInput(Model model) throws ManagerException {
		logger.warn(">>>action=siteInput");
		model.addAttribute("customers", queryAllCustomers());
		return "/site/site_input";
	}
	
	@RequestMapping(value = "/site/addSite")
	public String addSite(Site site,Model model) throws Exception{
		site.setLocation("1204,32");
		ServiceResult result	= siteService.addSite(site);
		if(!result.isSuccess()){
			logger.error(">>>action=addSite error" + site.getSiteName());
		}
		return "redirect:index.htm";
	}
	
	@RequestMapping(value = "/site/editSite")
	public String editSite(@RequestParam("siteId") long siteId,Model model) throws Exception{
		ServiceResult result = siteService.queryBySiteId(siteId);
		if(result.isSuccess()){
			Site site  = (Site)result.getModule();
			model.addAttribute("customers", queryAllCustomers());
			model.addAttribute("site", site);
		}else{
			model.addAttribute("msg", MsgUtils.MSG_FAIL);
		}
		return "site/site_edit_input";
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/site/querySiteList",method = RequestMethod.GET,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String querySiteList(@RequestParam("iDisplayStart") int start, @RequestParam("iDisplayLength") int pagesize, @RequestParam("sEcho") int sEcho,
			Site site) throws ManagerException {
		logger.warn(">>>action=querySiteList" + site.getSiteName());
		SiteQuery query = new SiteQuery();
		query.setSiteName(site.getSiteName());
		query.setStatus(site.getStatus());
		//query.setCustomerId(site.getCustomerId());
		query.setBegin(start);
		query.setEnd(start+pagesize);
		ServiceResult result = siteService.queryAllSite(query);
		ServiceResult numResult = siteService.totalCount(query);
		List<Site> siteList  = (List<Site>)result.getModule();
		List<Map> resultMap = buildList(siteList);
		return JsonUtil.buildJosn(resultMap, numResult, sEcho);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<Map> buildList(List<Site> siteList){
		List<Map> result = new ArrayList<Map>();
		for(Site site:siteList){
			Map map = new HashMap();
			map.put("0", site.getSiteId());
			map.put("1", site.getCustomerId());
			map.put("2", site.getSiteName());
			map.put("3", site.getContactName());
			map.put("4", site.getContactPhone());
			map.put("5", site.getContactEmail());
			map.put("6", site.getStatus());
			map.put("7", site.getSiteId());
			result.add(map);
		}
		return result;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private List<Customer> queryAllCustomers() throws ManagerException{
		ServiceResult result = customerService.queryAllCustomers();
		List<Customer> customers = (List)result.getModule();
		return customers;
	}
}
