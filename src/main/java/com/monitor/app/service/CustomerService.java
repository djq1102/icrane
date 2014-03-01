package com.monitor.app.service;

import javax.annotation.Resource;

import com.monitor.app.dao.user.CustomerDao;

/**
 * 
 * @author tmac
 *
 */

public class CustomerService {
	
	@Resource
	private CustomerDao customerDao;

}
