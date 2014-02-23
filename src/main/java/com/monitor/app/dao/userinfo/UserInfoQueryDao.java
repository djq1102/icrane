/**
 * 
 */
package com.monitor.app.dao.userinfo;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.monitor.app.dataobject.UserInfo;

/**
 * @author Administrator
 *
 */
public interface UserInfoQueryDao {

	@Select("SELECT * FROM cr_user_info WHERE login_name = #{nick}")
	@Results(value = { 
			  @Result(id = true, property = "userId", column = "user_id"), 
			  @Result(property = "userName", column = "user_name"),
			  @Result(property = "userPhone", column = "user_phone"),
			  @Result(property = "userEmail", column = "user_email"),
			  @Result(property = "loginName", column = "login_name"),
			  @Result(property = "password", column = "password"),
			  @Result(property = "customerId", column = "customer_id"),
			  @Result(property = "roleType", column = "role_type"),
			  @Result(property = "gmtCreate", column = "gmt_create"),
			  @Result(property = "gmtModify", column = "gmt_modify")
	})
	public UserInfo queryUserByNick(String nick);
	
}
