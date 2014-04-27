package com.monitor.app.validator;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.monitor.app.dataobject.Site;
import com.monitor.app.utils.ValidatorUtil;

public class SiteValidator implements Validator {
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Site.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		Site site = (Site) obj;  
		if(StringUtils.isBlank(site.getSiteName())){
			errors.rejectValue("siteName","siteName","现场名称不能为空!");
		}
		if(site.getCustomerId() <= 0){
		     errors.rejectValue("customerId", "customerId", "现场所属客户为必选!"); 		
		}
		if(StringUtils.isBlank(site.getContactName())){
			errors.rejectValue("contactName", "contactName", "现场联系人不能为空!");
		}
		if(StringUtils.isBlank(site.getContactEmail())){
			errors.rejectValue("contactEmail", "contactEmail", "现场邮件不能为空!");
		}
		if(!ValidatorUtil.checkEmail(site.getContactEmail())){
			errors.rejectValue("contactEmail", "contactEmail", "现场邮件输入有误!");
		}
		if(StringUtils.isBlank(site.getContactPhone())){
			errors.rejectValue("contactPhone", "contactPhone","现场的手机号不能为空!");
		}
		if(!ValidatorUtil.checkMobileNumber(site.getContactPhone())){
			errors.rejectValue("contactPhone", "contactPhone","现场的手机号输入有误!");
		}
		if(StringUtils.isBlank(site.getSiteAddress())){
			errors.rejectValue("siteAddress", "siteAddress", "现场地址不能为空!");
		}
	}

}
