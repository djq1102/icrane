package com.monitor.app.controller;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller
public class DeviceUserController {

	private static final Logger logger = LoggerFactory.getLogger(DeviceUserController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/deviceuser/adddevices", method = RequestMethod.GET)
	public String addDevice(Locale locale, Model model) {
		logger.info("[设备分配查询]");
		
		return "deviceuser/deviceuser";
	}
	
	@RequestMapping(value = "/deviceuser/addcommit", method = {RequestMethod.GET,RequestMethod.POST})
	public String addCommit(Locale locale, Model model,String[] my_multi_select2) {
		logger.info("[设备分配查询]分配数据："+my_multi_select2);
		return "deviceuser/deviceuser";
	}
}
