package com.monitor.app.controller.front;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.monitor.app.controller.AbstractController;
import com.monitor.app.dataobject.DeviceAccessoryBindDO;
import com.monitor.app.result.ServiceResult;
import com.monitor.app.service.AccessoryService;
import com.monitor.app.service.DeviceService;

@Controller
public class AccController  extends AbstractController{
	private static final Logger logger = LoggerFactory.getLogger(AccController.class);
	
	@Resource
	private DeviceService deviceService;
	@Resource
	private AccessoryService accessoryService;
	
	@RequestMapping(value = "/front/accessory/index")
	public String index(@RequestParam("deviceId") long deviceId, 
			@RequestParam(value="accType",required=true,defaultValue="0") int accType,Model model) throws Exception{
		logger.warn(">>>action=index");
		
		//1.根据设备id查询配件id
		ServiceResult result = deviceService.queryDeviceAccBindData(deviceId);
		if(!result.isSuccess()){
			return "error";
		}
		
		List<Long> accIds = new ArrayList<Long>();
		List<DeviceAccessoryBindDO> accessoryBindList = (List<DeviceAccessoryBindDO>)result.getModule();
		for(DeviceAccessoryBindDO accBind: accessoryBindList){
			accIds.add(accBind.getAccessoryId());
		}
		//2.查询配件ids的机械配件或电气配件
		ServiceResult queryResult = accessoryService.queryByAccessoryIds(accIds, accType);
		if(!queryResult.isSuccess()){
			return "error";
		}
		//3.配件list
		model.addAttribute("accs", queryResult.getModule());
		model.addAttribute("accType", accType);
		model.addAttribute("deviceId", deviceId);
		return "front/accessory/index";
	}
	
	@RequestMapping(value = "/front/accessory/addToCart")
	public String addCart(@RequestParam("deviceId") long deviceId,Model model){
		logger.warn(">>>action=addCart");
		
		model.addAttribute("deviceId", deviceId);
		return "front/accessory/addcart";
	}
}
