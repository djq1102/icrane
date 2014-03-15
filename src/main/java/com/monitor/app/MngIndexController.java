package com.monitor.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 管理端的首页
 * @author dujq
 *
 */
@Controller
public class MngIndexController {

	private static final Logger logger = LoggerFactory.getLogger(MngIndexController.class);

	@RequestMapping(value = "/mng/index",method = RequestMethod.GET)
	public String index(Model model) {
		logger.warn(">>>action=mng index");
		return "mngindex/index";
	}
	
}
