package com.monitor.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.monitor.app.dataobject.UserInfo;
import com.monitor.app.query.UserInfoQuery;
import com.monitor.app.result.ServiceResult;
import com.monitor.app.service.UserInfoService;
import com.monitor.app.utils.JsonUtil;
import com.monitor.app.utils.MsgUtils;

/**
 * 
 * @author tmac
 *
 */
@Controller
public class UserInfoController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserInfoController.class);
	@Resource
	private UserInfoService userInfoService;
	
	@RequestMapping(value = "/userInfo/userinfoInput",method = RequestMethod.GET)
	public String userinfoInput(Model model) {
		logger.warn(">>>action=userInput");
		return "userInfo/userinfo_input";
	}
	
	@RequestMapping(value = "/userInfo/addUserInfo",method = RequestMethod.POST)
	public String addUserInfo(@RequestParam("userName") String userName, @RequestParam("userPhone") String userPhone, 
			@RequestParam("userEmail") String userEmail, @RequestParam("loginName") String loginName,
			@RequestParam("password") String password, 
			@RequestParam("customerId") long customerId, 
			@RequestParam("roleType") int roleType, Model model) {
		logger.warn(">>>action=addUserInfo," + userName);
		UserInfo userInfo = new UserInfo();
		userInfo.setUserName(userName);
		userInfo.setUserPhone(userPhone);
		userInfo.setUserEmail(userEmail);
		userInfo.setLoginName(loginName);
		userInfo.setPassword(password);
		userInfo.setCustomerId(customerId);
		userInfo.setRoleType((short)roleType);
		ServiceResult result = userInfoService.userInfoAdd(userInfo);
		if(!result.isSuccess()){
			logger.error(">>>action=addUserInfor error" + userName);
		}
		return "redirect:/index.htm";
	}
	
	@RequestMapping(value = "/userInfo/editUserInfo",method = RequestMethod.GET)
	public String editUserInfo(@RequestParam("userId") long userId,Model model) {
		logger.warn(">>>action=edit" );
		ServiceResult result = userInfoService.queryUserInfoByuserId(userId);
		if(result.isSuccess()){
			UserInfo userInfo  = (UserInfo)result.getModule();
			model.addAttribute("userInfo", userInfo);
		}else{
			model.addAttribute("msg", MsgUtils.MSG_FAIL);
		}
		return "userInfo/userinfo_edit_input";
	}
	
	@RequestMapping(value = "/userInfo/deleteUserInfo",method = RequestMethod.GET)
	public String deleteUserInfo(@RequestParam("userId") long userId,Model model) {
		logger.warn(">>>action=edit" );
		ServiceResult result = userInfoService.userInfoDelte(userId);
		if(!result.isSuccess()){
			logger.warn(">>>action=deleteUserInfo error userId="+userId );
		}
		return "userInfo/userinfo";
	}
	
	@RequestMapping(value = "/userInfo/updateUserInfo")
	public String updateUserInfo(@RequestParam("userId")long userId,@RequestParam("userName") String userName, @RequestParam("userPhone") String userPhone, 
			@RequestParam("userEmail") String userEmail, @RequestParam("loginName") String loginName,
			@RequestParam("password") String password, 
			@RequestParam("customerId") long customerId, 
			@RequestParam("roleType") int roleType, Model model) {
		logger.warn(">>>action=edit," + userName);
		ServiceResult result = userInfoService.queryUserInfoByuserId(userId);
		if(result.isSuccess()){
			UserInfo userInfo = (UserInfo)result.getModule();
			userInfo.setUserName(userName);
			userInfo.setUserPhone(userPhone);
			userInfo.setUserEmail(userEmail);
			userInfo.setLoginName(loginName);
			userInfo.setPassword(password);
			userInfo.setCustomerId(customerId);
			userInfo.setRoleType((short)roleType);
			ServiceResult editResult = userInfoService.userInfoEdit(userInfo);
			if(editResult.isSuccess()){
				model.addAttribute("msg", MsgUtils.MSG_SUCCESS);
			}else{
				model.addAttribute("msg",MsgUtils.MSG_FAIL);
			}
			return "userInfo/userinfo";
		}else{
			return "userInfo/userinfo";
		}
	}
	
	@RequestMapping(value = "/userInfo/index",method = {RequestMethod.POST,RequestMethod.GET})
	public String index(Model model,UserInfo userInfo) {
		logger.warn(">>>action=/userInfo/index" );
		model.addAttribute("userPhone", userInfo.getUserPhone());
		model.addAttribute("userName", userInfo.getUserName());
		return "userInfo/userinfo";
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/userInfo/queryList",method = RequestMethod.GET,produces = "application/json; charset=utf-8")
	@ResponseBody 
	public String queryUserInfoList(@RequestParam("iDisplayStart") int start, @RequestParam("iDisplayLength") int pagesize, @RequestParam("sEcho") int sEcho,
		UserInfo userInfo){
		long customerId = 1;
		UserInfoQuery userInfoQuery = new UserInfoQuery();
		userInfoQuery.setCustomerId(customerId);
		userInfoQuery.setUserPhone(userInfo.getUserPhone());
		userInfoQuery.setUserName(userInfo.getUserName());
		userInfoQuery.setBegin(start);
		userInfoQuery.setEnd(start+pagesize);
		ServiceResult result = userInfoService.queryAllUserInfo(userInfoQuery);
		ServiceResult numResult = userInfoService.totalCount(userInfoQuery);
		List<UserInfo> userInfoList  = (List<UserInfo>)result.getModule();
		List<Map> resultMap = buildList(userInfoList);
		return JsonUtil.buildJosn(resultMap, numResult, sEcho);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<Map> buildList(List<UserInfo> userInfoList){
		List<Map> result = new ArrayList<Map>();
		for(UserInfo userInfo:userInfoList){
			Map map = new HashMap();
			map.put("0", userInfo.getUserId());
			map.put("1", userInfo.getCustomerId());
			map.put("2",userInfo.getRoleType());
			map.put("3", userInfo.getUserName());
			map.put("4", userInfo.getUserPhone());
			map.put("5", userInfo.getUserEmail());
			map.put("6", userInfo.getUserId());
			result.add(map);
		}
		return result;
	}

}
