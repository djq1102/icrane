/**
 * 
 */
package com.monitor.app.service;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.monitor.app.dao.model.AccessoryDao;
import com.monitor.app.dataobject.ModelAccessory;
import com.monitor.app.query.ModelAccessoryQuery;
import com.monitor.app.result.ServiceResult;
import com.monitor.app.result.msg.MsgEnum;
import com.monitor.app.utils.MsgUtils;

/**
 * @author ibm
 *
 */
@Service
public class AccessoryService {

	private static final Logger log = LoggerFactory.getLogger(AccessoryService.class);
	
	@Resource
	private AccessoryDao accessoryDao;
	
	public ServiceResult addAccessory(ModelAccessory accessory){
		try{
			int count = accessoryDao.addAccessory(accessory);
			if(count<=0){
				return MsgUtils.fillMsg(MsgEnum.ACCESSORY_ADD_FAIL);
			}
		}catch(Exception e){
			log.error("add_acc_fails",e);
			return MsgUtils.fillMsg(MsgEnum.ACCESSORY_ADD_FAIL);
		}
		//success
		return MsgUtils.fillModule(null);
	}
	
	public ServiceResult updateAccessory(ModelAccessory accessory){
		try{
			int count = accessoryDao.updateAccessory(accessory);
			if(count<=0){
				return MsgUtils.fillMsg(MsgEnum.ACCESSORY_UPDATE_FAIL);
			}
		}catch(Exception e){
			log.error("update_acc_fails",e);
			return MsgUtils.fillMsg(MsgEnum.ACCESSORY_UPDATE_FAIL);
		}
		//success
		return MsgUtils.fillModule(null);
		
	}
	
	public ServiceResult delAccessory(long accessoryId){
		if(accessoryId<=0) return MsgUtils.fillMsg(MsgEnum.REQUEST_PARAM_ERROR);
		try{
			int count = accessoryDao.delAccessory(accessoryId);
			if(count<=0){
				return MsgUtils.fillMsg(MsgEnum.ACCESSORY_DELETE_FAIL);
			}
		}catch(Exception e){
			log.error("del_acc_fails",e);
			return MsgUtils.fillMsg(MsgEnum.ACCESSORY_DELETE_FAIL);
		}
		//success
		return MsgUtils.fillModule(null);
		
	}
	
	public ServiceResult queryAccessorys(ModelAccessoryQuery query){
		List<ModelAccessory> accs = null;
		try{
			accs = accessoryDao.queryModelAccessory(query);
		}catch(Exception e){
			log.error("query_acc_fails",e);
			return MsgUtils.fillMsg(MsgEnum.ACCESSORY_QUERY_FAIL);
		}
		//success
		return MsgUtils.fillModule(accs);
	}
	
	public ServiceResult queryAccessory(long accessoryId){
		if(accessoryId<=0) return MsgUtils.fillMsg(MsgEnum.REQUEST_PARAM_ERROR);
		ModelAccessory acc = null;
		try{
			acc = accessoryDao.queryModelAccessoryById(accessoryId);
		}catch(Exception e){
			log.error("query_acc_fails,modelId="+accessoryId,e);
			return MsgUtils.fillMsg(MsgEnum.ACCESSORY_QUERY_FAIL);
		}
		//success
		return MsgUtils.fillModule(acc);
	}
	
	public ServiceResult totalCount(ModelAccessoryQuery query){
		int count = 0;
		try{
			count = accessoryDao.totalCount(query);
		}catch(Exception e){
			log.error("query_acc_total_count_fails",e);
			return MsgUtils.fillMsg(MsgEnum.ACCESSORY_QUERY_FAIL);
		}
		
		return MsgUtils.fillModule(count);
	}
	
	public ServiceResult queryAllAccessorys(){
		ModelAccessoryQuery query = new ModelAccessoryQuery();
		query.setNeedPagination(false);
		return queryAccessorys(query);
	}
}
