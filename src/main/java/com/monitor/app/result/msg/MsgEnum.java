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
	
	USER_DEVICE_RELATION_ADD_FAIL("添加用户关联设备失败"),
	USER_DEVICE_RELATION_DELETE_FAIL("删除用户关联设备失败"),
	USER_DEVICE_RELATION("用户关联设备不存在"),
	
	USER_SITE_RELATION_ADD_FAIL("添加用户关联现场失败"),
	USER_SITE_RELATION_DELETE_FAIL("删除用户关联现场失败"),
	
	MODEL_VAR_ADD_FAIL("变量管理增加失败"),
	MODEL_VAR_UPDATE_FAIL("变量管理更新失败"),
	MODEL_VAR_DELETE_FAIL("变量管理删除失败"),
	MODEL_VAR_QUERY_FAIL("变量管理查询失败"),
	MODEL_VAR_QUERY_EMPTY("未查到该机型的变量"),
	
	MODEL_ADD_FAIL("机型增加失败"),
	MODEL_UPDATE_FAIL("机型更新失败"),
	MODEL_DELETE_FAIL("机型删除失败"),
	MODEL_QUERY_FAIL("机型查询失败"),
	MODEL_QUERY_EMPTY("未查到该机型"),
	
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
