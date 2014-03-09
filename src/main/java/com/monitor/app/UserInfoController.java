package com.monitor.app;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
	public String index(@RequestParam("p") int page, @RequestParam("ps") int pagesize,Model model) {
		logger.warn(">>>action=queryAllUserInfo" );
		ServiceResult result = userInfoService.queryUsersByPage(page,pagesize);
		
		if(result.isSuccess()){
			List<UserInfo> userInfoList  = (List<UserInfo>)result.getModule();
			model.addAttribute("userInfoList", userInfoList);
		}else{
			model.addAttribute("msg", MsgUtils.MSG_FAIL);
		}
		return "userInfo/userinfo";
	}

}
