package com.monitor.app.controller.device;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
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

import com.alibaba.fastjson.JSONObject;
import com.monitor.app.controller.AbstractController;
import com.monitor.app.dataobject.Device;
import com.monitor.app.dataobject.Site;
import com.monitor.app.dataobject.UserDeviceRelation;
import com.monitor.app.dataobject.UserInfo;
import com.monitor.app.exception.ManagerException;
import com.monitor.app.result.ServiceResult;
import com.monitor.app.service.DeviceService;
import com.monitor.app.service.SiteService;
import com.monitor.app.service.UserDeviceRelationService;
import com.monitor.app.service.UserInfoService;

/**
 * Handles requests for the application home page.
 */

@Controller
public class DeviceUserController  extends AbstractController{

	private static final Logger logger = LoggerFactory.getLogger(DeviceUserController.class);
	@Resource
	private SiteService siteService;
	@Resource
	private DeviceService deviceService;
	@Resource
	private UserInfoService userInfoService;
	@Resource
	private UserDeviceRelationService userDeviceRelationService;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 * @throws ManagerException 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/deviceuser/adddevices", method = RequestMethod.GET)
	public String addDevice(@RequestParam("userId") long userId, Model model) throws ManagerException {
		logger.info("[设备分配查询]");
		ServiceResult userInfoResult = userInfoService.queryUserInfoByuserId(userId);
		if(userInfoResult.isSuccess()){
			UserInfo userInfo = (UserInfo)userInfoResult.getModule();
			ServiceResult siteResult = siteService.querySiteByCustomerId(userInfo.getCustomerId());
			List<Site> sites = (List)siteResult.getModule();
			Map<Long,Map<String,List<Device>>> siteMap = new HashMap<Long,Map<String,List<Device>>>();
			for(Site site : sites){
				List<Device> deviceList = new ArrayList<Device>();
				Map<String,List<Device>> deviceMap = new HashMap<String,List<Device>>();
				ServiceResult deviceResult = deviceService.queryBySiteId(site.getSiteId());
				if(deviceResult.isSuccess()){
					deviceList = (List)deviceResult.getModule();
					if(deviceList != null && deviceList.size() == 0){
						continue;
					}
					deviceMap.put(site.getSiteName(), deviceList);
					siteMap.put(site.getSiteId(), deviceMap);
				}
			}
			model.addAttribute("siteMap", siteMap);
			model.addAttribute("userId", userId);
		}
		return "deviceuser/deviceuser";
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes"})
	@RequestMapping(value = "/deviceuser/queryDeviceUserByuserId",method = {RequestMethod.GET,RequestMethod.POST},produces = "application/json; charset=utf-8")
	@ResponseBody
	public String queryDeviceUserByuserId(@RequestParam("userId") Long userId) throws ManagerException{
		logger.warn(">>>action=querySiteListBycustomerId" + userId);
		ServiceResult result = userDeviceRelationService.queryUserDeviceRelationByUserId(userId);
		List<UserDeviceRelation> userDeviceRelations = (List)result.getModule();
		String[] deviceIds = new String[userDeviceRelations.size()];
		for(int i=0;i<userDeviceRelations.size();i++){
			deviceIds[i] = String.valueOf(userDeviceRelations.get(i).getDeviceId());
		}
		Map<String,String[]> maps = new HashMap<String, String[]>(); 
		maps.put("deviceIds", deviceIds);
		logger.warn(">>>action=querySiteListBycustomerId:" + maps);
		return JSONObject.toJSONString(maps);
		
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/deviceuser/addcommit", method = {RequestMethod.GET,RequestMethod.POST})
	public String addCommit(Locale locale, Model model,String[] my_multi_select2,@RequestParam("userId") long userId) throws ManagerException {
		logger.info("[设备分配查询]分配数据："+ my_multi_select2);
		ServiceResult userDeviceRelationResult = userDeviceRelationService.queryUserDeviceRelationByUserId(userId);
		
		if(userDeviceRelationResult.isSuccess()){
			List<UserDeviceRelation> userDeviceRelations = (List)userDeviceRelationResult.getModule();
			if(userDeviceRelations.size() > 0){
				userDeviceRelationService.deleteUserDeviceRelationByUserId(userId);
			}
		}
		
		List<UserDeviceRelation> userDeviceRelations = new ArrayList<UserDeviceRelation>();
		if(my_multi_select2 != null){
			for(String deviceId : my_multi_select2){
				ServiceResult deviceResult = deviceService.queryByDeviceId(Long.valueOf(deviceId));
				if(deviceResult.isSuccess()){
					Device device = (Device)deviceResult.getModule();
					UserDeviceRelation relation = new UserDeviceRelation();
					relation.setDeviceId(device.getDeviceId());
					relation.setDeviceName(device.getDeviceName());
					relation.setSiteId(device.getSiteId());
					relation.setUserId(userId);
					userDeviceRelations.add(relation);
				}
			}
			ServiceResult result = userDeviceRelationService.addUserDeviceRelation(userDeviceRelations);
			if(!result.isSuccess()){
				logger.warn("[设备分配查询]分配数据失败："+ my_multi_select2);
				model.addAttribute("msg", result.getMsg());
				return "error";
			}
		}
	    return "userInfo/userinfo";
	}
}
