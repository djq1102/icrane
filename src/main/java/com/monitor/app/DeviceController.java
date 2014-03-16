package com.monitor.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.monitor.app.dataobject.Device;


@Controller
public class DeviceController {
	
	private static final Logger logger = LoggerFactory.getLogger(DeviceController.class);
	
	@RequestMapping(value = "/device/add")
	public String add(@RequestParam("type") int type, @RequestParam("name") String name, 
			@RequestParam("address") String address, @RequestParam("contactName") String contactName,
			@RequestParam("contactPhone") String contactPhone, 
			@RequestParam("contactEmail") String contactEmail, Model model) {
		
		return "device/deviceAdd";
	}
	
	@RequestMapping(value = "/device/index",method = {RequestMethod.POST,RequestMethod.GET})
	public String index(Model model,Device device) {
		logger.warn(">>>action=index");
		return "device/device";
	}


}
