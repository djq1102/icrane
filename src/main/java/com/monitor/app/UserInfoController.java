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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.monitor.app.dataobject.UserInfo;
import com.monitor.app.query.UserInfoQuery;
import com.monitor.app.result.ServiceResult;
import com.monitor.app.service.UserInfoService;
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
	
	@RequestMapping(value = "/userInfo/addUserInfo")
	public String addUserInfo(@RequestParam("userName") String userName, @RequestParam("userPhone") String userPhone, 
			@RequestParam("userEmail") String userEmail, @RequestParam("loginName") String loginName,
			@RequestParam("password") String password, 
			@RequestParam("customerId") long customerId, 
			@RequestParam("roleType") int roleType, Model model) {
		logger.warn(">>>action=addUserInfo," + userName);
		UserInfo userInfo = new UserInfo();
		userInfo.setUserName(userName);
		userInfo.setUserPhohe(userPhone);
		userInfo.setUserEmail(userEmail);
		userInfo.setLoginName(loginName);
		userInfo.setPassword(password);
		userInfo.setCustomerId(customerId);
		userInfo.setRoleType((short)roleType);
		ServiceResult result = userInfoService.userInfoAdd(userInfo);
		if(result.isSuccess()){
			model.addAttribute("msg", MsgUtils.MSG_SUCCESS);
		}else{
			model.addAttribute("msg",MsgUtils.MSG_FAIL);
		}
		return "userInfo/userinfo";
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
		return "userInfo/userInfoEdit";
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
			userInfo.setUserPhohe(userPhone);
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
	public String index(Model model,@ModelAttribute UserInfo userInfo) {
		logger.warn(">>>action=/userInfo/index" );
		if(userInfo.getUserPhone() != null){
			model.addAttribute("userPhone", userInfo.getUserPhone());
		}
		return "userInfo/userinfo";
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/userInfo/queryList",method = RequestMethod.GET,produces = "application/json; charset=utf-8")
	@ResponseBody 
	public String queryUserInfoList(@RequestParam("iDisplayStart") int start, @RequestParam("iDisplayLength") int pagesize, @RequestParam("sEcho") int sEcho,
		@ModelAttribute UserInfo userInfo){
		long customerId = 1;
		UserInfoQuery userInfoQuery = new UserInfoQuery();
		userInfoQuery.setCustomerId(customerId);
		ServiceResult result = userInfoService.queryAllUserInfo(userInfoQuery);
		ServiceResult numResult = userInfoService.totalCount(userInfoQuery);
		List<UserInfo> userInfoList  = (List<UserInfo>)result.getModule();
		List<Map> resultMap = buildList(userInfoList);
		Map map = new HashMap();//jquery datatable 需要的数据类型封装
		map.put("aaData", resultMap);//数据集
		map.put("iTotalRecords", numResult.getModule());//总条数
		map.put("iTotalDisplayRecords", numResult.getModule());//过滤之后显示的实际条数
		map.put("sEcho", sEcho);//来自客户端 sEcho 的没有变化的复制品
		logger.warn(">>>userInfo="+JSONObject.toJSONString(map));
		return JSONObject.toJSONString(map);
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
