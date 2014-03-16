package com.monitor.app.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.monitor.app.dao.user.CustomerDao;
import com.monitor.app.dataobject.Customer;
import com.monitor.app.exception.DAOException;
import com.monitor.app.exception.ManagerException;
import com.monitor.app.query.CustomerInfoQuery;
import com.monitor.app.result.ServiceResult;
import com.monitor.app.result.msg.MsgEnum;
import com.monitor.app.utils.MsgUtils;

/**
 * 
 * @author tmac
 *
 */
@Service
public class CustomerService {
	
	@Resource
	private CustomerDao customerDao;
	
	public ServiceResult addCustomer(Customer customer) throws ManagerException{
		try{
			int row = customerDao.addCustomer(customer);
			if(row != 1){
				return MsgUtils.fillMsg(MsgEnum.SITE_ADD_FAIL);
			}
		}catch(DAOException e){
			throw new ManagerException(e);
		}
		return MsgUtils.fillModule(customer);
	}
	
	public ServiceResult queryByCustomerId(long customerId) throws ManagerException{
		Customer customer ;
		try {
			customer = customerDao.queryByCustomerId(customerId);
			if(customer == null){
				return MsgUtils.fillMsg(MsgEnum.CUSTOMER_NOT_EXIST);
			}
		} catch (DAOException e) {
			throw new ManagerException(e);
		}
		return MsgUtils.fillModule(customer);
	}
	
	public ServiceResult updateCustomer(Customer customer) throws ManagerException{
		try {
			int rows = customerDao.updateCustomer(customer);
			if(rows != 1){
				return MsgUtils.fillMsg(MsgEnum.CUSTOMER_UPDATE_FAIL);
			}
		} catch (DAOException e){
			throw new ManagerException(e);
		}
		return MsgUtils.fillModule(customer);
	}
	
	public ServiceResult queryAllCustomer(CustomerInfoQuery query) throws ManagerException{
		List<Customer> list = new ArrayList<Customer>();
		try{
			list = customerDao.queryCustomer(query);
		}catch(DAOException e){
			throw new ManagerException(e);
		}
		ServiceResult result = new ServiceResult(true);
		result.setModule(list);
		return result;
	}
	
	public ServiceResult queryAllCustomers() throws ManagerException{
		List<Customer> list = new ArrayList<Customer>();
		try{
			list = customerDao.queryAllCustomer();
		}catch(DAOException e){
			throw new ManagerException(e);
		}
		ServiceResult result = new ServiceResult(true);
		result.setModule(list);
		return result;
	}

	
	public ServiceResult totalCount(CustomerInfoQuery query) throws ManagerException{
		int num = 0;
		try{
			num = customerDao.countAll(query);
		}catch(DAOException e){
			throw new ManagerException(e);
		}
		ServiceResult result = new ServiceResult(true);
		result.setModule(num);
		return result;
	}

}
