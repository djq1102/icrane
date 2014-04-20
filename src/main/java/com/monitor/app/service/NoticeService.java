/**
 * 
 */
package com.monitor.app.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.monitor.app.dao.user.NoticeDao;
import com.monitor.app.dataobject.Notice;
import com.monitor.app.query.NoticeQuery;
import com.monitor.app.result.ServiceResult;
import com.monitor.app.result.msg.MsgEnum;
import com.monitor.app.utils.MsgUtils;

/**
 * @author ibm
 *
 */
@Service
public class NoticeService {
	private static final Logger log = LoggerFactory.getLogger(NoticeService.class);
	
	@Resource
	private NoticeDao noticeDao;
	
	public ServiceResult addNotice(Notice notice){
		try{
			int count = noticeDao.addNotice(notice);
			if(count<=0){
				return MsgUtils.fillMsg(MsgEnum.NOTICE_ADD_FAIL);
			}
		}catch(Exception e){
			log.error("add_notice_fails",e);
			return MsgUtils.fillMsg(MsgEnum.NOTICE_ADD_FAIL);
		}
		//success
		return MsgUtils.fillModule(null);
	}
	
	
	public ServiceResult updateNotice(Notice notice){
		try{
			int count = noticeDao.updateNotice(notice);
			if(count<=0){
				return MsgUtils.fillMsg(MsgEnum.NOTICE_UPDATE_FAIL);
			}
		}catch(Exception e){
			log.error("update_notice_fails",e);
			return MsgUtils.fillMsg(MsgEnum.NOTICE_UPDATE_FAIL);
		}
		//success
		return MsgUtils.fillModule(null);
		
	}
	
	public ServiceResult delNotice(long noticeId){
		if(noticeId<=0) return MsgUtils.fillMsg(MsgEnum.REQUEST_PARAM_ERROR);
		try{
			int count = noticeDao.delNotice(noticeId);
			if(count<=0){
				return MsgUtils.fillMsg(MsgEnum.NOTICE_DELETE_FAIL);
			}
		}catch(Exception e){
			log.error("del_notice_fails",e);
			return MsgUtils.fillMsg(MsgEnum.NOTICE_DELETE_FAIL);
		}
		//success
		return MsgUtils.fillModule(null);
		
	}
	
	public ServiceResult queryNotices(NoticeQuery query){
		List<Notice> notices = null;
		try{
			
			notices = noticeDao.queryNotices(query);
			
			if(query.getToCustomerId()!=0L){
				if(notices!=null){
					List<Notice> filtedNotices = new ArrayList<Notice>();
					for(Notice notice: notices){
						String toCustomerId = notice.getToCustomerId();
						String[] cIds = toCustomerId.split(",");
						for(String cId : cIds){
							if(cId.equals(String.valueOf(query.getToCustomerId()))){
								filtedNotices.add(notice);
								break;
							}
						}
					}
					return MsgUtils.fillModule(filtedNotices);
				}
			}
			
		}catch(Exception e){
			log.error("query_notice_fails",e);
			return MsgUtils.fillMsg(MsgEnum.NOTICE_QUERY_FAIL);
		}
		//success
		return MsgUtils.fillModule(notices);
	}
	
	public ServiceResult queryNotice(long noticeId){
		if(noticeId<=0) return MsgUtils.fillMsg(MsgEnum.REQUEST_PARAM_ERROR);
		Notice notice = null;
		try{
			notice = noticeDao.queryNoticeById(noticeId);
		}catch(Exception e){
			log.error("query_notice_fails,noticeId="+noticeId,e);
			return MsgUtils.fillMsg(MsgEnum.NOTICE_QUERY_FAIL);
		}
		//success
		return MsgUtils.fillModule(notice);
	}
	
	public ServiceResult totalCount(NoticeQuery query){
		int count = 0;
		try{
			count = noticeDao.totalCount(query);
		}catch(Exception e){
			log.error("query_notice_total_count_fails",e);
			return MsgUtils.fillMsg(MsgEnum.NOTICE_QUERY_FAIL);
		}
		
		return MsgUtils.fillModule(count);
	}
}
