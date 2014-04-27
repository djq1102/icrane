package com.monitor.app.controller.device;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.monitor.app.controller.AbstractController;

/**
 * Handles requests for the application home page.
 */
@Controller
public class DeviceDataHistoryController  extends AbstractController{ 
	
	private static final Logger logger = LoggerFactory.getLogger(DeviceDataHistoryController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/data/datahistory", method = RequestMethod.GET)
	public String datahistory(Locale locale, Model model) {
		logger.info("[设备历史数据查询]");
		
		return "datahistory/datahistory";
	}
	
	@RequestMapping(value = "/data/datainput", method = RequestMethod.GET)
	public String input(Locale locale, Model model) {
		logger.info("[设备历史数据新增]");
		return "datahistory/datahistory_input";
	}
	
}
