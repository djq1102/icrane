/**
 * 
 */
package com.monitor.app.service;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.monitor.app.dao.model.ModelVarDao;
import com.monitor.app.dataobject.ModelVar;
import com.monitor.app.query.PlcModelVarQuery;
import com.monitor.app.result.ServiceResult;
import com.monitor.app.result.msg.MsgEnum;
import com.monitor.app.utils.MsgUtils;

/**
 * @author ibm
 *
 */
@Service
public class VariableService {

	private static final Logger log = LoggerFactory.getLogger(VariableService.class);
	
	@Resource
	private ModelVarDao modelVarDao;
	
	public ServiceResult addPlcModelVar(ModelVar modelVar){
		try{
			int count = modelVarDao.addPlcModelVar(modelVar);
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
	
	public ServiceResult updatePlcModelVar(ModelVar modelVar){
		try{
			int count = modelVarDao.updatePlcModelVar(modelVar);
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
	
	public ServiceResult delPlcModelVar(long varId){
		try{
			int count = modelVarDao.delPlcModelVar(varId);
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
	
	public ServiceResult queryPlcModelVar(PlcModelVarQuery query){
		List<ModelVar> vars = null;
		try{
			vars = modelVarDao.queryPlcModelVar(query);
			
		}catch(Exception e){
			log.error("query_model_var_fails",e);
			return MsgUtils.fillMsg(MsgEnum.MODEL_VAR_QUERY_FAIL);
		}
		//success
		return MsgUtils.fillModule(vars);
	}

	public ServiceResult totalCount(PlcModelVarQuery query){
		int count = 0;
		try{
			count = modelVarDao.totalCount(query);
		}catch(Exception e){
			log.error("query_model_total_count_fails",e);
			return MsgUtils.fillMsg(MsgEnum.MODEL_QUERY_FAIL);
		}
		
		return MsgUtils.fillModule(count);
	}
	
}
