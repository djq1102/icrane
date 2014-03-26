/**
 * 
 */
package com.monitor.app.controller.accessory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.monitor.app.controller.AbstractController;
import com.monitor.app.dataobject.DeviceAccessoryBindDO;
import com.monitor.app.result.ServiceResult;
import com.monitor.app.service.AccessoryService;
import com.monitor.app.service.DeviceService;

/**
 * @author ibm
 *
 */
@Controller
public class DeviceAccessoryController extends AbstractController{

	
	@Resource
	private AccessoryService accessoryService;
	@Resource
	private DeviceService deviceService;
	
	@RequestMapping(value = "/device/bindAcc")
	public String bind(@RequestParam("deviceId") long deviceId, 
			@RequestParam("addedAccessoryIds") String addedAccessoryIds,
			@RequestParam("deletedBindIds") String deletedBindIds,
			Model model) throws Exception{
		
		
		//1.先删除所有配件绑定关系。.
		
		
		//2.关联现在的配件
		
		
		return "redirect:device/index";
	}
	
	@RequestMapping(value = "/device/toBindAcc/{deviceId}")
	public String toBind(@PathVariable("deviceId") long deviceId, Model model) throws Exception{
		
		//1.所有配件
		ServiceResult allAccResult = accessoryService.queryAllAccessorys();
		if(!allAccResult.isSuccess()){
			model.addAttribute("msg", allAccResult.getMsg());
			return "error";
		}
		model.addAttribute("accs", allAccResult.getModule());
		model.addAttribute("deviceId", deviceId);
		
		return "deviceaccessory/device_accessory";
	}
	
	@RequestMapping(value = "/device/queryBindData/{deviceId}",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String queryBindData(@PathVariable("deviceId") long deviceId, Model model) throws Exception{
		
		//0.有效的设备id
		ServiceResult deviceQueryResult = deviceService.queryByDeviceId(deviceId);
		if(!deviceQueryResult.isSuccess()){
			model.addAttribute("msg", deviceQueryResult.getMsg());
			return "error";
		}
		
		//1.查询该设备以及绑定的配件
		ServiceResult accBindResult = deviceService.queryDeviceAccBindData(deviceId);
		if(!accBindResult.isSuccess()){
			model.addAttribute("msg", accBindResult.getMsg());
			return "error";
		}
		
		List<DeviceAccessoryBindDO> accBindDOList = (List<DeviceAccessoryBindDO>)accBindResult.getModule();
		model.addAttribute("accBinds", accBindDOList);
		
		Map<String,List> maps = new HashMap<String, List>(); 
		List<String> bindIds = new ArrayList<String>(accBindDOList.size());
		for(DeviceAccessoryBindDO bindDO : accBindDOList){
			bindIds.add(String.valueOf(bindDO.getId()));
		}
		maps.put("bindIds", bindIds);
		
		return JSONObject.toJSONString(maps);
	}

}
