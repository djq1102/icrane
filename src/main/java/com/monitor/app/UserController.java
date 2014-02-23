package com.monitor.app;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.monitor.app.dataobject.User;
import com.monitor.app.result.ServiceResult;
import com.monitor.app.service.UserService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	@Resource
	private UserService userService;
	
	/*@RequestMapping(value = "/user/{action}")
	public String home(@PathVariable("action") String action, Model model) {
		
		logger.warn(">>>action=" + action);
		
		ServiceResult result = userService.queryAllUser();
		if(result.isSuccess()){
			List<User> users  = (List<User>)result.getModule();
			model.addAttribute("users", users);
		}else{
			model.addAttribute("result", result);
		}
		return "testUser";
	}
	*/
	@RequestMapping(value = "/user/add")
	public String add(@RequestParam("type") int type, @RequestParam("name") String name, 
			@RequestParam("address") String address, @RequestParam("contactName") String contactName,
			@RequestParam("contactPhone") String contactPhone, 
			@RequestParam("contactEmail") String contactEmail, Model model) {
		
		logger.warn(">>>action=add," + name);
		
		ServiceResult result = userService.addUser(type, name, address, contactName, contactPhone, contactEmail);
		
		model.addAttribute("result", result);
		
		
		ServiceResult result2 = userService.queryAllUser();
		if(result2.isSuccess()){
			List<User> users  = (List<User>)result2.getModule();
			model.addAttribute("users", users);
		}else{
			model.addAttribute("result2", result2);
		}
		return "testUser";
	}
	
	@RequestMapping(value = "/user/query")
	public String query( Model model) {
		
		logger.warn(">>>action=query" );
		
		ServiceResult result2 = userService.queryAllUser();
		if(result2.isSuccess()){
			List<User> users  = (List<User>)result2.getModule();
			model.addAttribute("users", users);
		}else{
			model.addAttribute("result2", result2);
		}
		return "testUser";
	}
	
	@RequestMapping(value = "/login")
	public String login() {
		
		logger.warn(">>>action=login");
		
		
		return "login";
	}
	@RequestMapping(value = "/403")
	public String userDenied() {
		
		logger.warn(">>>action=denied" );
		
		
		return "403";
	}
	
	@RequestMapping(value = "/index")
	public String index() {
		
		logger.warn(">>>action=index" );
		
		
		return "index";
	}
	
	@RequestMapping(value = "/admin")
	public String admin() {
		
		logger.warn(">>>action=admin" );
		
		
		return "admin";
	}
	
}
