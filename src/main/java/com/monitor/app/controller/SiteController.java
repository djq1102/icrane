package com.monitor.app.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.monitor.app.dataobject.Customer;
import com.monitor.app.dataobject.Device;
import com.monitor.app.dataobject.Site;
import com.monitor.app.dataobject.UserInfo;
import com.monitor.app.exception.ManagerException;
import com.monitor.app.query.SiteQuery;
import com.monitor.app.result.ServiceResult;
import com.monitor.app.service.CustomerService;
import com.monitor.app.service.DeviceService;
import com.monitor.app.service.SiteService;
import com.monitor.app.service.UserInfoService;
import com.monitor.app.utils.JsonUtil;
import com.monitor.app.utils.MsgUtils;
import com.monitor.app.validator.SiteValidator;

/**
 * Handles requests for the application home page.
 */
@Controller
public class SiteController extends AbstractController{
	
	private static final Logger logger = LoggerFactory.getLogger(SiteController.class);
	
	@Resource
	private SiteService siteService;
	@Resource
	private UserInfoService userInfoService;
	@Resource
	private CustomerService customerService;
	@Resource
	private DeviceService deviceService;
	
	@InitBinder  
	public void initBinder(DataBinder binder) {  
	   binder.setValidator(new SiteValidator());  
	}  
	
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
	public String addSite(@Valid @ModelAttribute("site") Site site,BindingResult bingResult,Model model) throws Exception{
		site.setLocation("1204,32");
		if(bingResult.hasFieldErrors()){
			if(bingResult.getFieldError("siteName") != null){
				model.addAttribute("siteErrorName", bingResult.getFieldError("siteName").getDefaultMessage());
			}
			if(bingResult.getFieldError("siteAddress") != null){
				model.addAttribute("siteErrorAddress", bingResult.getFieldError("siteAddress").getDefaultMessage());
			}
			if(bingResult.getFieldError("contactName") != null){
				model.addAttribute("contactErrorName",bingResult.getFieldError("contactName").getDefaultMessage());
			}
			if(bingResult.getFieldError("contactEmail") != null){
				model.addAttribute("contactErrorEmail",bingResult.getFieldError("contactEmail").getDefaultMessage());
			}
			if(bingResult.getFieldError("contactPhone") != null){
				model.addAttribute("contactErrorPhone",bingResult.getFieldError("contactPhone").getDefaultMessage());
			}
			return siteInput(model);
		}
		ServiceResult result	= siteService.addSite(site);
		if(!result.isSuccess()){
			logger.error(">>>action=addSite error" + site.getSiteName());
		}
		return "redirect:index.htm";
	}
	
	@RequestMapping(value = "/site/updateSite")
	public String updateSite(@Valid @ModelAttribute("site") Site site,BindingResult bingResult,Model model) throws Exception{
				ServiceResult result = siteService.queryBySiteId(site.getSiteId());
		if(result.isSuccess()){
			if(bingResult.hasFieldErrors()){
				if(bingResult.getFieldError("siteName") != null){
					model.addAttribute("siteErrorName", bingResult.getFieldError("siteName").getDefaultMessage());
				}
				if(bingResult.getFieldError("siteAddress") != null){
					model.addAttribute("siteErrorAddress", bingResult.getFieldError("siteAddress").getDefaultMessage());
				}
				if(bingResult.getFieldError("contactName") != null){
					model.addAttribute("contactErrorName",bingResult.getFieldError("contactName").getDefaultMessage());
				}
				if(bingResult.getFieldError("contactEmail") != null){
					model.addAttribute("contactErrorEmail",bingResult.getFieldError("contactEmail").getDefaultMessage());
				}
				if(bingResult.getFieldError("contactPhone") != null){
					model.addAttribute("contactErrorPhone",bingResult.getFieldError("contactPhone").getDefaultMessage());
				}
				return editSite(site.getSiteId(),model);
			}
			ServiceResult updateresult = siteService.updateSite(site);
			if(!updateresult.isSuccess()){
				logger.error(">>>action=updateSite error" + site.getSiteName());
			}
		}else{
			logger.error(">>>action=queryBySiteId error" + site.getSiteId());
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
		query.setCustomerId(site.getCustomerId());
		query.setBegin(start);
		query.setEnd(start+pagesize);
		ServiceResult result = siteService.queryAllSite(query);
		ServiceResult numResult = siteService.totalCount(query);
		List<Site> siteList  = (List<Site>)result.getModule();
		List<Map> resultMap = buildList(siteList);
		return JsonUtil.buildJosn(resultMap, numResult, sEcho);
	}
	
	@RequestMapping(value = "/site/deleteSite",method = RequestMethod.GET)
	public String deleteSite(@RequestParam("siteId") long siteId,Model model,HttpSession session) throws ManagerException {
		logger.warn(">>>action=edit" );
		long userId = getUserId(session);
		ServiceResult siteResult = deviceService.queryBySiteId(siteId);
		if(siteResult.isSuccess()){
			List<Device> devices = (List<Device>)siteResult.getModule();
			if(!devices.isEmpty()){
				return "error";
			}
		}
		ServiceResult sericeResult = userInfoService.queryUserInfoByuserId(userId);
		if(sericeResult.isSuccess()){
			UserInfo userInfo = (UserInfo)sericeResult.getModule();
			ServiceResult relationResult = siteService.deteleSite(userInfo.getCustomerId(),siteId);
		    if(relationResult.isSuccess()){
		    	return "";
		    }else{
		    	return "error";
		    }

		}else{
			return "";
		}
		
	}
	
	@SuppressWarnings({ "unchecked"})
	@RequestMapping(value = "/site/querySiteListBycustomerId",method = {RequestMethod.GET,RequestMethod.POST},produces = "application/json; charset=utf-8")
	@ResponseBody
	public String querySiteListBycustomerId(@RequestParam("customerId") Long customerId) throws ManagerException{
		logger.warn(">>>action=querySiteListBycustomerId" + customerId);
		ServiceResult result = siteService.querySiteByCustomerId(customerId);
		List<Site> siteList  = (List<Site>)result.getModule();
		return JSONObject.toJSONString(siteList);
		
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
