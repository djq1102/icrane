/**
 * 
 */
package com.monitor.app.result.msg;

/**
 * @author Administrator
 *
 */
public enum MsgEnum {

	USER_QUERY_EMPTY("用户记录为空"),
	USER_NOT_EXIST("用户不存在"),
	USER_UPDATE_FAIL("用户修改失败"),
	USER_ADD_FAIL("用户添加失败"),
	FAIL_USER_QUERY("用户查询失败"),
	
	CUSTOMER_ADD_FAIL("客户添加失败"),
	CUSTOMER_UPDATE_FAIL("客户修改失败"),
	CUSTOMER_NOT_EXIST("客户不存在"),
	
	DEVICE_ADD_FAIL("设备添加失败"),
	DEVICE_UPDATE_FAIL("设备修改失败"),
	DEVICE_NOT_EXIST("设备不存在"),
	
	SITE_ADD_FAIL("现场添加失败"),
	SITE_NOT_EXIST("现场不存在"),
	SITE_UPDATE_FAIL("现场修改失败");
	
	
	private String msg;
	
	private MsgEnum(String msg){
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}
}
