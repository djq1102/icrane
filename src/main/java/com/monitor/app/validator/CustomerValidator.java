package com.monitor.app.validator;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.monitor.app.dataobject.Customer;

public class CustomerValidator implements Validator {
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Customer.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		Customer customer = (Customer) obj;  
		if(StringUtils.isBlank(customer.getCustomerName())){
			errors.rejectValue("customerName","customerName","客户名称不能为空!");
		}
		if(StringUtils.isBlank(customer.getContactName())){
		     errors.rejectValue("contactName", "contactName", "联系人不能为空!"); 		
		}
		if(StringUtils.isBlank(customer.getContactEmail())){
			errors.rejectValue("contactEmail", "contactEmail", "客户的邮箱不能为空!");
		}
		if(StringUtils.isBlank(customer.getContactPhone())){
			errors.rejectValue("contactPhone", "contactPhone","客户的手机号不能为空!");
		}
	}

}
