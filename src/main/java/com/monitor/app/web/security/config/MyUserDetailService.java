/**
 * 
 */
package com.monitor.app.web.security.config;

import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.monitor.app.dataobject.UserInfo;
import com.monitor.app.result.ServiceResult;
import com.monitor.app.service.UserInfoService;
import com.monitor.app.utils.SpringBeanUtil;
import com.monitor.app.web.security.util.RoleEnum;
import com.monitor.app.web.security.util.RoleEnumUtil;

/**
 * 
 * 从数据库中读入用户的密码，角色信息，是否锁定，账号是否过期等
 * 
 * @author Administrator
 * 
 */
public class MyUserDetailService implements UserDetailsService {

	//@Autowired
	//private UserInfoService userInfoService;
	
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		UserInfoService userInfoService = (UserInfoService)SpringBeanUtil.getBean("userInfoService");
		ServiceResult result = userInfoService.queryUser(username);
		if(result==null || !result.isSuccess()){
			throw new UsernameNotFoundException(username);
		}
		
		UserInfo userInfo = (UserInfo)result.getModule();
		String password = userInfo.getPassword();
		
		RoleEnum role = RoleEnumUtil.toAuthRole(userInfo.getRoleType());
		SimpleGrantedAuthority auth = new SimpleGrantedAuthority(role.name());
		
		Collection<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
		auths.add(auth);
		
		User user = new User(username, password, true, true, true, true, auths);
		return user;
	}
}
