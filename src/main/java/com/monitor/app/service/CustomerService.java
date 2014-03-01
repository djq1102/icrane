package com.monitor.app.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.monitor.app.dao.user.CustomerDao;

/**
 * 
 * @author tmac
 *
 */
@Service
public class CustomerService {
	
	@Resource
	private CustomerDao customerDao;

}
