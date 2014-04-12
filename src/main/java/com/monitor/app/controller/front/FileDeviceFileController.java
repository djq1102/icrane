package com.monitor.app.controller.front;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
@Controller
public class FileDeviceFileController {

	@RequestMapping(value = "/front/devicefile/index")
	public String index(@RequestParam("deviceId") long deviceId, Model model) throws Exception{
		model.addAttribute("deviceId", deviceId);
		return "front/devicefile/index";
	}
}