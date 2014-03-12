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
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/userInfo/index",method = RequestMethod.GET)
	public String index(@RequestParam("customerId") long customerId,Model model) {
		logger.warn(">>>action=queryAllUserInfo" );
		ServiceResult result = userInfoService.queryUserInfoByCustomerId(customerId);
		if(result.isSuccess()){
			List<UserInfo> userInfoList  = (List<UserInfo>)result.getModule();
			model.addAttribute("userInfoList", userInfoList);
		}else{
			model.addAttribute("msg", MsgUtils.MSG_FAIL);
		}
		return "userInfo/userinfo";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/userInfo/all",method = RequestMethod.GET)
	public String index(@RequestParam("userPhone") String userPhone,Model model) {
		logger.warn(">>>action=queryAllUserInfo" );
		
//		if(result.isSuccess()){
//			List<UserInfo> userInfoList  = (List<UserInfo>)result.getModule();
//			model.addAttribute("userInfoList", userInfoList);
//		}else{
//			model.addAttribute("msg", MsgUtils.MSG_FAIL);
//		}
		model.addAttribute("userPhone", userPhone);	
		return "userInfo/userinfo";
	}
	
	@RequestMapping(value = "/userInfo/queryList",method = RequestMethod.GET,produces = "application/json; charset=utf-8")
	@ResponseBody 
	public String queryUserInfoList(@RequestParam("iDisplayStart") int start, @RequestParam("iDisplayLength") int pagesize, @RequestParam("sEcho") int sEcho,@RequestParam("userPhone") long userPhone){
		
		List<Map> result ;
		if(userPhone == 1 ){
			result = mockUserInfo1();
		}else{
			result = mockUserInfo2();
		}
		Map map = new HashMap();//jquery datatable 需要的数据类型封装
		map.put("aaData", result);//数据集
		map.put("iTotalRecords", 10);//总条数
		map.put("iTotalDisplayRecords", 10);//过滤之后显示的实际条数
		map.put("sEcho", sEcho);//来自客户端 sEcho 的没有变化的复制品
		logger.warn(">>>userInfo="+JSONObject.toJSONString(map));
		return JSONObject.toJSONString(map);
	}
	
	private List<Map> mockUserInfo1(){
		UserInfo userInfo = new UserInfo();
		userInfo.setUserId(1L);
		userInfo.setCustomerId(200120L);
		userInfo.setRoleType((short)1);
		userInfo.setUserName("孔明");
		userInfo.setUserPhohe("12333333333");
		userInfo.setUserEmail("k@1212.con");
		List<Map> result = new ArrayList<Map>();
		Map map = new HashMap();
		map.put("0", userInfo.getUserId());
		map.put("1", userInfo.getCustomerId());
		map.put("2",userInfo.getRoleType());
		map.put("3", userInfo.getUserName());
		map.put("4", userInfo.getUserPhone());
		map.put("5", userInfo.getUserEmail());
		map.put("6", userInfo.getUserId());
		result.add(map);
		return result;
	}
	private List<Map> mockUserInfo2(){
		UserInfo userInfo = new UserInfo();
		userInfo.setUserId(1L);
		userInfo.setCustomerId(200120L);
		userInfo.setRoleType((short)1);
		userInfo.setUserName("孔明2");
		userInfo.setUserPhohe("12333333333");
		userInfo.setUserEmail("k@1212.con");
		List<Map> result = new ArrayList<Map>();
		Map map = new HashMap();
		map.put("0", userInfo.getUserId());
		map.put("1", userInfo.getCustomerId());
		map.put("2",userInfo.getRoleType());
		map.put("3", userInfo.getUserName());
		map.put("4", userInfo.getUserPhone());
		map.put("5", userInfo.getUserEmail());
		map.put("6", userInfo.getUserId());
		result.add(map);
		return result;
	}

}
