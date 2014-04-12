package com.monitor.app.dao.user;

import java.util.List;

import com.monitor.app.dataobject.Customer;
import com.monitor.app.exception.DAOException;
import com.monitor.app.query.CustomerInfoQuery;

/**
 * 
 * @author tmac
 *
 */
public interface CustomerDao {
	
	 public int addCustomer(Customer customer) throws DAOException;
	 
	 public int updateCustomer(Customer customer) throws DAOException;
	 
	 public int deleteCustomer(long customerId) throws DAOException;
	  
	 public List<Customer> queryCustomer(CustomerInfoQuery query) throws DAOException;
	 
	 public List<Customer> queryAllCustomer() throws DAOException;
	 
	 public Customer queryByCustomerId(long customerId) throws DAOException;
	 
	 public int countAll(CustomerInfoQuery query) throws DAOException;
	 

}
