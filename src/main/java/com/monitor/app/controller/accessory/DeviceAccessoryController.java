/**
 * 
 */
package com.monitor.app.controller.accessory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

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
	public String bind(@RequestParam("deviceId") long deviceId, String[] my_multi_select2, 
			Model model, HttpSession session) throws Exception{
		//1.先删除设备的配件绑定关系。.
		ServiceResult delResult = deviceService.batchDeleteAccBind(deviceId);
		if(!delResult.isSuccess()){
			model.addAttribute("msg", "删除老的关联数据失败");
			return "error";
		}
		//2.关联现在的配件
		long userId = getUserId(session);
		if(my_multi_select2!=null){
			List<DeviceAccessoryBindDO> binds = new ArrayList<DeviceAccessoryBindDO>();
			for(String accId : my_multi_select2){
				DeviceAccessoryBindDO bind = new DeviceAccessoryBindDO();
				bind.setDeviceId(deviceId);
				bind.setAccessoryId(Long.parseLong(accId));
				bind.setUserId(userId);
				binds.add(bind);
			}
			deviceService.batchAddAccBinds(binds);
		}
		
		return "redirect:/device/toBindAcc/"+deviceId;
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
			bindIds.add(String.valueOf(bindDO.getAccessoryId()));
		}
		maps.put("bindIds", bindIds);
		
		return JSONObject.toJSONString(maps);
	}

}
