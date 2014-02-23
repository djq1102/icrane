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
	
	
	USER_NOT_EXIST("用户不存在，或密码错误"),
	FAIL_USER_QUERY("用户查询失败");
	
	private String msg;
	
	private MsgEnum(String msg){
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}
}
