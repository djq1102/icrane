package com.monitor.app.controller.front;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.monitor.app.controller.AbstractController;
import com.monitor.app.controller.CustomerController;

@Controller
public class AccController  extends AbstractController{
	private static final Logger logger = LoggerFactory.getLogger(AccController.class);
	
	@RequestMapping(value = "/front/accessory/index")
	public String index(@RequestParam("deviceId") long deviceId, Model model) throws Exception{
		logger.warn(">>>action=index");
		
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
