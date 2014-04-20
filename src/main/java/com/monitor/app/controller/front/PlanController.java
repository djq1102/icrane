package com.monitor.app.controller.front;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PlanController {

private static final Logger logger = LoggerFactory.getLogger(AccController.class);
	
	@RequestMapping(value = "/front/plan/index")
	public String index(@RequestParam("deviceId") long deviceId, Model model) throws Exception{
		logger.warn(">>>action=index");
		
		model.addAttribute("deviceId", deviceId);
		return "front/plan/index";
	}

}
