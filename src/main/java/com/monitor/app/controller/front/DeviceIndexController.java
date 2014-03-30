package com.monitor.app.controller.front;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.monitor.app.controller.AbstractController;
import com.monitor.app.result.ServiceResult;
import com.monitor.app.service.UserDeviceRelationService;

@Controller
public class DeviceIndexController extends AbstractController{
	
	@Resource
	private UserDeviceRelationService userDeviceRelationService;

	@RequestMapping(value = "/front/device/index")
	public String index(@RequestParam("deviceId") long deviceId, Model model,HttpSession session) throws Exception{
//		long userId = getUserId(session);
//		ServiceResult result = userDeviceRelationService.queryUserDeviceRelationByUserIdAndDeviceId(userId, deviceId);
//		if(!result.isSuccess()){
//			/**跳转到公用没错误页面，没有权限看此设备**/
//			return "error";
//		}
		model.addAttribute("deviceId", deviceId);

		return "front/deviceindex";
	}
}
