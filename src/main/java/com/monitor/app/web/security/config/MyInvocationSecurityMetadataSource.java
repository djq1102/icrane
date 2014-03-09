/**
 * 
 */
package com.monitor.app.web.security.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.monitor.app.web.security.util.RoleEnum;

/**
 * @author Administrator
 * 
 */
public class MyInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

	private RequestMatcher urlMatcher = new AntPathRequestMatcher("/**");
	private static Map<String, Collection<ConfigAttribute>> resourceMap = null;

	public MyInvocationSecurityMetadataSource(){
		loadResourceDefine();
    }

	private void loadResourceDefine() {
		resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
		
		Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();
		ConfigAttribute ca_super = new SecurityConfig(RoleEnum.ROLE_SUPERADMIN.name());
		ConfigAttribute ca_customer = new SecurityConfig(RoleEnum.ROLE_CUTOMERADMIN.name());
		ConfigAttribute ca_general = new SecurityConfig(RoleEnum.ROLE_GENERALADMIN.name());
		atts.add(ca_super);
		atts.add(ca_customer);
		atts.add(ca_general);
		
		resourceMap.put("/index", atts);
	}

	@Override
	public Collection<ConfigAttribute> getAttributes(Object object)
			throws IllegalArgumentException {
		HttpServletRequest request = ((FilterInvocation)object).getRequest();
		
        Iterator<String> ite = resourceMap.keySet().iterator();
        while (ite.hasNext()) {
            String resURL = ite.next();
            if (urlMatcher.matches(request)) {
                return resourceMap.get(resURL);
            }
        }
		return null;
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}
}
