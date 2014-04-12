package com.monitor.app.validator;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.monitor.app.dataobject.Device;

public class DeviceValidator implements Validator {
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Device.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		Device device = (Device) obj;  
		if(device.getCustomerId() <= 0){
		   errors.rejectValue("customerId", "customerId", "设备所属客户为必选!"); 		
		}
		if(device.getSiteId() <= 0){
		   errors.rejectValue("siteId", "siteId", "设备所属现场为必选!"); 		
		}
		if(StringUtils.isBlank(device.getDeviceIp())){
			errors.rejectValue("deviceIp", "deviceIp", "设备ip不能为空!");
		}
		if(StringUtils.isBlank(device.getDeviceName())){
		   errors.rejectValue("deviceName","deviceName","设备名称不能为空!");
		}
		if(StringUtils.isBlank(device.getSerialNumber())){
			errors.rejectValue("serialNumber", "serialNumber", "序列号不能为空!");
		}
		if(StringUtils.isBlank(device.getUserName())){
			errors.rejectValue("userName", "userName", "设备号不能为空!");
		}
		if(StringUtils.isBlank(device.getLocation())){
			errors.rejectValue("location", "location", "请选择位置坐标!");
		}
		if(StringUtils.isBlank(device.getUserPwd())){
			errors.rejectValue("userPwd", "userPwd", "用户密码不能为空!");
		}
	}

}
