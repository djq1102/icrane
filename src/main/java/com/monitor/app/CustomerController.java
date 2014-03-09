package com.monitor.app;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.monitor.app.service.CustomerService;

/**
 * 
 * @author tmac
 *
 */

@Controller
public class CustomerController {
	
	private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);
	@Resource
	private CustomerService customerService;
	
	@RequestMapping(value = "/customer/customerInput",method = RequestMethod.GET)
	public String customerInput(Model model) {
		logger.warn(">>>action=userInput");
		return "customer/customer";
	}
	
	

}
