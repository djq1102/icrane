/**
 * 
 */
package com.monitor.app.service;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.monitor.app.dao.model.ModelDao;
import com.monitor.app.dataobject.PlcModel;
import com.monitor.app.query.PlcModelQuery;
import com.monitor.app.result.ServiceResult;
import com.monitor.app.result.msg.MsgEnum;
import com.monitor.app.utils.MsgUtils;

/**
 * @author ibm
 *
 */
@Service
public class ModelService {

	private static final Logger log = LoggerFactory.getLogger(ModelService.class);
	
	@Resource
	private ModelDao modelDao;
	
	public ServiceResult addModel(PlcModel plcModel){
		try{
			int count = modelDao.addModel(plcModel);
			if(count<=0){
				return MsgUtils.fillMsg(MsgEnum.MODEL_ADD_FAIL);
			}
		}catch(Exception e){
			log.error("add_model_fails",e);
			return MsgUtils.fillMsg(MsgEnum.MODEL_ADD_FAIL);
		}
		//success
		return MsgUtils.fillModule(null);
	}
	
	public ServiceResult updateModel(PlcModel plcModel){
		try{
			int count = modelDao.updateModel(plcModel);
			if(count<=0){
				return MsgUtils.fillMsg(MsgEnum.MODEL_UPDATE_FAIL);
			}
		}catch(Exception e){
			log.error("update_model_fails",e);
			return MsgUtils.fillMsg(MsgEnum.MODEL_UPDATE_FAIL);
		}
		//success
		return MsgUtils.fillModule(null);
		
	}
	
	public ServiceResult delModel(long modelId){
		try{
			int count = modelDao.delModel(modelId);
			if(count<=0){
				return MsgUtils.fillMsg(MsgEnum.MODEL_DELETE_FAIL);
			}
		}catch(Exception e){
			log.error("del_model_fails",e);
			return MsgUtils.fillMsg(MsgEnum.MODEL_DELETE_FAIL);
		}
		//success
		return MsgUtils.fillModule(null);
		
	}
	
	public ServiceResult queryModels(PlcModelQuery query){
		List<PlcModel> models = null;
		try{
			
			models = modelDao.queryPlcModel(query);
		}catch(Exception e){
			log.error("query_model_fails",e);
			return MsgUtils.fillMsg(MsgEnum.MODEL_QUERY_FAIL);
		}
		//success
		return MsgUtils.fillModule(models);
	}
	
	public ServiceResult totalCount(PlcModelQuery query){
		int count = 0;
		try{
			count = modelDao.totalCount(query);
		}catch(Exception e){
			log.error("query_model_total_count_fails",e);
			return MsgUtils.fillMsg(MsgEnum.MODEL_QUERY_FAIL);
		}
		
		return MsgUtils.fillModule(count);
	}
	
}
