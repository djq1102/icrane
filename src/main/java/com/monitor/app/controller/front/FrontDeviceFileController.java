package com.monitor.app.controller.front;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.monitor.app.dataobject.DeviceDoc;
import com.monitor.app.query.DeviceDocQuery;
import com.monitor.app.result.ServiceResult;
import com.monitor.app.service.DeviceDocService;
@Controller
public class FrontDeviceFileController {

	@Resource
	private DeviceDocService deviceDocService;
	
	@RequestMapping(value = "/front/devicefile/index")
	public String index(@RequestParam("deviceId") long deviceId, Model model) throws Exception{
		model.addAttribute("deviceId", deviceId);
		
		DeviceDocQuery query = new DeviceDocQuery();
		query.setDeviceId(deviceId);
		ServiceResult result = deviceDocService.queryDocs(query);
		if(!result.isSuccess()){
			model.addAttribute("error", "查询设备文案失败");
			return "redirect:error.vm";
		}
		
		List<DeviceDoc> docs = (List<DeviceDoc>)result.getModule();
		int size = docs.size()/4;
		if(size!=0) size+=1;
		
		List<List<DeviceDoc>> groupDocs = new ArrayList<List<DeviceDoc>>(size);
		List<DeviceDoc> groupDocList =null;
		for(int i=0;i<docs.size();i++){
			if(i%4==0){
				groupDocList = new ArrayList<DeviceDoc>();
				groupDocs.add(groupDocList);
			}
			groupDocList.add(docs.get(i));
		}
		
		model.addAttribute("groupDocs", groupDocs);
		
		return "front/devicefile/index";
	}
}
