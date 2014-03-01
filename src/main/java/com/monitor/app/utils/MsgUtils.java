/**
 * 
 */
package com.monitor.app.utils;

import com.monitor.app.result.ServiceResult;
import com.monitor.app.result.msg.MsgEnum;

/**
 * @author Administrator
 *
 */
public class MsgUtils {
	
	public static final String MSG_SUCCESS = "success";
	
	public static final String MSG_FAIL = "fail";
	
	public static ServiceResult fillMsg(MsgEnum msgEnum){
		ServiceResult result = new ServiceResult(false);
		
		result.setCode(msgEnum.name());
		result.setMsg(msgEnum.getMsg());
		
		return result;
	}
	
	public static ServiceResult fillModule(Object module){
		ServiceResult result = new ServiceResult(true);
		
		result.setModule(module);
		return result;
	}
}
