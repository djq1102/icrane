package com.monitor.app.controller;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.monitor.app.controller.AbstractController;
import com.monitor.app.dataobject.Device;
import com.monitor.app.dataobject.MerchantInfor;
import com.monitor.app.dataobject.Notice;
import com.monitor.app.dataobject.UserDeviceRelation;
import com.monitor.app.exception.ManagerException;
import com.monitor.app.query.NoticeQuery;
import com.monitor.app.result.ServiceResult;
import com.monitor.app.service.DeviceService;
import com.monitor.app.service.NoticeService;
import com.monitor.app.service.UserDeviceRelationService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController extends AbstractController{ 
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Resource
	private NoticeService noticeService;
	@Resource
	private DeviceService deviceService;
	@Resource
	private UserDeviceRelationService userDeviceRelationService;
	private static final long DAY = 2;
	
	private void setNotice(HttpSession session, Model model){
		NoticeQuery query = new NoticeQuery();
		Date noticeStart = new Date();
		Calendar c = Calendar.getInstance();
		long ago3Time = noticeStart.getTime() - 60*60*24*1000*DAY;
		c.setTimeInMillis(ago3Time);
		Date ago3Date = c.getTime();
		
		query.setToCustomerId(getCustomerId(session));
		query.setNoticeStart(ago3Date);
		query.setNeedPagination(false);
		ServiceResult result = noticeService.queryNotices(query);
		
		if(result.isSuccess()){
			List<Notice> notices = (List<Notice> )result.getModule();
			model.addAttribute("notices", notices);
		}
	}
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, HttpSession session,Model model) {
		logger.info("Welcome home! a T he client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		setNotice(session, model);
		return "index";
	}
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(Locale locale, HttpSession session,Model model) {
		logger.info("Welcome home! a T he client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "index";
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getMapListServer",method = RequestMethod.GET,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getMapListServer(Model model,HttpSession session) throws ManagerException {
		List<MerchantInfor> maplist = new ArrayList<MerchantInfor>();
		long userId = getUserId(session);
		
		ServiceResult result = userDeviceRelationService.queryUserDeviceRelationByUserId(1);
		if(!result.isSuccess()){
			logger.warn("");
		}
		
		List<UserDeviceRelation> userDeviceRelations = (List)result.getModule();
		List<Long> deviceIds = new ArrayList<Long> ();
		for(UserDeviceRelation userDeviceRelation:userDeviceRelations){
			deviceIds.add(userDeviceRelation.getDeviceId());
		}
		
		if(deviceIds.size() != 0){
			result = deviceService.queryByDeviceIds(deviceIds);
			List<Device> deviceList = (List)result.getModule();
			for(Device device:deviceList){
				String location = device.getLocation().trim();
				String array[] = location.split(",");
				MerchantInfor info = new MerchantInfor();
				info.setDeviceId(String.valueOf(device.getDeviceId()));
				info.setLatitude(array[0]);
				info.setLongitude(array[1]);
				info.setName(device.getDeviceName());
				maplist.add(info);
			}
		}
		return JSONObject.toJSONString(maplist);
	}
}
