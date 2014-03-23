package com.monitor.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.monitor.app.dataobject.Customer;
import com.monitor.app.exception.ManagerException;
import com.monitor.app.query.CustomerInfoQuery;
import com.monitor.app.result.ServiceResult;
import com.monitor.app.service.CustomerService;
import com.monitor.app.utils.JsonUtil;
import com.monitor.app.utils.MsgUtils;

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
	
	@RequestMapping(value = "/customer/index",method = {RequestMethod.POST,RequestMethod.GET})
	public String index(Model model,Customer customer) {
		logger.warn(">>>action=index");
		model.addAttribute("customerType", customer.getCustomerType());
		model.addAttribute("customerName", customer.getCustomerName());
		return "customer/customer";
	}
	
	@RequestMapping(value = "/customer/customerInput",method = RequestMethod.GET)
	public String customerInput(Model model) {
		logger.warn(">>>action=userInput");
		return "customer/customer_input";
	}
	
	@RequestMapping(value = "/customer/addCustomer",method = RequestMethod.POST)
	public String addCustomer(Customer customer, Model model) throws ManagerException {
		logger.warn(">>>action=addCustomers" + customer.getCustomerName());
		ServiceResult result = customerService.addCustomer(customer);
		if(!result.isSuccess()){
			logger.error(">>>action=addCustomers error" + customer.getCustomerName());
		}
		return "redirect:index.htm";
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/customer/queryCustomerList",method = RequestMethod.GET,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String queryCustomerList(@RequestParam("iDisplayStart") int start, @RequestParam("iDisplayLength") int pagesize, @RequestParam("sEcho") int sEcho,
			Customer customer) throws ManagerException {
		logger.warn(">>>action=queryCustomerList" + customer.getCustomerName());
		CustomerInfoQuery query = new CustomerInfoQuery();
		query.setCustomerName(customer.getCustomerName());
		query.setCustomerType(customer.getCustomerType());
		query.setBegin(start);
		query.setEnd(start+pagesize);
		ServiceResult result = customerService.queryAllCustomer(query);
		ServiceResult numResult = customerService.totalCount(query);
		List<Customer> customerList  = (List<Customer>)result.getModule();
		List<Map> resultMap = buildList(customerList);
		return JsonUtil.buildJosn(resultMap, numResult, sEcho);
	}
	
	@RequestMapping(value = "/customer/editCustomer",method = RequestMethod.GET)
	public String editCustomer(@RequestParam("customerId") long customerId,Model model) throws ManagerException {
		logger.warn(">>>action=editCustomer");
		ServiceResult result = customerService.queryByCustomerId(customerId);
		if(result.isSuccess()){
			Customer customer = (Customer)result.getModule();
			model.addAttribute("customer", customer);
		}else{
			model.addAttribute("msg", MsgUtils.MSG_FAIL);
		}
		return "customer/customer_edit_input";
	}
	
	@RequestMapping(value = "/customer/updateCustomer")
	public String updateCustomer(Customer customer,Model model) throws ManagerException {
		logger.warn(">>>action=updateCustomer," + customer.getCustomerId());
		ServiceResult result = customerService.queryByCustomerId(customer.getCustomerId());
		if(result.isSuccess()){
			Customer dbCustomer = (Customer)result.getModule();
			dbCustomer.setContactName(customer.getContactName());
			dbCustomer.setContactEmail(customer.getContactEmail());
			dbCustomer.setContactPhone(customer.getContactPhone());
			dbCustomer.setCustomerAddress(customer.getCustomerAddress());
			dbCustomer.setCustomerType(customer.getCustomerType());
			dbCustomer.setCustomerName(customer.getCustomerName());
			ServiceResult editResult = customerService.updateCustomer(dbCustomer);
			if(editResult.isSuccess()){
				model.addAttribute("msg", MsgUtils.MSG_SUCCESS);
			}else{
				model.addAttribute("msg",MsgUtils.MSG_FAIL);
			}
			return "customer/customer";
		}else{
			return "customer/customer";
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<Map> buildList(List<Customer> customerList){
		List<Map> result = new ArrayList<Map>();
		for(Customer customer:customerList){
			Map map = new HashMap();
			map.put("0", customer.getCustomerId());
			map.put("1", customer.getCustomerType());
			map.put("2", customer.getCustomerName());
			map.put("3", customer.getCustomerAddress());
			map.put("4", customer.getContactPhone());
			map.put("5", customer.getContactEmail());
			map.put("6", customer.getCustomerId());
			result.add(map);
		}
		return result;
	}

}
