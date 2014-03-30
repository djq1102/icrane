/**
 * 
 */
package com.monitor.app.controller.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.monitor.app.controller.AbstractController;
import com.monitor.app.dataobject.PlcModel;
import com.monitor.app.query.PlcModelQuery;
import com.monitor.app.result.ServiceResult;
import com.monitor.app.service.ModelService;
import com.monitor.app.utils.MsgUtils;

/**
 * @author ibm
 *
 */
@Controller
public class ModelController extends AbstractController{

	@Resource
	private ModelService modelService;
	
	@RequestMapping(value = "/model/index")
	public String index( PlcModel plcModel,Model model) throws Exception{
		
		model.addAttribute("modelId", plcModel.getModelId());
		model.addAttribute("modelName", plcModel.getModelName());
		return "plcModel/plcModel";
	}
	
	@RequestMapping(value = "/model/toAdd")
	public String toAdd(Model model) throws Exception{
		
		return "plcModel/add";
	}
	
	@RequestMapping(value = "/model/add")
	public String add(@RequestParam("modelName") String name, @RequestParam("sensorType") int sensorType, 
			@RequestParam("ioType") int ioType, Model model) throws Exception{
		
		PlcModel plcModel = buildPlcModel(name, sensorType, ioType);
		
		ServiceResult result = modelService.addModel(plcModel);
		
		if(result.isSuccess()){
			model.addAttribute("msg", MsgUtils.MSG_SUCCESS);
		}else{
			model.addAttribute("msg",result.getMsg());
		}
		return "plcModel/plcModel";
	}

	private PlcModel buildPlcModel(String name, int sensorType, int ioType) {
		PlcModel plcModel = new PlcModel();
		plcModel.setModelName(name);
		plcModel.setSensorType(sensorType);
		plcModel.setIoType(ioType);
		return plcModel;
	}

	@RequestMapping(value = "/model/query",method = RequestMethod.GET,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String query(@RequestParam("modelId") int modelId, @RequestParam("modelName") String modelName,
			@RequestParam("iDisplayStart") int start, @RequestParam("iDisplayLength") int pagesize,
			@RequestParam("sEcho") int sEcho, Model model) throws Exception{
		
		PlcModelQuery query = new PlcModelQuery();
		query.setModelId(modelId);
		query.setModelName(modelName);
		query.setBegin(start);
		query.setEnd(start+pagesize);
		
		ServiceResult dataResult = modelService.queryModels(query);
		ServiceResult countResult = modelService.totalCount(query);
		if(dataResult.isSuccess() && dataResult.getModule()!=null&& countResult.isSuccess()){
			List<PlcModel> plcModels = (List<PlcModel>)dataResult.getModule();
			List<Map> resultMap = buildList(plcModels);
			long total = Long.parseLong(countResult.getModule().toString());
			return outputJsonAsResponse(resultMap, total , sEcho);
		}
		
		return outputJsonAsResponse(Collections.EMPTY_LIST, 0 , sEcho);
	}
	
	@RequestMapping(value = "/model/update")
	public String update(@RequestParam("modelId") int modelId, @RequestParam("modelName") String modelName,
			@RequestParam("sensorType") int sensorType, @RequestParam("ioType") int ioType, 
			Model model) throws Exception{
		PlcModel plcModel = buildPlcModel(modelName, sensorType, ioType);
		plcModel.setModelId(modelId);
		
		ServiceResult result = modelService.updateModel(plcModel);
		if(result.isSuccess()){
			model.addAttribute("msg", MsgUtils.MSG_SUCCESS);
		}else{
			model.addAttribute("msg",MsgUtils.MSG_FAIL);
		}
		return "plcModel/plcModel";
	}
	
	@RequestMapping(value = "/model/edit")
	public String edit(@RequestParam("modelId") int modelId, Model model) throws Exception{
		ServiceResult result = modelService.queryModel(modelId);
		if(!result.isSuccess()){
			model.addAttribute("msg", result.getMsg());
			return "plcModel/plcModel";
		}
		
		model.addAttribute("plcModel", result.getModule());
		return "plcModel/edit";
	}
	
	@RequestMapping(value = "/model/del")
	public String del(@RequestParam("modelId") int modelId, Model model) throws Exception{
		ServiceResult result = modelService.delModel(modelId);
		if(!result.isSuccess()){
			model.addAttribute("msg", result.getMsg());
		}
		
		return "plcModel/plcModel";
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<Map> buildList(List<PlcModel> modelList){
		List<Map> result = new ArrayList<Map>();
		for(PlcModel plc:modelList){
			Map map = new HashMap();
			map.put("0", plc.getModelId());
			map.put("1", plc.getModelName());
			map.put("2", plc.getSensorType());
			map.put("3", plc.getIoType());
			map.put("4", plc.getModelId());
			result.add(map);
		}
		return result;
	}
}
