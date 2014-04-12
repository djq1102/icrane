package com.monitor.app.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.validation.Valid;  

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.monitor.app.dataobject.Customer;
import com.monitor.app.dataobject.UserInfo;
import com.monitor.app.exception.ManagerException;
import com.monitor.app.query.UserInfoQuery;
import com.monitor.app.result.ServiceResult;
import com.monitor.app.service.CustomerService;
import com.monitor.app.service.UserDeviceRelationService;
import com.monitor.app.service.UserInfoService;
import com.monitor.app.utils.JsonUtil;
import com.monitor.app.utils.MsgUtils;
import com.monitor.app.validator.UserInfoValidator;

/**
 * 
 * @author tmac
 *
 */
@Controller
public class UserInfoController extends AbstractController{
	
	private static final Logger logger = LoggerFactory.getLogger(UserInfoController.class);
	@Resource
	private UserInfoService userInfoService;
	@Resource
	private CustomerService customerService;
	@Resource
	private UserDeviceRelationService userDeviceRelationService;
	
	@InitBinder  
	public void initBinder(DataBinder binder) {  
	   binder.setValidator(new UserInfoValidator());  
	}  
	
	@RequestMapping(value = "/userInfo/userinfoInput",method = RequestMethod.GET)
	public String userinfoInput(Model model) throws ManagerException {
		logger.warn(">>>action=userInput");
		model.addAttribute("customers", queryAllCustomers());
		return "userInfo/userinfo_input";
	}
	
	@RequestMapping(value = "/userInfo/addUserInfo",method = RequestMethod.POST)
	public String addUserInfo(@Valid @ModelAttribute("userInfo")UserInfo userInfo,BindingResult bingResult,Model model) throws ManagerException {
		if(bingResult.hasFieldErrors()){
			if(bingResult.getFieldError("userName") != null){
				model.addAttribute("userErrorName", bingResult.getFieldError("userName").getDefaultMessage());
			}
			if(bingResult.getFieldError("loginName") != null){
				model.addAttribute("loginErrorName",bingResult.getFieldError("loginName").getDefaultMessage());
			}
			if(bingResult.getFieldError("userEmail") != null){
				model.addAttribute("userErrorEmail",bingResult.getFieldError("userEmail").getDefaultMessage());
			}
			if(bingResult.getFieldError("password") != null){
				model.addAttribute("errorPassword",bingResult.getFieldError("password").getDefaultMessage());
			}
			if(bingResult.getFieldError("userPhone") != null){
				model.addAttribute("userErrorPhone",bingResult.getFieldError("userPhone").getDefaultMessage());
			}
			return userinfoInput(model);
		}
		ServiceResult userInfoResult = userInfoService.queryUser(userInfo.getLoginName());
		if(userInfoResult.isSuccess()){
			model.addAttribute("loginErrorName", "登录户名已经存在!");
			return userinfoInput(model);
		}

		logger.warn(">>>action=addUserInfo," + userInfo.getUserName());
		ServiceResult result = userInfoService.userInfoAdd(userInfo);
		if(!result.isSuccess()){
			logger.error(">>>action=addUserInfor error" + userInfo.getUserName());
		}
		return "redirect:index.htm";
	}
	
	@RequestMapping(value = "/userInfo/editUserInfo",method = RequestMethod.GET)
	public String editUserInfo(@RequestParam("userId") long userId,Model model) throws ManagerException {
		logger.warn(">>>action=edit" );
		ServiceResult result = userInfoService.queryUserInfoByuserId(userId);
		if(result.isSuccess()){
			UserInfo userInfo  = (UserInfo)result.getModule();
			model.addAttribute("customers", queryAllCustomers());
			model.addAttribute("userInfo", userInfo);
		}else{
			model.addAttribute("msg", MsgUtils.MSG_FAIL);
		}
		return "userInfo/userinfo_edit_input";
	}
	
	@RequestMapping(value = "/userInfo/deleteUserInfo",method = RequestMethod.GET)
	public String deleteUserInfo(@RequestParam("userId") long userId,Model model) throws ManagerException {
		logger.warn(">>>action=edit" );
	    ServiceResult relationResult =userDeviceRelationService.deleteUserDeviceRelationByUserId(userId);	
	    if(relationResult.isSuccess()){
	    	ServiceResult result = userInfoService.userInfoDelte(userId);
			if(!result.isSuccess()){
				logger.warn(">>>action=deleteUserInfo error userId="+userId );
			}
			return "userInfo/userinfo";
	    }else{
	    	return "error";
	    }
    }
	
	@RequestMapping(value = "/userInfo/updateUserInfo")
	public String updateUserInfo(@Valid @ModelAttribute("userInfo")UserInfo userInfo,BindingResult bingResult, Model model) throws ManagerException {
		if(bingResult.hasFieldErrors()){
			if(bingResult.getFieldError("userName") != null){
				model.addAttribute("userErrorName", bingResult.getFieldError("userName").getDefaultMessage());
			}
			if(bingResult.getFieldError("loginName") != null){
				model.addAttribute("loginErrorName",bingResult.getFieldError("loginName").getDefaultMessage());
			}
			if(bingResult.getFieldError("userEmail") != null){
				model.addAttribute("userErrorEmail",bingResult.getFieldError("userEmail").getDefaultMessage());
			}
			if(bingResult.getFieldError("password") != null){
				model.addAttribute("errorPassword",bingResult.getFieldError("password").getDefaultMessage());
			}
			if(bingResult.getFieldError("userPhone") != null){
				model.addAttribute("userErrorPhone",bingResult.getFieldError("userPhone").getDefaultMessage());
			}
			return editUserInfo(userInfo.getUserId(),model);
		}
		logger.warn(">>>action=edit," + userInfo.getUserName());
		ServiceResult result = userInfoService.queryUserInfoByuserId(userInfo.getUserId());
		if(result.isSuccess()){
			UserInfo dbUserInfo = (UserInfo)result.getModule();
			if(!dbUserInfo.getLoginName().equals(userInfo.getLoginName())){
				ServiceResult userInfoResult = userInfoService.queryUser(userInfo.getLoginName());
				if(userInfoResult.isSuccess()){
					model.addAttribute("loginErrorName", "登录户名已经存在!");
					return userinfoInput(model);
				}
			}
			ServiceResult editResult = userInfoService.userInfoEdit(userInfo);
			if(editResult.isSuccess()){
				return "userInfo/userinfo";
			}else{
				model.addAttribute("msg",editResult.getMsg());
				return "error";
			}
		}else{
			model.addAttribute("msg", result.getMsg());
			return "error";
		}
	}
	
	@RequestMapping(value = "/userInfo/index",method = {RequestMethod.POST,RequestMethod.GET})
	public String index(Model model,UserInfo userInfo) throws ManagerException {
		logger.warn(">>>action=/userInfo/index" );
		model.addAttribute("customers", queryAllCustomers());
		model.addAttribute("customerId", userInfo.getCustomerId());
		model.addAttribute("roleType", userInfo.getRoleType());
		model.addAttribute("userPhone", userInfo.getUserPhone());
		model.addAttribute("userName", userInfo.getUserName());
		return "userInfo/userinfo";
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/userInfo/queryList",method = RequestMethod.GET,produces = "application/json; charset=utf-8")
	@ResponseBody 
	public String queryUserInfoList(@RequestParam("iDisplayStart") int start, @RequestParam("iDisplayLength") int pagesize, @RequestParam("sEcho") int sEcho,
		UserInfo userInfo){
		UserInfoQuery userInfoQuery = new UserInfoQuery();
		userInfoQuery.setCustomerId(userInfo.getCustomerId());
		userInfoQuery.setUserPhone(userInfo.getUserPhone());
		userInfoQuery.setUserName(userInfo.getUserName());
		userInfoQuery.setRoleType(userInfo.getRoleType());
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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private List<Customer> queryAllCustomers() throws ManagerException{
		ServiceResult result = customerService.queryAllCustomers();
		List<Customer> customers = (List)result.getModule();
		return customers;
	}

}
