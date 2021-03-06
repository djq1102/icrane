package com.monitor.app.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.monitor.app.dataobject.Customer;
import com.monitor.app.dataobject.Site;
import com.monitor.app.dataobject.UserInfo;
import com.monitor.app.exception.ManagerException;
import com.monitor.app.query.CustomerInfoQuery;
import com.monitor.app.result.ServiceResult;
import com.monitor.app.service.CustomerService;
import com.monitor.app.service.DeviceService;
import com.monitor.app.service.SiteService;
import com.monitor.app.service.UserInfoService;
import com.monitor.app.utils.JsonUtil;
import com.monitor.app.validator.CustomerValidator;

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
	@Resource
	private UserInfoService userInfoService;
	@Resource
	private SiteService siteService;
	@Resource
	private DeviceService deviceService;
	
	@InitBinder  
	public void initBinder(DataBinder binder) {  
	   binder.setValidator(new CustomerValidator());  
	}  
	
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
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/customer/deleteCustomer")
	public String deleteCustomer(@RequestParam("customerId") long customerId,Model model) throws ManagerException {
		ServiceResult userInfoResult = userInfoService.queryUserInfoByCustomerId(customerId);
		if(userInfoResult.isSuccess()){
			List<UserInfo> userInfos = (List<UserInfo>)userInfoResult.getModule();
			if(userInfos.isEmpty()){
				model.addAttribute("msg", "客户下存在用户，请先删除该客户下的用户!");
				return "error";
			}
		}else{
			model.addAttribute("msg", userInfoResult.getMsg());
			return "error";
		}
		
		ServiceResult siteResult = siteService.querySiteByCustomerId(customerId);
		if(siteResult.isSuccess()){
			List<Site> sites = (List<Site>)userInfoResult.getModule();
			if(sites.isEmpty()){
				model.addAttribute("msg", "客户下存在现场，请先删除该客户下的现场!");
				return "error";
			}
		}else{
			model.addAttribute("msg", userInfoResult.getMsg());
			return "error";
		}
		
		ServiceResult customerResult = customerService.queryByCustomerId(customerId);
		if(customerResult.isSuccess()){
			List<Customer> customers = (List<Customer>)userInfoResult.getModule();
			if(customers.isEmpty()){
				model.addAttribute("msg", "客户下存在设备，请先删除该客户下的设备!");
				return "error";
			}
		}else{
			model.addAttribute("msg", userInfoResult.getMsg());
			return "error";
		}

		ServiceResult result = customerService.deleteCustomer(customerId);
		if(!result.isSuccess()){
			logger.error(">>>action=delteCustomer error" + customerId);
			model.addAttribute("msg", result.getMsg());
			return "error";
		}
		return "redirect:index.htm";
	}
	
	@RequestMapping(value = "/customer/addCustomer")
	public String addCustomer(@Valid @ModelAttribute("customer") Customer customer,BindingResult bingResult,Model model) throws ManagerException {
		if(bingResult.hasFieldErrors()){
			if( bingResult.getFieldError("customerName") != null){
				model.addAttribute("customerErrorName", bingResult.getFieldError("customerName").getDefaultMessage());
			}
			if(bingResult.getFieldError("contactName") != null){
				model.addAttribute("contactErrorName",bingResult.getFieldError("contactName").getDefaultMessage());
			}
			if(bingResult.getFieldError("contactEmail") != null){
				model.addAttribute("contactErrorEmail",bingResult.getFieldError("contactEmail").getDefaultMessage());
			}
			if(bingResult.getFieldError("contactPhone") != null){
				model.addAttribute("contactErrorPhone",bingResult.getFieldError("contactPhone").getDefaultMessage());
			}
			return customerInput(model);
		}
		logger.warn(">>>action=addCustomers" + customer.getCustomerName());
		ServiceResult result = customerService.addCustomer(customer);
		if(!result.isSuccess()){
			logger.error(">>>action=addCustomers error" + customer.getCustomerName());
			model.addAttribute("msg", result.getMsg());
			return "error";
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
			model.addAttribute("msg",result.getMsg());
		}
		return "customer/customer_edit_input";
	}
	
	@RequestMapping(value = "/customer/updateCustomer")
	public String updateCustomer(@Valid @ModelAttribute("customer") Customer customer,BindingResult bingResult,Model model) throws ManagerException {
		if(bingResult.hasFieldErrors()){
			if( bingResult.getFieldError("customerName") != null){
				model.addAttribute("customerErrorName", bingResult.getFieldError("customerName").getDefaultMessage());
			}
			if(bingResult.getFieldError("contactName") != null){
				model.addAttribute("contactErrorName",bingResult.getFieldError("contactName").getDefaultMessage());
			}
			if(bingResult.getFieldError("contactEmail") != null){
				model.addAttribute("contactErrorEmail",bingResult.getFieldError("contactEmail").getDefaultMessage());
			}
			if(bingResult.getFieldError("contactPhone") != null){
				model.addAttribute("contactErrorPhone",bingResult.getFieldError("contactPhone").getDefaultMessage());
			}
			return editCustomer(customer.getCustomerId(),model);
		}
		
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
				return "customer/customer";
			}else{
				model.addAttribute("msg",editResult.getMsg());
				return "error";
			}
		}else{
			model.addAttribute("msg",result.getMsg());
			return "error";
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
