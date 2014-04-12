package com.monitor.app.validator;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.monitor.app.dataobject.UserInfo;

public class UserInfoValidator implements Validator {
	
	@Override
	public boolean supports(Class<?> clazz) {
		return UserInfo.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		
		UserInfo userInfo = (UserInfo) obj;  
	    if(null == userInfo.getPassword() || "".equals(userInfo.getPassword())){
		     errors.rejectValue("password", "password", "密码不能为空!"); 		
	    }
	    if(StringUtils.isBlank(userInfo.getUserPhone())){
		     errors.rejectValue("userPhone", "userPhone", "手机不能为空!"); 
	    }
	    if(StringUtils.isBlank(userInfo.getUserEmail())){
		     errors.rejectValue("userEmail", "userEmail", "邮箱不能为空!"); 		
	    }
	    if(StringUtils.isBlank(userInfo.getUserName())){
	    	errors.rejectValue("userName", "userName", "用户名不能为空!");
	    }
	    if(StringUtils.isBlank(userInfo.getLoginName())){
	    	errors.rejectValue("loginName", "loginName", "登录用户不能为空");
	    }
	}

}
