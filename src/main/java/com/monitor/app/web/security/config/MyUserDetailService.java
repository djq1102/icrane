/**
 * 
 */
package com.monitor.app.web.security.config;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static com.monitor.app.web.security.util.RoleEnum.*;

/**
 * 
 * 从数据库中读入用户的密码，角色信息，是否锁定，账号是否过期等
 * 
 * @author Administrator
 * 
 */
public class MyUserDetailService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {

		Collection<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
		SimpleGrantedAuthority auth2 = new SimpleGrantedAuthority(GENERAL_ADMIN.name());
		auths.add(auth2);

		if (username.equals("qinde")) {
			auths = new ArrayList<GrantedAuthority>();
			SimpleGrantedAuthority auth1 = new SimpleGrantedAuthority(SUPER_ADMIN.name());
			auths.add(auth1);
		}

		// User(String username, String password, boolean enabled, boolean
		// accountNonExpired,
		// boolean credentialsNonExpired, boolean accountNonLocked,
		// Collection<GrantedAuthority> authorities) {
		User user = new User(username, "123456", true, true, true, true, auths);
		return user;

	}
}
