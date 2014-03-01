package com.monitor.app;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class DeviceController {
	
	
	@RequestMapping(value = "/device/add")
	public String add(@RequestParam("type") int type, @RequestParam("name") String name, 
			@RequestParam("address") String address, @RequestParam("contactName") String contactName,
			@RequestParam("contactPhone") String contactPhone, 
			@RequestParam("contactEmail") String contactEmail, Model model) {
		
		return "device/deviceAdd";
	}
	


}
