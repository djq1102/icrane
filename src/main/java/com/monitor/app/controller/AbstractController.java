/**
 * 
 */
package com.monitor.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.alibaba.fastjson.JSONObject;
import com.monitor.app.web.security.ext.UserExt;
import com.monitor.app.web.security.util.RoleEnum;

/**
 * @author ibm
 *
 */
public class AbstractController {

	@SuppressWarnings({"unchecked","rawtypes"})
	public String outputJsonAsResponse(List<Map> data, long total,int echo){
			Map map = new HashMap();// jquery datatable 需要的数据类型封装
			map.put("aaData", data);// 数据集
			map.put("iTotalRecords", total);// 总条数
			map.put("iTotalDisplayRecords", total);// 过滤之后显示的实际条数
			map.put("sEcho", echo);// 来自客户端 sEcho 的没有变化的复制品
			
			return JSONObject.toJSONString(map);
	}
	
	private UserExt getUserExt(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth!=null){
			UserDetails userDetails = (UserDetails) auth.getPrincipal();
			if(userDetails!=null){
				UserExt userExt = (UserExt)userDetails;
				return userExt;
			}
		}
		return null;
	}
	
	public RoleEnum getRole(HttpSession session){
		UserExt userExt = getUserExt();
		if(userExt!=null) return userExt.getRole();
		
		return null;
	}
	
	public long getUserId(HttpSession session){
		UserExt userExt = getUserExt();
		if(userExt!=null) return userExt.getUserId();
		
		return 0L;
	}
	
	public long getCustomerId(HttpSession session){
		UserExt userExt = getUserExt();
		if(userExt!=null) return userExt.getCustomerId();
		
		return 0L;
	}
	
	public String getUserName(HttpSession session){
		UserExt userExt = getUserExt();
		if(userExt!=null) return userExt.getUsername();
		
		return null;
	}
}
