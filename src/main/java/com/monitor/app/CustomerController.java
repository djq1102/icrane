package com.monitor.app;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import com.monitor.app.service.CustomerService;

/**
 * 
 * @author tmac
 *
 */

@Controller
public class CustomerController {
	
	@Resource
	private CustomerService customerService;
	
	

}
