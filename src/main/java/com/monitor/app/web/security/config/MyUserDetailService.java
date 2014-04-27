/**
 * 
 */
package com.monitor.app.web.security.config;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.monitor.app.dataobject.UserInfo;
import com.monitor.app.result.ServiceResult;
import com.monitor.app.service.UserInfoService;
import com.monitor.app.utils.SpringBeanUtil;
import com.monitor.app.web.security.ext.UserExt;
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
		if(!result.isSuccess()){
			throw new UsernameNotFoundException(username);
		}
		
		UserInfo userInfo = (UserInfo)result.getModule();
		/*UserInfo userInfo = new UserInfo();
		userInfo.setCustomerId(1L);
		userInfo.setLoginName("qinde");
		userInfo.setPassword("123456");
		userInfo.setRoleType((short)0);*/
		String password = userInfo.getPassword();
		
		RoleEnum role = RoleEnumUtil.toAuthRole(userInfo.getRoleType());
		SimpleGrantedAuthority auth = new SimpleGrantedAuthority(role.name());
		
		Collection<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
		auths.add(auth);
		
		UserExt user = new UserExt(username, password, true, true, true, true, auths);
		user.setUserId(userInfo.getUserId());
		user.setCustomerId(userInfo.getCustomerId());
		user.setRole(role);
		return user;
	}
}
