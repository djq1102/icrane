/**
 * 
 */
package com.monitor.app.controller.model;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.monitor.app.dataobject.PlcModel;
import com.monitor.app.result.ServiceResult;
import com.monitor.app.service.ModelService;

/**
 * @author ibm
 *
 */
@Controller
public class ModelController {

	@Resource
	private ModelService modelService;
	
	@RequestMapping(value = "/model/index")
	public String index( Model model) throws Exception{
		
		
		return "plcModel/edit_input";
	}
	
	@RequestMapping(value = "/model/add")
	public String add(@RequestParam("name") String name, @RequestParam("sensorType") int sensorType, 
			@RequestParam("ioType") int ioType, Model model) throws Exception{
		
		PlcModel plcModel = buildPlcModel(name, sensorType, ioType);
		
		ServiceResult result = modelService.addModel(plcModel);
		
		
		return "plcModel/";
	}

	private PlcModel buildPlcModel(String name, int sensorType, int ioType) {
		PlcModel plcModel = new PlcModel();
		plcModel.setModelName(name);
		plcModel.setSensorType(sensorType);
		plcModel.setIoType(ioType);
		return plcModel;
	}

	@RequestMapping(value = "/model/update")
	public String update(@RequestParam("modelId") int modelId, @RequestParam("name") String name,
			@RequestParam("sensorType") int sensorType, @RequestParam("ioType") int ioType, 
			Model model) throws Exception{
		PlcModel plcModel = buildPlcModel(name, sensorType, ioType);
		plcModel.setModelId(modelId);
		
		ServiceResult result = modelService.updateModel(plcModel);
		return "";
	}
	
	@RequestMapping(value = "/model/del")
	public String del(@RequestParam("modelId") int modelId, Model model) throws Exception{
		ServiceResult result = modelService.delModel(modelId);
		
		return "";
	}
	
	@RequestMapping(value = "/model/query")
	public String query(@RequestParam("modelName") String modelName, Model model) throws Exception{
		ServiceResult result = modelService.queryModels(modelName);
		
		
		return "";
	}
	
}
