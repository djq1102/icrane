/**
 * 
 */
package com.monitor.app.controller.notice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.monitor.app.controller.AbstractController;
import com.monitor.app.dataobject.Notice;
import com.monitor.app.dataobject.PlcModel;
import com.monitor.app.query.NoticeQuery;
import com.monitor.app.result.ServiceResult;
import com.monitor.app.service.CustomerService;
import com.monitor.app.service.ModelService;
import com.monitor.app.service.NoticeService;
import com.monitor.app.utils.DateUtil;

/**
 * @author ibm
 *
 */
@Controller
public class NoticeController extends AbstractController{

	@Resource
	private NoticeService noticeService;
	@Resource
	private CustomerService customerService;
	
	@RequestMapping(value = "/notice/index")
	public String index( Notice notice,Model model) throws Exception{
		
		model.addAttribute("noticeStart", notice.getNoticeStart());
		model.addAttribute("noticeEnd", notice.getNoticeEnd());
		
		
		return "contentissue/contentissue";
	}
	
	@RequestMapping(value = "/notice/toAdd")
	public String toAdd(Notice notice,Model model) throws Exception{
		
		//1.查询所有客户列表
		ServiceResult queryCustomersResult = customerService.queryAllCustomers();
		if(!queryCustomersResult.isSuccess()){
			model.addAttribute("msg", queryCustomersResult.getMsg());
			return "error";
		}
		model.addAttribute("allCustomers", queryCustomersResult.getModule());
		
		return "contentissue/contentissue_input";
	}
	
	@RequestMapping(value = "/notice/addOrUpdate")
	public String add(@RequestParam(value="noticeId", required=false) long noticeId,
			@RequestParam("title") String title, @RequestParam("content") String content,
			 @RequestParam("my_multi_select2") String[] my_multi_select2, Model model, HttpSession session) throws Exception{
		if(my_multi_select2==null){
			model.addAttribute("msg","请选中发送通知对象");
			return "error";
		}
		long userId = getUserId(session);
		Notice notice = buildNotice(userId, title,content,my_multi_select2);
		ServiceResult result = null;
		if(noticeId!=0){
			notice.setNoticeId(noticeId);
			result = noticeService.updateNotice(notice);
		}else{
			result = noticeService.addNotice(notice);
		}
		
		if(!result.isSuccess()){
			model.addAttribute("msg",result.getMsg());
			return "error";
		}
		
		return "redirect:/notice/index";
	}
	
	private Notice buildNotice(long userId, String title, String content, String[] customerIds) {
		Notice notice = new Notice();
		notice.setUserId(userId);
		notice.setTitle(title);
		notice.setContent(content);
		if(customerIds!=null){
			StringBuilder ids = new StringBuilder();
			for(String cid : customerIds){
				ids.append(cid).append(",");
			}
			String tmpIds = ids.toString();
			notice.setToCustomerId(tmpIds.substring(0, tmpIds.length()-1));
		}
		return notice;
	}

	@RequestMapping(value = "/notice/query",method = RequestMethod.GET,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String query(@RequestParam("noticeStart") @DateTimeFormat(pattern="MM/dd/yyyy") Date noticeStart,
			@RequestParam("noticeEnd") @DateTimeFormat(pattern="MM/dd/yyyy") Date noticeEnd,
			@RequestParam("iDisplayStart") int start, @RequestParam("iDisplayLength") int pagesize,
			@RequestParam("sEcho") int sEcho, Model model) throws Exception{
		
		NoticeQuery query =new NoticeQuery();
		query.setNoticeStart(noticeStart);
		query.setNoticeEnd(noticeEnd);
		query.setBegin(start);
		query.setEnd(start+pagesize);
		
		ServiceResult dataResult =noticeService.queryNotices(query);
		ServiceResult countResult = noticeService.totalCount(query);
		if(dataResult.isSuccess() && dataResult.getModule()!=null){
			List<Notice> notices = (List<Notice>)dataResult.getModule();
			List<Map> resultMap = buildList(notices);
			long total = Long.parseLong(countResult.getModule().toString());
			return outputJsonAsResponse(resultMap, total , sEcho);
		}
		
		return outputJsonAsResponse(Collections.EMPTY_LIST, 0 , sEcho);
	}
	
	@RequestMapping(value = "/notice/edit/{noticeId}")
	public String edit(@PathVariable("noticeId") int noticeId, Model model) throws Exception{
		//1.查询通知
		ServiceResult result = noticeService.queryNotice(noticeId);
		if(!result.isSuccess()){
			model.addAttribute("msg", result.getMsg());
			return "error";
		}
		model.addAttribute("notice",result.getModule());
		
		//2.查询所有客户列表
		ServiceResult queryCustomersResult = customerService.queryAllCustomers();
		if(!queryCustomersResult.isSuccess()){
			model.addAttribute("msg", queryCustomersResult.getMsg());
			return "error";
		}
		
		model.addAttribute("noticeId",noticeId);
		model.addAttribute("allCustomers", queryCustomersResult.getModule());
		return "contentissue/contentissue_input";
	}
	
	@RequestMapping(value = "/notice/queryNoticeCustomerIds/{noticeId}",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String queryBindData(@PathVariable("noticeId") long noticeId, Model model) throws Exception{
		
		Map<String,List> maps = new HashMap<String, List>(); 
		if(noticeId<=0){
			maps.put("customerIds", new ArrayList());
			return JSONObject.toJSONString(maps);
		}
		
		//0.有效的设备id
		ServiceResult result = noticeService.queryNotice(noticeId);
		if(!result.isSuccess()){
			model.addAttribute("msg", result.getMsg());
			return "contentissue/contentissue";
		}
		
		//1.选中关系。。
		Notice notice = (Notice)result.getModule();
		String toCustomerIds = notice.getToCustomerId();
		
		String[] customerIds = toCustomerIds.split(",");
		List<String> bindIds = new ArrayList<String>(customerIds.length);
		for(String customerId : customerIds){
			bindIds.add(customerId);
		}
		maps.put("customerIds", bindIds);
		
		return JSONObject.toJSONString(maps);
	}
	
	@RequestMapping(value = "/notice/del/{noticeId}")
	public String del(@PathVariable("noticeId") long noticeId, Model model) throws Exception{
		ServiceResult result = noticeService.delNotice(noticeId);
		if(!result.isSuccess()){
			model.addAttribute("msg", result.getMsg());
		}
		
		return "contentissue/contentissue";
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<Map> buildList(List<Notice> noticeList){
		List<Map> result = new ArrayList<Map>();
		for(Notice notice:noticeList){
			Map map = new HashMap();
			map.put("0", notice.getNoticeId());
			map.put("1", notice.getTitle());
			map.put("2", DateUtil.formatDate(notice.getGmtCreate()));
			map.put("3", notice.getNoticeId());
			result.add(map);
		}
		return result;
	}
}
