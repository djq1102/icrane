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
				return MsgUtils.fillMsg(MsgEnum.MODEL_VAR_ADD_FAIL);
			}
		}catch(Exception e){
			log.error("add_model_var_fails",e);
			return MsgUtils.fillMsg(MsgEnum.MODEL_VAR_ADD_FAIL);
		}
		//success
		return MsgUtils.fillModule(null);
	}
	
	public ServiceResult updateModel(PlcModel plcModel){
		try{
			int count = modelDao.updateModel(plcModel);
			if(count<=0){
				return MsgUtils.fillMsg(MsgEnum.MODEL_VAR_UPDATE_FAIL);
			}
		}catch(Exception e){
			log.error("update_model_var_fails",e);
			return MsgUtils.fillMsg(MsgEnum.MODEL_VAR_UPDATE_FAIL);
		}
		//success
		return MsgUtils.fillModule(null);
		
	}
	
	public ServiceResult delModel(long modelId){
		try{
			int count = modelDao.delModel(modelId);
			if(count<=0){
				return MsgUtils.fillMsg(MsgEnum.MODEL_VAR_DELETE_FAIL);
			}
		}catch(Exception e){
			log.error("del_model_var_fails",e);
			return MsgUtils.fillMsg(MsgEnum.MODEL_VAR_DELETE_FAIL);
		}
		//success
		return MsgUtils.fillModule(null);
		
	}
	
	public ServiceResult queryModels(String modelName){
		List<PlcModel> models = null;
		try{
			models = modelDao.queryByModelName(modelName);
			
		}catch(Exception e){
			log.error("query_model_var_fails",e);
			return MsgUtils.fillMsg(MsgEnum.MODEL_VAR_QUERY_FAIL);
		}
		
		//success
		return MsgUtils.fillModule(models);
	}
	
}
