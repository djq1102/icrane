package com.monitor.app.controller.front;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.monitor.app.controller.AbstractController;
import com.monitor.app.dataobject.Notice;
import com.monitor.app.query.NoticeQuery;
import com.monitor.app.result.ServiceResult;
import com.monitor.app.service.NoticeService;

@Controller
public class PlanController extends AbstractController {

	private static final Logger logger = LoggerFactory.getLogger(AccController.class);
	@Resource
	private NoticeService noticeService;

	private static final long DAY = 3;
	
	@RequestMapping(value = "/front/plan/index")
	public String index(@RequestParam("deviceId") long deviceId, Model model) throws Exception{
		logger.warn(">>>action=index");
		
		NoticeQuery query = new NoticeQuery();
		Date noticeStart = new Date();
		Calendar c = Calendar.getInstance();
		long ago3Time = noticeStart.getTime() - 60*60*24*1000*DAY;
		c.setTimeInMillis(ago3Time);
		Date ago3Date = c.getTime();
		
		query.setNoticeStart(ago3Date);
		query.setNeedPagination(false);
		ServiceResult result = noticeService.queryNotices(query);
		
		if(result.isSuccess()){
			List<Notice> notices = (List<Notice> )result.getModule();
			model.addAttribute("notices", notices);
		}
		model.addAttribute("deviceId", deviceId);
		return "front/plan/index";
	}

}
