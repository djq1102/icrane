/**
 * 
 */
package com.monitor.app.dao.userinfo;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.monitor.app.dataobject.UserInfo;
import com.monitor.app.exception.DAOException;
import com.monitor.app.query.UserInfoQuery;

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
	
	@Select("SELECT * FROM cr_user_info WHERE user_id = #{userId}")
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
	public UserInfo queryUserInfoByUserId(long userId);
	
	@Insert({"insert into cr_user_info (user_name,user_phone,user_email,login_name,password,customer_id,role_type,gmt_create,gmt_modify) "
	  		+ "values (#{userName}, #{userPhone}, #{userEmail}, "
	  		+ "#{loginName}, #{password}, #{customerId},#{roleType},now(),now())"})
	public void addUserInfo(UserInfo userInfo);
	
	@Update({"update cr_user_info set cr_user_info user_name=#{userName}#,user_phone=#{userPhone}#,user_email=#userEmail#,login_name=#loginName#"
			+ "password=password,customer_id=customerId,role_type=roleType,gmt_modify=now()"})
    public void update(UserInfo userInfo);
    
	@Select("SELECT * FROM cr_user_info WHERE customer_id = #{customerId}")
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
	public List<UserInfo> queryUserInfo(UserInfoQuery userInfoQuery);
	
	
	
	@Select("SELECT count(*) as total FROM cr_user_info")
	int totalCount(UserInfoQuery userInfoQuery) throws DAOException;

	
}
