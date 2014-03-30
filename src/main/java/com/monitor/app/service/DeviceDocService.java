/**
 * 
 */
package com.monitor.app.service;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.monitor.app.dao.user.DeviceDocDao;
import com.monitor.app.dataobject.DeviceDoc;
import com.monitor.app.dataobject.Notice;
import com.monitor.app.query.DeviceDocQuery;
import com.monitor.app.query.NoticeQuery;
import com.monitor.app.result.ServiceResult;
import com.monitor.app.result.msg.MsgEnum;
import com.monitor.app.utils.MsgUtils;

/**
 * @author ibm
 *
 */
@Service
public class DeviceDocService {
	private static final Logger log = LoggerFactory.getLogger(DeviceDocService.class);
	
	@Resource
	private DeviceDocDao deviceDocDao;
	
	public ServiceResult saveDoc(DeviceDoc doc){
		try{
			int count = deviceDocDao.addDeviceDoc(doc);
			if(count<=0){
				return MsgUtils.fillMsg(MsgEnum.DEVICE_DOC_ADD_FAIL);
			}
		}catch(Exception e){
			log.error("save_doc_fails,name="+doc.getDocName(),e);
			return MsgUtils.fillMsg(MsgEnum.DEVICE_DOC_ADD_FAIL);
		}
		
		return MsgUtils.fillModule(null);
	}
	
	public ServiceResult delDoc(long docId){
		if(docId<=0) return MsgUtils.fillMsg(MsgEnum.REQUEST_PARAM_ERROR);
		try{
			int count = deviceDocDao.delDeviceDoc(docId);
			if(count<=0){
				return MsgUtils.fillMsg(MsgEnum.DEVICE_DOC_DELETE_FAIL);
			}
		}catch(Exception e){
			log.error("del_device_fails",e);
			return MsgUtils.fillMsg(MsgEnum.DEVICE_DOC_DELETE_FAIL);
		}
		//success
		return MsgUtils.fillModule(null);
	}
	
	public ServiceResult queryDocs(DeviceDocQuery query){
		List<DeviceDoc> docs = null;
		try{
			docs = deviceDocDao.queryDeviceDocs(query);
		}catch(Exception e){
			log.error("query_docs_fails",e);
			return MsgUtils.fillMsg(MsgEnum.DEVICE_DOC_QUERYFAIL);
		}
		//success
		return MsgUtils.fillModule(docs);
	}
}
